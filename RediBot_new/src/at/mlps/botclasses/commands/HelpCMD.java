package at.mlps.botclasses.commands;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCMD extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel chan = e.getChannel();
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "help") || args[0].equalsIgnoreCase(Main.botprefix + "botcommands")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(e.getMember().getColor());
				eb.setDescription("All commands are listed in alphabetic order.");
				if(args[1].equalsIgnoreCase("botowner")) {
					eb.addField(Main.botprefix + "addallusers", "Saves all users to File. (Optional for leavemsg)", false);
					eb.addField(Main.botprefix + "registerguild", "Botownercommand - registers a guild for guildlogging", false);
					eb.addField(Main.botprefix + "setactivity", "Botownercommand", false);
					eb.addField(Main.botprefix + "setgame", "Botownercommand", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("admin")) {
					eb.addField(Main.botprefix + "announce", "Announce something in a specified channel", false);
					eb.addField(Main.botprefix + "faq", "Admin Command", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("mod")) {
					eb.addField(Main.botprefix + "ban", "Bans a member from the guild.", false);
					eb.addField(Main.botprefix + "kick", "Kicks a member from this guild.", false);
					eb.addField(Main.botprefix + "mute", "Removes all roles and mutes him in this way.", false);
					eb.addField(Main.botprefix + "purge", "Mod Command, deletes up to 100 messages on one command.", false);
					eb.addField(Main.botprefix + "warn", "Warns a Member.", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("redicraft")) {
					eb.addField(Main.botprefix + "aua", "Fun command", false);
					eb.addField(Main.botprefix + "furry", "Fun command", false);
					eb.addField(Main.botprefix + "loa", "Staff command", false);
					eb.addField(Main.botprefix + "servers", "List all servers from Minecraft and give small informations about it.", false);
					eb.addField(Main.botprefix + "stream", "RediCraft Admin Command", false);
					eb.addField(Main.botprefix + "tag", "Display's a tag.", false);
					eb.addField(Main.botprefix + "tags", "List all tags.", false);
					eb.addField(Main.botprefix + "userinfo", "Displays some infos of your account what we stored.\nAlias: " + Main.botprefix + "user", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("user")) {
					eb.addField(Main.botprefix + "about", "Displays some infos who has written the bot.", false);
					eb.addField(Main.botprefix + "avatar", "Sends your avatar.", false);
					eb.addField(Main.botprefix + "botcommands", "Displays this embed \nAlias: " + Main.botprefix + "help", false);
					eb.addField(Main.botprefix + "botinfo", "Displays some informations regarding this bot.", false);
					eb.addField(Main.botprefix + "changelog", "Lists a changelog", false);
					eb.addField(Main.botprefix + "guilds", "Displays a list where the bot is in", false);
					eb.addField(Main.botprefix + "makemeacheesesandwich", "Fun command", false);
					eb.addField(Main.botprefix + "ping", "See the bot's ping", false);
					eb.addField(Main.botprefix + "server", "List detailed informations from a specified server.", false);
					eb.addField(Main.botprefix + "serverinfo", "Displays some informations regarding the guild where the command was sent in.\nThis is just for registered guilds available!", false);
					eb.addField(Main.botprefix + "whois", "Check the user when he has been registered, joined the guild and so on.", false);
					chan.sendMessage(eb.build()).queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "help") || args[0].equalsIgnoreCase(Main.botprefix + "botcommands")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Helplist");
				eb.addField(Main.botprefix + "help botowner", "Those commands are just available for the bot creator", false);
				eb.addField(Main.botprefix + "help admin", "Those commands are just available for server admins", false);
				eb.addField(Main.botprefix + "help mod", "Those commands are just available for server mods and higher", false);
				eb.addField(Main.botprefix + "help redicraft", "Those commands are mostly useable for everyone. RediCraft-Specific", false);
				eb.addField(Main.botprefix + "help user", "Those commands are for everyone.", false);
				eb.setFooter("Requested by: " + e.getMember().getUser().getName() + "#" + e.getMember().getUser().getDiscriminator(), e.getMember().getUser().getAvatarUrl());
				chan.sendMessage(eb.build()).queue();
			}
		}
	}
}