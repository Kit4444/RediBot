package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateTopic extends ListenerAdapter{
	
	public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent e) {
		List<Long> exemptchannels = new ArrayList<>(); 
		exemptchannels.add(757510387934560296L);
		exemptchannels.add(757510355395281006L);
		exemptchannels.add(757510041090785312L);
		exemptchannels.add(757510292425932851L);
		exemptchannels.add(757510325204418612L);
		exemptchannels.add(757510438924845127L);
		exemptchannels.add(757510458814234634L);
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        if(!exemptchannels.contains(e.getChannel().getIdLong())) {
        	eb.setTitle("Channel has been updated.");
            eb.setDescription("Textchannel: " + e.getChannel().getAsMention());
            eb.addField("Old Topic:", e.getOldTopic() + " ", false);
            eb.addField("New Topic:", e.getNewTopic() + " ", false);
            eb.setFooter(stime);
    		eb.setColor(gl.orange);
    		gl.sendMsg(eb, g);
        }
	}

}
