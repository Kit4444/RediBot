package at.mlps.botclasses.commands.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoEmbed extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "autoembed")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
				eb.setTitle("Guide for the [p]autoembed Command");
				eb.setDescription("The Autoembed Function is a quite handy feature for example for Staff-Changes like here: https://prnt.sc/18p3fz7 .\nIt's fully customizeable like with the logo and the footer, side-color, etc.");
				eb.addField("[p]autoembed help", "Shows this embed", true);
				eb.addField("[p]autoembed addchannel <Channel>", "Adds an Auto-Embed Channel. Do not use a channel where everyone can write.", true);
				eb.addField("[p]autoembed removechannel <Channel>", "Removes an Auto-Embed Channel", true);
				eb.addField("[p]autoembed list", "Lists all Auto-Embed Channels on the current guild", true);
				eb.addField("[p]autoembed setthumbnail [Type]", "Sets the Thumbnail in the Embed. Either choosing the Guild Icon or a custom URL like from Imgur or don't specify a parameter and upload a picture while executing the command.", true);
				eb.addField("[p]autoembed setcolor <Type>", "Sets a color for the Embed", true);
				eb.addField("[p]autoembed showconfig", "Shows, how an example would look like", true);
				chan.sendMessageEmbeds(eb.build()).queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "autoembed")) {
				if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]autoembed Command");
					eb.setDescription("The Autoembed Function is a quite handy feature for example for Staff-Changes like here: https://prnt.sc/18p3fz7 .\nIt's fully customizeable like with the logo and the footer, side-color, etc.");
					eb.addField("[p]autoembed help", "Shows this embed", true);
					eb.addField("[p]autoembed addchannel <Channel>", "Adds an Auto-Embed Channel. Do not use a channel where everyone can write.", true);
					eb.addField("[p]autoembed removechannel <Channel>", "Removes an Auto-Embed Channel", true);
					eb.addField("[p]autoembed list", "Lists all Auto-Embed Channels on the current guild", true);
					eb.addField("[p]autoembed setthumbnail [Type]", "Sets the Thumbnail in the Embed. Either choosing the Guild Icon or a custom URL like from Imgur or don't specify a parameter and upload a picture while executing the command.", true);
					eb.addField("[p]autoembed setcolor <Type>", "Sets a color for the Embed", true);
					eb.addField("[p]autoembed showconfig", "Shows, how an example would look like", true);
					chan.sendMessageEmbeds(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("showconfig")) {
					
				}else if(args[1].equalsIgnoreCase("list")) {
					YamlFile cfg = new YamlFile("configs/guildsettings.yml");
					try {
						cfg.load();
					} catch (InvalidConfigurationException | IOException e1) {
						e1.printStackTrace();
					}
					if(!cfg.contains("AutoEmbedChannels." + g.getIdLong())) {
						chan.sendMessage("This guild has not any AutoEmbed Channels yet").queue();
					}else {
						List<Long> channelList = cfg.getLongList("AutoEmbedChannels." + g.getIdLong());
						if(channelList.isEmpty()) {
							chan.sendMessage("This guild has not any AutoEmbed Channels.").queue();
						}else {
							List<String> stringChannelList = new ArrayList<>();
							for(Long l : channelList) {
								TextChannel channel = g.getTextChannelById(l);
								if(channel == null) {
									stringChannelList.add("``" + l + " `` - deleted Channel");
								}else {
									stringChannelList.add("``" + l + "`` - " + channel.getAsMention());
								}
							}
							StringBuilder sb = new StringBuilder();
							for(String s : stringChannelList) {
								sb.append(s);
								sb.append("\n");
							}
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(m.getColor());
							eb.setTitle("Auto-Embed Channels");
							eb.setDescription(sb.toString());
							chan.sendMessageEmbeds(eb.build()).queue();
						}
					}
				}
			}
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "autoembed")) {
				if(args[1].equalsIgnoreCase("addchannel")) {
					
				}else if(args[1].equalsIgnoreCase("removechannel")) {
					
				}else if(args[1].equalsIgnoreCase("setthumbnail")) {
					
				}else if(args[1].equalsIgnoreCase("setcolor")) {
					
				}
			}
		}
	}

}
