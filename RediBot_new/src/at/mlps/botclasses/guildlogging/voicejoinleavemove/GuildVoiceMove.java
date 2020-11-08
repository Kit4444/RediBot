package at.mlps.botclasses.guildlogging.voicejoinleavemove;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    	eb.setDescription("Member: " + e.getMember().getAsMention() + "\nOld Channel: " + e.getChannelLeft().getName() + "\nNew Channel: " + e.getChannelJoined().getName());
    	eb.setFooter(stime);
		eb.setColor(gl.green);
		if(gl.enabledLog(g, "guildvoicemove")) {
			gl.sendMsg(eb, g);
		}
	}

}
