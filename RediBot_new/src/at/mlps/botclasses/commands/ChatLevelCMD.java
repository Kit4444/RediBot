package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatLevelCMD extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "level")) {
				if(hasEnabled(g)) {
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
				}else {
					e.getChannel().sendMessage("This guild turned the XP Leveling off.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "leaderboard")) {
				if(hasEnabled(g)) {
					TextChannel chan = e.getChannel();
					long guildid = g.getIdLong();
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_discordchatlevel WHERE guildid = ? ORDER BY level DESC LIMIT 25");
						ps.setLong(1, guildid);
						ResultSet rs = ps.executeQuery();
						List<String> leaderbrd = new ArrayList<>();
						int i = 0;
						leaderbrd.add("Toplist | Name | XP | Level");
						while (rs.next()) {
							i++;
							Member member = g.getMemberById(rs.getLong("memberid"));
							String name = "";
							if(member == null) {
								name = "unknown member/" + rs.getLong("memberid");
							}else {
								name = member.getUser().getName() + "#" + member.getUser().getDiscriminator();
							}
							int level = rs.getInt("mainlevel");
							int xp = rs.getInt("level");
							leaderbrd.add("Top " + i + " | " + name + " | " + xp + " XP | Level " + level);
						}
						StringBuilder sb = new StringBuilder();
						for(String s : leaderbrd) {
							sb.append(s);
							sb.append("\n");
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("```" + sb.toString() + "```");
						eb.setColor(m.getColor());
						eb.setTitle("Top " + i + " of " + g.getName());
						chan.sendMessage(eb.build()).queue();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					e.getChannel().sendMessage("This guild turned the XP Leveling off.").queue();
				}
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
	
	private boolean hasEnabled(Guild g) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT hasLeveling FROM redibot_guildlog WHERE guildid = ?");
			ps.setLong(1, g.getIdLong());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				boo = rs.getBoolean("hasLeveling");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boo;
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
