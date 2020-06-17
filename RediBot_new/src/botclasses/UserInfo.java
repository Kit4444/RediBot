package botclasses;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserInfo extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "userinfo") || args[0].equalsIgnoreCase(Main.botprefix + "user")) {
				chan.sendMessage("<:deny:597877001264824320> Usage: " + Main.botprefix + "userinfo <ID>").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "userinfo") || args[0].equalsIgnoreCase(Main.botprefix + "user")) {
				EmbedBuilder eb = new EmbedBuilder();
				SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
		        String stime = time.format(new Date());
		        eb.setFooter("Requested from: " + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + " at " + stime, e.getAuthor().getAvatarUrl());
				eb.setAuthor("RediCraft", "http://www.redicraft.eu/");
				try {
					int id = Integer.parseInt(args[1]);
					eb.setTitle("Usersearch by ID: " + id);
					HashMap<String, Object> hm = new HashMap<>();
					hm.put("userid", id);
					if(Main.mysql.isInDatabase("redicore_userstats", hm)) {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE userid = ?");
						ps.setInt(1, id);
						ResultSet rs = ps.executeQuery();
						rs.next();
						eb.addField("Name:", rs.getString("username"), false);
						eb.addField("UUID:", rs.getString("uuid"), false);
						eb.setThumbnail("https://minotar.net/armor/body/" + rs.getString("uuid") + "/512.png");
						eb.addField("Money:", "User " + retMoney(rs.getString("uuid_ut"), "money") + " Coins \nBank: " + retMoney(rs.getString("uuid_ut"), "bankmoney") + " Coins", false);
						eb.addField("First Server Join:", rs.getString("firstjoinstring"), false);
						if(rs.getBoolean("isstaff")) {
							eb.addField("Is Staff:", "yes", false);
							eb.addField("Rank:", rs.getString("userrank"), false);
							if(rs.getBoolean("loggedin")) {
								eb.addField("Is logged in:", "no", false);
							}else {
								eb.addField("Is logged in:", "yes", false);
							}
						}else {
							eb.addField("Is Staff:", "no", false);
							eb.addField("Rank:", rs.getString("userrank"), false);
							if(rs.getBoolean("loggedin")) {
								eb.addField("Has hidden their group", "no", false);
							}else {
								eb.addField("Has hidden their group", "yes", false);
							}
						}
						if(rs.getBoolean("online")) {
							eb.addField("Online:", "yes", false);
							eb.addField("Current Server:", rs.getString("server"), false);
							if(rs.getBoolean("afk")) {
								eb.addField("Is AFK:", "yes", false);
							}else {
								eb.addField("Is AFK:", "no", false);
							}
						}else {
							eb.addField("Online:", "no", false);
							eb.addField("Last server:", rs.getString("server"), false);
							eb.addField("Last Join:", rs.getString("lastjoinstring"), false);
						}
						eb.setColor(e.getMember().getColor());
						if(rs.getString("userprefix_ncc").equalsIgnoreCase("RESET")) {
							eb.addField("Custom ID:", rs.getInt("userid") + "", false);
						}else {
							eb.addField("Custom ID:", rs.getInt("userid") + " " + rs.getString("userprefix_ncc"), false);
						}
						String lang = rs.getString("language");
						switch(lang) {
						case "de-de": eb.addField("Language: ", "German", false); break;
						case "en-uk": eb.addField("Language: ", "English", false); break;
						}
					}else {
						eb.setColor(Color.decode("#FF5555"));
						eb.addField("Error:", "This ID can't be found in our system.", false);
						eb.setThumbnail("https://minotar.net/armor/body/Steve/256.png");
					}
				}catch (SQLException e1) {
					eb.setColor(Color.decode("#AA0000"));
					eb.addField("Error:", "An error has occured.", false);
					eb.setThumbnail("https://minotar.net/armor/body/Steve/256.png");
				}
				chan.sendMessage(eb.build()).queue();
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "userinfo")) {
				chan.sendMessage("<:deny:597877001264824320> Usage: " + Main.botprefix + "userinfo <ID>").queue();
			}
		}
	}
	
	private int retMoney(String uuid, String type) throws SQLException {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_money WHERE uuid_ut = ?");
		ps.setString(1, uuid);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(type);
	}

}