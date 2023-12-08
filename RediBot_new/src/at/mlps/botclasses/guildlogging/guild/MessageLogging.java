package at.mlps.botclasses.guildlogging.guild;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.TextCryptor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageLogging extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		if(!e.getAuthor().isBot() || !e.isWebhookMessage()) {
			String messageold = e.getMessage().getContentRaw();
			String messagenew = TextCryptor.encrypt(messageold, getPassword());
			GuildLogEvents gl = new GuildLogEvents();
			gl.insertMsg(g.getIdLong(), e.getMessageIdLong(), messagenew, e.getAuthor().getIdLong(), e.getAuthor().isBot());
		}
	}
	
	public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        String originalTextOld = gl.returnOriginalMessage(g.getIdLong(), e.getMessageIdLong());
        String originalTextNew = TextCryptor.decrypt(originalTextOld, getPassword());
        String updatedTextOld = gl.returnLastUpdatedMessage(g.getIdLong(), e.getMessageIdLong());
        String updatedTextNew = TextCryptor.decrypt(updatedTextOld, getPassword());
        eb.setTitle("Message has been updated.");
        eb.setAuthor(m.getUser().getName(), null, m.getUser().getAvatarUrl());
        eb.setDescription("Channel: " + e.getChannel().getAsMention() + " \nJump to Message [here](" + e.getMessage().getJumpUrl() + ")");
        if(originalTextNew.length() >= 512) {
       	 	eb.addField("Original Message:", originalTextNew.substring(0, 512) + " ", false);
        }else {
       	 	eb.addField("Original Message:", originalTextNew + " ", false);
        }
        gl.updateLastUpdatedMessage(g.getIdLong(), e.getMessageIdLong(), TextCryptor.encrypt(e.getMessage().getContentRaw(), getPassword()));
        if(!updatedTextNew.equalsIgnoreCase(originalTextNew)) {
        	if(updatedTextNew.length() >= 512) {
           	 	eb.addField("Previously Edited Message:", updatedTextNew.substring(0, 512) + " ", false);
            }else {
           	 	eb.addField("Previously Edited Message:", updatedTextNew + " ", false);
            }
        }
        if(e.getMessage().getContentStripped().length() >= 512) {
        	eb.addField("New Message:", e.getMessage().getContentRaw().substring(0, 512), false);
        }else {
        	eb.addField("New Message:", e.getMessage().getContentRaw(), false);
        }
        eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		eb.setColor(gl.orange);
		if(!gl.isBotMessage(g.getIdLong(), e.getMessageIdLong())) {
			if(gl.enabledLog(g, "guildmessageupdate")) {
				YamlFile cfg = new YamlFile("configs/guildsettings.yml");
				try {
					cfg.load();
				} catch (InvalidConfigurationException | IOException e1) {
					e1.printStackTrace();
				}
				if(cfg.contains("Exempts." + g.getIdLong())) {
					List<Long> exempts = cfg.getLongList("Exempts." + g.getIdLong());
					if(!exempts.contains(e.getChannel().getIdLong())) {
						gl.sendMsg(eb, g);
					}
				}else {
					gl.sendMsg(eb, g);
				}
			}
		}
	}
	
	public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        YamlFile cfg = new YamlFile("configs/guildsettings.yml");
		try {
			cfg.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
        String messageold = gl.returnLastUpdatedMessage(g.getIdLong(), e.getMessageIdLong());
        String messagenew = TextCryptor.decrypt(messageold, getPassword());
        eb.setTitle("Message has been deleted.");
        if(gl.retMID(g.getIdLong(), e.getMessageIdLong()) != 0L) {
        	Member m = g.getMemberById(gl.retMID(g.getIdLong(), e.getMessageIdLong()));
        	eb.setDescription("Channel: " + e.getChannel().getAsMention());
        	eb.setAuthor(m.getUser().getName(), null, m.getUser().getAvatarUrl());
        }else {
        	eb.setDescription("Member: not cached." + "\nChannel: " + e.getChannel().getAsMention());
        }
        if(messagenew.length() >= 512) {
        	 eb.addField("Message:", messagenew.substring(0, 512) + " ", false);
        }else {
        	 eb.addField("Message:", messagenew + " ", false);
        }
        eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		eb.setColor(gl.orange);
		if(!gl.isBotMessage(g.getIdLong(), e.getMessageIdLong())) {
			if(gl.enabledLog(g, "guildmessagedelete")) {
				if(cfg.contains("Exempts." + g.getIdLong())) {
					List<Long> exempts = cfg.getLongList("Exempts." + g.getIdLong());
					if(!exempts.contains(e.getChannel().getIdLong())) {
						gl.sendMsg(eb, g);
					}
				}else {
					gl.sendMsg(eb, g);
				}
			}
		}
	}
	
	private char[] getPassword() {
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		return file.getString("BotConfig.DEncryptPass").toCharArray();
	}
}