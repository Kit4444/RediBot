package at.mlps.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.TimerTask;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class Runner extends TimerTask{
	
	static int timer = 0;
	
	public JDA api;
	public Runner(JDA api) {
		this.api = api;
	}

	@Override
	public void run() {
		String pl = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "name");
		String art = returnRadio1("https://api.laut.fm/station/redifm/current_song", "artist", "name");
		String tra = returnRadio("https://api.laut.fm/station/redifm/current_song", "title");
		String alb = returnRadio("https://api.laut.fm/station/redifm/current_song", "album");
		String listeners = returnRadioListeners();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redifm_current SET track = ?, artist = ?, album = ?, playlist = ?, current_listener = ?  WHERE id = ?");
			ps.setString(1, tra);
			ps.setString(2, art);
			ps.setString(3, alb);
			ps.setString(4, pl);
			ps.setString(5, listeners);
			ps.setInt(6, 1);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int code1 = random(0, 5000);
		int code2 = random(5001, 10000);
		int code3 = random(10001, 15000);
		int code4 = random(15001, 20000);
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE useless_testtable SET toupdate = ? WHERE type = ?");
			ps.setString(2, "bot_my1");
			String gcode1 = code1 + "-" + code2;
			ps.setString(1, gcode1);
			ps.executeUpdate();
			
			String gcode2 = code3 + "-" + code4;
			Main.mysql.update("UPDATE useless_testtable SET toupdate = '" + gcode2 + "' WHERE type = 'bot_my2';");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		timer++;
		if(timer == 1) {
			//redifm
			api.getPresence().setActivity(Activity.listening("RediFM | " + tra + " - " + art));
		}else if(timer == 2) {
			//watching
			int guilds = 0;
			int members = 0;
			for(Guild g : api.getGuilds()) {
				guilds++;
				for(@SuppressWarnings("unused") Member m : g.getMembers()) {
					members++;
				}
			}
			api.getPresence().setActivity(Activity.watching("over " + guilds + " Guilds and " + members + " Members"));
		}else if(timer == 3) {
			//defaultstate (cfg)
			timer = 0;
			YamlFile file = new YamlFile("configs/configuration.yml");
			try {
				file.load();
			} catch (InvalidConfigurationException | IOException e) {
				e.printStackTrace();
			}
			if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("PLAYING")) {
				api.getPresence().setActivity(Activity.playing(file.getString("BotConfig.Activity.Text")));
			}else if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("WATCHING")) {
				api.getPresence().setActivity(Activity.watching(file.getString("BotConfig.Activity.Text")));
			}else if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("LISTENING")) {
				api.getPresence().setActivity(Activity.listening(file.getString("BotConfig.Activity.Text")));
			}else if(file.getString("BotConfig.Activity.Type").equalsIgnoreCase("STREAMING")) {
				api.getPresence().setActivity(Activity.streaming(file.getString("BotConfig.Activity.Text"), file.getString("BotConfig.Activity.StreamURL")));
			}
			if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("ONLINE")) {
				api.getPresence().setStatus(OnlineStatus.ONLINE);
			}else if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("IDLE")) {
				api.getPresence().setStatus(OnlineStatus.IDLE);
			}else if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("DONOTDISTURB")) {
				api.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
			}else if(file.getString("BotConfig.Activity.Onlinestatus").equalsIgnoreCase("OFFLINE")) {
				api.getPresence().setStatus(OnlineStatus.OFFLINE);
			}
		}
	}
	
	int random(int low, int max) {
		Random r = new Random();
		int number = r.nextInt(max);
		while(number < low) {
			number = r.nextInt(max);
		}
		return number;
	}
	
	private static String returnRadio(String uri, String node) {
		String s = "";
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(uri);
			URLConnection urlc = url.openConnection();
			BufferedReader bR = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			String line;
			while ((line = bR.readLine()) != null) {
				content.append(line + "\n");
			}
			bR.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		String lortu = content.toString();
		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject)parser.parse(lortu);
		if(jo.get(node) == null) {
			s = "None";
		}else {
			s = (String) jo.get(node).toString();
		}
		return s.replace("\"", "");
	}
	
	private static String returnRadio1(String uri, String node, String subnode) {
		String s = "";
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(uri);
			URLConnection urlc = url.openConnection();
			BufferedReader bR = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			String line;
			while ((line = bR.readLine()) != null) {
				content.append(line + "\n");
			}
			bR.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		String lortu = content.toString();
		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject)parser.parse(lortu);
		if(jo.get(node) == null) {
			s = "None";
		}else {
			JsonObject sub = (JsonObject) jo.get(node);
			s = (String) sub.get(subnode).toString();
		}
		return s.replace("\"", "");
	}
	
	private static String returnRadioListeners() {
		String s = "";
		try {
			URL url = new URL("https://api.laut.fm/station/redifm/listeners");
			URLConnection urlc = url.openConnection();
			BufferedReader bR = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			s = bR.readLine();
			bR.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return s.replace("\"", "");
	}
}