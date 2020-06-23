package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateSlowmodeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateSlowmode extends ListenerAdapter{
	
	public void onTextChannelUpdateSlowmode(TextChannelUpdateSlowmodeEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Text channel has been updated.");
        eb.setDescription("Textchannel: " + e.getChannel().getAsMention());
        eb.addField("Old Slowmode:", e.getOldSlowmode() + " seconds", false);
        eb.addField("New Slowmode:", e.getNewSlowmode() + " seconds", false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}

}
