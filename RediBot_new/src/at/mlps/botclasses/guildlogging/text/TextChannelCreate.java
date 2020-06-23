package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelCreate extends ListenerAdapter{
	
	public void onTextChannelCreate(TextChannelCreateEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Text Channel has been created.");
    	eb.setDescription("Textchannel: " + e.getChannel().getAsMention());
    	eb.addField("Parent:", e.getChannel().getParent().getName(), false);
    	eb.addField("NSFW:", "" + e.getChannel().isNSFW(), false);
    	eb.addField("ID:", e.getChannel().getIdLong() + "", false);
    	eb.setFooter(stime);
		eb.setColor(gl.green);
		gl.sendMsg(eb, g);
	}

}
