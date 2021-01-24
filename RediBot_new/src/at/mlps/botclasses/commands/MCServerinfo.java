package at.mlps.botclasses.commands;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MCServerinfo extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		MessageChannel chan = e.getChannel();
		User m = e.getAuthor();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        
        if(args.length == 1) {
        	if(args[0].equalsIgnoreCase(Main.botprefix + "servers")) {
        		EmbedBuilder eb = new EmbedBuilder();
        		eb.setColor(e.getMember().getColor());
        		eb.setTitle("RediCraft Serverstats");
        		eb.setFooter("Requested from: " + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + " at " + stime, e.getAuthor().getAvatarUrl());
        		setEBField(eb, "Lobby");
        		setEBField(eb, "Creative");
        		setEBField(eb, "Survival");
        		setEBField(eb, "SkyBlock");
        		setEBField(eb, "Towny");
        		setEBField(eb, "Farmserver");
        		chan.sendMessage(eb.build()).queue();
        	}else if(args[0].equalsIgnoreCase(Main.botprefix + "server")) {
        		chan.sendMessage("<:deny:678036504702091278> | Usage: " + Main.botprefix + "server <Server> - (To retrieve a list of possible server just do ``" + Main.botprefix + "server list``").queue();
        	}
        }else if(args.length == 2) {
        	if(args[0].equalsIgnoreCase(Main.botprefix + "server")) {
        		String server = args[1];
        		Guild g = e.getGuild();
        		if(server.equalsIgnoreCase("Creative")) {
        			setEmbedNew(g, m, chan, "Creative");
        		}else if(server.equalsIgnoreCase("Farmserver")) {
        			setEmbedNew(g, m, chan, "Farmserver");
        		}else if(server.equalsIgnoreCase("Lobby")) {
        			setEmbedNew(g, m, chan, "Lobby");
        		}else if(server.equalsIgnoreCase("SkyBlock")) {
        			setEmbedNew(g, m, chan, "SkyBlock");
        		}else if(server.equalsIgnoreCase("Staffserver")) {
        			setEmbedNew(g, m, chan, "Staffserver");
        		}else if(server.equalsIgnoreCase("Survival")) {
        			setEmbedNew(g, m, chan, "Survival");
        		}else if(server.equalsIgnoreCase("Towny")) {
        			setEmbedNew(g, m, chan, "Towny");
        		}else if(server.equalsIgnoreCase("list")) {
        			chan.sendMessage("<:deny:678036504702091278> | Possible Servers: Lobby, Creative, Survival, SkyBlock, Farmserver, Towny, Staffserver").queue();
        		}else {
        			chan.sendMessage("<:deny:678036504702091278> | Possible Servers: Lobby, Creative, Survival, SkyBlock, Farmserver, Towny, Staffserver").queue();
        		}
        	}
        }else {
        	if(args[0].equalsIgnoreCase(Main.botprefix + "server")) {
        		chan.sendMessage("<:deny:678036504702091278> | Usage: " + Main.botprefix + "server <Server> - (To retrieve a list of possible server just do ``" + Main.botprefix + "server list``").queue();
        	}
        }
	}
	
	void setEBField(EmbedBuilder eb, String server) {
		eb.addField(getName(server), getInfo(server), false);
	}
	
	String getName(String server) {
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			s = "Server: " + rs.getString("servername") + " / " + rs.getString("serverid");
		}catch (SQLException e) {
			e.printStackTrace();
			s = "ERRORED!";
		}
		return s;
	}
	
	String getInfo(String server) {
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			boolean lock = rs.getBoolean("locked");
			boolean monitor = rs.getBoolean("monitoring");
			
			String online = "";
			if(rs.getBoolean("online")) {
				online = "<:upvote:671772876474679326>";
			}else {
				online = "<:downvote:671772876432605204>";
			}
			if(monitor == true && lock == true) {
				s = "<:monitoring:671772876436799508> <:dnd:708982976838369320> **|** Players: " + rs.getInt("currPlayers") + ", Online: " + online + ", Version: " + rs.getString("version");
			}else if(monitor == true && lock != true) {
				s = "<:monitoring:671772876436799508> **|** Players: " + rs.getInt("currPlayers") + ", Online: " + online + ", Version: " + rs.getString("version");
			}else if(monitor != true && lock == true) {
				s = "<:dnd:708982976838369320> **|** Players: " + rs.getInt("currPlayers") + ", Online: " + online + ", Version: " + rs.getString("version");
			}else {
				s = "<:online:671772876482936862> **|** Players: " + rs.getInt("currPlayers") + ", Online: " + online + ", Version: " + rs.getString("version");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			s = "ERRORED!";
		}
		return s;
	}
	
	void setEmbedNew(Guild g, User m, MessageChannel chan, String server) {
		String onlinecode = "#55FF55";
		String offlinecode = "#FF5555";
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Serverinfo", "http://www.redicraft.eu", g.getIconUrl());
        eb.setFooter("Requested from: " + m.getName() + "#" + m.getDiscriminator() + " at " + stime + "CET", m.getAvatarUrl());
        try {
        	PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
        	ps.setString(1, server);
        	ResultSet rs = ps.executeQuery();
        	rs.next();
        	String servername = rs.getString("servername");
        	String serverid = rs.getString("serverid");
        	String version = rs.getString("version");
        	int ram = rs.getInt("ramusage");
			int allocRam = rs.getInt("ramavailable");
			int currPlayer = rs.getInt("currPlayers");
			int maxPlayer = rs.getInt("maxPlayers");
			int staffmembers = rs.getInt("currStaffmembers");
			boolean online = rs.getBoolean("online");
			boolean staffs = rs.getBoolean("staffserver");
			boolean monitoring = rs.getBoolean("monitoring");
			boolean locked = rs.getBoolean("locked");
			float loadRam = (Integer.valueOf(ram) * 100/Integer.valueOf(allocRam));
			float loadPlayer = (Integer.valueOf(currPlayer) * 100/Integer.valueOf(maxPlayer));
			
			//embedbuilder
			eb.setTitle("Selected Server: " + servername + " / " + serverid);
			if(online == true) {
				eb.setColor(Color.decode(onlinecode));
				eb.addField("RAM:", ram + "MB used from " + allocRam + "MB (" + loadRam + "% ram load factor)", false);
				if(staffs == true) {
					eb.addField("Staffserver:", "yes", false);
				}
				eb.addField("Serverversion:", version, false);
				eb.addField("Players:", "Online: " + currPlayer + "(" + staffmembers + " are Staffs)" + " **|** (" + loadPlayer + "% player load factor)", false);
				if(monitoring == true) {
					eb.addField("Monitored:", "This server is currently getting monitored.", false);
				}
				if(locked == true) {
					eb.addField("Locked:", "This server is currently locked.", false);
				}
			}else {
				eb.addField("Serverstatus:", "<:downvote:671772876432605204> Offline", false);
				eb.setColor(Color.decode(offlinecode));
			}
			chan.sendMessage(eb.build()).queue();
        }catch (SQLException e) {
        	e.printStackTrace();
        }
	}

}