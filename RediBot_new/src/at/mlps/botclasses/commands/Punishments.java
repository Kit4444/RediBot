package at.mlps.botclasses.commands;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Punishments extends ListenerAdapter{
	
	long redimainguild = 548136727697555496L;
	long moderatorrole = 548175915054792704L;
	long redibotlog = 637362851245195264L;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		Guild g = e.getGuild();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "kick")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "mute")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "warn")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "ban")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "kick")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "mute")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "warn")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "ban")) {
				if(g.getIdLong() == redimainguild) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}
		}
	}

}