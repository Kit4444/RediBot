package at.mlps.botclasses.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
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
					YamlFile cfg = new YamlFile("configs/guildsettings.yml");
					try {
						cfg.load();
					} catch (InvalidConfigurationException e2) {
						e2.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
						
					TextChannel welcome = e.getGuild().getTextChannelById(cfg.getLong("InviteChannel." + guildid + ".Channel"));
					welcome.createInvite().setMaxUses(cfg.getInt("InviteChannel." + guildid + ".Uses")).setMaxAge(cfg.getInt("InviteChannel." + guildid + ".Time")).reason(e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + " has created an invite.").queue(inv -> {
						e.getAuthor().openPrivateChannel().queue(pchannel -> {
							e.getMessage().delete().queue();
							e.getChannel().sendMessage("Hey, " + e.getAuthor().getAsMention() + ", I've sent you the Invite in your DMs. The URL is just available for " + retPTime(cfg.getInt("InviteChannel." + guildid + ".Time")) + " or " + cfg.getInt("InviteChannel." + guildid + ".Uses") + " use(s).").queue(sm -> {
								sm.delete().queueAfter(30, TimeUnit.SECONDS);
							});
							pchannel.sendMessage("Hello " + e.getAuthor().getAsMention() + "! You have requested a invite code for " + e.getGuild().getName() + ".\nInvite: " + inv.getUrl() + " \nInfo, the URL is just available for " + retPTime(cfg.getInt("InviteChannel." + guildid + ".Time")) + " or " + cfg.getInt("InviteChannel." + guildid + ".Uses") + " use(s).").queue();
							});
						});
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
		SimpleDateFormat sdf = null;
		if(time >= 86400) {
			sdf = new SimpleDateFormat("dd HH:mm:ss");
		}else {
			sdf = new SimpleDateFormat("HH:mm:ss");
		}
		return sdf.format(new Date((time * 1000)));
	}*/
	
	private String retPTime(long time) {
		long seconds = time;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		while(seconds > 59) {
			seconds -= 59;
			minutes++;
		}
		while(minutes > 59) {
			minutes -= 59;
			hours++;
		}
		while(hours > 24) {
			hours -= 24;
			days++;
		}
		if(seconds == 0) {
			return "permanent";
		}else {
			if(days == 0) {
				return "Time: " + hours + ":" + minutes + ":" + seconds;
			}else {
				return "Days: " + days + ", Time: " + hours + ":" + minutes + ":" + seconds;
			}
		}
	}
}