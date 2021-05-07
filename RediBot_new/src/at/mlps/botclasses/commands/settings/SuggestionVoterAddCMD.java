package at.mlps.botclasses.commands.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestionVoterAddCMD extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		YamlFile cfg = new YamlFile("configs/guildsettings.yml");
		TextChannel chan = e.getChannel();
		//command syntax: [p]suggestionchannel add|remove|list #channel
		if(args.length == 2) {
			//list
			if(args[0].equalsIgnoreCase(Main.botprefix + "suggestionchannel")) {
				if(args[1].equalsIgnoreCase("list")) {
					try {
						cfg.load();
					} catch (InvalidConfigurationException | IOException e1) {
						e1.printStackTrace();
					}
					if(!cfg.contains("SuggestionVoter.list." + g.getIdLong())) {
						chan.sendMessage(failed + "This guild does not have any suggestion channels").queue();
					}else {
						List<Long> suggChannel = cfg.getLongList("SuggestionVoter.list." + g.getIdLong());
						if(suggChannel.isEmpty()) {
							chan.sendMessage(failed + "This guild does not have any suggestion channels").queue();
						}else {
							List<String> suggStrings = new ArrayList<>();
							for(Long l : suggChannel) {
								TextChannel tchan = g.getTextChannelById(l);
								if(tchan == null) {
									suggStrings.add("``" + l + "`` - deleted channel");
								}else {
									suggStrings.add("``" + l + "`` - " + tchan.getAsMention());
								}
							}
							StringBuilder sb = new StringBuilder();
							for(String s : suggStrings) {
								sb.append(s);
								sb.append("\n");
							}
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(m.getColor());
							eb.setTitle("Suggestion Channels (" + suggStrings.size() + ")");
							eb.setDescription(sb.toString());
							chan.sendMessage(eb.build()).queue();
						}
					}
				}
			}
		}else if(args.length == 3) {
			//add remove
			if(args[0].equalsIgnoreCase(Main.botprefix + "suggestionchannel")) {
				if(args[1].equalsIgnoreCase("add")) {
					if(hasSettingPerms(m)) {
						long uid = 0;
						TextChannel tchan = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							tchan = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								tchan = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								tchan = e.getMessage().getMentionedChannels().get(0);
							}
						}
						if(tchan != null) {
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							if(cfg.contains("SuggestionVoter.list." + g.getIdLong())) {
								List<Long> suggChannels = cfg.getLongList("SuggestionVoter.list." + g.getIdLong());
								if(suggChannels.contains(tchan.getIdLong())) {
									chan.sendMessage(failed + "This channel is already a suggestion channel!").queue();
								}else {
									suggChannels.add(tchan.getIdLong());
									cfg.set("SuggestionVoter.list." + g.getIdLong(), suggChannels);
									try {
										cfg.save();
										chan.sendMessage(success + "The channel " + tchan.getAsMention() + " is now a suggestion channel.").queue();
									} catch (IOException e1) {
										chan.sendMessage(failed + "Oh no! Something went wrong! Get in touch with Newt#3100 ASAP! EC: 104").queue();
										e1.printStackTrace();
									}
								}
							}else {
								List<Long> suggChannels = new ArrayList<>();
								suggChannels.add(tchan.getIdLong());
								cfg.set("SuggestionVoter.list." + g.getIdLong(), suggChannels);
								try {
									cfg.save();
									chan.sendMessage(success + "The channel " + tchan.getAsMention() + " is now a suggestion channel.").queue();
								} catch (IOException e1) {
									chan.sendMessage(failed + "Oh no! Something went wrong! Get in touch with Newt#3100 ASAP! EC: 104").queue();
									e1.printStackTrace();
								}
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("remove")) {
					if(hasSettingPerms(m)) {
						long uid = 0;
						TextChannel tchan = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							tchan = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								tchan = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								tchan = e.getMessage().getMentionedChannels().get(0);
							}
						}
						if(tchan != null) {
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							if(cfg.contains("SuggestionVoter.list." + g.getIdLong())) {
								List<Long> suggChannels = cfg.getLongList("SuggestionVoter.list." + g.getIdLong());
								if(!suggChannels.contains(tchan.getIdLong())) {
									chan.sendMessage(failed + "This channel is not a suggestion channel!").queue();
								}else {
									suggChannels.remove(tchan.getIdLong());
									cfg.set("SuggestionVoter.list." + g.getIdLong(), suggChannels);
									try {
										cfg.save();
										chan.sendMessage(success + "The channel " + tchan.getAsMention() + " is not anymore a suggestion channel.").queue();
									} catch (IOException e1) {
										chan.sendMessage(failed + "Oh no! Something went wrong! Get in touch with Newt#3100 ASAP! EC: 104").queue();
										e1.printStackTrace();
									}
								}
							}else {
								chan.sendMessage(failed + "This guild has no suggestion channels.").queue();
							}
						}else {
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							if(cfg.contains("SuggestionVoter.list." + g.getIdLong())) {
								List<Long> suggChannels = cfg.getLongList("SuggestionVoter.list." + g.getIdLong());
								if(suggChannels.contains(uid)) {
									suggChannels.remove(uid);
									cfg.set("SuggestionVoter.list." + g.getIdLong(), suggChannels);
									try {
										cfg.save();
										chan.sendMessage(success + "The channel ``" + uid + "`` / ``deleted channel`` is not anymore a suggestion channel.").queue();
									} catch (IOException e1) {
										chan.sendMessage(failed + "Oh no! Something went wrong! Get in touch with Newt#3100 ASAP! EC: 104").queue();
										e1.printStackTrace();
									}
								}else {
									chan.sendMessage(failed + "This channel is not a suggestion channel!").queue();
								}
							}else {
								chan.sendMessage(failed + "This guild has no suggestion channels.").queue();
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("setreactions")) {
					if(args[2].equalsIgnoreCase("0") || args[2].equalsIgnoreCase("default")) {
						try {
							cfg.load();
							cfg.set("SuggestionVoter.reaction." + g.getIdLong(), 0);
							cfg.save();
							chan.sendMessage(success + "The reactions are now as followed: <:upvote:671772876474679326> <:neutralvote:671772876453707776> <:downvote:671772876432605204>").queue();
						} catch (InvalidConfigurationException | IOException e1) {
							e1.printStackTrace();
						}
					}else if(args[2].equalsIgnoreCase("1")) {
						try {
							cfg.load();
							cfg.set("SuggestionVoter.reaction." + g.getIdLong(), 1);
							cfg.save();
							chan.sendMessage(success + "The reactions are now as followed: <:upvote:671772876474679326> <:downvote:671772876432605204>").queue();
						} catch (InvalidConfigurationException | IOException e1) {
							e1.printStackTrace();
						}
					}else if(args[2].equalsIgnoreCase("2")) {
						try {
							cfg.load();
							cfg.set("SuggestionVoter.reaction." + g.getIdLong(), 2);
							cfg.save();
							chan.sendMessage(success + "The reactions are now as followed: <:plus:836638630109118474> <:minus:836638629978701894>").queue();
						} catch (InvalidConfigurationException | IOException e1) {
							e1.printStackTrace();
						}
					}else {
						chan.sendMessage(failed + "Please choose a number between 0 - 2 or ``default``").queue();
					}
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "suggestionchannel")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
				eb.setColor(m.getColor());
				eb.setTitle("Guide for the [p]suggestionchannel Command");
				eb.addField("[p]suggestionchannel add <Mention#Channel|SnowflakeID>", "Adds the Channel and the bot will react with <:upvote:671772876474679326> <:neutralvote:671772876453707776> <:downvote:671772876432605204> when a message is sent in this channel", false);
				eb.addField("[p]suggestionchannel remove <Mention#Channel|SnowflakeID>", "Removes the channel and the bot will stop reacting.", false);
				eb.addField("[p]suggestionchannel list", "List all channels in this guild", false);
				eb.addField("[p]suggestionchannel setreactions <ReactionType>", "Set a reaction-type. Default and ``0`` is <:upvote:671772876474679326> <:neutralvote:671772876453707776> <:downvote:671772876432605204>, ``1`` is <:upvote:671772876474679326> <:downvote:671772876432605204> and ``2`` is <:plus:836638630109118474> <:minus:836638629978701894>", false);
				chan.sendMessage(eb.build()).queue();
			}
		}
	}
	
	private boolean hasSettingPerms(Member m) {
		boolean boo = false;
		if(m.hasPermission(Permission.ADMINISTRATOR) || m.hasPermission(Permission.getPermissions(Permission.ALL_GUILD_PERMISSIONS)) || m.hasPermission(Permission.MANAGE_SERVER) || m.isOwner()) {
			boo = true;
		}else {
			boo = false;
		}
		return boo;
	}
}