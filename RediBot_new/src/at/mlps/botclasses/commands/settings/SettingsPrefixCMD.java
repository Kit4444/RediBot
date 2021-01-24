package at.mlps.botclasses.commands.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SettingsPrefixCMD extends ListenerAdapter{
	
	String noperm = "<:deny:678036504702091278> Error - You are not permissible to do that!";
	String success = "<:approved:678036504391581730> Success - ";
	String failed = "<:deny:678036504702091278> Error - ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "prefix")) {
				if(isRegistered(g.getIdLong())) {
					String current = "rb!";
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT botprefix FROM redibot_guildlog WHERE guildid = ?");
						ps.setLong(1, g.getIdLong());
						ResultSet rs = ps.executeQuery();
						rs.next();
						current = rs.getString("botprefix");
					} catch (SQLException e1) {
						current = "rb!";
						e1.printStackTrace();
					}
					e.getChannel().sendMessage("The current prefix for " + g.getName() + " is ``" + current + "`` !\nDo you want to have another prefix? -> [p]prefix <newPrefix>").queue();
				}
			}
		}if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "prefix")) {
				if(isRegistered(g.getIdLong())) {
					if(hasSettingPerms(e.getMember())) {
						if(args[1].equalsIgnoreCase("reset")) {
							try {
								PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET botprefix = ? WHERE guildid = ?");
								ps.setString(1, "rb!");
								ps.setLong(2, g.getIdLong());
								ps.executeUpdate();
								e.getChannel().sendMessage(success + "The Prefix for " + g.getName() + " has been resetted to the default! Prefix: ``rb!``").queue();
							}catch (SQLException e1) {
								e1.printStackTrace();
							}
						}else {
							if(args[1].length() >= 1 && args[1].length() <= 8) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_guildlog SET botprefix = ? WHERE guildid = ?");
									ps.setString(1, args[1]);
									ps.setLong(2, g.getIdLong());
									ps.executeUpdate();
									e.getChannel().sendMessage(success + "The Prefix for " + g.getName() + " has been changed to ``" + args[1] + "``!").queue();
								}catch (SQLException e1) {
									e1.printStackTrace();
								}
							}else {
								e.getChannel().sendMessage(failed + "The prefix can't be longer than 8 chars or shorter than 1 char.\nActual length: " + args[1].length()).queue();
							}
						}
					}else {
						e.getChannel().sendMessage(noperm).queue();
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