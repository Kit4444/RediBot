package at.mlps.botclasses.commands.settings;

import java.util.ArrayList;
import java.util.List;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SettingsCommand extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings") || args[0].equalsIgnoreCase(Main.botprefix + "configuration")) {
				List<String> cmdex = new ArrayList<>();
				cmdex.add("[p]welcomer help - Say hello to your new members");
				cmdex.add("[p]prefix - ***A prefix can be set, but it doesn't work yet***");
				cmdex.add("[p]guildlogs help - This Bot also can log like guildupdates or memberupdates");
				cmdex.add("[p]messagelogging - You have a super secret channel noone should know about?");
				cmdex.add("[p]xpsettings - Settings for the XP Leveling");
				cmdex.add("[p]joinroles - Give newly joined members a role");
				cmdex.add("[p]reactionroles - Let your users claim roles by reacting to a message.");
				cmdex.add("[p]invitechannel - ***Under work***");
				cmdex.add("[p]automod - ***This feature does NOT exists yet***");
				cmdex.add("[p]autoembed help - Set a channel, the bot deletes it and it will be auto-embeded.");
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(m.getColor());
				eb.setDescription("You can configure some modules individually for this guild.\nThis Command just shows you the other commands.\n \nPermission needed: Owner / Server Admin / Manage Server");
				eb.addField("Commands:", getFromList(cmdex), false);
				chan.sendMessageEmbeds(eb.build()).queue(); 
			}
		}
	}
	
	private String getFromList(List<String> list) {
		
		StringBuilder sb = new StringBuilder();
		for(String ss : list) {
			sb.append(ss);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unused")
	private List<String> nodeAutoModList(){
		List<String> nodelist = new ArrayList<>();
		nodelist.add("capstext");
		nodelist.add("badwords");
		nodelist.add("links");
		nodelist.add("invites");
		nodelist.add("attachments");
		nodelist.add("spam");
		return nodelist;
	}
}