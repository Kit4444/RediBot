package at.mlps.botclasses.commands.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SettingsGuildlog extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "guildlogs")) {
				
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "guildlogs")) {
				if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]guildlogs Command.");
					eb.addField("[p]guildlogs help", "Shows this Embed", false);
					eb.addField("[p]guildlogs shownodes [type]", "Shows current settings per category", false);
					eb.addField("[p]guildlogs guildnode boolean", "Updates a given Guildnode ", false);
					eb.addField("Types for 'shownodes':", "textlogs\nvoicelogs\nrolelogs\nuserlogs\nemotelogs\ncategorylogs\nguildlogs\nguildlognodes* \n \n \n* > Shows the nodes for the Update a Guildlognode", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("shownodes")) {
					if(hasSettingPerms(m)) {
						List<String> textlist = new ArrayList<>();
						List<String> voicelist = new ArrayList<>();
						List<String> rolelist = new ArrayList<>();
						List<String> userlist = new ArrayList<>();
						List<String> guildlist = new ArrayList<>();
						List<String> emotelist = new ArrayList<>();
						List<String> categorylist = new ArrayList<>();
						
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
						userlist.add("- User Update Online Status: " + getSetting(g.getIdLong(), "userupdateonlinestatus"));
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
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
			}
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "guildlogs")) {
				if(hasSettingPerms(m)) {
					if(args[1].equalsIgnoreCase("shownodes")) {
						if(args[2].equalsIgnoreCase("textlogs")) {
							List<String> textlist = new ArrayList<>();
							textlist.add("- Update Name: " + getSetting(g.getIdLong(), "textupdatename"));
							textlist.add("- Update NSFW: " + getSetting(g.getIdLong(), "textupdatensfw"));
							textlist.add("- Update Topic: " + getSetting(g.getIdLong(), "textupdatetopic"));
							textlist.add("- Update Parent: " + getSetting(g.getIdLong(), "textupdateparent"));
							textlist.add("- Create Channel: " + getSetting(g.getIdLong(), "textcreate"));
							textlist.add("- Delete Channel: " + getSetting(g.getIdLong(), "textdelete"));
							textlist.add("- Update Slowmode: " + getSetting(g.getIdLong(), "textupdateslowmode"));
							chan.sendMessage("Category: Textchannels (" + textlist.size() + " Events) \n" + getFromList(textlist)).queue();
						}else if(args[2].equalsIgnoreCase("voicelogs")) {
							List<String> voicelist = new ArrayList<>();
							voicelist.add("- Update Name: " + getSetting(g.getIdLong(), "voiceupdatename"));
							voicelist.add("- Update Parent: " + getSetting(g.getIdLong(), "voiceupdateparent"));
							voicelist.add("- Create Channel: " + getSetting(g.getIdLong(), "voicecreate"));
							voicelist.add("- Delete Channel: " + getSetting(g.getIdLong(), "voicedelete"));
							voicelist.add("- Update Bitrate: " + getSetting(g.getIdLong(), "voiceupdatebitrate"));
							voicelist.add("- Update Userlimit: " + getSetting(g.getIdLong(), "voiceupdateuserlimit"));
							chan.sendMessage("Category: Voicechannels (" + voicelist.size() + " Events) \n" + getFromList(voicelist)).queue();
						}else if(args[2].equalsIgnoreCase("rolelogs")) {
							List<String> rolelist = new ArrayList<>();
							rolelist.add("- Create Role: " + getSetting(g.getIdLong(), "rolecreate"));
							rolelist.add("- Delete Role: " + getSetting(g.getIdLong(), "roledelete"));
							rolelist.add("- Update Name: " + getSetting(g.getIdLong(), "roleupdatename"));
							rolelist.add("- Update Color: " + getSetting(g.getIdLong(), "roleupdatecolor"));
							rolelist.add("- Update Hoisted: " + getSetting(g.getIdLong(), "roleupdatehoisted"));
							rolelist.add("- Update Mentionable: " + getSetting(g.getIdLong(), "roleupdatementionable"));
							chan.sendMessage("Category: Roles (" + rolelist.size() + " Events) \n" + getFromList(rolelist)).queue();
						}else if(args[2].equalsIgnoreCase("userlogs")) {
							List<String> userlist = new ArrayList<>();
							userlist.add("- User Update Name: " + getSetting(g.getIdLong(), "userupdatename"));
							userlist.add("- User Update Avatar: " + getSetting(g.getIdLong(), "userupdateavatar"));
							userlist.add("- User Update Discriminator: " + getSetting(g.getIdLong(), "userupdatediscriminator"));
							userlist.add("- User Update Online Status: " + getSetting(g.getIdLong(), "userupdateonlinestatus"));
							chan.sendMessage("Category: User (" + userlist.size() + " Events) \n" + getFromList(userlist)).queue();
						}else if(args[2].equalsIgnoreCase("emotelogs")) {
							List<String> emotelist = new ArrayList<>();
							emotelist.add("- Create Emote: " + getSetting(g.getIdLong(), "emoteadd"));
							emotelist.add("- Delete Emote: " + getSetting(g.getIdLong(), "emoteremove"));
							emotelist.add("- Emote Update Name: " + getSetting(g.getIdLong(), "emoteupdatename"));
							chan.sendMessage("Category: Emote (" + emotelist.size() + " Events) \n" + getFromList(emotelist)).queue();
						}else if(args[2].equalsIgnoreCase("categorylogs")) {
							List<String> categorylist = new ArrayList<>();
							categorylist.add("- Create Category: " + getSetting(g.getIdLong(), "categorycreate"));
							categorylist.add("- Delete Category: " + getSetting(g.getIdLong(), "categorydelete"));
							categorylist.add("- Update Name: " + getSetting(g.getIdLong(), "categoryupdatename"));
							chan.sendMessage("Category: Categorychannels (" + categorylist.size() + " Events) \n" + getFromList(categorylist)).queue();
						}else if(args[2].equalsIgnoreCase("guildlogs")) {
							List<String> guildlist = new ArrayList<>();
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
						}else if(args[2].equalsIgnoreCase("guildlognodes")) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("In order to configure the bot properly,\nyou need to know, which nodes you can set\nindividually for this guild.");
							eb.addField("Textchannellogging", "textcreate, textdelete, textupdatename, textupdatensfw, textupdateparent, textupdateslowmode, textupdatetopic", false);
							eb.addField("Voicechannellogging", "voicecreate, voicedelete, voiceupdatebitrate, voiceupdatename, voiceupdateparent, voiceupdateuserlimit", false);
							eb.addField("Rolelogging", "rolecreate, roledelete, roleupdatecolor, roleupdatehoisted, roleupdatementionable, roleupdatename", false);
							eb.addField("Usereventlogging", "userupdateavatar, userupdatediscriminator, userupdatename, userupdateonlinestatus", false);
							eb.addField("Emoteeventlogging", "emoteadd, emoteremove, emoteupdatename", false);
							eb.addField("Categoryeventlogging", "categorycreate, categorydelete, categoryupdatename", false);
							eb.addField("Guildeventlogging", "guildmemberjoin, guildmemberremove, guildmemberroleadd, guildmemberroleremove, guildupdateicon, guildupdatename, guildupdatexplicitcontentlevel, guildupdateregion, guildupdateverificationlevel, guildupdateboostcount, guildupdateboosttier, guildupdateowner, guildinvitecreate, guildinvitedelete, guildmessagedelete, guildmessageupdate, guildvoicejoin, guildvoiceleave, guildvoicemove", false);
							eb.setColor(m.getColor());
							chan.sendMessage(eb.build()).queue();
						}else {
							chan.sendMessage(failed + "Node ``" + args[2] + "`` isn't a valid one.\nView all nodes here: ``[p]guildlogs nodes <type>`` \n Following Types are possible: " + getFromList(nodeGuildLogTypeList())).queue();
						}
					}else {
						if(nodeGuildlogList().contains(args[1].toLowerCase())) {
							boolean bool = getSettingBoolean(g.getIdLong(), args[1]);
							if(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("1")) {
								if(bool == true) {
									chan.sendMessage(failed + "The node ``" + args[1].toLowerCase() + "`` is already enabled.").queue();
								}else {
									if(setSetting(g.getIdLong(), args[1].toLowerCase(), true) == true) {
										chan.sendMessage(success + "The Node ``" + args[1].toLowerCase() + "`` is now enabled.").queue();
									}else {
										chan.sendMessage(failed + "***FATAL ERROR - COULD NOT UPDATE NODE!*** Please contact Maurice ASAP!").queue();
									}
								}
							}else if(args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("0")) {
								if(bool == true) {
									if(setSetting(g.getIdLong(), args[1].toLowerCase(), false) == true) {
										chan.sendMessage(success + "The Node ``" + args[1].toLowerCase() + "`` is now disabled.").queue();
									}else {
										chan.sendMessage(failed + "***FATAL ERROR - COULD NOT UPDATE NODE!*** Please contact Maurice ASAP!").queue();
									}
								}else {
									chan.sendMessage(failed + "The node ``" + args[1].toLowerCase() + "`` is already disabled.").queue();
								}
							}else {
								chan.sendMessage(failed + "Please use ``true``/``1`` or ``false``/``0``, cannot parse ``" + args[2].toLowerCase() + "`` to a boolean.").queue();
							}
						}else {
							chan.sendMessage(failed + "Node ``" + args[1].toLowerCase() + " isn't a valid one.\nView all nodes here: ``[p]guildlogs showGuildlogtypes").queue();
						}
					}
				}else {
					chan.sendMessage(noperm).queue();
				}
			}
		}
	}

	private boolean setSetting(long guildid, String setting, boolean state) {
		boolean isRegistered = isRegistered(guildid);
		boolean i = false;
		if(isRegistered == true) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET " + setting + " = ? WHERE guildid = ?");
				ps.setBoolean(1, state);
				ps.setLong(2, guildid);
				ps.executeUpdate();
				i = true;
			} catch (SQLException e) {
				e.printStackTrace();
				i = false;
			}
		}
		return i;
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
	
	private List<String> nodeGuildLogTypeList(){
		List<String> nodelist = new ArrayList<>();
		nodelist.add("textlogs");
		nodelist.add("voicelogs");
		nodelist.add("rolelogs");
		nodelist.add("userlogs");
		nodelist.add("emotelogs");
		nodelist.add("categorylogs");
		nodelist.add("guildlogs");
		nodelist.add("guildlognodes");
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
		nodelist.add("userupdateonlinestatus");
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