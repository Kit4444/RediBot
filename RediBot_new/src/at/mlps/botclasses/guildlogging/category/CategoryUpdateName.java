package at.mlps.botclasses.guildlogging.category;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent;

public class CategoryUpdateName {

	public void onCategoryUpdateName(CategoryUpdatePositionEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Category has been moved.");
        eb.setDescription("Category: " + e.getCategory().getName());
        eb.addField("Old Position:", e.getCategory().getParent().getName() + " / " + e.getOldPosition(), false);
        eb.addField("New Position:", e.getCategory().getParent().getName() + " / " + e.getNewPosition(), false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		gl.sendMsg(eb, g);
	}
}
