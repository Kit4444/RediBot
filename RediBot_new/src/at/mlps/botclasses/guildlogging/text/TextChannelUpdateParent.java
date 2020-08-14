package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateParentEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateParent extends ListenerAdapter{
	
	public void onTextChannelUpdateParent(TextChannelUpdateParentEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Channel has been moved.");
        eb.setDescription("Textchannel: " + e.getChannel().getAsMention());
        eb.addField("Old Parent:", e.getOldParent().getName(), false);
        eb.addField("New Parent:", e.getNewParent().getName(), false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}

}
