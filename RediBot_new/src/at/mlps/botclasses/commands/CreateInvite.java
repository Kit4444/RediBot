package at.mlps.botclasses.commands;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CreateInvite extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "createinvite")) {
				TextChannel welcome = e.getGuild().getTextChannelById(548191322738130944L);
				welcome.createInvite().setMaxUses(1).setMaxAge(1800).reason(e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + " has created an invite.").queue(inv -> {
					e.getAuthor().openPrivateChannel().queue(pchannel -> {
						pchannel.sendMessage("Hello " + e.getAuthor().getAsMention() + "! You have requested a invite code for RediCraft.\nInvite: " + inv.getUrl()).queue();
					});
				});
			}
		}
	}
}