package at.mlps.botclasses.guildlogging.category;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;

public class CategoryCreate {
	
	public void onCategoryCreate(CategoryCreateEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Category has been created.");
        eb.setDescription("Category: " + e.getCategory().getName());
        eb.addField("Name:", e.getCategory().getName() + "", false);
        eb.addField("ID:", e.getIdLong() + " ", false);
        eb.addField("Parent:", e.getCategory().getParent().getName(), false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}

}
