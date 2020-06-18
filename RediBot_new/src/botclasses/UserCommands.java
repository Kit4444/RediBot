package botclasses;

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
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserCommands extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String cont = e.getMessage().getContentRaw();
		MessageChannel chan = e.getChannel();
		if(cont.equalsIgnoreCase(Main.botprefix + "ping")) {
			long time = System.currentTimeMillis();
			chan.sendMessage("Pong!").queue(response -> {
				response.editMessageFormat("Pong! ``%d ms``", System.currentTimeMillis() - time).queue();
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
			eb.setTitle("Botinfo");
			eb.addField("JDA-Version:", "JDA " + JDAInfo.VERSION, false);
			eb.addField("Java-Version:", System.getProperty("java.version"), false);
			eb.addField("Bot-Version:", file.getString("BotInfo.version"), false);
			eb.addField("Online since:", file.getString("BotInfo.online.string"), false);
			eb.addField("Systemtime: ", stime, false);
			eb.addField("Guilds:", "Total: " + e.getJDA().getGuilds().size() + ", Registered: " + returnRegisteredGuilds(), false);
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
		}else if(cont.equalsIgnoreCase(Main.botprefix + "botcommands") || cont.equalsIgnoreCase(Main.botprefix + "help")) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(e.getMember().getColor());
			eb.setTitle("Helplist for RediBot");
			eb.setDescription("All commands are listed in alphabetic order.");
			//eb.addField(Main.botprefix + "", "", false);
			eb.addField(Main.botprefix + "about", "Displays some infos who has written the bot.", false);
			eb.addField(Main.botprefix + "aua", "Fun command", false);
			eb.addField(Main.botprefix + "avatar", "Sends your avatar.", false);
			eb.addField(Main.botprefix + "ban", "Bans a member from the guild.", false);
			eb.addField(Main.botprefix + "botcommands", "Displays this embed \nAlias: " + Main.botprefix + "help", false);
			eb.addField(Main.botprefix + "botinfo", "Displays some informations regarding this bot.", false);
			eb.addField(Main.botprefix + "changelog", "Lists a changelog", false);
			eb.addField(Main.botprefix + "faq", "Admin Command", false);
			eb.addField(Main.botprefix + "furry", "Fun command", false);
			eb.addField(Main.botprefix + "guilds", "Displays a list where the bot is in", false);
			eb.addField(Main.botprefix + "kick", "Kicks a member from this guild.", false);
			eb.addField(Main.botprefix + "loa", "Staff command", false);
			eb.addField(Main.botprefix + "makemeacheesesandwich", "Fun command", false);
			eb.addField(Main.botprefix + "mute", "Removes all roles and mutes him in this way.", false);
			eb.addField(Main.botprefix + "ping", "See the bot's ping", false);
			eb.addField(Main.botprefix + "purge", "Mod Command, deletes up to 100 messages on one command.", false);
			eb.addField(Main.botprefix + "registerguild", "Botownercommand - registers a guild for guildlogging", false);
			eb.addField(Main.botprefix + "serverinfo", "Displays some informations regarding the guild where the command was sent in.\nThis is just for registered guilds available!", false);
			eb.addField(Main.botprefix + "setactivity", "Botownercommand", false);
			eb.addField(Main.botprefix + "setgame", "Botownercommand", false);
			eb.addField(Main.botprefix + "userinfo", "Displays some infos of your account what we stored.\nAlias: " + Main.botprefix + "user", false);
			eb.addField(Main.botprefix + "warn", "Warns a Member.", false);
			chan.sendMessage(eb.build()).queue();
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
			eb.setColor(Color.white);
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
			eb.addField("15.6.2020", "- Changed JDA-Version from build 101 to 165\n- added the [p]changelog command\n- changed some backend code", false);
			chan.sendMessage(eb.build()).queue();
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
}