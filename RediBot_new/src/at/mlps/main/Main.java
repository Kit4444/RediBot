package at.mlps.main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.botclasses.commands.FAQ;
import at.mlps.botclasses.commands.LOA;
import at.mlps.botclasses.commands.Punishments;
import at.mlps.botclasses.commands.PurgeCommand;
import at.mlps.botclasses.commands.RegisterGuilds;
import at.mlps.botclasses.commands.Serverinfo;
import at.mlps.botclasses.commands.SetStatesCMD;
import at.mlps.botclasses.commands.UserCommands;
import at.mlps.botclasses.commands.UserInfo;
import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import at.mlps.rc.mysql.lpb.MySQL;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main implements EventListener{
	
	public static String host = "207.180.202.73";
	public static String port = "3306";
	public static String DB = "RediCraft";
	public static String user = "mauriceb";
	public static String pw = "MauriceB2400";
	public static Main instance;
	public static MySQL mysql;
	
	public static String botprefix = "rb!";

	public static void main(String[] args) {
		YamlFile file = new YamlFile("configuration.yml");
		if(!file.exists()) {
			try {
				file.createNewFile(true);
				System.out.println("Config Files Created.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				file.load();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setCFG();
		String host = file.getString("MySQL.Host");
		int port = file.getInt("MySQL.Port");
		String db = file.getString("MySQL.Database");
		String user = file.getString("MySQL.Username");
		String pass = file.getString("MySQL.Password");
		try {
			at.mlps.rc.mysql.lb.MySQL.connect(host, port, db, user, pass);
			mysql = new MySQL(host, port, db, user, pass);
			mysql.connect();
			onLog(1, "Connected to database.");
			System.out.println("Connected to DB");
		} catch (ClassNotFoundException e) {
			onLog(3, "An error occured. Error: " + e);
		} catch (SQLException e) {
			onLog(3, "An error occured while connecting to the database. Error: " + e);
		}
		try {
			startBot();
		} catch (InvalidConfigurationException | IOException | LoginException e) {
			e.printStackTrace();
		}
	}
	
	static void setCFG() {
		YamlFile file = new YamlFile("configuration.yml");
		try {
			file.load();
			onLog(1, "Loaded Config file");
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String stime = time.format(new Date());
		file.addDefault("BotConfig.Activity.Type", "PLAYING");
		file.addDefault("BotConfig.Activity.Text", "Being a baby.");
		file.addDefault("BotConfig.Activity.StreamURL", "https://www.youtube.com/channel/UCBJhuPBSaucwk_TthujYrBw");
		file.addDefault("BotConfig.Activity.Onlinestatus", "ONLINE");
		file.addDefault("BotInfo.version", "19700101_v0.0.0abs");
		file.addDefault("MySQL.Host", "localhost");
		file.addDefault("MySQL.Port", 3306);
		file.addDefault("MySQL.Database", "database");
		file.addDefault("MySQL.Username", "username");
		file.addDefault("MySQL.Password", "password");
		file.addDefault("BotConfig.Bottoken", "the bottoken goes here");
		file.set("BotInfo.online.ts", System.currentTimeMillis());
		file.set("BotInfo.online.string", stime);
		file.options().copyDefaults(true);
		try {
			file.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void startBot() throws InvalidConfigurationException, IOException, LoginException {
		YamlFile file = new YamlFile("configuration.yml");
		file.load();
		JDABuilder builder = JDABuilder.createDefault(file.getString("BotConfig.Bottoken"));
		if(at.mlps.rc.mysql.lb.MySQL.isConnected()) {
			if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("PLAYING")) {
				builder.setActivity(Activity.playing(file.getString("BotConfig.Activity.Text")));
			}else if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("WATCHING")) {
				builder.setActivity(Activity.watching(file.getString("BotConfig.Activity.Text")));
			}else if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("LISTENING")) {
				builder.setActivity(Activity.listening(file.getString("BotConfig.Activity.Text")));
			}else if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("STREAMING")) {
				builder.setActivity(Activity.streaming(file.getString("BotConfig.Activity.Text"), file.getString("BotConfig.Activity.StreamURL")));
			}
			if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("ONLINE")) {
				builder.setStatus(OnlineStatus.ONLINE);
			}else if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("IDLE")) {
				builder.setStatus(OnlineStatus.IDLE);
			}else if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("DONOTDISTURB")) {
				builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
			}else if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("OFFLINE")) {
				builder.setStatus(OnlineStatus.OFFLINE);
			}
		}else {
			builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
			builder.setActivity(Activity.watching("the DB connection"));
		}
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		builder.enableIntents(GatewayIntent.GUILD_BANS);
		builder.enableIntents(GatewayIntent.GUILD_EMOJIS);
		builder.enableIntents(GatewayIntent.GUILD_INVITES);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
		builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
		builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGES);
		builder.setMemberCachePolicy(MemberCachePolicy.ALL);
		builder.addEventListeners(new LOA());
		builder.addEventListeners(new PurgeCommand());
		builder.addEventListeners(new Serverinfo());
		builder.addEventListeners(new SetStatesCMD());
		builder.addEventListeners(new UserCommands());
		builder.addEventListeners(new GuildLogEvents());
		builder.addEventListeners(new FAQ());
		builder.addEventListeners(new UserInfo());
		builder.addEventListeners(new Punishments());
		builder.addEventListeners(new RegisterGuilds());
		builder.build();
	}
	
	@Override
	public void onEvent(GenericEvent arg0) {
	}
	
	public static void onLog(int log, String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		if(log == 1) { //info
			System.out.println(sdf.format(new Date()) + " [INFO] " + msg);
		}else if(log == 2) { //warning
			System.out.println(sdf.format(new Date()) + " [WARNING] " + msg);
		}else if(log == 3) { //critical
			System.out.println(sdf.format(new Date()) + " [CRITICAL] " + msg);
		}else {
			System.out.println(sdf.format(new Date()) + " | " + msg);
		}
	}
}