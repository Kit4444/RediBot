package botclasses;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import net.dv8tion.jda.api.Permission;
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
				}else {
					e.getMember().getUser().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Hey you, you can't use the !purge command, as you don't have the Moderator - Role.").queue();
					});
				}
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "purge") || args[0].equalsIgnoreCase(Main.botprefix + "clear")) {
				int msgs = Integer.valueOf(args[1]);
				if(e.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
					TextChannel chann = (TextChannel) chan;
					clear(chann, msgs);
				}else {
					e.getMember().getUser().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Hey you, you can't use the !purge command, as you don't have the Moderator - Role.").queue();
					});
				}
			}
		}
	}
	
	private void clear(TextChannel channel, int msgtodel) {
		if(msgtodel >= 101) {
			channel.sendMessage("You can't delete more than 100 messages in one command!").queue();
		}else {
			OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);
			System.out.println("Deleting " + msgtodel + " messages in channel: " + channel.getName());
			List<Message> messages = channel.getHistory().retrievePast((msgtodel + 1)).complete();
			messages.removeIf(m -> m.getTimeCreated().isBefore(twoWeeksAgo));
			if(messages.isEmpty()) {
				System.out.println("Done deleting: " + channel.getName());
				return;
			}else {
				messages.forEach(m -> System.out.println("Deleting: " + m));
				channel.deleteMessages(messages).complete();
				channel.sendMessage("Deleted " + (msgtodel + 1) + " Messages in " + channel.getAsMention()).queue(msg -> {
					msg.delete().queueAfter(5, TimeUnit.SECONDS);
				});
				
			}
		}
	}
}