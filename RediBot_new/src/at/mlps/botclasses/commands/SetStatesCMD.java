package at.mlps.botclasses.commands;

import java.io.IOException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetStatesCMD extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		String[] args = e.getMessage().getContentRaw().split(" ");
		User maurice = e.getJDA().getUserById(228145889988837385L);
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "setonlinestatus")) {
				if(e.getAuthor() == maurice) {
					chan.sendMessage("Usage: " + Main.botprefix + "setactivity <online|idle|dnd|offline>").queue();
				}else {
					e.getMessage().addReaction("deny:678036504702091278");
					chan.sendMessage("You are not " + maurice.getName() + "#" + maurice.getDiscriminator() + " !").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "setactivity")) {
				if(e.getAuthor() == maurice) {
					chan.sendMessage("Usage: " + Main.botprefix + "setgame <playing|watching|listening|streaming> <Game mArg>").queue();
				}else {
					e.getMessage().addReaction("deny:678036504702091278");
					chan.sendMessage("You are not " + maurice.getName() + "#" + maurice.getDiscriminator() + " !").queue();
				}
			}
		}else if(args.length >= 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "setonlinestatus")) {
				if(e.getAuthor() == maurice) {
					String mode = args[1];
					YamlFile file = new YamlFile("configuration.yml");
					try {
						file.load();
					} catch (InvalidConfigurationException e1) { e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }
					if(mode.equalsIgnoreCase("online")) {
						file.set("BotConfig.Activity.Onlinestatus", "ONLINE");
						e.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
						e.getMessage().addReaction("approved:678036504391581730").queue();
						try { 
							file.save();
						} catch (IOException e1) { 
							e1.printStackTrace();
						}
					}else if(mode.equalsIgnoreCase("idle")) {
						file.set("BotConfig.Activity.Onlinestatus", "IDLE");
						e.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
						e.getMessage().addReaction("approved:678036504391581730").queue();
						try {
							file.save();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}else if(mode.equalsIgnoreCase("dnd")) {
						file.set("BotConfig.Activity.Onlinestatus", "DONOTDISTURB");
						e.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
						e.getMessage().addReaction("approved:678036504391581730").queue();
						try {
							file.save();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}else if(mode.equalsIgnoreCase("offline")) {
						file.set("BotConfig.Activity.Onlinestatus", "OFFLINE");
						e.getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
						e.getMessage().addReaction("approved:678036504391581730").queue();
						try {
							file.save();
						} catch (IOException e1) { 
							e1.printStackTrace(); 
						}
					}else {
						chan.sendMessage("Usage: " + Main.botprefix + "setactivity **<online|idle|dnd|offline>**").queue();
						e.getMessage().addReaction("deny:678036504702091278").queue();
					}
				}else {
					e.getMessage().addReaction("deny:678036504702091278");
					chan.sendMessage("You are not " + maurice.getName() + "#" + maurice.getDiscriminator() + " !").queue();
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "setactivity")) {
				if(e.getAuthor() == maurice) {
					StringBuilder sb = new StringBuilder();
					for(int i = 2; i < args.length; i++) {
						sb.append(args[i]).append(" ");
					}
					String reason = sb.toString().trim();
					String state = args[1];
					YamlFile file = new YamlFile("configuration.yml");
					try {
						file.load();
					} catch (InvalidConfigurationException e1) { e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }
					if(state.equalsIgnoreCase("playing")) {
						file.set("BotConfig.Activity.Type", "PLAYING");
						file.set("BotConfig.Activity.Text", reason);
						e.getJDA().getPresence().setActivity(Activity.playing(reason));
						try {
							file.save();
							e.getMessage().addReaction("approved:678036504391581730").queue();
						} catch (IOException e1) {
							e.getMessage().addReaction("deny:678036504702091278").queue();
							e1.printStackTrace();
						}
					}else if(state.equalsIgnoreCase("watching")) {
						file.set("BotConfig.Activity.Text", reason);
						file.set("BotConfig.Activity.Type", "WATCHING");
						e.getJDA().getPresence().setActivity(Activity.watching(reason));
						try {
							file.save();
							e.getMessage().addReaction("approved:678036504391581730").queue();
						} catch (IOException e1) {
							e.getMessage().addReaction("deny:678036504702091278").queue();
							e1.printStackTrace();
						}
					}else if(state.equalsIgnoreCase("listening")) {
						file.set("BotConfig.Activity.Text", reason);
						file.set("BotConfig.Activity.Type", "LISTENING");
						e.getJDA().getPresence().setActivity(Activity.listening(reason));
						try {
							file.save();
							e.getMessage().addReaction("approved:678036504391581730").queue();
						} catch (IOException e1) {
							e.getMessage().addReaction("deny:678036504702091278").queue();
							e1.printStackTrace();
						}
					}else if(state.equalsIgnoreCase("streaming")) {
						file.set("BotConfig.Activity.Text", reason);
						file.set("BotConfig.Activity.Type", "STREAMING");
						e.getJDA().getPresence().setActivity(Activity.streaming(reason, file.getString("BotConfig.Activity.StreamURL")));
						try {
							file.save();
							e.getMessage().addReaction("approved:678036504391581730").queue();
						} catch (IOException e1) {
							e.getMessage().addReaction("deny:678036504702091278").queue();
							e1.printStackTrace();
						}
					}else {
						chan.sendMessage("Usage: " + Main.botprefix + "setgame **<playing|watching|listening|streaming>** <Game mArg>").queue();
					}
				}else {
					e.getMessage().addReaction("deny:678036504702091278");
					chan.sendMessage("You are not " + maurice.getName() + "#" + maurice.getDiscriminator() + " !").queue();
				}
			}
		}
	}
}