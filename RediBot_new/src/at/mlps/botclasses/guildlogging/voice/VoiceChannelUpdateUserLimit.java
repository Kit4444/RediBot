package at.mlps.botclasses.guildlogging.voice;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelUpdateUserLimit extends ListenerAdapter{
	
	public void onVoiceChannelUpdateUserLimit(VoiceChannelUpdateUserLimitEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Channel has been updated.");
        eb.setDescription("Voicechannel: " + e.getChannel().getName());
        eb.addField("Old Limit:", e.getOldUserLimit() + " Users", false);
        eb.addField("New Limit:", e.getNewUserLimit() + " Users", false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		if(gl.enabledLog(g, "voiceupdateuserlimit")) {
			gl.sendMsg(eb, g);
		}
	}

}
