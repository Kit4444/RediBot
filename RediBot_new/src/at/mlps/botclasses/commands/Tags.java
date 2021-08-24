package at.mlps.botclasses.commands;

import at.mlps.main.Main;
import at.mlps.main.StatisticsClass;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Tags extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		StatisticsClass.messages = StatisticsClass.messages + 1;
		String[] args = e.getMessage().getContentRaw().split(" ");
		MessageChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "tags")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(e.getMember().getColor());
				eb.setTitle("Tags");
				//eb.addField(Main.botprefix + "tag ", "", false);
				eb.addField(Main.botprefix + "tag ask", "Don't ask to a...", false);
				eb.addField(Main.botprefix + "tag when", "We don't know, wh...", false);
				eb.addField(Main.botprefix + "tag ijava", "For optimal game ...", false);
				eb.addField(Main.botprefix + "tag mclauncher", "Who don't kn...", false);
				eb.addField(Main.botprefix + "tag too_much_ram", "Too much RA...", false);
				eb.addField(Main.botprefix + "tag ipv4", "When you hav...", false);
				chan.sendMessage(eb.build()).queue();
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "tag")) {
				chan.sendMessage("<:deny:678036504702091278> | " + Main.botprefix + "tag <tag> | Get the tags with " + Main.botprefix + "tags").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "tag")) {
				String tag = args[1];
				if(tag.equalsIgnoreCase("ask")) {
					chan.sendMessage("Don't ask to ask. Just ask. If you write your question directly, more people can help you more faster.").queue();
				}else if(tag.equalsIgnoreCase("when")) {
					chan.sendMessage("We don't know, when the latest version will be released.\nFirstly has Mojang to release the Version.\nSecondly Spigot has to upgrade their Builds also BungeeCord.\nAfter this, Maurice has to upgrade every server and their plugins.\n \nAsking when it comes won't speed up the progress.").queue();
				}else if(tag.equalsIgnoreCase("ijava")) {
					chan.sendMessage("For optimal game experience, we recommend you to use Java 8.\nhttps://github.com/MultiMC/MultiMC5/wiki/Using-the-right-Java").queue();
				}else if(tag.equalsIgnoreCase("mclauncher")) {
					chan.sendMessage("Who don't know it. You have many profiles and you also playing with more than two modpacks? Then use MultiMC!\nIt's a great launcher with their own installation per instance.\nThere are no more mod-version conflicts and you also can sort them by groups you create.\n \nDownload it here: https://multimc.org/").queue();
				}else if(tag.equalsIgnoreCase("too_much_ram")) {
					chan.sendMessage("Too much RAM lets java be lazy with clearing out unused data so you'll get major lagspikes since the useless data piles up.\nToo little RAM will leave you with poor performance all around because java has to try to make space for new stuff all the time.\nTry to find a good balance.").queue();
				}else if(tag.equalsIgnoreCase("ipv4")) {
					chan.sendMessage("When you have IPv6 and encounter problems, you have to add ``-Djava.net.preferIPv4Stack=true`` in your Java-Arguments.").queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "tag")) {
				chan.sendMessage("<:deny:678036504702091278> | " + Main.botprefix + "tag <tag> | Get the tags with " + Main.botprefix + "tags").queue();
			}
		}
	}

}
