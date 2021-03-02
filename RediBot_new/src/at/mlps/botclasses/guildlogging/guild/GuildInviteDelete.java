package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildInviteDelete extends ListenerAdapter{
	
	public void onGuildInviteDelete(GuildInviteDeleteEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has now an invite less.");
		eb.setColor(gl.green);
    	eb.setDescription("The guild has now lost an invite.");
    	eb.addField("InviteURL:", e.getUrl(), false);
    	eb.setFooter(g.getName() + " - " + stime);
    	if(gl.enabledLog(g, "guildinvitedelete")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
