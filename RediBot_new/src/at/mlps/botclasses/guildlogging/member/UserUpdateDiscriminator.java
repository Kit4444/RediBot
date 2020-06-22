package at.mlps.botclasses.guildlogging.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;

public class UserUpdateDiscriminator {
	
	public void onUserUpdateDiscriminator(UserUpdateDiscriminatorEvent e) {
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        GuildLogger gl = new GuildLogger();
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Discriminator Update");
		eb.setDescription(e.getUser().getAsMention() + " updated it's discriminator.");
		eb.setFooter(stime);
		eb.setColor(gl.green);
		eb.addField("Old Discriminator:", e.getOldDiscriminator(), false);
		eb.addField("New Discriminator:", e.getNewDiscriminator(), false);
		List<Guild> guilds = e.getUser().getMutualGuilds();
		if(!(guilds.isEmpty()) && !(e.getUser().isBot())) {
			for(Guild guild : guilds) {
				gl.sendMsg(eb, guild);
			}
		}
	}

}
