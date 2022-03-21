package at.mlps.main;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.TimerTask;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class Runner extends TimerTask{
	
	static int timer = 3;
	
	public JDA api;
	public Runner(JDA api) {
		this.api = api;
	}

	@Override
	public void run() {
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
			api.getPresence().setActivity(Activity.listening("🎵 RediFM 🎵 | " + getRadioInfo("track") + " - " + getRadioInfo("artist")));
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
		}else if(timer == 4) {
			timer = 0;
			//christmas timer
			//Date set to 24th Dec 2021
			//Autoforward to 31th Dec 2021
			//times are always set to 00:00:00 in CET
			//1d = 86.400 s
			long dec24 = 1640300400;
			long dec24e = 1640386799;
			long dec31 = 1640905200;
			long dec31e = 1640991599;
			long current = System.currentTimeMillis() / 1000;
			String watch1 = "the days to";
			String watch2 = "";
			if(current <= dec24) {
				//before 24th dec
				watch2 = watch1 + " Christmas: " + getDays((dec24 - current));
			}else if(current >= dec24 && current <= dec24e) {
				//while 24th dec
				watch2 = "how players are enjoying christmas.";
			}else if(current <= dec31) {
				//before 31th dec
				watch2 = watch1 + " New Year: " + getDays((dec31 - current));
			}else if(current >= dec31 && current <= dec31e) {
				//while 31th dec
				watch2 = "how players are enjoying new year. Happy 2022!";
			}else {
				watch2 = "ERROR #119";
			}
			api.getPresence().setActivity(Activity.watching(watch2));
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
	
	private String getRadioInfo(String col) {
		String data = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redifm_current WHERE id = ?");
			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			rs.next();
			data = rs.getString(col);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	String getDays(long input) {
		long seconds = input;
		long minutes = 0;
		long hours = 0;
		long days = 0;
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
		String day = "";
		String hour = "";
		String minute = "";
		if(days < 10) {
			day = "0" + days;
		}else {
			day = "" + days;
		}
		if(hours < 10) {
			hour = "0" + hours;
		}else {
			hour = "" + hours;
		}
		if(minutes < 10) {
			minute = "0" + minutes;
		}else {
			minute = "" + minutes;
		}
		if(day.equalsIgnoreCase("00")) {
			return hour + ":" + minute + "h remaining";
		}else {
			return day + " Days, " + hour + ":" + minute + "h remaining";
		}
	}
}