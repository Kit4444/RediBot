package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildInviteCreate extends ListenerAdapter{
	
	public void onGuildInviteCreate(GuildInviteCreateEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has a new Invite");
		eb.setColor(gl.green);
    	eb.setDescription("The guild has a new invite.");
    	eb.addField("InviteURL:", e.getInvite().getUrl(), false);
    	eb.addField("Inviter:", e.getInvite().getInviter().getName() + "#" + e.getInvite().getInviter().getDiscriminator(), false);
    	eb.addField("Max Uses:", e.getInvite().getMaxUses() + " ", false);
    	eb.addField("Channel:", "Name: " + e.getInvite().getChannel().getName() + "\nChanneltype: " + e.getInvite().getChannel().getType().toString(), false);
    	eb.addField("Creation Date:", gl.retDate(e.getInvite().getTimeCreated()), false);
    	if(e.getInvite().getMaxAge() == 0) {
    		eb.addField("Time Valid:", "permanent", false);
    	}else {
    		eb.addField("Time Valid:", e.getInvite().getMaxAge() + " seconds", false);
    	}
    	eb.addField("Current Uses: ", e.getInvite().getUses() + " ", false);
    	eb.setFooter(stime);
    	if(gl.enabledLog(g, "guildinvitecreate")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
