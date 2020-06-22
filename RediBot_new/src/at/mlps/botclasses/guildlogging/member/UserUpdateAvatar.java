package at.mlps.botclasses.guildlogging.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;

public class UserUpdateAvatar {
	
	public void onUserUpdateAvatar(UserUpdateAvatarEvent e) {
		List<Guild> guilds = e.getUser().getMutualGuilds();
		GuildLogger gl = new GuildLogger();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Click me if you don't see the avatar", e.getNewAvatarUrl());
		eb.setDescription(e.getUser().getAsMention() + " updated it's avatar.");
		eb.setImage(e.getNewAvatarUrl());
		eb.setColor(gl.green);
		eb.setFooter(stime);
		eb.addField("Old AvatarURL:", e.getOldAvatarUrl() + "", false);
		eb.addField("New AvatarURL:", e.getNewAvatarUrl() + "", false);
		if(!(guilds.isEmpty()) && !(e.getUser().isBot())) {
			for(Guild guild : guilds) {
				gl.sendMsg(eb, guild);
			}
		}
	}

}
