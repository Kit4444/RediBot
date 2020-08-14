package at.mlps.botclasses.guildlogging.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserUpdateAvatar extends ListenerAdapter{
	
	public void onUserUpdateAvatar(UserUpdateAvatarEvent e) {
		List<Guild> guilds = e.getUser().getMutualGuilds();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        String fname = e.getUser().getName() + "#" + e.getUser().getDiscriminator();
		eb.setDescription(e.getUser().getAsMention() + " / " + fname + " has updated their avatar.");
		eb.setImage(e.getNewAvatarUrl());
		eb.setColor(gl.green);
		eb.setFooter(stime);
		if(!(guilds.isEmpty()) && !(e.getUser().isBot())) {
			for(Guild guild : guilds) {
				gl.sendMsg(eb, guild);
			}
		}
	}

}
