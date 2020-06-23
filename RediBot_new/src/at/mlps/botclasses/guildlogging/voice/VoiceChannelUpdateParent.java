package at.mlps.botclasses.guildlogging.voice;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateParentEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelUpdateParent extends ListenerAdapter{
	
	public void onVoiceChannelUpdateParent(VoiceChannelUpdateParentEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Voice channel has been moved.");
        eb.setDescription("Voicechannel: " + e.getChannel().getName());
        eb.addField("Old Parent:", e.getOldParent().getName(), false);
        eb.addField("New Parent:", e.getNewParent().getName(), false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}

}
