package at.mlps.botclasses.commands;

import java.util.List;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WhoisCMD extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		String[] args = e.getMessage().getContentRaw().split(" ");
		GuildLogEvents gl = new GuildLogEvents();
		EmbedBuilder eb = new EmbedBuilder();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "whois")) {
				eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
				eb.setDescription(m.getAsMention());
				eb.addField("Joined", gl.retDate(m.getTimeJoined()), false);
				eb.addField("Registered", gl.retDate(m.getUser().getTimeCreated()), false);
				if(m.isOwner()) {
					eb.addField("Owner of the guild:", "yes", false);
				}else {
					eb.addField("Owner of the guild:", "no", false);
				}
				if(m.getTimeBoosted() != null) {
					eb.addField("Nitro Booster:", "yes, since: " + gl.retDate(m.getTimeBoosted()), false);
				}else {
					eb.addField("Nitro Booster:", "no", false);
				}
				eb.setColor(m.getColor());
				eb.addField("Onlinestatus:", m.getOnlineStatus().getKey(), false);
				eb.setThumbnail(m.getUser().getAvatarUrl());
				List<Role> roles = m.getRoles();
				StringBuilder sb = new StringBuilder();
				for(Role rs : roles) {
					sb.append(rs.getAsMention());
					sb.append(", ");
				}
				if(roles.size() <= 32) {
					eb.addField("Roles [" + roles.size() + " ]", sb.toString(), true);
				}else {
					eb.addField("Roles [" + roles.size() + " ]", "Too many roles to list.", true);
				}
				eb.setFooter("ID: " + m.getUser().getId(), g.getIconUrl());
				chan.sendMessage(eb.build()).queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "whois")) {
				long uid = 0L;
				Member m2 = null;
				if(args[1].matches("^[0-9]+$")) {
					uid = Long.parseLong(args[1]);
					m2 = g.getMemberById(uid);
				}else {
					m2 = e.getMessage().getMentionedMembers().get(0);
				}
				eb.setAuthor(m2.getUser().getName() + "#" + m2.getUser().getDiscriminator(), m2.getUser().getAvatarUrl());
				eb.setDescription(m2.getAsMention());
				eb.addField("Joined", gl.retDate(m2.getTimeJoined()), false);
				eb.addField("Registered", gl.retDate(m2.getUser().getTimeCreated()), false);
				if(m2.isOwner()) {
					eb.addField("Owner of the guild:", "yes", false);
				}else {
					eb.addField("Owner of the guild:", "no", false);
				}
				if(m2.getTimeBoosted() != null) {
					eb.addField("Nitro Booster:", "yes, since: " + gl.retDate(m2.getTimeBoosted()), false);
				}else {
					eb.addField("Nitro Booster:", "no", false);
				}
				eb.setColor(m2.getColor());
				eb.addField("Onlinestatus:", m2.getOnlineStatus().getKey(), false);
				eb.setThumbnail(m2.getUser().getAvatarUrl());
				List<Role> roles = m2.getRoles();
				StringBuilder sb = new StringBuilder();
				for(Role rs : roles) {
					sb.append(rs.getAsMention());
					sb.append(", ");
				}
				if(roles.size() <= 32) {
					eb.addField("Roles [" + roles.size() + " ]", sb.toString(), true);
				}else {
					eb.addField("Roles [" + roles.size() + " ]", "Too many roles to list.", true);
				}
				eb.setFooter("ID: " + m2.getUser().getId(), g.getIconUrl());
				chan.sendMessage(eb.build()).queue();
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "whois")) {
				chan.sendMessage("<:deny:678036504702091278> | Command-Usage: " + Main.botprefix + "whois [Snowflake|Mention]").queue();
			}
		}
	}
}