package at.mlps.botclasses.commands;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Punishments extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	String successColor = "#55ff55";
	String failedColor = "#aa0000";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		Guild g = e.getGuild();
		Member operator = e.getMember();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss a z");
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel modlogchan = g.getTextChannelById(getModlogChannel(g.getIdLong()));
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "kick")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]kick <User@Mention|ID> <Reason>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "mute")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]mute <User@Mention|ID> <Reason>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "warn")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]warn <User@Mention|ID> <Reason>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "ban")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]ban <User@Mention|ID> <Reason>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "tempmute")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]tempmute <User@Mention|ID> <Time> <Unit> <Reason>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "tempban")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]tempmute <User@Mention|ID> <Time> <Unit> <Reason>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "setmuterole")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: [p]setmuterole <Role@Mention|ID>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "setmuterole")) {
				if(isRegistered(g.getIdLong())) {
					String role = args[1];
					Role r = null;
					long uid = 0L;
					if(role.matches("^[0-9]+$")) {
						uid = Long.parseLong(role);
						r = g.getRoleById(uid);
					}else {
						if(role.startsWith("!")) {
							r = g.getRolesByName(role.substring(1), true).get(0);
						}else {
							r = e.getMessage().getMentionedRoles().get(0);
						}
					}
					long roleId = r.getIdLong();
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET muteRole = ? WHERE guildid = ?");
						ps.setLong(1, roleId);
						ps.setLong(2, g.getIdLong());
						ps.executeUpdate();
						e.getMessage().addReaction("approved:678036504391581730").queue();
						chan.sendMessage(success + "Alright, the mute role has been set! Role: " + r.getName()).queue();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "kick")) {
				if(isRegistered(g.getIdLong())) {
					if(hasPermission(operator)) {
						String user = args[1];
						Member m = null;
						long uid = 0L;
						if(user.matches("^[0-9]+$")) {
							uid = Long.parseLong(user);
							m = g.getMemberById(uid);
						}else {
							if(user.startsWith("!")) {
								m = g.getMembersByName(user.substring(1), true).get(0);
							}else {
								m = g.getMember(e.getMessage().getMentionedUsers().get(0));
							}
						}
						if(m != null) {
							StringBuilder sb = new StringBuilder();
							for(int i = 2; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String text = sb.toString();
							m.getUser().openPrivateChannel().queue(pm -> {
								pm.sendMessage("You were kicked from " + g.getName() + ".\nAdmin: " + operator.getUser().getName() + "#" + operator.getUser().getDiscriminator() + "\nReason: " + text).queue();
							});
							insertDB(m.getUser(), operator, g, 0, "KICK", sdf.format(new Date()), text);
							sendModlog(modlogchan, m, operator, g, 0, "Kick", sdf.format(new Date()), text);
							g.kick(m, text).queue();
							e.getMessage().delete();
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(Color.decode(successColor));
							eb.setDescription("<:allow:671772876461834251> ***" + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + "*** was kicked with the reason " + text);
							chan.sendMessage(eb.build()).queue();
						}else {
							chan.sendMessage(failed + "You didn't defined a member!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "mute")) {
				if(isRegistered(g.getIdLong())) {
					if(hasPermission(operator)) {
						long muteRole = getMuteRole(g);
						if(muteRole == 0) {
							chan.sendMessage(failed + "You don't have set the mute role yet. Do that with [p]setmuterole <Role@Mention|ID>").queue();
						}else {
							String user = args[1];
							Member m = null;
							long uid = 0L;
							if(user.matches("^[0-9]+$")) {
								uid = Long.parseLong(user);
								m = g.getMemberById(uid);
							}else {
								if(user.startsWith("!")) {
									m = g.getMembersByName(user.substring(1), true).get(0);
								}else {
									m = g.getMember(e.getMessage().getMentionedUsers().get(0));
								}
							}
							Role muted = g.getRoleById(muteRole);
							if(muted != null) {
								if(m != null) {
									StringBuilder sb = new StringBuilder();
									for(int i = 2; i < args.length; i++) {
										sb.append(args[i]).append(" ");
									}
									String text = sb.toString();
									m.getUser().openPrivateChannel().queue(pm -> {
										pm.sendMessage("You were muted permanently in " + g.getName() + ".\nAdmin: " + operator.getUser().getName() + "#" + operator.getUser().getDiscriminator() + "\nReason: " + text).queue();
									});
									insertDB(m.getUser(), operator, g, 0, "MUTE", sdf.format(new Date()), text);
									sendModlog(modlogchan, m, operator, g, 0, "Permanent Mute", sdf.format(new Date()), text);
									e.getMessage().delete();
									g.addRoleToMember(m, muted).queue();
									EmbedBuilder eb = new EmbedBuilder();
									eb.setColor(Color.decode(successColor));
									eb.setDescription("<:allow:671772876461834251> ***" + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + "*** was warned with the reason " + text);
									chan.sendMessage(eb.build()).queue();
								}else {
									chan.sendMessage(failed + "You didn't defined a member!").queue();
								}
							}else {
								chan.sendMessage(failed + "The mute role doesn't exists or you didn't have set one!").queue();
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "warn")) {
				if(isRegistered(g.getIdLong())) {
					if(hasPermission(operator)) {
						String user = args[1];
						Member m = null;
						long uid = 0L;
						if(user.matches("^[0-9]+$")) {
							uid = Long.parseLong(user);
							m = g.getMemberById(uid);
						}else {
							if(user.startsWith("!")) {
								m = g.getMembersByName(user.substring(1), true).get(0);
							}else {
								m = g.getMember(e.getMessage().getMentionedUsers().get(0));
							}
						}
						if(m != null) {
							StringBuilder sb = new StringBuilder();
							for(int i = 2; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String text = sb.toString();
							m.getUser().openPrivateChannel().queue(pm -> {
								pm.sendMessage("You were warned in " + g.getName() + ".\nAdmin: " + operator.getUser().getName() + "#" + operator.getUser().getDiscriminator() + "\nReason: " + text).queue();
							});
							insertDB(m.getUser(), operator, g, 0, "WARN", sdf.format(new Date()), text);
							sendModlog(modlogchan, m, operator, g, 0, "Warn", sdf.format(new Date()), text);
							e.getMessage().delete();
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(Color.decode(successColor));
							eb.setDescription("<:allow:671772876461834251> ***" + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + "*** was warned with the reason " + text);
							chan.sendMessage(eb.build()).queue();
						}else {
							chan.sendMessage(failed + "You didn't defined a member!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
					
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "ban")) {
				if(isRegistered(g.getIdLong())) {
					if(hasPermission(operator)) {
						String user = args[1];
						Member m = null;
						long uid = 0L;
						if(user.matches("^[0-9]+$")) {
							uid = Long.parseLong(user);
							m = g.getMemberById(uid);
						}else {
							if(user.startsWith("!")) {
								m = g.getMembersByName(user.substring(1), true).get(0);
							}else {
								m = g.getMember(e.getMessage().getMentionedUsers().get(0));
							}
						}
						if(m != null) {
							StringBuilder sb = new StringBuilder();
							for(int i = 2; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String text = sb.toString();
							m.getUser().openPrivateChannel().queue(pm -> {
								pm.sendMessage("You were banned permanently in " + g.getName() + ".\nAdmin: " + operator.getUser().getName() + "#" + operator.getUser().getDiscriminator() + "\nReason: " + text).queue();
							});
							insertDB(m.getUser(), operator, g, 0, "WARN", sdf.format(new Date()), text);
							sendModlog(modlogchan, m, operator, g, 0, "Permanent Ban", sdf.format(new Date()), text);
							g.ban(m, 0, text).queue();
							e.getMessage().delete();
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(Color.decode(successColor));
							eb.setDescription("<:allow:671772876461834251> ***" + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + "*** was banned permanently with the reason " + text);
							chan.sendMessage(eb.build()).queue();
						}else {
							chan.sendMessage(failed + "You didn't defined a member!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "tempmute")) {
				if(isRegistered(g.getIdLong())) {
					if(hasPermission(operator)) {
						String user = args[1];
						Member m = null;
						long uid = 0L;
						if(user.matches("^[0-9]+$")) {
							uid = Long.parseLong(user);
							m = g.getMemberById(uid);
						}else {
							if(user.startsWith("!")) {
								m = g.getMembersByName(user.substring(1), true).get(0);
							}else {
								m = g.getMember(e.getMessage().getMentionedUsers().get(0));
							}
						}
						if(m != null) {
							
						}else {
							chan.sendMessage(failed + "You didn't defined a member!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "tempban")) {
				if(isRegistered(g.getIdLong())) {
					if(hasPermission(operator)) {
						String user = args[1];
						Member m = null;
						long uid = 0L;
						if(user.matches("^[0-9]+$")) {
							uid = Long.parseLong(user);
							m = g.getMemberById(uid);
						}else {
							if(user.startsWith("!")) {
								m = g.getMembersByName(user.substring(1), true).get(0);
							}else {
								m = g.getMember(e.getMessage().getMentionedUsers().get(0));
							}
						}
						if(m != null) {
							
						}else {
							chan.sendMessage(failed + "You didn't defined a member!").queue();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}
		}
	}
	
	private boolean isRegistered(long guildid) {
		boolean boo = false;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		try {
			if(Main.mysql.isInDatabase("redibot_guildlog", hm)) {
				boo = true;
			}else {
				boo = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boo;
	}
	
	private long getModlogChannel(long guildid) {
		long l = 0;
		PreparedStatement ps;
		try {
			ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
			ps.setLong(1, guildid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			l = rs.getLong("channelid");
		} catch (SQLException e) {
		}
		return l;	
	}
	
	private boolean hasPermission(Member m) {
		boolean bool = false;
		if(m.hasPermission(Permission.ADMINISTRATOR) || m.hasPermission(Permission.BAN_MEMBERS) || m.hasPermission(Permission.KICK_MEMBERS) || m.hasPermission(Permission.MANAGE_SERVER) || m.isOwner()) {
			bool = true;
		}
		return bool;
	}
	
	private void sendModlog(TextChannel modlogchannel, Member perpetrator, Member operator, Guild g, long activeUntil, String punType, String dateTime, String reason) {
		EmbedBuilder eb = new EmbedBuilder();
		int cases = returnCases(g);
		eb.setAuthor("Case " + cases + " | " + punType + " | " + perpetrator.getUser().getName() + "#" + perpetrator.getUser().getDiscriminator(), null, perpetrator.getUser().getAvatarUrl());
		eb.addField("User:", perpetrator.getAsMention(), true);
		eb.addField("Operator", operator.getAsMention(), true);
		eb.addField("Reason", reason, true);
		eb.setFooter("Guild: " + g.getName() + " | Date: " + dateTime, g.getIconUrl());
		modlogchannel.sendMessage(eb.build()).queue();
	}
	
	private void insertDB(User perpetrator, Member operator, Guild g, long activeUntil, String punType, String dateTime, String reason) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_bkwm (userID, operatorID, guildID, punishType, dateTime, reason, activeUntil) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setLong(1, perpetrator.getIdLong());
			ps.setLong(2, operator.getIdLong());
			ps.setLong(3, g.getIdLong());
			ps.setString(4, punType);
			ps.setString(5, dateTime);
			ps.setString(6, reason);
			ps.setLong(7, activeUntil);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private long getMuteRole(Guild g) {
		long l = 0L;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT muteRole FROM redibot_guildlog WHERE guildid = ?");
			ps.setLong(1, g.getIdLong());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				l = rs.getLong("muteRole");
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
		}
		return l;
	}
	
	private int returnCases(Guild g) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT id FROM redibot_bkwm WHERE guildID = ?");
			ps.setLong(1, g.getIdLong());
			ResultSet rs = ps.executeQuery();
			if(rs.last()) {
				i = rs.getRow();
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
		}
		return i;
	}
}