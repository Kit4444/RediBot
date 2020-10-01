package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Punishments extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		Guild g = e.getGuild();
		String[] args = e.getMessage().getContentRaw().split(" ");
		long modlog = getModlogChannel(g.getIdLong());
		TextChannel modlogchan = g.getTextChannelById(modlog);
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "kick")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: rb!kick <User@Mention|id> <Reason>\nYou can mention the User or use the Snowflake-ID. You also can attach a picture onto the command, the Bot will always select the first attachment.").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "mute")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> Usage: rb!mute <User@Mention|id> <Reason>\\nYou can mention the User or use the Snowflake-ID. You also can attach a picture onto the command, the Bot will always select the first attachment.").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "warn")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "ban")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "kick")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "mute")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "warn")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> ***|*** This guild is not registered.").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "ban")) {
				if(isRegistered(g.getIdLong())) {
					chan.sendMessage("<:deny:678036504702091278> ***This command is not implemented. This is a feature for the next version.***").queue();
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
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		try {
			if(Main.mysql.isInDatabase("redibot_guildlog", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
				ps.setLong(1, guildid);
				ResultSet rs = ps.executeQuery();
				rs.next();
				l = rs.getLong("channelid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	private void insertDB(User perpetrator, User operator, long guildID, String punType, String dateTime, String reason) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_bkwm SET (userID, operatorID, guildID, userName, operatorName, punishType, dateTime, reason) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setLong(1, perpetrator.getIdLong());
			ps.setLong(2, operator.getIdLong());
			ps.setLong(3, guildID);
			ps.setString(4, perpetrator.getName() + "#" + perpetrator.getDiscriminator());
			ps.setString(5, operator.getName() + "#" + operator.getDiscriminator());
			ps.setString(6, punType);
			ps.setString(7, dateTime);
			ps.setString(8, reason);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}