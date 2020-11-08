package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostTierEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateBoostTier extends ListenerAdapter{
	
	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("New Boost Count");
		eb.setColor(gl.green);
    	eb.setDescription("The Boostlevel has been changed.");
    	eb.addField("Old Values", "Max. Bitrate: " + e.getOldBoostTier().getMaxBitrate() + "\nMax. Emotes: " + e.getOldBoostTier().getMaxEmotes() + "\nMax. Filesize: " + e.getOldBoostTier().getMaxFileSize(), false);
    	eb.addField("New Count", "Max. Bitrate: " + e.getNewBoostTier().getMaxBitrate() + "\nMax. Emotes: " + e.getNewBoostTier().getMaxEmotes() + "\nMax. Filesize: " + e.getNewBoostTier().getMaxFileSize(), false);
    	eb.setFooter(stime);
    	if(gl.enabledLog(g, "guildupdateboosttier")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
