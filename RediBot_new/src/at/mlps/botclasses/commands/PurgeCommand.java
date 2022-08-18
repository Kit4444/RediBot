package at.mlps.botclasses.commands;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PurgeCommand extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		MessageChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "purge") || args[0].equalsIgnoreCase(Main.botprefix + "clear")) {
				if(e.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
					chan.sendMessage("<:deny:597877001264824320> **|** Usage: !purge <Messages (max. 100)>").queue();
				}
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "purge") || args[0].equalsIgnoreCase(Main.botprefix + "clear")) {
				int msgs = Integer.valueOf(args[1]);
				if(e.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
					TextChannel chann = (TextChannel) chan;
					e.getMessage().delete().queue();
					clear(chann, msgs, e.getMember());
				}
			}
		}
	}
	
	private void clear(TextChannel channel, int msgtodel, Member member) {
		if(msgtodel >= 101) {
			channel.sendMessage("You can't delete more than 100 messages in one command!").queue();
		}else {
			OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);
			List<Message> messages = channel.getHistory().retrievePast(msgtodel).complete();
			messages.removeIf(m -> m.getTimeCreated().isBefore(twoWeeksAgo));
			if(messages.isEmpty()) {
				System.out.println("Done deleting: " + channel.getName());
				return;
			}else {
				List<String> msgs = new ArrayList<>();
				messages.forEach(m -> msgs.add(m.getAuthor().getAsMention() + ": " + m.getContentDisplay()));
				channel.deleteMessages(messages).complete();
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle((msgtodel + 1) + " Messages has been deleted.");
				eb.setAuthor(member.getUser().getName() + "#" + member.getUser().getDiscriminator());
				StringBuilder sb = new StringBuilder();
				for(String s : msgs) {
					sb.append(s);
					sb.append("\n");
				}
				if(sb.toString().length() >= 512) {
					eb.addField("Messages deleted:", sb.toString().substring(0, 512), false);
				}else {
					eb.addField("Messages deleted:", sb.toString(), false);
				}
				
				eb.setColor(member.getColor());
				channel.sendMessageEmbeds(eb.build()).queue(msg -> {
					msg.delete().queueAfter(5, TimeUnit.SECONDS);
				});
				
			}
		}
	}
}