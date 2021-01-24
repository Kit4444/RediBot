package at.mlps.botclasses.commands;

import java.io.IOException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LOA extends ListenerAdapter{
	
	String loa = "[LOA] ";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Message msg = e.getMessage();
		String cont = msg.getContentRaw();
		Guild g = e.getGuild();
		if(cont.equalsIgnoreCase(Main.botprefix + "loa")) {
			YamlFile cfg = new YamlFile("configs/nicks.yml");
			try {
				cfg.load();
			} catch (InvalidConfigurationException | IOException e1) {
				e1.printStackTrace();
			}
			String nick = e.getMember().getNickname();
			String realname = e.getMember().getUser().getName();
			String newNickLOA = "";
			String newNick = "";
			if(nick == null) {
				newNickLOA = loa + realname;
				newNick = realname;
			}else {
				newNickLOA = loa + nick;
				newNick = nick;
			}
			try {
				cfg.save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(g.getIdLong() == 612372586386423824L) {
				//Staff Guild
				Role loa_staff = g.getRoleById(784900192175783977L);
				Guild mainserver = e.getJDA().getGuildById(548136727697555496L);
				Role loa_main = mainserver.getRoleById(574642929289789440L);
				if(e.getMember().getRoles().contains(loa_staff)) {
					g.removeRoleFromMember(e.getMember(), loa_staff).queue();
					Member m = mainserver.getMember(e.getMember().getUser());
					mainserver.removeRoleFromMember(e.getMember().getIdLong(), loa_main).complete();
					mainserver.modifyNickname(m, cfg.getString("Users." + e.getMember().getId())).queue();
					g.modifyNickname(e.getMember(), cfg.getString("Users." + e.getMember().getId())).queue();
					e.getMessage().addReaction("approved:678036504391581730").queue();
				}else {
					g.addRoleToMember(e.getMember(), loa_staff).queue();
					mainserver.addRoleToMember(e.getMember().getIdLong(), loa_main).complete();
					cfg.set("Users." + e.getMember().getId(), newNick);
					try {
						cfg.save();
						Member m = mainserver.getMember(e.getMember().getUser());
						mainserver.modifyNickname(m, newNickLOA).queue();
						g.modifyNickname(e.getMember(), newNickLOA).queue();
						e.getMessage().addReaction("approved:678036504391581730").queue();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}else if(g.getIdLong() == 548136727697555496L) {
				//Main Guild
				Role loa_main = e.getGuild().getRoleById(574642929289789440L);
				Role staff = e.getGuild().getRoleById(552161168412639235L);
				Guild staffserver = e.getJDA().getGuildById(612372586386423824L);
				Role loa_staff = staffserver.getRoleById(784900192175783977L);
				if(e.getMember().getRoles().contains(staff)) {
					if(e.getMember().getRoles().contains(loa_main)) {
						g.removeRoleFromMember(e.getMember(), loa_main).complete();
						Member m = staffserver.getMember(e.getMember().getUser());
						staffserver.removeRoleFromMember(e.getMember().getIdLong(), loa_staff).queue();
						staffserver.modifyNickname(m, cfg.getString("Users." + e.getMember().getId())).queue();
						g.modifyNickname(e.getMember(), cfg.getString("Users." + e.getMember().getId())).queue();
						e.getMessage().addReaction("approved:678036504391581730").queue();
					}else {
						g.addRoleToMember(e.getMember(), loa_main).complete();
						staffserver.addRoleToMember(e.getMember().getIdLong(), loa_staff).queue();
						e.getMessage().addReaction("approved:678036504391581730").queue();
						cfg.set("Users." + e.getMember().getId(), newNick);
						try {
							cfg.save();
							Member m = staffserver.getMember(e.getMember().getUser());
							staffserver.modifyNickname(m, newNickLOA).queue();
							g.modifyNickname(e.getMember(), newNickLOA).queue();
							e.getMessage().addReaction("approved:678036504391581730").queue();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}else {
					e.getMessage().addReaction("deny:678036504702091278").queue();
				}
			}
		}
	}
}