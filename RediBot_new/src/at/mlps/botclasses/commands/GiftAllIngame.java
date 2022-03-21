package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GiftAllIngame extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(g.getIdLong() == 612372586386423824l) {
			if(m.getIdLong() == 228145889988837385l) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase(Main.botprefix + "ingameGifts")) {
						List<String> uuids = returnAllMembers();
						int i = 0;
						for(String s : uuids) {
							i++;
							setGift(s, "3d:xpboost");
						}
						e.getChannel().sendMessage(i + " Players got the gift 3d XP-Boost.").queue();
					}
				}
			}
		}
	}
	
	private List<String> returnAllMembers(){
		List<String> uuids = new ArrayList<>();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT uuid_ut FROM redicore_userstats");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				uuids.add(rs.getString("uuid_ut"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uuids;
	}
	
	private void setGift(String uuid, String giftkey) {
		String giftCode = RandomStringUtils.randomAlphanumeric(6);
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_gifts(uuid, giftcode, giftkey) VALUES (?, ?, ?)");
			ps.setString(1, uuid);
			ps.setString(2, giftCode);
			ps.setString(3, giftkey);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
