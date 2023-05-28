package at.mlps.botclasses.guildlogging.category;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;

public class CategoryDelete {
	
	public void onCategoryDelete(CategoryDeleteEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Category has been deleted.");
        eb.setDescription("Category: " + e.getCategory().getName());
        eb.addField("Name:", e.getCategory().getName() + "", false);
        eb.addField("ID:", e.getIdLong() + " ", false);
        eb.addField("Parent:", e.getCategory().getParent().getName(), false);
        eb.addField("Creation Date:", gl.retDate(e.getCategory().getTimeCreated()), false);
        eb.addField("Channels:", "Textchannels: " + e.getCategory().getTextChannels().size() + "\nVoicechannels: " + e.getCategory().getVoiceChannels().size(), false);
        eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		eb.setColor(gl.orange);
		if(gl.enabledLog(g, "categorydelete")) {
			gl.sendMsg(eb, g);
		}
	}

}
