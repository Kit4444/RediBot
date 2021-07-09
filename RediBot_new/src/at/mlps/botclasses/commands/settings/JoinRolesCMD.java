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
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinRolesCMD extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "joinroles")) {
				chan.sendMessage("In case of you don't know the proper syntax,\nenter this command: ``[p]joinroles help``").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "joinroles")) {
				if(args[1].equalsIgnoreCase("help")) {
					    EmbedBuilder eb = new EmbedBuilder();
					    eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
						eb.setTitle("Guide for the [p]joinroles Command");
						eb.setDescription("If you want to have the bot assigning specified roles to a member when he is joining, you are right here.");
						eb.addField("[p]joinroles help", "Displays this embed", false);
						eb.addField("[p]joinroles show", "Returns a list of the Joinroles", false);
						eb.addField("[p]joinroles add <Role@Mention|SnowflakeID>", "Adds a role to Joinroles.", false);
						eb.addField("[p]joinroles remove <Role@Mention|SnowflakeID>", "Removes a role from Joinroles.", false);
						chan.sendMessageEmbeds(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("show")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							YamlFile cfg = new YamlFile("configs/guildsettings.yml");
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							if(!cfg.contains("Joinroles." + g.getIdLong())) {
								chan.sendMessage(failed + "This Guild doesn't have any joinroles.").queue();
							}else {
								List<Long> joinroles = cfg.getLongList("Joinroles." + g.getIdLong());
								if(joinroles.isEmpty()) {
									chan.sendMessage(failed + "This Guild doesn't have any joinroles.").queue();
								}else {
									List<String> joinStrings = new ArrayList<>();
									for(Long l : joinroles) {
										Role role = g.getRoleById(l);
										if(role == null) {
											joinStrings.add("``" + l + "`` - deleted Role");
										}else {
											joinStrings.add("``" + role.getIdLong() + "`` - " + role.getAsMention());
										}
									}
									StringBuilder sb = new StringBuilder();
									for(String s : joinStrings) {
										sb.append(s);
										sb.append("\n");
									}
									EmbedBuilder eb = new EmbedBuilder();
									eb.setColor(m.getColor());
									eb.setTitle("Joinroles");
									eb.setDescription(sb.toString());
									chan.sendMessageEmbeds(eb.build()).queue();
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
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "joinroles")) {
				if(args[1].equalsIgnoreCase("add")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							YamlFile cfg = new YamlFile("configs/guildsettings.yml");
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							long uid = 0L;
							Role exempted = null;
							if(args[2].matches("^[0-9]+$")) {
								uid = Long.parseLong(args[2]);
								exempted = g.getRoleById(uid);
							}else {
								if(args[2].startsWith("!")) {
									exempted = g.getRolesByName(args[2].substring(1), true).get(0);
								}else {
									exempted = e.getMessage().getMentionedRoles().get(0);
								}
							}
							long channelID = exempted.getIdLong();
							if(!cfg.contains("Joinroles." + g.getIdLong())) {
								List<Long> joinroles = new ArrayList<>();
								joinroles.add(channelID);
								cfg.set("Joinroles." + g.getIdLong(), joinroles);
								try {
									cfg.save();
									chan.sendMessage(success + "This role will now be given when a member is joining.").queue();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}else {
								List<Long> joinroles = cfg.getLongList("Joinroles." + g.getIdLong());
								if(joinroles.contains(channelID)) {
									chan.sendMessage(failed + "This Role is already in the Joinroles!").queue();
								}else {
									joinroles.add(channelID);
									cfg.set("Joinroles." + g.getIdLong(), joinroles);
									try {
										cfg.save();
										chan.sendMessage(success + "This Role will now be given when a member is joining.").queue();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							}
						}else {
							chan.sendMessage(failed + "This guild is not registered!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("remove")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							YamlFile cfg = new YamlFile("configs/guildsettings.yml");
							try {
								cfg.load();
							} catch (InvalidConfigurationException | IOException e1) {
								e1.printStackTrace();
							}
							long uid = 0L;
							Role exempted = null;
							if(args[2].matches("^[0-9]+$")) {
								uid = Long.parseLong(args[2]);
								exempted = g.getRoleById(uid);
							}else {
								if(args[2].startsWith("!")) {
									exempted = g.getRolesByName(args[2].substring(1), true).get(0);
								}else {
									exempted = e.getMessage().getMentionedRoles().get(0);
								}
							}
							long channelID = exempted.getIdLong();
							if(!cfg.contains("Joinroles." + g.getIdLong())) {
								chan.sendMessage(failed + "This Guild doesn't have any joinroles.").queue();
							}else {
								List<Long> joinroles = cfg.getLongList("Joinroles." + g.getIdLong());
								if(joinroles.contains(channelID)) {
									joinroles.remove(channelID);
									cfg.set("Joinroles." + g.getIdLong(), joinroles);
									try {
										cfg.save();
										chan.sendMessage(success + "This Role won't be given anymore when a member joins.").queue();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}else {
									chan.sendMessage(failed + "This Role is not in the joinroles.").queue();
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