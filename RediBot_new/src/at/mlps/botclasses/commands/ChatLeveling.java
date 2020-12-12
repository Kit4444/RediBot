package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatLeveling extends ListenerAdapter{
	
	public void onGuildMember(GuildMemberJoinEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		if(isRegistered(g.getIdLong())) {
			if(!MemberExists(g.getIdLong(), m.getIdLong())) {
				addMember(g.getIdLong(), m.getIdLong());
			}
		}
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(isRegistered(g.getIdLong())) {
			if(!MemberExists(g.getIdLong(), m.getIdLong())) {
				addMember(g.getIdLong(), m.getIdLong());
			}else {
				int cXP = getXP(g.getIdLong(), m.getIdLong());
				int aXP = 1;
				int nXP = (cXP + aXP);
				if(!(e.getAuthor().getIdLong() == 588547204063428637L) || !e.getAuthor().isBot()) {
					setXPLite(g.getIdLong(), m.getIdLong(), nXP, (e.getAuthor().isBot() ||  e.isWebhookMessage()));
					switch(nXP) {
					case 10: sendEmbed(chan, m, g, 1, nXP, 25); break;
					case 25: sendEmbed(chan, m, g, 2, nXP, 50); break;
					case 50: sendEmbed(chan, m, g, 3, nXP, 75); break;
					case 75: sendEmbed(chan, m, g, 4, nXP, 100); break;
					case 100: sendEmbed(chan, m, g, 5, nXP, 150); changeRole(g, 548136727697555496L, m, 0L, 766645157541576725L); break;
					case 150: sendEmbed(chan, m, g, 6, nXP, 200); break;
					case 200: sendEmbed(chan, m, g, 7, nXP, 350); break;
					case 350: sendEmbed(chan, m, g, 8, nXP, 400); break;
					case 400: sendEmbed(chan, m, g, 9, nXP, 500); break;
					case 500: sendEmbed(chan, m, g, 10, nXP, 580); changeRole(g, 548136727697555496L, m, 766645157541576725L, 766645159294795828L); break;
					case 580: sendEmbed(chan, m, g, 11, nXP, 670); break;
					case 670: sendEmbed(chan, m, g, 12, nXP, 760); break;
					case 760: sendEmbed(chan, m, g, 13, nXP, 850); break;
					case 850: sendEmbed(chan, m, g, 14, nXP, 940); break;
					case 940: sendEmbed(chan, m, g, 15, nXP, 1030); changeRole(g, 548136727697555496L, m, 766645159294795828L, 766645162235265024L); break;
					case 1030: sendEmbed(chan, m, g, 16, nXP, 1120); break;
					case 1120: sendEmbed(chan, m, g, 17, nXP, 1210); break;
					case 1210: sendEmbed(chan, m, g, 18, nXP, 1300); break;
					case 1300: sendEmbed(chan, m, g, 19, nXP, 1400); break;
					case 1400: sendEmbed(chan, m, g, 20, nXP, 1600); changeRole(g, 548136727697555496L, m, 766645162235265024L, 766645164491145216L); break;
					case 1600: sendEmbed(chan, m, g, 21, nXP, 1800); break;
					case 1800: sendEmbed(chan, m, g, 22, nXP, 2000); break;
					case 2000: sendEmbed(chan, m, g, 23, nXP, 2250); break;
					case 2250: sendEmbed(chan, m, g, 24, nXP, 2500); break;
					case 2500: sendEmbed(chan, m, g, 25, nXP, 2750); changeRole(g, 548136727697555496L, m, 766645164491145216L, 766645166684766228L); break;
					case 2750: sendEmbed(chan, m, g, 26, nXP, 3000); break;
					case 3000: sendEmbed(chan, m, g, 27, nXP, 3250); break;
					case 3250: sendEmbed(chan, m, g, 28, nXP, 3500); break;
					case 3500: sendEmbed(chan, m, g, 29, nXP, 4000); break;
					case 4000: sendEmbed(chan, m, g, 30, nXP, 4500); changeRole(g, 548136727697555496L, m, 766645166684766228L, 766645169033838602L); break;
					case 4500: sendEmbed(chan, m, g, 31, nXP, 5000); break;
					case 5000: sendEmbed(chan, m, g, 32, nXP, 5500); break;
					case 5500: sendEmbed(chan, m, g, 33, nXP, 6000); break;
					case 6000: sendEmbed(chan, m, g, 34, nXP, 6500); break;
					case 6500: sendEmbed(chan, m, g, 35, nXP, 7500); changeRole(g, 548136727697555496L, m, 766645169033838602L, 766645170875269132L); break;
					case 7500: sendEmbed(chan, m, g, 36, nXP, 8500); break;
					case 8500: sendEmbed(chan, m, g, 37, nXP, 9500); break;
					case 9500: sendEmbed(chan, m, g, 38, nXP, 10500); break;
					case 10500: sendEmbed(chan, m, g, 39, nXP, 12000); break;
					case 12000: sendEmbed(chan, m, g, 40, nXP, 14000); changeRole(g, 548136727697555496L, m, 766645170875269132L, 766645173123809302L); break;
					case 14000: sendEmbed(chan, m, g, 41, nXP, 16000); break;
					case 16000: sendEmbed(chan, m, g, 42, nXP, 18000); break;
					case 18000: sendEmbed(chan, m, g, 43, nXP, 20000); break;
					case 20000: sendEmbed(chan, m, g, 44, nXP, 22000); break;
					case 22000: sendEmbed(chan, m, g, 45, nXP, 25000); changeRole(g, 548136727697555496L, m, 766645173123809302L, 766645175735943169L); break;
					case 25000: sendEmbed(chan, m, g, 46, nXP, 27500); break;
					case 27500: sendEmbed(chan, m, g, 47, nXP, 30000); break;
					case 30000: sendEmbed(chan, m, g, 48, nXP, 32500); break;
					case 32500: sendEmbed(chan, m, g, 49, nXP, 37500); break;
					case 37500: sendEmbed(chan, m, g, 50, nXP, 37500); changeRole(g, 548136727697555496L, m, 766645175735943169L, 766645177972424724L); break;
					default: System.out.println(m.getUser().getName() + " achieved XP " + nXP + " Points on Guild " + g.getName()); break;
					}
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
	
	private void setXP(long guildid, long memberid, int xp, int level, int nextlevel) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_discordchatlevel SET level = ?, mainlevel = ?, nextlevel = ? WHERE guildid = ? AND memberid = ?");
			ps.setInt(1, xp);
			ps.setInt(2, level);
			ps.setInt(3, nextlevel);
			ps.setLong(4, guildid);
			ps.setLong(5, memberid);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setXPLite(long guildid, long memberid, int xp, boolean bot) {
		PreparedStatement ps;
		try {
			ps = MySQL.getConnection().prepareStatement("UPDATE redibot_discordchatlevel SET level = ?, isBot = ? WHERE guildid = ? AND memberid = ?");
			ps.setInt(1, xp);
			ps.setBoolean(2, bot);
			ps.setLong(3, guildid);
			ps.setLong(4, memberid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isRegistered(long guildid) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
			ps.setLong(1, guildid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				boo = true;
			}else {
				boo = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boo;
	}

	private boolean MemberExists(long guildid, long memberid) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_discordchatlevel WHERE guildid = ? AND memberid = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, memberid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				boo = true;
			}else {
				boo = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boo;
	}
	
	private void addMember(long guildid, long memberid) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_discordchatlevel (guildid, memberid, level) VALUES (?, ?, ?)");
			ps.setLong(1, guildid);
			ps.setLong(2, memberid);
			ps.setInt(3, 1);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void sendEmbed(TextChannel chan, Member m, Guild g, int level, int xp, int nextlevel) {
		setXP(g.getIdLong(), m.getIdLong(), xp, level, nextlevel);
		EmbedBuilder eb = new EmbedBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy - HH:mm:ss");
		eb.setColor(m.getColor());
		eb.setThumbnail(m.getUser().getAvatarUrl());
		eb.setDescription(m.getUser().getName() + " has leveled up.\nYou are now on level " + level);
		eb.setFooter(g.getName() + ", " + sdf.format(new Date()), g.getIconUrl());
		chan.sendMessage(eb.build()).queue(msg -> {
			msg.delete().queueAfter(30, TimeUnit.SECONDS);
		});
	}
	
	private void changeRole(@NotNull Guild g, @NotNull long guildid, @NotNull Member m, @NotNull long oldRole, @Nullable long newRole) {
		if(g.getIdLong() == guildid) {
			Role oldR = g.getRoleById(oldRole);
			Role newR = g.getRoleById(newRole);
			if(oldR != null) {
				if(m.getRoles().contains(oldR)) {
					g.removeRoleFromMember(m, oldR).complete();
				}
			}
			if(newR != null) {
				if(!m.getRoles().contains(newR)) {
					g.addRoleToMember(m, newR).complete();
				}
			}
		}
	}
}