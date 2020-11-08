package at.mlps.botclasses.guildlogging.emote;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteAdd extends ListenerAdapter{
	
	public void onEmoteAdded(EmoteAddedEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        eb.setTitle("Emote has been added.");
        eb.setThumbnail(e.getEmote().getImageUrl());
        eb.addField("Name:", e.getEmote().getName(), false);
        eb.setFooter(stime);
		eb.setColor(gl.green);
		if(gl.enabledLog(g, "emoteadd")) {
			gl.sendMsg(eb, g);
		}
	}

}
