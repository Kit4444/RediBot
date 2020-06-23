package at.mlps.botclasses.guildlogging.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserUpdateName extends ListenerAdapter{
	
	public void onUserUpdateName(UserUpdateNameEvent e) {
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        GuildLogEvents gl = new GuildLogEvents();
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Name Update");
		eb.setDescription(e.getUser().getAsMention() + " updated it's Name.");
		eb.setFooter(stime);
		eb.setColor(gl.green);
		eb.addField("Old Name:", e.getOldName(), false);
		eb.addField("New Name:", e.getNewName(), false);
		List<Guild> guilds = e.getUser().getMutualGuilds();
		if(!(guilds.isEmpty()) && !(e.getUser().isBot())) {
			for(Guild guild : guilds) {
				gl.sendMsg(eb, guild);
			}
		}
	}

}
