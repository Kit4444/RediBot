package at.mlps.botclasses.guildlogging.voicejoinleavemove;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.ocpsoft.prettytime.PrettyTime;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceLeave extends ListenerAdapter{
	
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
		Guild g = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Member has left a voicechannel.");
    	HashMap<String, Long> statsMap = DurCalc.removeMember(g.getIdLong(), e.getMember().getIdLong());
    	VoiceChannel initChannel = g.getVoiceChannelById(decryptHashMap(statsMap, "initialJoinChannel"));
    	long initTime = (decryptHashMap(statsMap, "initialJoinTime") * 1000);
    	PrettyTime pt = new PrettyTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy - HH:mm:ss");
    	eb.setDescription("Member: " + e.getMember().getAsMention() + "\nChannel: " + e.getChannelLeft().getAsMention() + "\n \nInitially Joined Channel: " + initChannel.getAsMention() + "\nInitially Joined Date/Time: " + sdf.format(new Date(initTime)) + ", " + pt.format(new Date(initTime)));
    	eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		eb.setColor(gl.green);
		if(gl.enabledLog(g, "guildvoiceleave")) {
			gl.sendMsg(eb, g);
		}
	}
	
	private long decryptHashMap(HashMap<String, Long> hashMap, String node) {
		long id = 0l;
		if(hashMap.containsKey(node)) {
			id = hashMap.get(node);
		}else {
			id = -1;
		}
		return id;
	}

}