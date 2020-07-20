package at.mlps.botclasses.commands;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StreamAdvCMD extends ListenerAdapter{
	
	/*
	 * streamcmd: rb!stream <yt|twitch|stop> [Message]
	 */
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "stream")) {
				if(e.getGuild().getIdLong() == 548136727697555496L) {
					chan.sendMessage("<:deny:678036504702091278> | " + Main.botprefix + "stream <yt|twitch|stop> [Message]").queue();
				}
			}
		}else if(args.length >= 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "stream")) {
				if(e.getGuild().getIdLong() == 548136727697555496L) {
					String state = args[1];
					Member m = e.getMember();
					if(m.hasPermission(Permission.MANAGE_SERVER) || m.hasPermission(Permission.ADMINISTRATOR)) {
						StringBuilder sb = new StringBuilder();
						for(int i = 2; i < args.length; i++) {
							sb.append(args[i]);
							sb.append(" ");
						}
						String msg = sb.toString();
						YamlFile file = new YamlFile("configuration.yml");
						try {
							file.load();
						} catch (InvalidConfigurationException e1) { e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }
						if(state.equalsIgnoreCase("yt")) {
							file.set("BotConfig.Activity.Onlinestatus", "STREAMING");
							e.getJDA().getPresence().setActivity(Activity.streaming("on YouTube", "https://www.youtube.com/channel/UCBJhuPBSaucwk_TthujYrBw"));
							try {
								file.save();
								sendEmbed("yt", msg, e.getGuild());
								e.getMessage().addReaction("approved:678036504391581730").queue();
							} catch (IOException e1) {
								e.getMessage().addReaction("deny:678036504702091278").queue();
								e1.printStackTrace();
							}
						}else if(state.equalsIgnoreCase("twitch")) {
							file.set("BotConfig.Activity.Onlinestatus", "STREAMING");
							e.getJDA().getPresence().setActivity(Activity.streaming("on Twitch", "https://www.twitch.tv/redicrafteu"));
							try {
								file.save();
								sendEmbed("twitch", msg, e.getGuild());
								e.getMessage().addReaction("approved:678036504391581730").queue();
							} catch (IOException e1) {
								e.getMessage().addReaction("deny:678036504702091278").queue();
								e1.printStackTrace();
							}
						}else if(state.equalsIgnoreCase("stop")) {
							file.set("BotConfig.Activity.Text", "over the players.");
							file.set("BotConfig.Activity.Type", "WATCHING");
							e.getJDA().getPresence().setActivity(Activity.watching("over the players."));
							try {
								file.save();
								e.getMessage().addReaction("approved:678036504391581730").queue();
							} catch (IOException e1) {
								e.getMessage().addReaction("deny:678036504702091278").queue();
								e1.printStackTrace();
							}
						}else {
							chan.sendMessage("<:deny:678036504702091278> | " + Main.botprefix + "stream <yt|twitch|stop> [Message]").queue();
						}
					}else {
						chan.sendMessage("<:deny:678036504702091278> | Insufficent Permissions.").queue(msg -> {
							msg.delete().completeAfter(10, TimeUnit.SECONDS);
						});
					}
				}
			}
		}
	}
	
	private void sendEmbed(String state, String msg, Guild g) {
		if(g.getIdLong() == 548136727697555496L) {
			EmbedBuilder eb = new EmbedBuilder();
			TextChannel streamadv = g.getTextChannelById(732601395446022245L);
			Role streamsub = g.getRoleById(734679044947181579L);
			if(state.equalsIgnoreCase("yt")) {
				eb.setColor(Color.decode("#c4302b"));
				eb.setTitle("RediCraft Livestream", "https://www.youtube.com/channel/UCBJhuPBSaucwk_TthujYrBw");
				eb.setDescription("We are live on YouTube.");
			}else if(state.equalsIgnoreCase("twitch")) {
				eb.setColor(Color.decode("#6441a5"));
				eb.setDescription("We are live on Twitch.");
				eb.setTitle("RediCraft Livestream", "https://www.twitch.tv/redicrafteu");
			}
			SimpleDateFormat time = new SimpleDateFormat("dd MMMM yyyy");
	        String stime = time.format(new Date());
			eb.setFooter(msg + " ● " + stime);
			streamadv.sendMessage(streamsub.getAsMention()).embed(eb.build()).queue();
		}
	}
}