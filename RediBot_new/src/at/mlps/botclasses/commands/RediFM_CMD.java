package at.mlps.botclasses.commands;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RediFM_CMD extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel chan = e.getChannel();
		Member m = e.getMember();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "redifm") || args[0].equalsIgnoreCase(Main.botprefix + "rfm")) {
				try {
					PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redifm_current WHERE id = 1");
					ResultSet rs = ps.executeQuery();
					rs.next();
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(Color.decode(rs.getString("color")));
					eb.setTitle("RediFM", "https://laut.fm/redifm");
					eb.setAuthor(m.getUser().getName(), null, null);
					eb.setThumbnail(rs.getString("stationImage"));
					eb.setDescription("RediFM, the own radio from RediCraft for the community!");
					eb.addField("Current Song:", "Name: " + rs.getString("track") + "\nAlbum: " + rs.getString("album") + "\nArtist: " + rs.getString("artist") + "\nCurrent Listeners: " + rs.getInt("current_listener"), false);
					eb.addField("Current Playlist:", "Name: " + rs.getString("playlist") + "\nDescription: " + rs.getString("description") + "\nStarted at: " + parseDate(rs.getString("startedAt"), "dd.MM.yy HH:mm:ss") + "\nEnds at: " + parseDate(rs.getString("endAt"), "dd.MM.yy HH:mm:ss"), false);
					eb.addField("Next Playlist:", "Name: " + rs.getString("a_pl") + "\nDescription: " + rs.getString("a_description") + "\nStarts at: " + parseDate(rs.getString("a_startAt"), "dd.MM.yy HH:mm:ss") + "\nEnds at: " + parseDate(rs.getString("a_endAt"), "dd.MM.yy HH:mm:ss"), false);
					chan.sendMessageEmbeds(eb.build()).queue();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	String parseDate(String input, String newPattern) {
		String output = "";
		SimpleDateFormat sdf = new SimpleDateFormat(newPattern);
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(input);
			output = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return output;
	}
}