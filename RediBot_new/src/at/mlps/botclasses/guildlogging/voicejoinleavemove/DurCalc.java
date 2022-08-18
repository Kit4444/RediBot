package at.mlps.botclasses.guildlogging.voicejoinleavemove;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import at.mlps.rc.mysql.lb.MySQL;

public class DurCalc {
	
	//calculating initial channel join time between move and leaving time
	//showing on leave the initial joined channel, even after moving.
	public static void addMember(long guildId, long channelId, long memberId) {
		long currentTime = (System.currentTimeMillis() / 1000);
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_voiceduration(guildId, memberId, timeJoin, channelJoin) VALUES (?, ?, ?, ?)");
			ps.setLong(1, guildId);
			ps.setLong(2, memberId);
			ps.setLong(3, currentTime);
			ps.setLong(4, channelId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void moveMember(long guildId, long memberId) {
		long currentTime = (System.currentTimeMillis() / 1000);
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redibot_voiceduration SET channelLastMove = ? WHERE guildId = ? AND memberId = ?");
			ps.setLong(1, currentTime);
			ps.setLong(2, guildId);
			ps.setLong(3, memberId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static long getJoinedTime(long guildId, long memberId) {
		long joinTime = 0l;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT timeJoin FROM redibot_voiceduration WHERE guildId = ? AND memberId = ?");
			ps.setLong(1, guildId);
			ps.setLong(2, memberId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				joinTime = rs.getLong("timeJoin");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return joinTime;
	}
	
	public static HashMap<String, Long> removeMember(long guildId, long memberId) {
		HashMap<String, Long> stats = new HashMap<>();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_voiceduration WHERE guildId = ? AND memberId = ?");
			ps.setLong(1, guildId);
			ps.setLong(2, memberId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				stats.put("initialJoinTime", rs.getLong("timeJoin"));
				stats.put("initialJoinChannel", rs.getLong("channelJoin"));
				stats.put("lastMovedTime", rs.getLong("channelLastMove"));
			}else {
				stats.put("initialJoinTime", 0l);
				stats.put("initialJoinChannel", 0l);
				stats.put("lastMovedTime", 0l);
			}
			deleteData(guildId, memberId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stats;
	}
	
	private static void deleteData(long guildId, long memberId) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM redibot_voiceduration WHERE guildId = ? AND memberId = ?");
			ps.setLong(1, guildId);
			ps.setLong(2, memberId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
