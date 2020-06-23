package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateTopic extends ListenerAdapter{
	
	public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Text channel has been updated.");
        eb.setDescription("Textchannel: " + e.getChannel().getAsMention());
        eb.addField("Old Topic:", e.getOldTopic() + "", false);
        eb.addField("New Topic:", e.getNewTopic() + "", false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}

}
