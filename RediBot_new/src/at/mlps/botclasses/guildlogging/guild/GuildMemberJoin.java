package at.mlps.botclasses.guildlogging.guild;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter{
	
	public void onGuildMemberJoin(GuildMemberJoinEvent e){
		Guild g = e.getGuild();
		Member m = e.getMember();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
		if(g.getIdLong() == gl.redimain) {
			TextChannel rules = g.getTextChannelById(548187134687838218L);
			Role guest = e.getGuild().getRoleById(651569972920713226L);
			g.addRoleToMember(e.getMember(), guest).complete();
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Member has joined the server");
			eb.setDescription("Welcome on RediCraft, " + m.getAsMention() + ".\nBefore you continue using this server, please read our rules carefully through.\nYou can find them in " + rules.getAsMention() + "\n \nHave fun on RediCraft\nThe RediCraft Administration");
			eb.setThumbnail(m.getUser().getAvatarUrl());
			eb.setFooter("Joindate: " + stime, g.getIconUrl());
			eb.setColor(Color.decode("#55FF55"));
			g.getTextChannelById(637343872422248458L).sendMessage(eb.build()).queue();
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.green);
		eb.setTitle("Member joined");
		eb.setDescription( m.getAsMention() + " has joined the guild.");
		eb.setFooter(stime, g.getIconUrl());
		if(gl.enabledLog(g, "guildmemberjoin")) {
			gl.sendMsg(eb, g);
		}
	}

}
