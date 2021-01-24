package at.mlps.botclasses.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CreateInvite extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		long guildid = e.getGuild().getIdLong();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "createinvite")) {
				if(isRegistered(guildid)) {
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
						ps.setLong(1, guildid);
						ResultSet rs = ps.executeQuery();
						rs.next();
						
						long invChannel = rs.getLong("InviteChanel");
						int invMaxTime = rs.getInt("InviteMaxTime");
						int invMaxUses = rs.getInt("InviteUses");
						
						TextChannel welcome = e.getGuild().getTextChannelById(invChannel);
						welcome.createInvite().setMaxUses(invMaxUses).setMaxAge(invMaxTime).reason(e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + " has created an invite.").queue(inv -> {
							e.getAuthor().openPrivateChannel().queue(pchannel -> {
								e.getMessage().delete().queue();
								e.getChannel().sendMessage("Hey, " + e.getAuthor().getAsMention() + ", I've sent you the Invite in your DMs. The URL is just available for " + retPTime(invMaxTime) + " or " + invMaxUses + " use(s).").queue(sm -> {
									sm.delete().queueAfter(30, TimeUnit.SECONDS);
								});
								pchannel.sendMessage("Hello " + e.getAuthor().getAsMention() + "! You have requested a invite code for RediCraft.\nInvite: " + inv.getUrl() + " \nInfo, the URL is just available for " + retPTime(invMaxTime) + " or " + invMaxUses + " use(s). That's not a permanent invite.").queue();
								
							});
						});
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					e.getChannel().sendMessage("This guild is not registered for this command!").queue();
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
	
	/*private String retPTime(long time) {
		long seconds = time;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		while(seconds > 60) {
			seconds -= 60;
			minutes++;
		}
		while(minutes > 60) {
			minutes -= 60;
			hours++;
		}
		while(hours > 24) {
			hours -= 24;
			days++;
		}
		if(days == 0) {
			return "Time: " + hours + ":" + minutes + ":" + seconds;
		}else {
			return "Days: " + days + ", Time: " + hours + ":" + minutes + ":" + seconds;
		}
	}*/
	
	private String retPTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if(time >= 86400) {
			sdf = new SimpleDateFormat("dd HH:mm:ss");
		}else {
			sdf = new SimpleDateFormat("HH:mm:ss");
		}
		String sdff = sdf.format(new Date((time * 1000)));
		return sdff;
	}
}