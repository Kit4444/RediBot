package at.mlps.botclasses.guildlogging.guild;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLogEvents extends ListenerAdapter{
	
	public Color darkred = Color.decode("#AA0000");
	public Color red = Color.decode("#FF5555");
	public Color green = Color.decode("#55FF55");
	public Color orange = Color.decode("#FFAA00");
	
	long redimain = 548136727697555496L;
	long redibotlog = 637362851245195264L;

	public long rediassetg = 671772592390144061L;
	public long rediassetlog = 709379395428679681L;
	
	public void insertMsg(long guildid, long msgid, String msgtxt, long memberid, boolean botmsg) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_msglog (guildid, msgid, originalText, lastUpdatedText, memberid, botmsg) VALUES (?, ?, ?, ?, ?, ?)");
			ps.setLong(1, guildid);
			ps.setLong(2, msgid);
			ps.setString(3, msgtxt);
			ps.setString(4, msgtxt);
			ps.setLong(5, memberid);
			ps.setBoolean(6, botmsg);
			ps.executeUpdate();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isBotMessage(long guildid, long msgid) {
		boolean boo = false;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		hm.put("msgid", msgid);
		try {
			if(Main.mysql.isInDatabase("redibot_msglog", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_msglog WHERE guildid = ? AND msgid = ?");
				ps.setLong(1, guildid);
				ps.setLong(2, msgid);
				ResultSet rs = ps.executeQuery();
				rs.next();
				boo = rs.getBoolean("botmsg");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return boo;
	}
	
	@Nullable
	public String returnOriginalMessage(long guildid, long msgid) {
		String msg = "";
		PreparedStatement ps;
		try {
			ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_msglog WHERE guildid = ? AND msgid = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, msgid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				msg = rs.getString("originalText");
			}else {
				msg = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	@Nullable
	public String returnLastUpdatedMessage(long guildid, long msgid) {
		String msg = "";
		PreparedStatement ps;
		try {
			ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_msglog WHERE guildid = ? AND msgid = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, msgid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				msg = rs.getString("lastUpdatedText");
			}else {
				msg = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	@Nonnull
	public void updateLastUpdatedMessage(long guildid, long msgid, String obfuscatedText) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_msglog SET lastUpdatedText = ? WHERE guildid = ? AND msgid = ?");
			ps.setString(1, obfuscatedText);
			ps.setLong(2, guildid);
			ps.setLong(3, msgid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public long retMID(long guildid, long msgid) {
		long id = 0L;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_msglog WHERE guildid = ? AND msgid = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, msgid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getLong("memberid");
			}else {
				id = 0;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void sendWelcome(EmbedBuilder eb, Guild g) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog");
			ResultSet rs = ps.executeQuery();
			long guildid = g.getIdLong();
			while(rs.next()) {
				if(guildid == rs.getLong("guildid")) {
					long channelid = rs.getLong("welcomechannel");
					g.getTextChannelById(channelid).sendMessageEmbeds(eb.build()).queue();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static int sentLogs = 0;
	static int totalLogs = 0;
	
	public void sendMsg(EmbedBuilder eb, Guild g) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog");
			ResultSet rs = ps.executeQuery();
			long guildid = g.getIdLong();
			while(rs.next()) {
				long saveguildid = rs.getLong("guildid");
				long savetxtchan = rs.getLong("channelid");
				if(guildid == saveguildid) {
					TextChannel chan = g.getTextChannelById(savetxtchan);
					chan.sendMessageEmbeds(eb.build()).queue();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean enabledLog(Guild g, String key) {
		boolean log = false;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", g.getIdLong());
		try {
			if(Main.mysql.isInDatabase("redibot_guildlog", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
				ps.setLong(1, g.getIdLong());
				ResultSet rs = ps.executeQuery();
				rs.next();
				log = rs.getBoolean(key);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return log;
	}
	
	public boolean isRegistered(long guildid) {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		try {
			if(Main.mysql.isInDatabase("redibot_guildlog", hm)) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String hexCol(int color) {
		return String.format("#%06X", (0xFFFFFF & color)).toLowerCase();
	}
	
	public String retDate(OffsetDateTime odt) {
		return odt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss"));
	}
	
	public String getFormattedDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	public void welcCon() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss a z");
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		int bootscreen = file.getInt("BotConfig.Bootlogo");
		if(bootscreen == 0) {
			System.out.println("####################################");
			System.out.println("#                                  #");
			System.out.println("# ###  #### ###  # ###   ##  ##### #");
			System.out.println("# #  # #    #  # # #  # #  #   #   #");
			System.out.println("# #  # #    #  # # #  # #  #   #   #");
			System.out.println("# ###  ###  #  # # ###  #  #   #   #");
			System.out.println("# # #  #    #  # # #  # #  #   #   #");
			System.out.println("# #  # #    #  # # #  # #  #   #   #");
			System.out.println("# #  # #### ###  # ###   ##    #   #");
			System.out.println("#                                  #");
			System.out.println("####################################");
		}else if(bootscreen == 1) {
			System.out.println("###################################");
			System.out.println("#                                 #");
			System.out.println("# ###          #   ###         #  #");
			System.out.println("# #  #         # # #  #        #  #");
			System.out.println("# #  #         #   #  #        #  #");
			System.out.println("# ###   ##   ### # ###   ###  ### #");
			System.out.println("# #  # #  # #  # # #  # #   #  #  #");
			System.out.println("# #  # ###  #  # # #  # #   #  #  #");
			System.out.println("# #  # #    #  # # #  # #   #  #  #");
			System.out.println("# #  #  ###  ### # ###   ###    # #");
			System.out.println("#                                 #");
			System.out.println("###################################");
		}
		System.out.print("RediBot has been loaded.\nVersion: " + file.getString("BotInfo.version") + "\nJavaversion: " + System.getProperty("java.version") + "\nJDA-Version: " + JDAInfo.VERSION + "\nSystemtime: " + sdf.format(new Date()) + "\n");
	}
}