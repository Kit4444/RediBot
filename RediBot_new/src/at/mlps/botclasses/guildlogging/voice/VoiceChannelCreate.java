package at.mlps.botclasses.guildlogging.voice;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelCreate extends ListenerAdapter{
	
	public void onVoiceChannelCreate(VoiceChannelCreateEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Voice Channel has been created.");
    	eb.setDescription("Voicechannel: " + e.getChannel().getName());
    	eb.addField("Parent:", e.getChannel().getParent().getName(), false);
    	eb.addField("Members:", e.getChannel().getUserLimit() + "", false);
    	eb.addField("Bitrate:", e.getChannel().getBitrate() + "", false);
    	eb.addField("ID:", e.getChannel().getIdLong() + "", false);
    	eb.setFooter(stime);
		eb.setColor(gl.green);
		gl.sendMsg(eb, g);
	}

}
