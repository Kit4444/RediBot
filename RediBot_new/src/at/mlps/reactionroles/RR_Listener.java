package at.mlps.reactionroles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vdurmont.emoji.EmojiParser;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RR_Listener extends ListenerAdapter{
	
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		if(!e.getUser().isBot()) {
			long guildid = e.getGuild().getIdLong();
			long channelid = e.getChannel().getIdLong();
			long messageid = e.getMessageIdLong();
			Emote emote = null;
			String emoteKey = "";
			if(e.getReactionEmote().isEmote()) {
				emote = e.getReactionEmote().getEmote();
				if(emote.isAnimated()) {
					emoteKey = "a:" + emote.getName() + ":" + emote.getIdLong();
				}else {
					emoteKey = emote.getName() + ":" + emote.getIdLong();
				}
			}else if(e.getReactionEmote().isEmoji()) {
				emoteKey = EmojiParser.parseToAliases(e.getReactionEmote().getEmoji());
				System.out.println("Before: " + emoteKey);
				if(emoteKey.equalsIgnoreCase(":tools:")) {
					emoteKey = ":hammer_and_wrench:";
				}else if(emoteKey.equalsIgnoreCase(":speaking_head:")) {
					emoteKey = ":speaking_head_in_silhouette:";
				}else if(emoteKey.equalsIgnoreCase(":hammer_and_wrench:")) {
					emoteKey = ":tools:";
				}else if(emoteKey.equalsIgnoreCase(":speaking_head_in_silhouette:")) {
					emoteKey = ":speaking_head:";
				}
				System.out.println("After: " + emoteKey);
			}
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT roleid FROM redibot_reactionroles WHERE guildid = ? AND channelid = ? AND messageid = ? AND emote = ?");
				ps.setLong(1, guildid);
				ps.setLong(2, channelid);
				ps.setLong(3, messageid);
				ps.setString(4, emoteKey);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					long roleid = rs.getLong("roleid");
					Role role = e.getGuild().getRoleById(roleid);
					if(role != null) {
						e.getGuild().addRoleToMember(e.getMember(), role).queue();
						if(isSingleUse(guildid, channelid, messageid, emoteKey)) {
							e.getReaction().removeReaction(e.getMember().getUser()).queue();
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
		if(!e.getUser().isBot()) {
			long guildid = e.getGuild().getIdLong();
			long channelid = e.getChannel().getIdLong();
			long messageid = e.getMessageIdLong();
			Emote emote = null;
			String emoteKey = "";
			if(e.getReactionEmote().isEmote()) {
				emote = e.getReactionEmote().getEmote();
				if(emote.isAnimated()) {
					emoteKey = "a:" + emote.getName() + ":" + emote.getIdLong();
				}else {
					emoteKey = emote.getName() + ":" + emote.getIdLong();
				}
			}else if(e.getReactionEmote().isEmoji()) {
				emoteKey = EmojiParser.parseToAliases(e.getReactionEmote().getEmoji());
				if(emoteKey.equalsIgnoreCase(":tools:")) {
					emoteKey = ":hammer_and_wrench:";
				}else if(emoteKey.equalsIgnoreCase(":speaking_head:")) {
					emoteKey = ":speaking_head_in_silhouette:";
				}else if(emoteKey.equalsIgnoreCase(":hammer_and_wrench:")) {
					emoteKey = ":tools:";
				}else if(emoteKey.equalsIgnoreCase(":speaking_head_in_silhouette:")) {
					emoteKey = ":speaking_head:";
				}
			}
			boolean singleuse = isSingleUse(guildid, channelid, messageid, emoteKey);
			if(!singleuse) {
				try {
					PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT roleid FROM redibot_reactionroles WHERE guildid = ? AND channelid = ? AND messageid = ? AND emote = ?");
					ps.setLong(1, guildid);
					ps.setLong(2, channelid);
					ps.setLong(3, messageid);
					ps.setString(4, emoteKey);
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						long roleid = rs.getLong("roleid");
						Role role = e.getGuild().getRoleById(roleid);
						if(role != null) {
							e.getGuild().removeRoleFromMember(e.getMember(), role).queue();
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private boolean isSingleUse(long guildid, long channelid, long messageid, String emote) {
		boolean singleuse = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT single_use FROM redibot_reactionroles WHERE guildid = ? AND channelid = ? AND messageid = ? AND emote = ?");
			ps.setLong(1, guildid);
			ps.setLong(2, channelid);
			ps.setLong(3, messageid);
			ps.setString(4, emote);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				singleuse = rs.getBoolean("single_use");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return singleuse;
	}
}