package at.mlps.botclasses.commands;

import java.util.ArrayList;
import java.util.List;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmojiList extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String cont = e.getMessage().getContentRaw();
		Member m = e.getMember();
		Guild g = e.getGuild();
		TextChannel chan = e.getChannel();
		if(cont.equalsIgnoreCase(Main.botprefix + "emojilist")) {
			List<Emote> nonAnim = new ArrayList<>();
			List<Emote> Anim = new ArrayList<>();
			for(Emote em : g.getEmotes()) {
				if(em.isAnimated()) {
					Anim.add(em);
				}else {
					nonAnim.add(em);
				}
			}
			EmbedBuilder ebAnim = new EmbedBuilder();
			EmbedBuilder ebNonAnim = new EmbedBuilder();
			StringBuilder sbAnim = new StringBuilder();
			StringBuilder sbNonAnim = new StringBuilder();
			for(Emote em : nonAnim) {
				sbNonAnim.append(em.getAsMention() + " - ``:" + em.getName() + ":``");
				sbNonAnim.append("\n");
			}
			for(Emote em : Anim) {
				sbAnim.append(em.getAsMention() + " - ``:" + em.getName() + ":``");
				sbAnim.append("\n");
			}
			ebNonAnim.setDescription(sbNonAnim.toString());
			ebAnim.setDescription(sbAnim.toString());
			ebNonAnim.setColor(m.getColor());
			ebAnim.setColor(m.getColor());
			ebNonAnim.setAuthor("Static Emojis (" + nonAnim.size() + ")", g.getIconUrl());
			ebAnim.setAuthor("Animated Emojis (" + Anim.size() + ")", g.getIconUrl());
			chan.sendMessage(ebNonAnim.build()).queue();
			chan.sendMessage(ebAnim.build()).queue();
		}
	}

}
