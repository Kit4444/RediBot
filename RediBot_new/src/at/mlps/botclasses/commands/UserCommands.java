package at.mlps.botclasses.commands;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserCommands extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		System.gc();
		String cont = e.getMessage().getContentRaw();
		MessageChannel chan = e.getChannel();
		if(cont.equalsIgnoreCase(Main.botprefix + "ping")) {
			long time = System.currentTimeMillis();
			chan.sendMessage("Pong!").queue(response -> {
				response.editMessageFormat("Pong! ``%d ms`` Gatewayping: ``%d ms``", System.currentTimeMillis() - time, e.getJDA().getGatewayPing()).queue();
			});
		}else if(cont.equalsIgnoreCase(Main.botprefix + "botinfo")) {
			YamlFile file = new YamlFile("configuration.yml");
			try {
				file.load();
			} catch (InvalidConfigurationException | IOException e1) {
				e1.printStackTrace();
			}
			SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
			String stime = time.format(new Date());
			Runtime run = Runtime.getRuntime();
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(e.getMember().getColor());
			String ver = file.getString("BotInfo.version");
			eb.setTitle("Botinfo");
			eb.addField("JDA-Version:", "JDA " + JDAInfo.VERSION, false);
			eb.addField("Java-Version:", System.getProperty("java.version"), false);
			eb.addField("Bot-Version:", ver, false);
			long difftime = (System.currentTimeMillis() - file.getLong("BotInfo.online.ts"));
			eb.addField("Online since:", file.getString("BotInfo.online.string") + " or \n" + retPTime(difftime), false);
			eb.addField("Systemtime: ", stime, false);
			if(ver.equalsIgnoreCase(retVer())) {
				eb.addField("Info", "The bot is on the latest version!", false);
			}else {
				eb.addField("Info", "The bot is not on the latest version!", false);
			}
			eb.addField("Guilds:", "Total: " + e.getJDA().getGuilds().size() + ", Registered: " + returnRegisteredGuilds(), false);
			int i = 0;
			for(Guild g : e.getJDA().getGuilds()) {
				for(@SuppressWarnings("unused") Member m : g.getMembers()) {
					i++;
				}
			}
			eb.addField("Members:", "Serving " + i + " Members", true);
			eb.addField("Ram", "Usage: " + String.valueOf((run.totalMemory() - run.freeMemory()) / 1048576L) + "mb / Allocated: " + String.valueOf(run.totalMemory() / 1048576L) + "mb", false);
			if(MySQL.isConnected()) {
				eb.addField("DB Connection:", "connected", true);
			}else {
				eb.addField("DB Connection:", "not connected", true);
			}
			chan.sendMessage(eb.build()).queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "aua")) {
			chan.sendMessage("Schlechter Witz <:picardfacepalm:555680906199826432>").queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "furry")) {
			chan.sendMessage("Du wurdest soeben von Inferno geknuddelt <:blep:572536387241246720>").queue();
		}else if(cont.equalsIgnoreCase("moin") || cont.equalsIgnoreCase("moino") || cont.equalsIgnoreCase("hey") || cont.equalsIgnoreCase("hi") || cont.equalsIgnoreCase("grüzli")) {
			e.getMessage().addReaction("a:catwave:555680908490047489").queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "avatar")) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(e.getMember().getColor());
			eb.setTitle("Click me if you don't see your avatar.", e.getMember().getUser().getAvatarUrl());
			eb.setDescription("This is " + e.getAuthor().getAsMention() + " 's avatar.");
			eb.setImage(e.getMember().getUser().getAvatarUrl());
			chan.sendMessage(eb.build()).queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "makemeacheesesandwich")) {
			chan.sendMessage("Here you are! <:cheesesandwich:708988688486629376>").queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "about")) {
			EmbedBuilder eb = new EmbedBuilder();
			YamlFile file = new YamlFile("configuration.yml");
			try {
				file.load();
			} catch (InvalidConfigurationException | IOException e1) {
				e1.printStackTrace();
			}
			User u = e.getJDA().getUserById(228145889988837385L);
			eb.setColor(Color.WHITE);
			eb.setTitle("RediCraft", "http://www.redicraft.eu/");
			eb.addField("Version", file.getString("BotInfo.version"), true);
			eb.addField("Developed by", u.getName(), true);
			eb.addField("Bot's Owner", u.getName() + "#" + u.getDiscriminator(), false);
			chan.sendMessage(eb.build()).queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "changelog")) {
			EmbedBuilder eb = new EmbedBuilder();
			YamlFile file = new YamlFile("configuration.yml");
			try {
				file.load();
			} catch (InvalidConfigurationException | IOException e1) {
				e1.printStackTrace();
			}
			eb.setDescription("Current Botversion: " + file.getString("BotInfo.version"));
			//eb.addField("dd.MM.yyyy", "Annotation", false);
			eb.setColor(Color.green);
			eb.addField("01.10.2020", "- updated JDA Minorversion from 202 to 207\n- MySQL-Connection will stay now on keepAlive\n- optimized the GC\n- implemented a settings command and the settings to control the bot\n- when purging messages, a small chatlog will be sent and deleted afterwards\n- changed the stream announcements embed\n- changed the announcements embed\n- on the GuildMemberRemoveEvent the roles will be displayed what the user had before leaving - Joindate will be given from API and not anymore from config\n- Roles will be mentioned now in [p]serverinfo and [p]whois\n- prepared DB for Chatlevel-System", false);
			eb.addField("06.09.2020", "- Added Attachment-Support for Messagelogging\n- Reworked the Announcement-Feature, roles can be pinged, is not a must.\n- Disabled Moneyview for [p]user \n- added some more infos on [p]whois Command \nChanged JDA-Build 202\n- Added the detailed online-since feature on [p]botinfo", false);
			eb.addField("19.08.2020", "- Added an Announcement-Command\n- fixed again few bugs\n- added Playtime in [p]user - Command", false);
			eb.addField("15.08.2020", "- Added a version-checker\n- extended the [p]help - Command\n- fixed few bugs\n- added for RC the Voter in discord suggestions", false);
			eb.addField("14.08.2020", "- Reworked some Guildlogging-Messages, so it's more conform to the correct spelling\n- Added Gatewayping on [p]ping\n- Changed JDA-Build to 191", false);
			eb.addField("20.07.2020", "- Added [p]stream command\n- edited a small row in the RC-Rules\n- Changed some backend code", false);
			eb.addField("12.07.2020", "- Changed JDA-Version to 4.2.0_177\n- Messagelogging has been reworked and messages are now encrypted\n- added new tags", false);
			//eb.addField("04.07.2020", "- Changed JDA-Version to Release 4.2.0_168\n- Reworked the Messagelogging (for Updating&Deleting) - Bot's messages won't be displayed anymore\n- some backend and frontend changes.", false);
			//eb.addField("21.06.2020", "- Added Discord's new Gateway Intents\n- added new events for the registered guildlogging\n- removed the Sleeps in the FAQ and Ruleset-Thread\n- changed something in the rules §9", false);
			//eb.addField("15.06.2020", "- Changed JDA-Version from build 101 to 165\n- added the [p]changelog command\n- changed some backend code", false);
			chan.sendMessage(eb.build()).queue();
		}else if(cont.equalsIgnoreCase(Main.botprefix + "911")) {
			chan.sendMessage("Hello, how can I help you?").queue();
		}
	}
	
	private int returnRegisteredGuilds() {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				i++;
			}
		}catch (SQLException e) { }
		return i;
	}
	
	private String retVer() {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_versions WHERE type = ?");
			ps.setString(1, "bot");
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString("version");
		}catch (SQLException e) {
			return "ERROR";
		}
	}
	
	private String retPTime(long time) {
		long tst = time / 1000;
		long seconds = tst;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		long weeks = 0;
		while(seconds > 60) {
			seconds -= 60;
			minutes++;
		}
		while(minutes > 60) {
			minutes -= 60;
			hours++;
		}
		while(hours > 24) {
			hours -= 24;
			days++;
		}
		while(days > 7) {
			days -= 7;
			weeks++;
		}
		return "Weeks: " + weeks + ", Days: " + days + ", Hours: " + hours + ", Minutes: " + minutes + ", Seconds: " + seconds;
	}
}