package at.mlps.botclasses.commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StaffChangeSCINT extends ListenerAdapter{
	
	static long channelid = 854019432745533460l;
	static long guildid = 777578576282255411l;
	static String colcode = "#EE2437";
	static String tnurl = "https://cdn.discordapp.com/attachments/830137556770357320/857222756106371072/synergylogo.png";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String text = "";
		if(e.getGuild().getIdLong() == guildid) {
			if(e.getChannel().getIdLong() == channelid) {
				if(!e.getAuthor().isBot() || !(e.getAuthor().getIdLong() == 588547204063428637L)) {
					text = e.getMessage().getContentRaw();
					e.getMessage().delete().queue();
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription(text);
					eb.setColor(Color.decode(colcode));
					eb.setThumbnail(tnurl);
					e.getChannel().sendMessageEmbeds(eb.build()).queue();
				}
			}
		}
	}

}
