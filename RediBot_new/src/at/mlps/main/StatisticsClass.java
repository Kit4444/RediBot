package at.mlps.main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class StatisticsClass extends TimerTask{
	
	public static int messages = 0;
	
	JDA jda;
	public StatisticsClass(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		SimpleDateFormat dateF = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat timeF = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		long guilds = 0;
		long members = 0;
		long slots = 250000;
		for(Guild g : jda.getGuilds()) {
			guilds++;
			for(@SuppressWarnings("unused") Member m : g.getMembers()) {
				members++;
			}
		}
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_statistic (service, date, time, currentPlayers, maxPlayers, guilds, messages) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, "RediBot");
			ps.setString(2, dateF.format(date));
			ps.setString(3, timeF.format(date));
			ps.setLong(4, members);
			ps.setLong(5, slots);
			ps.setLong(6, guilds);
			ps.setInt(7, messages);
			ps.executeUpdate();
			ps.close();
			messages = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}