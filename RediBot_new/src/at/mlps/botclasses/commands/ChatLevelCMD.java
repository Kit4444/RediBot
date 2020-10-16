package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatLevelCMD extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "level")) {
				int xp = getXP(g.getIdLong(), m.getIdLong());
				int level = getLevel(g.getIdLong(), m.getIdLong(), true);
				int nlevel = (level + 1);
				int nextlevel = getLevel(g.getIdLong(), m.getIdLong(), false);
				int progress = (xp * 100 / nextlevel);
				
				String progr = "";
				if(progress <= 5) {
					progr = "⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 6 && progress <= 10) {
					progr = "⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 11 && progress <= 15) {
					progr = "⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 16 && progress <= 20) {
					progr = "⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 21 && progress <= 25) {
					progr = "⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 26 && progress <= 30) {
					progr = "⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 31 && progress <= 35) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 36 && progress <= 40) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 41 && progress <= 45) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 46 && progress <= 50) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 51 && progress <= 55) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 56 && progress <= 60) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 61 && progress <= 65) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 66 && progress <= 70) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜";
				}else if(progress >= 71 && progress <= 75) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜";
				}else if(progress >= 76 && progress <= 80) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜";
				}else if(progress >= 81 && progress <= 85) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜";
				}else if(progress >= 86 && progress <= 90) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜⬜";
				}else if(progress >= 91 && progress <= 95) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬜";
				}else if(progress >= 96 && progress <= 100) {
					progr = "⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛";
				}else {
					progr = "Error-INDEF_CLCMD#72";
				}
				
				EmbedBuilder eb = new EmbedBuilder();
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy - HH:mm:ss");
				eb.setColor(m.getColor());
				eb.setThumbnail(m.getUser().getAvatarUrl());
				eb.setDescription(m.getUser().getName() + "'s Level is " + level + "\nNext Level is " + nlevel + "\nProgress:\n" + progr + "\nTotal XP: " + xp);
				eb.setFooter(g.getName() + ", " + sdf.format(new Date()), g.getIconUrl());
				e.getChannel().sendMessage(eb.build()).queue();
			}
		}
	}
	
	private int getXP(long guildid, long memberid) {
		int level = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_discordchatlevel WHERE guildid = ? AND memberid = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, memberid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			level = rs.getInt("level");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return level;
	}
	
	private int getLevel(long guildid, long memberid, boolean type) {
		int level = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_discordchatlevel WHERE guildid = ? AND memberid = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, memberid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(type == true) {
				level = rs.getInt("mainlevel");
			}else {
				level = rs.getInt("nextlevel");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return level;
	}
}
