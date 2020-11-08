package at.mlps.botclasses.guildlogging.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserUpdateDiscriminator extends ListenerAdapter{
	
	public void onUserUpdateDiscriminator(UserUpdateDiscriminatorEvent e) {
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        GuildLogEvents gl = new GuildLogEvents();
        EmbedBuilder eb = new EmbedBuilder();
        String fname = e.getUser().getName() + "#" + e.getUser().getDiscriminator();
		eb.setDescription(e.getUser().getAsMention() + " / " + fname + " has updated their discriminator.\nOld Discriminator: ``" + e.getOldDiscriminator() + "``\nNew Discriminator: ``" + e.getNewDiscriminator() + "``");
		eb.setFooter(stime);
		eb.setColor(gl.green);
		List<Guild> guilds = e.getUser().getMutualGuilds();
		if(!(guilds.isEmpty()) && !(e.getUser().isBot())) {
			for(Guild guild : guilds) {
				if(gl.enabledLog(guild, "userupdatediscriminator")) {
					gl.sendMsg(eb, guild);
				}
			}
		}
	}

}
