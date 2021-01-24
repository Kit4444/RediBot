package at.mlps.reactionroles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RR_Command extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "reactionroles") || args[0].equalsIgnoreCase(Main.botprefix + "rr")) {
				chan.sendMessage("In case you don't know the proper syntax, type ``[p]rr help``").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "reactionroles") || args[0].equalsIgnoreCase(Main.botprefix + "rr")) {
				if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]messagelogging Command");
					eb.addField("[p]rr help", "Displays this Embed", false);
					eb.addField("[p]rr addrole <Channel#Mention|ID> <MessageID> <Role@Mention|ID> <Emote>", "Adds a Reaction role in the Message.", false);
					eb.addField("[p]rr removerole <Channel#Mention|ID> <MessageID> <Emote>", "Removes a Reaction Role from the Message.", false);
					eb.addField("[p]rr togglerole <Channel#Mention|ID> <MessageID> <Emote>", "Switch between Single use (Verification Mode) and default use (removeable)", false);
					eb.addField("[p]rr listroles <Channel#Mention|ID> <MessageID>", "Displays a List, which Emote gives/removes the Role and if Single use Default use.", false);
					chan.sendMessage(eb.build()).queue();
				}else {
					String emote = args[1];
					chan.sendMessage("``" + emote + "``").queue();
				}
			}
		}else if(args.length == 4) {
			//listroles
			if(args[0].equalsIgnoreCase(Main.botprefix + "reactionroles") || args[0].equalsIgnoreCase(Main.botprefix + "rr")) {
				if(args[1].equalsIgnoreCase("listroles")) {
					if(hasSettingPerms(m)) {
						long uid = 0l;
						TextChannel channel = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							channel = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								channel = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								channel = e.getMessage().getMentionedChannels().get(0);
							}
						}
						long channelid = channel.getIdLong();
						if(args[3].matches("^[0-9]+$")) {
							long messageid = Long.parseLong(args[3]);
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_reactionroles WHERE guildid = ? AND channelid = ? AND messageid = ?");
								ps.setLong(1, g.getIdLong());
								ps.setLong(2, channelid);
								ps.setLong(3, messageid);
								ResultSet rs = ps.executeQuery();
								int id = 0;
								List<String> reactionroleslist = new ArrayList<>();
								while(rs.next()) {
									id++;
									long rid = rs.getLong("roleid");
									Role role = g.getRoleById(rid);
									if(role != null) {
										reactionroleslist.add("ID: " + id + " | " + rs.getString("emote") + " - " + role.getAsMention() + " | Single Use: " + rs.getBoolean("single_use"));
									}else {
										reactionroleslist.add("ID: " + id + " | " + rs.getString("emote") + " - " + rid + "/role deleted | Single Use: " + rs.getBoolean("single_use"));
									}
								}
								
								StringBuilder sb = new StringBuilder();
								for(String s : reactionroleslist) {
									sb.append(s);
									sb.append("\n");
								}
								String result = sb.toString();
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("Current Reaction Roles for channel " + channel.getAsMention() + " and MessageID " + messageid + ": \n " + result);
								eb.setColor(m.getColor());
								chan.sendMessage(eb.build()).queue();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}else {
							chan.sendMessage(failed + "Error, the MessageID can just contain numbers between 0 - 9").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
			}
		}else if(args.length == 5) {
			//removerole & togglerole
			if(args[0].equalsIgnoreCase(Main.botprefix + "reactionroles") || args[0].equalsIgnoreCase(Main.botprefix + "rr")) {
				if(args[1].equalsIgnoreCase("removerole")) {
					if(hasSettingPerms(m)) {
						long uid = 0l;
						TextChannel channel = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							channel = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								channel = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								channel = e.getMessage().getMentionedChannels().get(0);
							}
						}
						if(args[3].matches("^[0-9]+$")) {
							long messagekey = Long.parseLong(args[3]);
							
							String reaction_old = args[4];
							String reaction = "";
							if(reaction_old.length() == 1) {
								reaction = reaction_old;
							}else {
								if(reaction_old.charAt(1) == 'a') {
									reaction = reaction_old.substring(1, (reaction_old.length() - 1));
								}else {
									reaction = reaction_old.substring(2, (reaction_old.length() - 1));
								}
							}
							
							int result = deleteRR(g.getIdLong(), m.getIdLong(), messagekey, channel.getIdLong(), reaction);
							String answer = "";
							switch(result){
							case 0: answer = failed + "An Error occured."; break;
							case 1: answer = success + "Deleted the Reaction Role."; break;
							case 2: answer = failed + "This reaction is not registered"; break;
							}
							chan.sendMessage(answer).queue();
						}else {
							chan.sendMessage(failed + "Error, the MessageID can just contain numbers between 0 - 9").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("togglerole")) {
					if(hasSettingPerms(m)) {
						long uid = 0l;
						TextChannel channel = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							channel = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								channel = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								channel = e.getMessage().getMentionedChannels().get(0);
							}
						}
						if(args[3].matches("^[0-9]+$")) {
							long messagekey = Long.parseLong(args[3]);
							
							String reaction_old = args[4];
							String reaction = "";
							if(reaction_old.length() == 1) {
								reaction = reaction_old;
							}else {
								if(reaction_old.charAt(1) == 'a') {
									reaction = reaction_old.substring(1, (reaction_old.length() - 1));
								}else {
									reaction = reaction_old.substring(2, (reaction_old.length() - 1));
								}
							}
							int result = updateSingleUseRR(g.getIdLong(), m.getIdLong(), messagekey, channel.getIdLong(), reaction);
							String answer = "";
							switch(result) {
							case 5: answer = success + "This role is now in default mode"; break;
							case 6: answer = success + "This role is now in single mode"; break;
							case 4: answer = failed + "This reaction is not registered"; break;
							case 3: answer = failed + "An Error occured."; break;
							}
							chan.sendMessage(answer).queue();
						}else {
							chan.sendMessage(failed + "Error, the MessageID can just contain numbers between 0 - 9").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
			}
		}else if(args.length == 6) {
			//addrole
			if(args[0].equalsIgnoreCase(Main.botprefix + "reactionroles") || args[0].equalsIgnoreCase(Main.botprefix + "rr")) {
				if(args[1].equalsIgnoreCase("addrole")) {
					if(hasSettingPerms(m)) {
						long uid = 0l;
						TextChannel channel = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							channel = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								channel = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								channel = e.getMessage().getMentionedChannels().get(0);
							}
						}
						if(args[3].matches("^[0-9]+$")) {
							long messagekey = Long.parseLong(args[3]);
							
							long rolekey = 0l;
							Role role = null;
							if(args[4].matches("^[0-9]+$")) {
								rolekey = Long.parseLong(args[4]);
								role = g.getRoleByBot(rolekey);
							}else {
								if(args[4].startsWith("!")) {
									role = g.getRolesByName(args[4].substring(1), true).get(0);
								}else {
									role = e.getMessage().getMentionedRoles().get(0);
								}
							}
							String reaction_old = args[5];
							String reaction = "";
							if(reaction_old.length() == 1) {
								reaction = reaction_old;
							}else if(reaction_old.charAt(1) == 'a') {
								reaction = reaction_old.substring(1, (reaction_old.length() - 1));
							}else {
								reaction = reaction_old.substring(2, (reaction_old.length() - 1));
							}
							int result = insertRR(g.getIdLong(), m.getIdLong(), role.getIdLong(), messagekey, channel.getIdLong(), reaction);
							String answer = "";
							switch(result) {
							case 0: answer = failed + "Could not add a reaction role."; break;
							case 1: answer = failed + "This Reaction already exists."; break;
							case 2: answer = success + "Added the Reaction Role."; channel.addReactionById(messagekey, reaction).queue(); break;
							}
							chan.sendMessage(answer).queue();
						}else {
							chan.sendMessage(failed + "Error, the MessageID can just contain numbers between 0 - 9").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
			}
		}
	}
	
	private int insertRR(long guildid, long memberid, long roleid, long messageid, long channelid, String emote) {
		int success = 0;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		hm.put("channelid", channelid);
		hm.put("messageid", messageid);
		hm.put("roleid", roleid);
		hm.put("emote", emote);
		try {
			if(Main.mysql.isInDatabase("redibot_reactionroles", hm)) {
				success = 1;
			}else {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss a z");
				hm.put("adminid", memberid);
				hm.put("datetime", sdf.format(new Date()));
				Main.mysql.insertInto("redibot_reactionroles", hm);
				success = 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			success = 0;
		}
		return success;
	}
	
	private int deleteRR(long guildid, long memberid, long messageid, long channelid, String emote) {
		int success = 0;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		hm.put("channelid", channelid);
		hm.put("messageid", messageid);
		hm.put("emote", emote);
		try {
			if(Main.mysql.isInDatabase("redibot_reactionroles", hm)) {
				Main.mysql.delete("redibot_reactionroles", hm);
				success = 1;
			}else {
				success = 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			success = 0;
		}
		return success;
	}
	
	//5 = defaultmode, 6 = singlemode, 4 = not exist in DB, 3 = failed
	private int updateSingleUseRR(long guildid, long memberid, long messageid, long channelid, String emote) {
		int success = 0;
		boolean singleuse = false;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		hm.put("channelid", channelid);
		hm.put("messageid", messageid);
		hm.put("emote", emote);
		try {
			if(Main.mysql.isInDatabase("redibot_reactionroles", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT single_use FROM redibot_reactionroles WHERE guildid = ? AND messageid = ? AND channelid = ? AND emote = ?");
				ps.setLong(1, guildid);
				ps.setLong(2, messageid);
				ps.setLong(3, channelid);
				ps.setString(4, emote);
				ResultSet rs = ps.executeQuery();
				rs.next();
				singleuse = rs.getBoolean("single_use");
				if(singleuse) {
					PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE redibot_reactionroles SET single_use = ? WHERE guildid = ? AND messageid = ? AND channelid = ? AND emote = ?");
					ps1.setBoolean(1, false);
					ps1.setLong(2, guildid);
					ps1.setLong(3, messageid);
					ps1.setLong(4, channelid);
					ps1.setString(5, emote);
					ps1.executeUpdate();
					success = 5;
				}else {
					PreparedStatement ps1 = MySQL.getConnection().prepareStatement("UPDATE redibot_reactionroles SET single_use = ? WHERE guildid = ? AND messageid = ? AND channelid = ? AND emote = ?");
					ps1.setBoolean(1, true);
					ps1.setLong(2, guildid);
					ps1.setLong(3, messageid);
					ps1.setLong(4, channelid);
					ps1.setString(5, emote);
					ps1.executeUpdate();
					success = 6;
				}
			}else {
				success = 4;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			success = 3;
		}
		return success;
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