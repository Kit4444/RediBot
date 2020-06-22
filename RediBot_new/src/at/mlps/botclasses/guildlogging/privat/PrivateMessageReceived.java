package at.mlps.botclasses.guildlogging.privat;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class PrivateMessageReceived {
	
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("New Private Message received");
        GuildLogger gl = new GuildLogger();
        StringBuilder sb = new StringBuilder();
        for(Guild g : e.getJDA().getMutualGuilds(e.getAuthor())) {
        	sb.append(g.getName());
        	sb.append(", ");
        }
        eb.setDescription("Author: " + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator());
        eb.addField("Message:", e.getMessage().getContentRaw(), false);
        eb.addField("Mutual Guilds:", sb.toString() + " ", false);
        eb.setImage(e.getAuthor().getAvatarUrl());
        eb.setFooter(stime);
        e.getJDA().getGuildById(gl.rediassetg).getTextChannelById(gl.rediassetlog).sendMessage(eb.build()).queue();
	}

}
