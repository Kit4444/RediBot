package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
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

public class RegisterGuilds extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		Guild g = e.getGuild();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "registerguild")) {
				chan.sendMessage("***Usage***: " + Main.botprefix + "registerguild <Channel@Mention>").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "registerguild")) {
				TextChannel logchannel = e.getMessage().getMentionedChannels().get(0);
				long logchannelid = logchannel.getIdLong();
				long guildid = g.getIdLong();
				if(e.getAuthor().getIdLong() == 228145889988837385L) {
					if(checkDB(guildid)) {
						chan.sendMessage("<:deny:678036504702091278> This guild is already registered.").queue();
					}else {
						insertDB(guildid, logchannelid);
						chan.sendMessage("<:approved:678036504391581730> This guild has been registered.\nGuild: " + g.getName() + "\nLogchannel: " + logchannel.getAsMention()).queue();
					}
				}else {
					User u = e.getJDA().getUserById(228145889988837385L);
					chan.sendMessage("<:deny:678036504702091278> You can't use this command. This command can just execute " + u.getName() + "#" + u.getDiscriminator()).queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "registerguild")) {
				chan.sendMessage("***Usage***: " + Main.botprefix + "registerguild <Channel@Mention>").queue();
			}
		}
	}
	
	private static boolean checkDB(long guildid) {
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
	
	private static void insertDB(long guildid, long channelid) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_guildlog (guildid, channelid) VALUES (?, ?)");
			ps.setLong(1, guildid);
			ps.setLong(2, channelid);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}