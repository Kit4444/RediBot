package at.mlps.botclasses.guildlogging.privat;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter{
	
	public void onReady(ReadyEvent e) {
		EmbedBuilder eb = new EmbedBuilder();
		GuildLogEvents gl = new GuildLogEvents();
		eb.setDescription("The bot is now online.");
		eb.setFooter("LOGLEVEL: INFO", "https://redicraft.eu/redianim.gif");
		eb.setColor(Color.green);
		e.getJDA().getGuildById(671772592390144061L).getTextChannelById(709379395428679681L).sendMessage(eb.build()).queue();
		gl.welcCon();
		
		EmbedBuilder ebwelc = new EmbedBuilder();
		ebwelc.setDescription("```####################################\n#                                  #\n# ###  #### ###  # ###   ##  ##### #\n# #  # #    #  # # #  # #  #   #   #\n# #  # #    #  # # #  # #  #   #   #\n# ###  ###  #  # # ###  #  #   #   #\n# # #  #    #  # # #  # #  #   #   #\n# #  # #    #  # # #  # #  #   #   #\n# #  # #### ###  # ###   ##    #   #\n#                                  #\n####################################```");
		YamlFile file = new YamlFile("configuration.yml");
		try {
			file.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
		String stime = time.format(new Date());
		ebwelc.setTitle("Bot is now online!");
		ebwelc.setFooter(stime);
		ebwelc.setColor(Color.magenta);
		ebwelc.addField("Botversion:", file.getString("BotInfo.version"), false);
		ebwelc.addField("Javaversion:", System.getProperty("java.version"), false);
		ebwelc.addField("JDA-Version:", JDAInfo.VERSION, false);
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				long saveguildid = rs.getLong("guildid");
				long savetxtchan = rs.getLong("channelid");
				for(Guild g : e.getJDA().getGuilds()) {
					if(g.getIdLong() == saveguildid) {
						//g.getTextChannelById(savetxtchan).sendMessage(ebwelc.build()).queue();
					}
				}
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}