package at.mlps.main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.rc.mysql.lpb.MySQL;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class Main implements EventListener{
	
	public static Main instance;
	public static MySQL mysql;
	
	public static String botprefix = "rb!";

	public static void main(String[] args) {
		YamlFile file = new YamlFile("configs/configuration.yml");
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
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
			onLog(1, "Loaded Config file");
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
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
		file.addDefault("BotConfig.Bootlogo", 0);
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
		YamlFile file = new YamlFile("configs/configuration.yml");
		file.load();
		JDABuilder builder = JDABuilder.createDefault(file.getString("BotConfig.Bottoken"));
		Manager man = new Manager();
		man.init(builder);
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