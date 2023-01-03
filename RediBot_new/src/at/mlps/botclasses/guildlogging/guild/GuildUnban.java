package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUnban extends ListenerAdapter{
	
	public void onGuildUnban(GuildUnbanEvent e) {
		User user = e.getUser();
		Guild guild = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(gl.green);
		eb.setTitle("Member unbanned");
		eb.setThumbnail(user.getAvatarUrl());
		eb.setDescription(user.getName() + "#" + user.getDiscriminator() + " has been unbanned.");
		eb.setFooter(guild.getName() + " - " + stime, guild.getIconUrl());
		if(gl.enabledLog(guild, "guildunban"))
			gl.sendMsg(eb, guild);
	}

}
