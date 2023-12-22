package at.mlps.main;

import java.util.TimerTask;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class PCHMG_MemberCount extends TimerTask{
	
	public JDA jda;
	public PCHMG_MemberCount(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		Guild guild = jda.getGuildById(471382081902084128l);
		VoiceChannel channel = guild.getVoiceChannelById(1005389436659445770l);
		
		channel.getManager().setName("💙 Member: " + guild.getMemberCount()).queue();
	}

}
