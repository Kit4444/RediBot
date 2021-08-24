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
	
	static int timer = 0;
	
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
			//ad for SCINT
			timer = 0;
			api.getPresence().setActivity(Activity.watching("over Synergy Carriers International! Join today our VTC!"));
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
}