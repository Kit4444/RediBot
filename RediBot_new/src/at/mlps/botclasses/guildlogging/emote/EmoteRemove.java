package at.mlps.botclasses.guildlogging.emote;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteRemove extends ListenerAdapter{
	
	public void onEmoteRemoved(EmoteRemovedEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        eb.setTitle("Emote has been removed.");
        eb.setThumbnail(e.getEmote().getImageUrl());
        eb.addField("Name:", e.getEmote().getName(), false);
        eb.setFooter(stime);
		eb.setColor(gl.red);
		if(gl.enabledLog(g, "emoteremove")) {
			gl.sendMsg(eb, g);
		}
	}

}
