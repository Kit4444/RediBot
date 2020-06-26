package at.mlps.botclasses.commands;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Tags extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		MessageChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "tags")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(e.getMember().getColor());
				eb.setTitle("Tags");
				//eb.addField(Main.botprefix + "tag ", "", false);
				eb.addField(Main.botprefix + "tag ask", "``Don't ask ...``", false);
				eb.addField(Main.botprefix + "tag when", "We don't know, wh...", false);
				chan.sendMessage(eb.build()).queue();
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "tag")) {
				chan.sendMessage("<:deny:678036504702091278> | " + Main.botprefix + "tag <tag> | Get the tags with " + Main.botprefix + "tags").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "tag")) {
				String tag = args[1];
				if(tag.equalsIgnoreCase("ask")) {
					chan.sendMessage("``Don't ask to ask. Just ask. If you write your question directly, more people can help you more faster.``").queue();
				}else if(tag.equalsIgnoreCase("when")) {
					chan.sendMessage("We don't know, when the latest version will be released.\nFirstly has Mojang to release the Version.\nSecondly Spigot has to upgrade their Builds also BungeeCord.\nAfter this, Maurice has to upgrade every server and their plugins.\n \nAsking when it comes won't speed up the progress.").queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "tag")) {
				chan.sendMessage("<:deny:678036504702091278> | " + Main.botprefix + "tag <tag> | Get the tags with " + Main.botprefix + "tags").queue();
			}
		}
	}

}
