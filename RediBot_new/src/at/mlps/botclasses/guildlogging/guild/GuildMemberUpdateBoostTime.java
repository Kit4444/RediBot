package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberUpdateBoostTime extends ListenerAdapter{
	
	public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent e) {
		Member m = e.getMember();
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("RediCraft » Boost");
    	eb.setDescription(m.getAsMention() + " has boosted this server! Thank you for your kindness. <a:love:745237526368747682>");
    	eb.setFooter("Today at " + stime);
		eb.setColor(gl.green);
		if(g.getIdLong() == 548136727697555496L) {
			TextChannel general = g.getTextChannelById(552163655366475786L);
			general.sendMessageEmbeds(eb.build()).queue();
		}
	}

}
