package at.mlps.botclasses.guildlogging.emote;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteUpdateName extends ListenerAdapter{
	
	public void onEmoteUpdateName(EmoteUpdateNameEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        eb.setTitle("Emote has been updated.");
        eb.setThumbnail(e.getEmote().getImageUrl());
        eb.addField("Old Name:", e.getOldName() + " ", false);
        eb.addField("New Name:", e.getNewName() + " ", false);
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		if(gl.enabledLog(g, "emoteupdatename"))
		gl.sendMsg(eb, g);
	}

}
