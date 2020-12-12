package at.mlps.botclasses.commands.settings;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InviteChannelCMD extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "invitechannel")) {
				chan.sendMessage("In case of you don't know the proper syntax,\nenter this command: ``[p]invitechannel help``").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "invitechannel")) {
				if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]invitechannel Command");
					eb.addField("[p]invitechannel help", "Displays this Embed", false);
					eb.addField("[p]invitechannel remove", "Removes the Invitechannel from Disk. You can't create Invites through the bot anymore.", false);
					eb.addField("[p]invitechannel setchannel <Channel#Mention|SnowflakeID>", "Set the channel where the bot will create the Invite from it.", false);
					eb.addField("[p]invitechannel setmaxtime <time>", "Set the max time until the Invite will run out.\nSetting it to ``0`` or ``permanent`` has the same effect.", false);
					eb.addField("[p]invitechannel setmaxuses <uses>", "Set the max uses until the Invite will run out.\nSetting it to ``0`` or ``permanent`` has the same effect.", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("remove")) {
					if(isRegistered(g.getIdLong())) {
						if(hasSettingPerms(m)) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteChanel = ? WHERE guildid = ?");
								ps.setLong(1, 0L);
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(success + "The Channel has been removed.").queue();
							}catch (SQLException e1) {
							}
						}else {
							chan.sendMessage(noperm).queue();
						}
					}
				}
			}
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "invitechannel")) {
				if(args[1].equalsIgnoreCase("setchannel")) {
					if(isRegistered(g.getIdLong())) {
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
						}else {
							chan.sendMessage(noperm).queue();
						}
					}
				}else if(args[1].equalsIgnoreCase("setmaxtime")) {
					if(isRegistered(g.getIdLong())) {
						if(hasSettingPerms(m)) {
							int time = 0;
							if(args[3].matches("^[0-9]+$")) {
								time = Integer.parseInt(args[3]);
								if(time == 0) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteMaxTime = ? WHERE guildid = ?");
										ps.setInt(1, time);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage(success + "Invites created over the bot are now permanently.").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}else {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteMaxTime = ? WHERE guildid = ?");
										ps.setInt(1, time);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage(success + "Invites created over the bot have now a " + time + " second limit.").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}else {
								if(args[3].equalsIgnoreCase("permanent")) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteMaxTime = ? WHERE guildid = ?");
										ps.setInt(1, 0);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage(success + "Invites created over the bot are now permanently.").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}else {
									chan.sendMessage("Error - " + args[3] + " is not a valid integer value.").queue();
								}
							}
						}else {
							chan.sendMessage(noperm).queue();
						}
					}
				}else if(args[1].equalsIgnoreCase("setmaxuses")) {
					if(isRegistered(g.getIdLong())) {
						if(hasSettingPerms(m)) {
							int uses = 0;
							if(args[3].matches("^[0-9]+$")) {
								uses = Integer.parseInt(args[3]);
								if(uses == 0) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteUses = ? WHERE guildid = ?");
										ps.setInt(1, 0);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage("Success - Invites created over the bot have now unlimited uses.").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}else {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteMaxTime = ? WHERE guildid = ?");
										ps.setInt(1, uses);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage("Success - Invites created over the bot have now " + uses + " uses maximal.").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}else {
								if(args[3].equalsIgnoreCase("unlimited")) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET InviteUses = ? WHERE guildid = ?");
										ps.setInt(1, 0);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage("Success - Invites created over the bot have now unlimited uses.").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}else {
									chan.sendMessage("Error - " + args[3] + " is not a valid integer value.").queue();
								}
							}
						}else {
							chan.sendMessage("<:deny:678036504702091278> Error - You are not permissible to do that!").queue();
						}
					}
				}
			}
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