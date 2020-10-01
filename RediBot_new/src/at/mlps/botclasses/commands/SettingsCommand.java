package at.mlps.botclasses.commands;

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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SettingsCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(m.getColor());
				eb.setDescription("To set up the bot for the guild correctly, please use following format(s):\n [p]settings <guildlogs|automod> <node(show|nodes|userupdateavatar|roleupdatehoisted|textdelete|voicecreate)> <boolean>");
				SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
				String member = m.getUser().getName() + "#" + m.getUser().getDiscriminator();
				eb.setFooter("Requested by: " + member + " at " + time.format(new Date()));
				eb.addField("show", "Shows the settings for this guild", false);
				eb.addField("guildlogs", "Sets a value for the given node. Example: \n[p]settings guildlogs voicecreate true \nThis Command above will make the bot to log in this guild, when a Voice Channel is created.", false);
				eb.addField("automod", "This doesn't work *yet* - Please keep in mind, that I'm still in the Beta!", false);
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
				if(args[1].equalsIgnoreCase("guildlogs")) {
					if(args[2].equalsIgnoreCase("show")) {
						List<String> textlist = new ArrayList<>();
						List<String> voicelist = new ArrayList<>();
						List<String> rolelist = new ArrayList<>();
						List<String> userlist = new ArrayList<>();
						textlist.add("- Create Channel: " + getSetting(g.getIdLong(), "textcreate"));
						textlist.add("- Delete Channel: " + getSetting(g.getIdLong(), "textdelete"));
						textlist.add("- Update Name: " + getSetting(g.getIdLong(), "textupdatename"));
						textlist.add("- Update NSFW: " + getSetting(g.getIdLong(), "textupdatensfw"));
						textlist.add("- Update Parent: " + getSetting(g.getIdLong(), "textupdateparent"));
						textlist.add("- Update Slowmode: " + getSetting(g.getIdLong(), "textupdateslowmode"));
						textlist.add("- Update Topic: " + getSetting(g.getIdLong(), "textupdatetopic"));
						//voice
						voicelist.add("- Create Channel: " + getSetting(g.getIdLong(), "voicecreate"));
						voicelist.add("- Delete Channel: " + getSetting(g.getIdLong(), "voicedelete"));
						voicelist.add("- Update Name: " + getSetting(g.getIdLong(), "voiceupdatename"));
						voicelist.add("- Update Bitrate: " + getSetting(g.getIdLong(), "voiceupdatebitrate"));
						voicelist.add("- Update Parent: " + getSetting(g.getIdLong(), "voiceupdateparent"));
						voicelist.add("- Update Userlimit: " + getSetting(g.getIdLong(), "voiceupdateuserlimit"));
						//role
						rolelist.add("- Create Role: " + getSetting(g.getIdLong(), "rolecreate"));
						rolelist.add("- Delete Role: " + getSetting(g.getIdLong(), "roledelete"));
						rolelist.add("- Update Color: " + getSetting(g.getIdLong(), "roleupdatecolor"));
						rolelist.add("- Update Hoisted: " + getSetting(g.getIdLong(), "roleupdatehoisted"));
						rolelist.add("- Update Mentionable: " + getSetting(g.getIdLong(), "roleupdatementionable"));
						rolelist.add("- Update Name: " + getSetting(g.getIdLong(), "roleupdatename"));
						//User
						userlist.add("- User Update Avatar: " + getSetting(g.getIdLong(), "userupdateavatar"));
						userlist.add("- User Update Discriminator: " + getSetting(g.getIdLong(), "userupdatediscriminator"));
						userlist.add("- User Update Name: " + getSetting(g.getIdLong(), "userupdatename"));
						chan.sendMessage("The Settings for this guild will be loaded. Please wait a few seconds.").queue(msgedit -> {
							msgedit.delete().queue();
						});
						chan.sendMessage("Category: Textchannels (7 Events) \n" + getFromList(textlist)).queue();
						chan.sendMessage("Category: Voicechannels (6 Events) \n" + getFromList(voicelist)).queue();
						chan.sendMessage("Category: Roles (6 Events) \n" + getFromList(rolelist)).queue();
						chan.sendMessage("Category: User (3 Events) \n" + getFromList(userlist)).queue();
					}else if(args[2].equalsIgnoreCase("nodes")) {
						
					}
				}
			}
		}else if(args.length == 4) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "guildlogs")) {
				if(args[1].equalsIgnoreCase("guildlogs")) {
					String node = args[2];
					String state = args[3];
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "settings")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(m.getColor());
				eb.setDescription("To set up the bot for the guild correctly, please use following format(s):\n [p]settings <guildlogs|automod> <node(show|nodes|userupdateavatar|roleupdatehoisted|textdelete|voicecreate)> <boolean>");
				SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
				String member = m.getUser().getName() + "#" + m.getUser().getDiscriminator();
				eb.setFooter("Requested by: " + member + " at " + time.format(new Date()));
				eb.addField("show", "Shows the settings for this guild", false);
				eb.addField("guildlogs", "Sets a value for the given node. Example: \n[p]settings guildlogs voicecreate true \nThis Command above will make the bot to log in this guild, when a Voice Channel is created.", false);
				eb.addField("automod", "This doesn't work *yet* - Please keep in mind, that I'm still in the Beta!", false);
				chan.sendMessage(eb.build()).queue();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void setSetting(long guildid, String setting, boolean state) {
		boolean isRegistered = isRegistered(guildid);
		if(isRegistered == true) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET ? = ? WHERE guildid = ?");
				ps.setString(1, setting);
				ps.setBoolean(2, state);
				ps.setLong(3, guildid);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
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
}