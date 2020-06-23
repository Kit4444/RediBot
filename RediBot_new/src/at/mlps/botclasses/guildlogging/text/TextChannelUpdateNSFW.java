package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNSFWEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateNSFW extends ListenerAdapter{
	
	public void onTextChannelUpdateNSFFW(TextChannelUpdateNSFWEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Text channel has been updated.");
        eb.setDescription("Textchannel: " + e.getChannel().getAsMention());
        eb.addField("Is NSFW:", e.getNewValue() + "", false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}

}
