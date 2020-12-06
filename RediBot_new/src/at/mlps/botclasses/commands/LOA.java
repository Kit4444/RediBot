package at.mlps.botclasses.commands;

import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LOA extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		Message msg = e.getMessage();
		String cont = msg.getContentRaw();
		Role staff = e.getGuild().getRoleById(552161168412639235L);
		Role loa = e.getGuild().getRoleById(574642929289789440L);
		long mem = e.getMember().getIdLong();
		if(cont.equalsIgnoreCase(Main.botprefix + "loa")) {
			Guild staffguild = e.getJDA().getGuildById(612372586386423824L);
			Role loa_staff = staffguild.getRoleById(784900192175783977L);
			if(e.getMember().getRoles().contains(staff)) {
				if(e.getMember().getRoles().contains(loa)) {
					e.getGuild().removeRoleFromMember(e.getMember(), loa).complete();
					e.getMessage().addReaction("approved:678036504391581730").queue();
					staffguild.removeRoleFromMember(mem, loa_staff).queue();
				}else {
					e.getGuild().addRoleToMember(e.getMember(), loa).complete();
					e.getMessage().addReaction("approved:678036504391581730").queue();
					staffguild.addRoleToMember(mem, loa_staff).queue();
				}
			}else {
				e.getMessage().addReaction("no:642212652566839326").queue();
				chan.sendMessage("Hey, you can't do that!").queue(msg1 -> {
					msg.delete().completeAfter(30, TimeUnit.SECONDS);
				});
			}
		}
	}
}