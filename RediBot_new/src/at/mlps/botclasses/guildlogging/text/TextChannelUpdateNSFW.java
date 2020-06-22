package at.mlps.botclasses.guildlogging.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNSFWEvent;

public class TextChannelUpdateNSFW {
	
	public void onTextChannelUpdateNSFFW(TextChannelUpdateNSFWEvent e) {
		Guild g = e.getGuild();
		GuildLogger gl = new GuildLogger();
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
