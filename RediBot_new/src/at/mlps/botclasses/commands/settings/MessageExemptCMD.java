package at.mlps.botclasses.commands.settings;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class MessageExemptCMD extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "messagelogging")) {
				chan.sendMessage("In case of you don't know the proper syntax,\nenter this command: ``[p]messagelogging help``").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "messagelogging")) {
				if(args[1].equalsIgnoreCase("show")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							YamlFile cfg = new YamlFile("guildsettings.yml");
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							if(!cfg.contains("Exempts." + g.getIdLong())) {
								chan.sendMessage(failed + "This guild doesn't have any exempted channels.").queue();
							}else {
								List<Long> exempts = cfg.getLongList("Exempts." + g.getIdLong());
								if(exempts.isEmpty()) {
									chan.sendMessage(failed + "This guild doesn't have any exempted channels.").queue();
								}else {
									List<String> exempts_String = new ArrayList<>();
									for(Long l : exempts) {
										TextChannel channel = g.getTextChannelById(l);
										if(channel == null) {
											exempts_String.add("``" + l + "`` - deleted Channel");
										}else {
											exempts_String.add("``" + channel.getIdLong() + "`` - " + channel.getAsMention());
										}
									}
									StringBuilder sb = new StringBuilder();
									for(String s : exempts_String) {
										sb.append(s);
										sb.append("\n");
									}
									EmbedBuilder eb = new EmbedBuilder();
									eb.setColor(m.getColor());
									eb.setTitle("Exempted Channels");
									eb.setDescription(sb.toString());
									chan.sendMessage(eb.build()).queue();
								}
							}
						}else {
							chan.sendMessage(failed + "This guild is not registered!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]messagelogging Command");
					eb.setDescription("Despite the fact the bot logs all messages sent in the server unless it's a webhook message or a bot message, he don't.\nWe all have a channel who noone should see what this channel contains.\nWhen exempting a channel, the bot wont be triggered on a message update or delete.\nSo no message will appear in the modlog channel.\nStill the bot logs the message. But all messages are encrypted and deleted after 30 days.");
					eb.addField("[p]messagelogging help", "Displays this embed", false);
					eb.addField("[p]messagelogging show", "Returns a list of exempted Channels", false);
					eb.addField("[p]messagelogging exempt <Channel#Mention|SnowflakeID>", "Exempts a channel from logging.", false);
					eb.addField("[p]messagelogging unexempt <Channel#Mention|SnowflakeID>", "Unexempts a channel from logging.", false);
					chan.sendMessage(eb.build()).queue();
				}
			}
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "messagelogging")) {
				if(args[1].equalsIgnoreCase("exempt")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							YamlFile cfg = new YamlFile("guildsettings.yml");
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							long uid = 0L;
							TextChannel exempted = null;
							if(args[2].matches("^[0-9]+$")) {
								uid = Long.parseLong(args[2]);
								exempted = g.getTextChannelById(uid);
							}else {
								if(args[2].startsWith("!")) {
									exempted = g.getTextChannelsByName(args[2].substring(1), true).get(0);
								}else {
									exempted = e.getMessage().getMentionedChannels().get(0);
								}
							}
							long channelID = exempted.getIdLong();
							if(!cfg.contains("Exempts." + g.getIdLong())) {
								List<Long> exempts = new ArrayList<>();
								exempts.add(channelID);
								cfg.set("Exempts." + g.getIdLong(), exempts);
								try {
									cfg.save();
									chan.sendMessage(success + "This channel is now exempted from logging.").queue();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}else {
								List<Long> exempts = cfg.getLongList("Exempts." + g.getIdLong());
								if(exempts.contains(channelID)) {
									chan.sendMessage(failed + "This channel is already exempted!").queue();
								}else {
									exempts.add(channelID);
									cfg.set("Exempts." + g.getIdLong(), exempts);
									try {
										cfg.save();
										chan.sendMessage(success + "This channel is now exempted.").queue();
									}catch (IOException e1) {
										
									}
								}
							}
						}else {
							chan.sendMessage(failed + "This guild is not registered!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("unexempt")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							YamlFile cfg = new YamlFile("guildsettings.yml");
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							long uid = 0L;
							TextChannel exempted = null;
							if(args[2].matches("^[0-9]+$")) {
								uid = Long.parseLong(args[2]);
								exempted = g.getTextChannelById(uid);
							}else {
								if(args[2].startsWith("!")) {
									exempted = g.getTextChannelsByName(args[2].substring(1), true).get(0);
								}else {
									exempted = e.getMessage().getMentionedChannels().get(0);
								}
							}
							long channelID = exempted.getIdLong();
							if(!cfg.contains("Exempts." + g.getIdLong())) {
								chan.sendMessage(failed + "This guild doesn't have any exempted channels.").queue();
							}else {
								List<Long> exempts = cfg.getLongList("Exempts." + g.getIdLong());
								if(exempts.contains(channelID)) {
									exempts.remove(channelID);
									cfg.set("Exempts." + g.getIdLong(), exempts);
									try {
										cfg.save();
										chan.sendMessage(success + "This channel is no longer exempted!").queue();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}else {
									chan.sendMessage(failed + "This channel is not exempted!").queue();
								}
							}
						}else {
							chan.sendMessage(failed + "This guild is not registered!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
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
	
	private boolean isRegistered(long guildid) {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		try {
			if(Main.mysql.isInDatabase("redibot_guildlog", hm)) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}