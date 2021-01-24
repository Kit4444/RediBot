package at.mlps.botclasses.guildlogging.privat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeave extends ListenerAdapter{
	
	public void onGuildLeave(GuildLeaveEvent e) {
		GuildLogEvents gl = new GuildLogEvents();
		Guild g = e.getJDA().getGuildById(gl.rediassetg);
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        int guildstatus = 0;
        if(isRegistered(e.getGuild().getIdLong())) {
        	guildstatus = 1;
        	try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM redibot_guildlog WHERE guildid = ?");
				ps.setLong(1, e.getGuild().getIdLong());
				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
				guildstatus = 3;
			}
        }else {
        	guildstatus = 2;
        }
    	eb.setTitle("Bot has left guild");
    	eb.setColor(gl.red);
    	switch(guildstatus) {
    	case 1: eb.addField("Guildlogstatus:", "Guild was registered, deleted from Disk successfully.", false); break;
    	case 2: eb.addField("Guildlogstatus:", "Guild was not registered.", false); break;
    	case 3: eb.addField("Guildlogstatus:", "Guild was registered, cannot deleted from Disk!", false); break;
    	}
    	eb.addField("Guildname:", e.getGuild().getName(), false);
    	eb.addField("Members:", e.getGuild().getMembers().size() + " / 250.000", false);
    	eb.addField("Boostcount:", e.getGuild().getBoostCount() + " with " + e.getGuild().getBoosters().size(), false);
    	eb.addField("Cat/Text/Voice", e.getGuild().getCategories().size() + " / " + e.getGuild().getTextChannels().size() + " / " + e.getGuild().getVoiceChannels().size(), false);
    	eb.setThumbnail(e.getGuild().getIconUrl());
    	eb.addField("Roles:", e.getGuild().getRoles().size() + "", false);
    	eb.setFooter(stime);
        if(g.getIdLong() == gl.rediassetg) {
        	g.getTextChannelById(gl.rediassetlog).sendMessage(eb.build()).queue();
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
}