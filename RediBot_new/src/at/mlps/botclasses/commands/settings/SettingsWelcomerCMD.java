package at.mlps.botclasses.commands.settings;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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

public class SettingsWelcomerCMD extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	//TODO add a subcommand "[p]welcomer exampleembed" to see how it could look like
	
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "welcomer")) {
				chan.sendMessage("In case of you don't know the proper syntax,\nenter this command: ``[p]welcomer help``").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "welcomer")) {
				if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]welcomer Command");
					eb.addField("[p]welcomer help", "Displays this Embed", false);
					//eb.addField("[p]welcomer exampleembed", "Shows an example embed how the current settings are look like", false);
					eb.addField("[p]welcomer setchannel <Mention#Channel|SnowflakeID>", "Set the Channel where the bot posts the join message.", false);
					eb.addField("[p]welcomer removechannel", "Stops the bot posting the message in the channel.", false);
					eb.addField("[p]welcomer showchannel", "The bot will mention the Channel where he posts the messages.", false);
					eb.addField("[p]welcomer settext <type> <Your Text>", "You also can set a custom text with placeholders.", false);
					eb.addField("[p]welcomer setcolor <type>", "Sets the color for the embed.", false);
					eb.addField("[p]welcomer setthumbnail <type>", "Set the thumbnail for the Embed", false);
					eb.addField("[p]welcomer setrulechannel <Mention#Channel|SnowflakeID>", "Defines the channel for the ``%ruleschannel`` Placeholder.", false);
					eb.addField("Placeholders for the Text", "%servername - Display's the guildname\n%usermention - Mentions the user\n%usernamewodisc - Plain Username without Discriminator (User~~#1234~~)\n%usernamewdisc - Plain Username with Discriminator (User#1234)\n%date - Returns the current date for the Central European (Summer) Time (01/01/1970 - 00:00:00)\n%members - Displays the current Members on the server", false);
					eb.addField("Types for the Text", "title\nmaintext\nfooter", false);
					eb.addField("Types for the Color", "membercolor\nrandom\n(HEX Colorcode ``#FFFFFF`` or Integer)", false);
					eb.addField("Typed for the Thumbnail", "useravatar\nguildicon\n(own URL)", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("showchannel")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							long channelID = 0L;
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT welcomechannel FROM redibot_guildlog WHERE guildid = ?");
								ps.setLong(1, g.getIdLong());
								ResultSet rs = ps.executeQuery();
								rs.next();
								channelID = rs.getLong("welcomechannel");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							TextChannel welcomeChannel = g.getTextChannelById(channelID);
							if(welcomeChannel == null) {
								chan.sendMessage(failed + "The channel doesn't exists.").queue();
							}else {
								chan.sendMessage(success + "The welcomer posts the joins in " + welcomeChannel.getAsMention()).queue();
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("removechannel")) {
					if(hasSettingPerms(m)) {
						if(isRegistered(g.getIdLong())) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomechannel = ? WHERE guildid = ?");
								ps.setLong(1, 0L);
								ps.executeUpdate();
								chan.sendMessage(success + "The Welcomerchannel has been deleted from disk.\nThe Bot won't post joinmessages anymore.").queue();
							} catch (SQLException e1) {
								chan.sendMessage(failed + "The Welcomerchannel couldn't be deleted from disk.\nErrorcode: " + e1.getMessage()).queue();
								e1.printStackTrace();
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
			}
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "welcomer")) {
				if(args[1].equalsIgnoreCase("setchannel")) {
					if(hasSettingPerms(m)) {
						long uid = 0L;
						TextChannel welcome = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							welcome = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								welcome = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								welcome = e.getMessage().getMentionedChannels().get(0);
							}
						}
						long channelID = welcome.getIdLong();
						try {
							PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomechannel = ? WHERE guildid = ?");
							ps.setLong(1, channelID);
							ps.setLong(2, g.getIdLong());
							ps.executeUpdate();
							chan.sendMessage(success + "The Channel " + welcome.getAsMention() + " is saved.\nThe Bot will post now joinmessages there.").queue();
						} catch (SQLException e1) {
							chan.sendMessage(failed + "Couldn't save the Welcomechannel.\nErrorcode: " + e1.getMessage()).queue();
							e1.printStackTrace();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("setrulechannel")) {
					if(hasSettingPerms(m)) {
						long uid = 0L;
						TextChannel welcome = null;
						if(args[2].matches("^[0-9]+$")) {
							uid = Long.parseLong(args[2]);
							welcome = g.getTextChannelById(uid);
						}else {
							if(args[2].startsWith("!")) {
								welcome = g.getTextChannelsByName(args[2].substring(1), true).get(0);
							}else {
								welcome = e.getMessage().getMentionedChannels().get(0);
							}
						}
						long channelID = welcome.getIdLong();
						try {
							PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcRuleChannel = ? WHERE guildid = ?");
							ps.setLong(1, channelID);
							ps.setLong(2, g.getIdLong());
							ps.executeUpdate();
							chan.sendMessage(success + "The Channel " + welcome.getAsMention() + " is saved as rules channel.").queue();
						} catch (SQLException e1) {
							chan.sendMessage(failed + "Couldn't save the Rulechannel.\nErrorcode: " + e1.getMessage()).queue();
							e1.printStackTrace();
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("setcolor")) {
					if(hasSettingPerms(m)) {
						if(args[2].equalsIgnoreCase("membercolor")) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_color = ? WHERE guildid = ?");
								ps.setString(1, "membercolor");
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(success + "The Color for the Embed is set to the Color of the Member.").queue();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}else if(args[2].equalsIgnoreCase("random")) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_color = ? WHERE guildid = ?");
								ps.setString(1, "random");
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(success + "The Color for the Embed is set to Random.").queue();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}else {
							if(args[2].startsWith("#")) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_color = ? WHERE guildid = ?");
									ps.setString(1, args[2].substring(1));
									ps.setLong(2, g.getIdLong());
									ps.executeUpdate();
									chan.sendMessage(success + "The Color for the Embed is set to ``" + args[2] + "``").queue();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}else {
								if(args[2].matches("^[0-9]+$")) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_color = ? WHERE guildid = ?");
										ps.setString(1, args[2]);
										ps.setLong(2, g.getIdLong());
										ps.executeUpdate();
										chan.sendMessage(success + "The Color for the Embed is set to ``" + args[2] + "``").queue();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}else if(args[1].equalsIgnoreCase("setthumbnail")) {
					if(hasSettingPerms(m)) {
						if(args[2].equalsIgnoreCase("useravatar")) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_thumbnail = ? WHERE guildid = ?");
								ps.setString(1, "useravatar");
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(success + "The bot will use the User Avatar as Thumbnail.").queue();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}else if(args[2].equalsIgnoreCase("guildicon")) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_thumbnail = ? WHERE guildid = ?");
								ps.setString(1, "guildicon");
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								chan.sendMessage(success + "The bot will use the Guild Icon as Thumbnail.").queue();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}else {
							if(args[2].length() >= 6 && args[2].length() <= 512) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET welcomer_thumbnail = ? WHERE guildid = ?");
									ps.setString(1, args[2]);
									ps.setLong(2, g.getIdLong());
									ps.executeUpdate();
									chan.sendMessage(success + "The bot will use a custom URL as Thumnail.\nURL: " + args[2]).queue();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}else {
								chan.sendMessage(failed + "The URL you provided seems to be too short or too long.\nThe URL has to be at least 6 chars long, maximal 512 Chars.\nYour URL has " + args[2].length() + " Chars.").queue();
							}
						}
					}else {
						chan.sendMessage(noperm).queue();
					}
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "welcomer")) {
				if(hasSettingPerms(m)) {
					if(args[1].equalsIgnoreCase("settext")) {
						if(args[2].equalsIgnoreCase("title")) {
							StringBuilder sb = new StringBuilder();
							for(int i = 3; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String text = sb.toString();
							setWelcomeText(g.getIdLong(), "welcTitle", text, chan);
						}else if(args[2].equalsIgnoreCase("maintext")) {
							StringBuilder sb = new StringBuilder();
							for(int i = 3; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String text = sb.toString();
							setWelcomeText(g.getIdLong(), "welcText", text, chan);
						}else if(args[2].equalsIgnoreCase("footer")) {
							StringBuilder sb = new StringBuilder();
							for(int i = 3; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String text = sb.toString();
							setWelcomeText(g.getIdLong(), "welcFooter", text, chan);
						}else {
							chan.sendMessage(failed + "Unknown node for the current Mainnode.").queue();
						}
					}
				}else {
					chan.sendMessage(noperm).queue();
				}
			}
		}
	}
	
	private void setWelcomeText(long guildid, String node, String text, TextChannel chan) {
		boolean isRegistered = isRegistered(guildid);
		if(isRegistered == true) {
			YamlFile cfg = new YamlFile("configs/guildsettings.yml");
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
				chan.sendMessage(success + "The Text has been saved successfully.\nNode: " + node + " \nText: \n``" + text + "``").queue();
			} catch (IOException e1) {
				e1.printStackTrace();
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