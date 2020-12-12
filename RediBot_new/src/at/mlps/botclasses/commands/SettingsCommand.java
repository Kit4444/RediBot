package at.mlps.botclasses.commands;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SettingsCommand extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings")) {
				List<String> cmdex = new ArrayList<>();
				cmdex.add("Get the Settings: [p]settings guildlogs show type");
				cmdex.add("^-> [p]settings guildlogs show voice <- This will only show the voice settings");
				cmdex.add("Get the Settingnodes: [p]settings guildlogs nodes");
				cmdex.add("Change a Settingnode: [p]settings guildlogs node boolean");
				cmdex.add("^-> [p]settings guildlogs categorycreate true <- The Bot will log, when a new category has been created.");
				cmdex.add("Adds an autorele: [p]settings joinroles add <Role@Mention|ID>");
				cmdex.add("Get the autoroles: [p]settings joinroles show");
				cmdex.add("Remove an autorole: [p]settings joinroles remove <Role@Mention|ID>");
				cmdex.add("Sets a channel for welcoming a member: [p]settings welcomechannel <Channel@Mention|ID>");
				cmdex.add("Removing a channel for welcoming a member: [p]settings welcomechannel <null|none>");
				cmdex.add("Automodconfig: [p]settings automod");
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(m.getColor());
				eb.setDescription("In order to configure the bot properly,\nyou need to know, which nodes you can set\nindividually for this guild.\n \nPermission needed: Owner / Server Admin / Manage Server");
				SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
				String member = m.getUser().getName() + "#" + m.getUser().getDiscriminator();
				eb.setFooter("Requested by: " + member + " at " + time.format(new Date()));
				eb.addField("Commandexamples:", getFromList(cmdex), false);
				chan.sendMessage(eb.build()).queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings")) {
				if(args[1].equalsIgnoreCase("guildlogs")) {
					chan.sendMessage("What do you want to do now? Please check the correct syntax.").queue();
				}else if(args[1].equalsIgnoreCase("automod")) {
					chan.sendMessage("This function is still in developement.").queue();
				}
			}
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings")) {
				if(args[1].equalsIgnoreCase("prefix")) {
					if(hasSettingPerms(m)) {
						String newPrefix = args[2];
						if(newPrefix.length() >= 1 && newPrefix.length() <= 8) {
							if(newPrefix.equalsIgnoreCase("reset")) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET botprefix = ? WHERE guildid = ?");
									ps.setString(1, "rb!");
									ps.setLong(2, g.getIdLong());
									ps.executeUpdate();
									chan.sendMessage("Success - The Prefix for " + g.getName() + " has been resetted to the default! Prefix: ``rb!``").queue();
								}catch (SQLException e1) {
									e1.printStackTrace();
								}
							}else {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET botprefix = ? WHERE guildid = ?");
									ps.setString(1, newPrefix);
									ps.setLong(2, g.getIdLong());
									ps.executeUpdate();
									chan.sendMessage("Success - The Prefix for " + g.getName() + " has been changed to ``" + newPrefix + "``!").queue();
								}catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
						}else {
							chan.sendMessage("Error - The prefix can't be longer (inclusive) than 8 characters and shorter (inclusive) than 1 character.\nThe prefix you set was " + newPrefix.length() + " characters long.").queue();
						}
					}else {
						
					}
				}else if(args[1].equalsIgnoreCase("invitechannel")) {
					if(args[2].equalsIgnoreCase("remove")) {
						if(hasSettingPerms(m)) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteChanel = ? WHERE guildid = ?");
								ps.setLong(1, 0L);
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(" Success - The Channel has been removed.").queue();
							}catch (SQLException e1) {
							}
						}else {
							
						}
					}else if(args[2].equalsIgnoreCase("get")) {
						if(hasSettingPerms(m)) {
							long id = 0L;
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
								ps.setLong(1, g.getIdLong());
								ResultSet rs = ps.executeQuery();
								rs.next();
								id = rs.getLong("InviteChanel");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							if(id == 0L) {
								chan.sendMessage("No Channel is set. Set one by executing this command: [p]settings invitechannel set <Channel#Mention|ChannelID>").queue();
							}else {
								TextChannel invitechanel = g.getTextChannelById(id);
								chan.sendMessage("The current Invite Channel is " + invitechanel.getAsMention()).queue();
							}
						}else {
							
						}
					}
				}else if(args[1].equalsIgnoreCase("guildlogs")) {
					if(args[2].equalsIgnoreCase("nodes")) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("In order to configure the bot properly,\nyou need to know, which nodes you can set\nindividually for this guild.");
						eb.addField("Textchannellogging", "textcreate, textdelete, textupdatename, textupdatensfw, textupdateparent, textupdateslowmode, textupdatetopic", false);
						eb.addField("Voicechannellogging", "voicecreate, voicedelete, voiceupdatebitrate, voiceupdatename, voiceupdateparent, voiceupdateuserlimit", false);
						eb.addField("Rolelogging", "rolecreate, roledelete, roleupdatecolor, roleupdatehoisted, roleupdatementionable, roleupdatename", false);
						eb.addField("Usereventlogging", "userupdateavatar, userupdatediscriminator, userupdatename", false);
						eb.addField("Emoteeventlogging", "emoteadd, emoteremove, emoteupdatename", false);
						eb.addField("Categoryeventlogging", "categorycreate, categorydelete, categoryupdatename", false);
						eb.addField("Guildeventlogging", "guildmemberjoin, guildmemberremove, guildmemberroleadd, guildmemberroleremove, guildupdateicon, guildupdatename, guildupdatexplicitcontentlevel, guildupdateregion, guildupdateverificationlevel, guildupdateboostcount, guildupdateboosttier, guildupdateowner, guildinvitecreate, guildinvitedelete, guildmessagedelete, guildmessageupdate, guildvoicejoin, guildvoiceleave, guildvoicemove", false);
						eb.setColor(m.getColor());
						chan.sendMessage(eb.build()).queue();
					}else if(args[2].equalsIgnoreCase("show")) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("To avoid useless spam, you can decide on your own, which Guildlog-Nodes you want to see.\nFollowing Nodes you can see: \n- textlogs \n- voicelogs \n- rolelogs \n- userlogs \n- emotelogs \n- categorylogs \n- guildlogs \n- all \n \nUsage: [p]settings guildlogs show <node>");
						eb.setColor(m.getColor());
						chan.sendMessage(eb.build()).queue();
					}
				}
			}
		}else if(args.length == 4) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings")) {
				if(args[1].equalsIgnoreCase("invitechannel")) {
					if(args[2].equalsIgnoreCase("setchannel")) {
						if(hasSettingPerms(m)) {
							long uid = 0L;
							TextChannel welcome = null;
							if(args[3].matches("^[0-9]+$")) {
								uid = Long.parseLong(args[3]);
								welcome = g.getTextChannelById(uid);
							}else {
								welcome = e.getMessage().getMentionedChannels().get(0);
							}
							long channelid = welcome.getIdLong();
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteChanel = ? WHERE guildid = ?");
								ps.setLong(1, channelid);
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(" Success - The Channel " + welcome.getAsMention() + " has been saved.").queue();
							}catch (SQLException e1) {
							}
						}
					}
				}else if(args[1].equalsIgnoreCase("guildlogs")) {
					if(args[2].equalsIgnoreCase("show")) {
						if(hasSettingPerms(m)) {
							if(nodeGuildLogTypeList().contains(args[3].toLowerCase())) {
								List<String> textlist = new ArrayList<>();
								List<String> voicelist = new ArrayList<>();
								List<String> rolelist = new ArrayList<>();
								List<String> userlist = new ArrayList<>();
								List<String> guildlist = new ArrayList<>();
								List<String> emotelist = new ArrayList<>();
								List<String> categorylist = new ArrayList<>();
								if(args[3].equalsIgnoreCase("all")) {
									//text
									textlist.add("- Update Name: " + getSetting(g.getIdLong(), "textupdatename"));
									textlist.add("- Update NSFW: " + getSetting(g.getIdLong(), "textupdatensfw"));
									textlist.add("- Update Topic: " + getSetting(g.getIdLong(), "textupdatetopic"));
									textlist.add("- Update Parent: " + getSetting(g.getIdLong(), "textupdateparent"));
									textlist.add("- Create Channel: " + getSetting(g.getIdLong(), "textcreate"));
									textlist.add("- Delete Channel: " + getSetting(g.getIdLong(), "textdelete"));
									textlist.add("- Update Slowmode: " + getSetting(g.getIdLong(), "textupdateslowmode"));
									//voice
									voicelist.add("- Update Name: " + getSetting(g.getIdLong(), "voiceupdatename"));
									voicelist.add("- Update Parent: " + getSetting(g.getIdLong(), "voiceupdateparent"));
									voicelist.add("- Create Channel: " + getSetting(g.getIdLong(), "voicecreate"));
									voicelist.add("- Delete Channel: " + getSetting(g.getIdLong(), "voicedelete"));
									voicelist.add("- Update Bitrate: " + getSetting(g.getIdLong(), "voiceupdatebitrate"));
									voicelist.add("- Update Userlimit: " + getSetting(g.getIdLong(), "voiceupdateuserlimit"));
									//role
									rolelist.add("- Create Role: " + getSetting(g.getIdLong(), "rolecreate"));
									rolelist.add("- Delete Role: " + getSetting(g.getIdLong(), "roledelete"));
									rolelist.add("- Update Name: " + getSetting(g.getIdLong(), "roleupdatename"));
									rolelist.add("- Update Color: " + getSetting(g.getIdLong(), "roleupdatecolor"));
									rolelist.add("- Update Hoisted: " + getSetting(g.getIdLong(), "roleupdatehoisted"));
									rolelist.add("- Update Mentionable: " + getSetting(g.getIdLong(), "roleupdatementionable"));
									//User
									userlist.add("- User Update Name: " + getSetting(g.getIdLong(), "userupdatename"));
									userlist.add("- User Update Avatar: " + getSetting(g.getIdLong(), "userupdateavatar"));
									userlist.add("- User Update Discriminator: " + getSetting(g.getIdLong(), "userupdatediscriminator"));
									//emote
									emotelist.add("- Create Emote: " + getSetting(g.getIdLong(), "emoteadd"));
									emotelist.add("- Delete Emote: " + getSetting(g.getIdLong(), "emoteremove"));
									emotelist.add("- Emote Update Name: " + getSetting(g.getIdLong(), "emoteupdatename"));
									//category
									categorylist.add("- Create Category: " + getSetting(g.getIdLong(), "categorycreate"));
									categorylist.add("- Delete Category: " + getSetting(g.getIdLong(), "categorydelete"));
									categorylist.add("- Update Name: " + getSetting(g.getIdLong(), "categoryupdatename"));
									//Guild
									guildlist.add("- Guild Update Owner: " + getSetting(g.getIdLong(), "guildupdateowner"));
									guildlist.add("- Guild Update Name: " + getSetting(g.getIdLong(), "guildupdatename"));
									guildlist.add("- Guild Update Icon: " + getSetting(g.getIdLong(), "guildupdateicon"));
									guildlist.add("- Guild Member Join: " + getSetting(g.getIdLong(), "guildmemberjoin"));
									guildlist.add("- Guild Member Leave: " + getSetting(g.getIdLong(), "guildmemberremove"));
									guildlist.add("- Guild Invite Create: " + getSetting(g.getIdLong(), "guildinvitecreate"));
									guildlist.add("- Guild Invite Delete: " + getSetting(g.getIdLong(), "guildinvitedelete"));
									guildlist.add("- Guild Update Region: " + getSetting(g.getIdLong(), "guildupdateregion"));
									guildlist.add("- Guild Message Update: " + getSetting(g.getIdLong(), "guildmessageupdate"));
									guildlist.add("- Guild Message Delete: " + getSetting(g.getIdLong(), "guildmessagedelete"));
									guildlist.add("- Guild Member Role Add: " + getSetting(g.getIdLong(), "guildmemberroleadd"));
									guildlist.add("- Guild Update Boost Tier: " + getSetting(g.getIdLong(), "guildupdateboosttier"));
									guildlist.add("- Guild Member Voice Join: " + getSetting(g.getIdLong(), "guildvoicejoin"));
									guildlist.add("- Guild Member Voice Move: " + getSetting(g.getIdLong(), "guildvoicemove"));
									guildlist.add("- Guild Update Boost Count: " + getSetting(g.getIdLong(), "guildupdateboostcount"));
									guildlist.add("- Guild Member Voice Leave: " + getSetting(g.getIdLong(), "guildvoiceleave"));
									guildlist.add("- Guild Member Role Remove: " + getSetting(g.getIdLong(), "guildmemberroleremove"));
									guildlist.add("- Guild Member Update Nickname: " + getSetting(g.getIdLong(), "guildmemberupdatenickname"));
									guildlist.add("- Guild Update Verification Level: " + getSetting(g.getIdLong(), "guildupdateverificationlevel"));
									guildlist.add("- Guild Update Explicit Content Level: " + getSetting(g.getIdLong(), "guildupdateexplicitcontentlevel"));
									//messagesend
									chan.sendMessage("Category: Textchannels (" + textlist.size() + " Events) \n" + getFromList(textlist)).queue();
									chan.sendMessage("Category: Voicechannels (" + voicelist.size() + " Events) \n" + getFromList(voicelist)).queue();
									chan.sendMessage("Category: Roles (" + rolelist.size() + " Events) \n" + getFromList(rolelist)).queue();
									chan.sendMessage("Category: Categorychannels (" + categorylist.size() + " Events) \n" + getFromList(categorylist)).queue();
									chan.sendMessage("Category: User (" + userlist.size() + " Events) \n" + getFromList(userlist)).queue();
									chan.sendMessage("Category: Emote (" + emotelist.size() + " Events) \n" + getFromList(emotelist)).queue();
									chan.sendMessage("Category: Guild (" + guildlist.size() + " Events) \n" + getFromList(guildlist)).queue();
								}else if(args[3].equalsIgnoreCase("textlogs")) {
									textlist.add("- Update Name: " + getSetting(g.getIdLong(), "textupdatename"));
									textlist.add("- Update NSFW: " + getSetting(g.getIdLong(), "textupdatensfw"));
									textlist.add("- Update Topic: " + getSetting(g.getIdLong(), "textupdatetopic"));
									textlist.add("- Update Parent: " + getSetting(g.getIdLong(), "textupdateparent"));
									textlist.add("- Create Channel: " + getSetting(g.getIdLong(), "textcreate"));
									textlist.add("- Delete Channel: " + getSetting(g.getIdLong(), "textdelete"));
									textlist.add("- Update Slowmode: " + getSetting(g.getIdLong(), "textupdateslowmode"));
									chan.sendMessage("Category: Textchannels (" + textlist.size() + " Events) \n" + getFromList(textlist)).queue();
								}else if(args[3].equalsIgnoreCase("voicelogs")) {
									voicelist.add("- Update Name: " + getSetting(g.getIdLong(), "voiceupdatename"));
									voicelist.add("- Update Parent: " + getSetting(g.getIdLong(), "voiceupdateparent"));
									voicelist.add("- Create Channel: " + getSetting(g.getIdLong(), "voicecreate"));
									voicelist.add("- Delete Channel: " + getSetting(g.getIdLong(), "voicedelete"));
									voicelist.add("- Update Bitrate: " + getSetting(g.getIdLong(), "voiceupdatebitrate"));
									voicelist.add("- Update Userlimit: " + getSetting(g.getIdLong(), "voiceupdateuserlimit"));
									chan.sendMessage("Category: Voicechannels (" + voicelist.size() + " Events) \n" + getFromList(voicelist)).queue();
								}else if(args[3].equalsIgnoreCase("rolelogs")) {
									rolelist.add("- Create Role: " + getSetting(g.getIdLong(), "rolecreate"));
									rolelist.add("- Delete Role: " + getSetting(g.getIdLong(), "roledelete"));
									rolelist.add("- Update Name: " + getSetting(g.getIdLong(), "roleupdatename"));
									rolelist.add("- Update Color: " + getSetting(g.getIdLong(), "roleupdatecolor"));
									rolelist.add("- Update Hoisted: " + getSetting(g.getIdLong(), "roleupdatehoisted"));
									rolelist.add("- Update Mentionable: " + getSetting(g.getIdLong(), "roleupdatementionable"));
									chan.sendMessage("Category: Roles (" + rolelist.size() + " Events) \n" + getFromList(rolelist)).queue();
								}else if(args[3].equalsIgnoreCase("userlogs")) {
									userlist.add("- User Update Name: " + getSetting(g.getIdLong(), "userupdatename"));
									userlist.add("- User Update Avatar: " + getSetting(g.getIdLong(), "userupdateavatar"));
									userlist.add("- User Update Discriminator: " + getSetting(g.getIdLong(), "userupdatediscriminator"));
									chan.sendMessage("Category: User (" + userlist.size() + " Events) \n" + getFromList(userlist)).queue();
								}else if(args[3].equalsIgnoreCase("emotelogs")) {
									emotelist.add("- Create Emote: " + getSetting(g.getIdLong(), "emoteadd"));
									emotelist.add("- Delete Emote: " + getSetting(g.getIdLong(), "emoteremove"));
									emotelist.add("- Emote Update Name: " + getSetting(g.getIdLong(), "emoteupdatename"));
									chan.sendMessage("Category: Emote (" + emotelist.size() + " Events) \n" + getFromList(emotelist)).queue();
								}else if(args[3].equalsIgnoreCase("categorylogs")) {
									categorylist.add("- Create Category: " + getSetting(g.getIdLong(), "categorycreate"));
									categorylist.add("- Delete Category: " + getSetting(g.getIdLong(), "categorydelete"));
									categorylist.add("- Update Name: " + getSetting(g.getIdLong(), "categoryupdatename"));
									chan.sendMessage("Category: Categorychannels (" + categorylist.size() + " Events) \n" + getFromList(categorylist)).queue();
								}else if(args[3].equalsIgnoreCase("guildlogs")) {
									guildlist.add("- Guild Update Owner: " + getSetting(g.getIdLong(), "guildupdateowner"));
									guildlist.add("- Guild Update Name: " + getSetting(g.getIdLong(), "guildupdatename"));
									guildlist.add("- Guild Update Icon: " + getSetting(g.getIdLong(), "guildupdateicon"));
									guildlist.add("- Guild Member Join: " + getSetting(g.getIdLong(), "guildmemberjoin"));
									guildlist.add("- Guild Member Leave: " + getSetting(g.getIdLong(), "guildmemberremove"));
									guildlist.add("- Guild Invite Create: " + getSetting(g.getIdLong(), "guildinvitecreate"));
									guildlist.add("- Guild Invite Delete: " + getSetting(g.getIdLong(), "guildinvitedelete"));
									guildlist.add("- Guild Update Region: " + getSetting(g.getIdLong(), "guildupdateregion"));
									guildlist.add("- Guild Message Update: " + getSetting(g.getIdLong(), "guildmessageupdate"));
									guildlist.add("- Guild Message Delete: " + getSetting(g.getIdLong(), "guildmessagedelete"));
									guildlist.add("- Guild Member Role Add: " + getSetting(g.getIdLong(), "guildmemberroleadd"));
									guildlist.add("- Guild Update Boost Tier: " + getSetting(g.getIdLong(), "guildupdateboosttier"));
									guildlist.add("- Guild Member Voice Join: " + getSetting(g.getIdLong(), "guildvoicejoin"));
									guildlist.add("- Guild Member Voice Move: " + getSetting(g.getIdLong(), "guildvoicemove"));
									guildlist.add("- Guild Update Boost Count: " + getSetting(g.getIdLong(), "guildupdateboostcount"));
									guildlist.add("- Guild Member Voice Leave: " + getSetting(g.getIdLong(), "guildvoiceleave"));
									guildlist.add("- Guild Member Role Remove: " + getSetting(g.getIdLong(), "guildmemberroleremove"));
									guildlist.add("- Guild Member Update Nickname: " + getSetting(g.getIdLong(), "guildmemberupdatenickname"));
									guildlist.add("- Guild Update Verification Level: " + getSetting(g.getIdLong(), "guildupdateverificationlevel"));
									guildlist.add("- Guild Update Explicit Content Level: " + getSetting(g.getIdLong(), "guildupdateexplicitcontentlevel"));
									chan.sendMessage("Category: Guild (" + guildlist.size() + " Events) \n" + getFromList(guildlist)).queue();
								}
							}else {
								chan.sendMessage("<:deny:678036504702091278> Node ``" + args[3] + "`` isn't a valid one.\nView all nodes here: ``[p]settings guildlogs nodes <type>`` \n Following Types are possible: " + getFromList(nodeGuildLogTypeList())).queue();
							}
						}else {
							chan.sendMessage("<:deny:678036504702091278> Error - You are not permissible to do that!").queue();	
						}
					}else {
						if(hasSettingPerms(m)) {
							String node = args[2].toLowerCase();
							if(nodeGuildlogList().contains(node)) {
								if(args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("1")) {
									boolean boo = getSettingBoolean(g.getIdLong(), node);
									if(boo == true) {
										chan.sendMessage("<:deny:678036504702091278> Failed. Node ``" + node + "`` is already enabled.").queue();
									}else {
										setSetting(g.getIdLong(), node, true);
										chan.sendMessage("<:approved:678036504391581730> Success - Node ``" + node + "`` has been enabled.").queue();
									}
								}else if(args[3].equalsIgnoreCase("false") || args[3].equalsIgnoreCase("0")) {
									boolean boo = getSettingBoolean(g.getIdLong(), node);
									if(boo == true) {
										setSetting(g.getIdLong(), node, false);
										chan.sendMessage("<:approved:678036504391581730> Success - Node ``" + node + "`` has been disabled.").queue();
									}else {
										chan.sendMessage("<:deny:678036504702091278> Failed. Node ``" + node + "`` is already disabled.").queue();
									}
								}else {
									chan.sendMessage("<:deny:678036504702091278> Please just use true/1 or false/0 as boolean.").queue();
								}
							}else {
								chan.sendMessage("<:deny:678036504702091278> Node ``" + node + "`` isn't a valid one.\nView all nodes here: ``[p]settings guildlogs nodes``").queue();
							}
						}else {
							chan.sendMessage("<:deny:678036504702091278> Error - You are not permissible to do that!").queue();
						}
					}
				}
			}
		}
	}
	
	private void setSetting(long guildid, String setting, boolean state) {
		boolean isRegistered = isRegistered(guildid);
		if(isRegistered == true) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET " + setting + " = ? WHERE guildid = ?");
				ps.setBoolean(1, state);
				ps.setLong(2, guildid);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setWelcomeText(long guildid, String node, String text) {
		boolean isRegistered = isRegistered(guildid);
		if(isRegistered == true) {
			YamlFile cfg = new YamlFile("guildsettings.yml");
			try {
				if(!cfg.exists()) {
					cfg.createNewFile(true);
				}
				cfg.load();
			} catch (InvalidConfigurationException | IOException e1) {
				e1.printStackTrace();
			}
			cfg.set("Welcomer." + guildid + "." + node, text);
			try {
				cfg.save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private String getSetting(long guildid, String setting) {
		boolean isRegistered = isRegistered(guildid);
		if(isRegistered == true) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
				ps.setLong(1, guildid);
				ResultSet rs = ps.executeQuery();
				rs.next();
				boolean set = rs.getBoolean(setting);
				if(set == true) {
					return "<:approved:678036504391581730>";
				}else {
					return "<:deny:678036504702091278>";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "ERRORED " + e.getMessage();
			}
		}else {
			return "Not Registered";
		}
	}
	
	private boolean getSettingBoolean(long guildid, String setting) {
		boolean isRegistered = isRegistered(guildid);
		if(isRegistered == true) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
				ps.setLong(1, guildid);
				ResultSet rs = ps.executeQuery();
				rs.next();
				boolean set = rs.getBoolean(setting);
				return set;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
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
	
	private String getFromList(List<String> list) {
		
		StringBuilder sb = new StringBuilder();
		for(String ss : list) {
			sb.append(ss);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private List<String> nodeWelcomeList(){
		List<String> nodelist = new ArrayList<>();
		nodelist.add("title");
		nodelist.add("maintext");
		nodelist.add("footer");
		return nodelist;
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
	
	private List<String> nodeGuildLogTypeList(){
		List<String> nodelist = new ArrayList<>();
		nodelist.add("textlogs");
		nodelist.add("voicelogs");
		nodelist.add("rolelogs");
		nodelist.add("userlogs");
		nodelist.add("emotelogs");
		nodelist.add("categorylogs");
		nodelist.add("guildlogs");
		return nodelist;
	}
	
	private List<String> nodeGuildlogList() {
		List<String> nodelist = new ArrayList<>();
		nodelist.add("rolecreate");
		nodelist.add("roledelete");
		nodelist.add("roleupdatecolor");
		nodelist.add("roleupdatehoisted");
		nodelist.add("roleupdatementionable");
		nodelist.add("roleupdatename");
		nodelist.add("textcreate");
		nodelist.add("textdelete");
		nodelist.add("textupdatename");
		nodelist.add("textupdatensfw");
		nodelist.add("textupdateslowmode");
		nodelist.add("textupdatetopic");
		nodelist.add("voicecreate");
		nodelist.add("voicedelete");
		nodelist.add("voiceupdatebitrate");
		nodelist.add("voiceupdatename");
		nodelist.add("voiceupdateuserlimit");
		nodelist.add("userupdateavatar");
		nodelist.add("userupdatediscriminator");
		nodelist.add("userupdatename");
		nodelist.add("emoteadd");
		nodelist.add("emoteremove");
		nodelist.add("emoteupdatename");
		nodelist.add("categorycreate");
		nodelist.add("categorydelete");
		nodelist.add("categoryupdatename");
		nodelist.add("guildmemberjoin");
		nodelist.add("guildmemberremove");
		nodelist.add("guildmemberupdatenickname");
		nodelist.add("guildmemberroleadd");
		nodelist.add("guildmemberroleremove");
		nodelist.add("guildupdateicon");
		nodelist.add("guildupdatename");
		nodelist.add("guildupdateexplicitcontentlevel");
		nodelist.add("guildupdateregion");
		nodelist.add("guildupdateverificationlevel");
		nodelist.add("guildupdateboostcount");
		nodelist.add("guildupdateboosttier");
		nodelist.add("guildupdateowner");
		nodelist.add("guildinvitecreate");
		nodelist.add("guildinvitedelete");
		nodelist.add("guildmessagedelete");
		nodelist.add("guildmessageupdate");
		nodelist.add("guildvoicejoin");
		nodelist.add("guildvoiceleave");
		nodelist.add("guildvoicemove");
		return nodelist;
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