package at.mlps.botclasses.commands.settings;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SettingsXPLevel extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		String[] args = e.getMessage().getContentRaw().split(" ");
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "xpsettings")) {
				chan.sendMessage("In case of you don't know the proper syntax,\nenter this command: ``[p]xpsettings help``").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "xpsettings")) {
				if(args[1].equalsIgnoreCase("help")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setAuthor(m.getUser().getName() + "#" + m.getUser().getDiscriminator(), m.getUser().getAvatarUrl());
					eb.setTitle("Guide for the [p]xpsettings Command");
					eb.setDescription("You do or don't like the XP Leveling? Or do you want to change some things? Here you go.");
					eb.addField("[p]xpsettings help", "Displays this embed", false);
					eb.addField("[p]xpsettings enable/disable", "Enable/Disable the XP Leveling in this Guild (Also the Commands for the Leaderboard will be disabled!)", false);
					chan.sendMessage(eb.build()).queue();
				}else if(args[1].equalsIgnoreCase("enable")) {
					if(hasSettingPerms(m)) {
						setXPStatus(g, true);
						chan.sendMessage("XP Leveling has been enabled for this guild.").queue();
					}
				}else if(args[1].equalsIgnoreCase("disable")) {
					if(hasSettingPerms(m)) {
						setXPStatus(g, false);
						chan.sendMessage("XP Leveling has been disabled for this guild.\n(The levels stay but noone continues to level. The [p]leaderboard is disabled too)").queue();
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
	
	private void setXPStatus(Guild g, boolean state) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET hasLeveling = ? WHERE guildid = ?");
			ps.setBoolean(1, state);
			ps.setLong(2, g.getIdLong());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}