package at.mlps.botclasses.guildlogging.member;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserUpdateOnlineStatus extends ListenerAdapter{
	
	public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent e) {
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        Guild g = e.getGuild();
        String fname = e.getUser().getName() + "#" + e.getUser().getDiscriminator();
		eb.setDescription(e.getUser().getAsMention() + " / " + fname + " has updated their online status.\n \nOld Status: " + e.getOldOnlineStatus().getKey() + "\nNew Status: " + e.getNewOnlineStatus().getKey());
		eb.setColor(gl.green);
		eb.setFooter(stime);
		if(gl.enabledLog(g, "userupdateonlinestatus")) {
			gl.sendMsg(eb, g);
		}
	}
}