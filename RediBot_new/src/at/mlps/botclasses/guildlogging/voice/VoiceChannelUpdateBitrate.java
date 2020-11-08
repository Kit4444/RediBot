package at.mlps.botclasses.guildlogging.voice;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateBitrateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelUpdateBitrate extends ListenerAdapter{
	
	public void onVoiceChannelUpdateBitrate(VoiceChannelUpdateBitrateEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Channel has been updated.");
        eb.setDescription("Voicechannel: " + e.getChannel().getName());
        eb.addField("Old Bitrate:", e.getOldBitrate() + " Bit", false);
        eb.addField("New Bitrate:", e.getNewBitrate() + " Bit", false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		if(gl.enabledLog(g, "voiceupdatebitrate")) {
			gl.sendMsg(eb, g);
		}
	}

}
