package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.main.Main;
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
			String messagenew = Main.encrypt(messageold);
			GuildLogEvents gl = new GuildLogEvents();
			if(e.getMessage().getAttachments().size() == 0) {
				gl.insertMsg(g.getIdLong(), e.getMessageIdLong(), messagenew, e.getAuthor().getIdLong(), e.getAuthor().isBot(), "noAttach");
			}else {
				gl.insertMsg(g.getIdLong(), e.getMessageIdLong(), messagenew, e.getAuthor().getIdLong(), e.getAuthor().isBot(), e.getMessage().getAttachments().get(0).getUrl());
			}
		}
	}
	
	public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        String messageold = gl.retMsg(g.getIdLong(), e.getMessageIdLong());
        String messagenew = Main.decrypt(messageold);
        String attachUri = gl.getAttach(g.getIdLong(), e.getMessageIdLong());
        eb.setTitle("Message has been updated.");
        eb.setDescription("Member: " + m.getAsMention() + "\nChannel: " + e.getChannel().getAsMention() + "\nJump to Message: " + e.getMessage().getJumpUrl());
        if(messagenew.length() >= 512) {
       	 	eb.addField("Message:", messagenew.substring(0, 512) + " ", false);
        }else {
       	 	eb.addField("Message:", messagenew + " ", false);
        }
        if(e.getMessage().getContentStripped().length() >= 512) {
        	eb.addField("New Message:", e.getMessage().getContentStripped().substring(0, 512), false);
        }else {
        	eb.addField("New Message:", e.getMessage().getContentStripped(), false);
        }
        if(!attachUri.equalsIgnoreCase("nouri") || !attachUri.equalsIgnoreCase("noAttach")) {
        	eb.addField("Attachment:", attachUri, false);
        }
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		if(!gl.isBotMessage(g.getIdLong(), e.getMessageIdLong())) {
			if(gl.enabledLog(g, "guildmessageupdate")) {
				gl.sendMsg(eb, g);
			}
		}
	}
	
	public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
        String messageold = gl.retMsg(g.getIdLong(), e.getMessageIdLong());
        String messagenew = Main.decrypt(messageold);
        String attachUri = gl.getAttach(g.getIdLong(), e.getMessageIdLong());
        eb.setTitle("Message has been deleted.");
        if(gl.retMID(g.getIdLong(), e.getMessageIdLong()) != 0L) {
        	Member m = g.getMemberById(gl.retMID(g.getIdLong(), e.getMessageIdLong()));
        	eb.setDescription("Member: " + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + "\nChannel: " + e.getChannel().getAsMention());
        }else {
        	eb.setDescription("Member: not cached." + "\nChannel: " + e.getChannel().getAsMention());
        }
        if(messagenew.length() >= 512) {
        	 eb.addField("Message:", messagenew.substring(0, 512) + " ", false);
        }else {
        	 eb.addField("Message:", messagenew + " ", false);
        }
        if(!attachUri.equalsIgnoreCase("nouri") || !attachUri.equalsIgnoreCase("noAttach")) {
        	eb.addField("Attachment:", attachUri, false);
        }
        eb.setFooter(stime);
		eb.setColor(gl.orange);
		if(!gl.isBotMessage(g.getIdLong(), e.getMessageIdLong())) {
			if(gl.enabledLog(g, "guildmessagedelete"))
			gl.sendMsg(eb, g);
		}
	}
}