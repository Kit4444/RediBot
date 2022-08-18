package at.mlps.botclasses.guildlogging.voicejoinleavemove;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceMove extends ListenerAdapter{
	
	public void onGuildVoiceMove(GuildVoiceMoveEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Member has switched the voicechannel.");
    	long joinTime = (DurCalc.getJoinedTime(g.getIdLong(), e.getMember().getIdLong()) * 1000);
    	PrettyTime pt = new PrettyTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy - HH:mm:ss");
    	eb.setDescription("Member: " + e.getMember().getAsMention() + "\nOld Channel: " + e.getChannelLeft().getAsMention() + "\nNew Channel: " + e.getChannelJoined().getAsMention() + "\nInitial Voice Join: " + sdf.format(new Date(joinTime)) + ", " + pt.format(new Date(joinTime)));
    	eb.setFooter(stime);
		eb.setColor(gl.green);
		if(gl.enabledLog(g, "guildvoicemove")) {
			gl.sendMsg(eb, g);
		}
	}

}
