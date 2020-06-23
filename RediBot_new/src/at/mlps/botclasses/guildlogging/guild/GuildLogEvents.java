package at.mlps.botclasses.guildlogging.guild;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostTierEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateExplicitContentLevelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateOwnerEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateRegionEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateVerificationLevelEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLogEvents extends ListenerAdapter{
	
	public Color darkred = Color.decode("#AA0000");
	public Color red = Color.decode("#FF5555");
	public Color green = Color.decode("#55FF55");
	public Color orange = Color.decode("#FFAA00");
	
	long redimain = 548136727697555496L;
	long redibotlog = 637362851245195264L;

	public long rediassetg = 671772592390144061L;
	public long rediassetlog = 709379395428679681L;
	
	//if a user joins the server where the bot is in.
	public void onGuildMemberJoin(GuildMemberJoinEvent e){
		Guild g = e.getGuild();
		Member m = e.getMember();
		if(g.getIdLong() == redimain) {
			YamlFile file = new YamlFile("configuration.yml");
			try {
				file.load();
			} catch (InvalidConfigurationException | IOException e1) {
				e1.printStackTrace();
			}
			TextChannel rules = g.getTextChannelById(548187134687838218L);
			Role guest = e.getGuild().getRoleById(651569972920713226L);
			g.addRoleToMember(e.getMember(), guest).complete();
			SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
	        String stime = time.format(new Date());
			if(file.getBoolean("Members.ID." + g.getIdLong() + "." + m.getIdLong())) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Member has rejoined the server");
				eb.setDescription("Welcome on RediCraft, " + m.getAsMention() + ".\nBefore you continue using this server, please read our rules carefully through.\nYou can find them in " + rules.getAsMention() + "\n \nHave fun on RediCraft\nThe RediCraft Administration");
				eb.setThumbnail(m.getUser().getAvatarUrl());
				eb.setColor(Color.decode("#5555FF"));
				eb.setFooter("Joindate: " + stime, g.getIconUrl());
				g.getTextChannelById(637343872422248458L).sendMessage(eb.build()).queue();
			}else {
				file.set("Members.ID." + g.getIdLong() + "." + m.getIdLong(), true);
				file.set("Members.Date." + g.getIdLong() + "." + m.getIdLong(), stime);
				try {
					file.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Member has joined the server");
				eb.setDescription("Welcome on RediCraft, " + m.getAsMention() + ".\nBefore you continue using this server, please read our rules carefully through.\nYou can find them in " + rules.getAsMention() + "\n \nHave fun on RediCraft\nThe RediCraft Administration");
				eb.setThumbnail(m.getUser().getAvatarUrl());
				eb.setFooter("Joindate: " + stime, g.getIconUrl());
				eb.setColor(Color.decode("#55FF55"));
				g.getTextChannelById(637343872422248458L).sendMessage(eb.build()).queue();
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(Color.green);
			eb.setTitle("Member joined");
			eb.setDescription( m.getAsMention() + " has joined the guild.");
			eb.setFooter(stime, g.getIconUrl());
			sendMsg(eb, g);
		}
	}

	public void onGuildMemberRemove(GuildMemberRemoveEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		YamlFile file = new YamlFile("configuration.yml");
		try {
			file.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(red);
		eb.setTitle("Member left");
		eb.setDescription(m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " has left the guild.\nJoindate: " + file.getString("Members.Date." + g.getIdLong() + "." + m.getIdLong()));
		eb.setFooter(stime, g.getIconUrl());
		sendMsg(eb, g);
	}

	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Nickname update");
		eb.addField("User: ", m.getAsMention(), false);
		eb.addField("Old Nick:", e.getOldNickname() + " ", false);
		eb.addField("New Nick:", e.getNewNickname() + " ", false);
		eb.setColor(orange);
		eb.setFooter(stime, g.getIconUrl());
		sendMsg(eb, g);
	}
	
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role add");
		eb.setColor(orange);
		if(e.getRoles().size() == 1) {
			eb.setDescription("User: " + m.getAsMention() + "\nRole added: " + e.getRoles().get(0).getName());
		}else {
			StringBuilder sb = new StringBuilder();
			for(Role r : e.getRoles()) {
				sb.append(r.getName());
				sb.append(", ");
			}
			eb.setDescription("User: " + m.getAsMention() + "\nRoles added: " + sb.toString());
		}
		eb.setFooter(stime, g.getIconUrl());
		if(g.getIdLong() == redimain) {
			Role guest = e.getGuild().getRoleById(651569972920713226L);
			Role player = e.getGuild().getRoleById(548175925901000734L);
			if(m.getRoles().contains(player)) {
				g.removeRoleFromMember(m, guest).complete();
			}
		}
		sendMsg(eb, g);
	}
	
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role remove");
		eb.setColor(orange);
		if(e.getRoles().size() == 1) {
			eb.setDescription("User: " + m.getAsMention() + "\nRole removed: " + e.getRoles().get(0).getName());
		}else {
			StringBuilder sb = new StringBuilder();
			for(Role r : e.getRoles()) {
				sb.append(r.getName());
				sb.append(", ");
			}
			eb.setDescription("User: " + m.getAsMention() + "\nRoles removed: " + sb.toString());
		}
		eb.setFooter(stime, g.getIconUrl());
		sendMsg(eb, g);
	}
	
	public void onGuildUpdateIcon(GuildUpdateIconEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Click me if you don't see the avatar", e.getNewIconUrl());
		eb.setDescription("Guild Icon has been updated.");
		eb.setImage(e.getNewIconUrl());
		eb.setColor(green);
		eb.setFooter(stime);
		eb.addField("Old AvatarURL:", e.getOldIconUrl(), false);
		eb.addField("New AvatarURL:", e.getNewIconUrl(), false);
		sendMsg(eb, g);
	}
	
	public void onGuildUpdateName(GuildUpdateNameEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has been updated");
		eb.setColor(green);
    	eb.setDescription("The guild has now a new name.");
    	eb.addField("Old Name:", e.getOldName(), false);
    	eb.addField("New Name:", e.getNewName(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildUpdateExplicitContentLevel(GuildUpdateExplicitContentLevelEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has been updated");
		eb.setColor(green);
    	eb.setDescription("The guild has now a new explicit content level.");
    	eb.addField("Old ECL:", e.getOldLevel().getDescription(), false);
    	eb.addField("New ECL:", e.getNewLevel().getDescription(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildUpdateRegion(GuildUpdateRegionEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has been updated");
		eb.setColor(green);
    	eb.setDescription("The guild is now in a new region.");
    	eb.addField("Old Region:", e.getOldRegion().getName(), false);
    	eb.addField("New Region:", e.getNewRegion().getName(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildUpdateVerificationLevel(GuildUpdateVerificationLevelEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has been updated");
		eb.setColor(green);
    	eb.setDescription("The guild has now a new verification level.");
    	eb.addField("Old VL:", e.getOldValue().toString(), false);
    	eb.addField("New VL:", e.getNewValue().toString(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent e) {
		Member m = e.getMember();
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("New Boost Time");
    	eb.setDescription(m.getAsMention() + " has renewed the boost");
    	eb.addField("Old Time", retDate(e.getOldTimeBoosted()), false);
    	eb.addField("New Time", retDate(e.getNewTimeBoosted()), false);
    	eb.setFooter(stime);
		eb.setColor(green);
		sendMsg(eb, g);
	}

	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("New Boost Count");
		eb.setColor(green);
    	eb.setDescription("The Boostcount has been changed.");
    	eb.addField("Old Count", e.getOldBoostCount() + "", false);
    	eb.addField("New Count", e.getNewBoostCount() + "", false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("New Boost Count");
		eb.setColor(green);
    	eb.setDescription("The Boostcount has been changed.");
    	eb.addField("Old Values", "Max. Bitrate: " + e.getOldBoostTier().getMaxBitrate() + "\nMax. Emotes: " + e.getOldBoostTier().getMaxEmotes() + "\nMax. Filesize: " + e.getOldBoostTier().getMaxFileSize(), false);
    	eb.addField("New Count", "Max. Bitrate: " + e.getNewBoostTier().getMaxBitrate() + "\nMax. Emotes: " + e.getNewBoostTier().getMaxEmotes() + "\nMax. Filesize: " + e.getNewBoostTier().getMaxFileSize(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildUpdateOwner(GuildUpdateOwnerEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has been updated");
		eb.setColor(green);
    	eb.setDescription("The guild has now a new owner.");
    	eb.addField("Old Owner:", e.getOldOwner().getAsMention() + " / " + e.getOldOwner().getUser().getName() + "#" + e.getOldOwner().getUser().getDiscriminator(), false);
    	eb.addField("New Owner:", e.getNewOwner().getAsMention() + " / " + e.getNewOwner().getUser().getName() + "#" + e.getNewOwner().getUser().getDiscriminator(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildInviteCreate(GuildInviteCreateEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has a new Invite");
		eb.setColor(green);
    	eb.setDescription("The guild has a new invite.");
    	eb.addField("InviteURL:", e.getInvite().getUrl(), false);
    	eb.addField("Inviter:", e.getInvite().getInviter().getName() + "#" + e.getInvite().getInviter().getDiscriminator(), false);
    	eb.addField("Max Uses:", e.getInvite().getMaxUses() + " ", false);
    	eb.addField("Channel:", "Name: " + e.getInvite().getChannel().getName() + "\nChanneltype: " + e.getInvite().getChannel().getType().toString(), false);
    	eb.addField("Creation Date:", retDate(e.getInvite().getTimeCreated()), false);
    	eb.addField("Time Valid:", e.getInvite().getMaxAge() + " seconds", false);
    	eb.addField("Current Uses: ", e.getInvite().getUses() + " ", false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onGuildInviteDelete(GuildInviteDeleteEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Guild has now an invite less.");
		eb.setColor(green);
    	eb.setDescription("The guild has now lost an invite.");
    	eb.addField("InviteURL:", e.getUrl(), false);
    	eb.setFooter(stime);
    	sendMsg(eb, g);
	}
	
	public void onEmoteAdded(EmoteAddedEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Emote has been added.");
        eb.setThumbnail(e.getEmote().getImageUrl());
        eb.addField("Name:", e.getEmote().getName(), false);
        eb.setFooter(stime);
		eb.setColor(green);
        sendMsg(eb, g);
	}
	
	public void onEmoteRemoved(EmoteRemovedEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Emote has been removed.");
        eb.setThumbnail(e.getEmote().getImageUrl());
        eb.addField("Name:", e.getEmote().getName(), false);
        eb.setFooter(stime);
		eb.setColor(red);
		sendMsg(eb, g);
	}
	
	public void onEmoteUpdateNameEvent(EmoteUpdateNameEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Emote has been updated.");
        eb.setThumbnail(e.getEmote().getImageUrl());
        eb.addField("Old Name:", e.getOldName(), false);
        eb.addField("New Name:", e.getNewName(), false);
        eb.setFooter(stime);
		eb.setColor(orange);
		sendMsg(eb, g);
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		if(!e.getAuthor().isBot() || !e.getAuthor().isFake() || !e.isWebhookMessage()) {
			insertMsg(g.getIdLong(), e.getMessageIdLong(), e.getMessage().getContentDisplay().toString());
		}
	}
	
	public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Message has been updated.");
        eb.setDescription("Member: " + m.getAsMention() + "\nChannel: " + e.getChannel().getAsMention() + "\nJump to Message: " + e.getMessage().getJumpUrl());
        eb.addField("Old Message:", retMsg(g.getIdLong(), e.getMessageIdLong()), false);
        eb.addField("New Message:", e.getMessage().getContentStripped(), false);
        eb.setFooter(stime);
		eb.setColor(orange);
		if(!e.getAuthor().isBot() || !e.getAuthor().isFake()) {
			sendMsg(eb, g);
		}
	}
	
	public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Message has been deleted.");
        eb.setDescription("Channel: " + e.getChannel().getAsMention());
        eb.addField("Message:", retMsg(g.getIdLong(), e.getMessageIdLong()), false);
        eb.setFooter(stime);
		eb.setColor(orange);
		sendMsg(eb, g);
	}
	
	public void insertMsg(long guildid, long msgid, String msgtxt) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redibot_msglog (guildid, msgid, msgtxt) VALUES (?, ?, ?)");
			ps.setLong(1, guildid);
			ps.setLong(2, msgid);
			ps.setString(3, msgtxt);
			ps.executeUpdate();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String retMsg(long guildid, long msgid) {
		String msg = "";
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("guildid", guildid);
		hm.put("msgid", msgid);
		try {
			if(Main.mysql.isInDatabase("redibot_msglog", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_msglog WHERE guildid = ? AND msgid = ?");
				ps.setLong(1, guildid);
				ps.setLong(2, msgid);
				ResultSet rs = ps.executeQuery();
				rs.next();
				msg = rs.getString("msgtxt");
			}else {
				msg = "This message doesn't exists in the Database!";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public void sendMsg(EmbedBuilder eb, Guild g) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog");
			ResultSet rs = ps.executeQuery();
			long guildid = g.getIdLong();
			while(rs.next()) {
				long saveguildid = rs.getLong("guildid");
				long savetxtchan = rs.getLong("channelid");
				if(guildid == saveguildid) {
					TextChannel chan = g.getTextChannelById(savetxtchan);
					chan.sendMessage(eb.build()).queue();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String hexCol(int color) {
		return String.format("#%06X", (0xFFFFFF & color)).toLowerCase();
	}
	
	public String retDate(OffsetDateTime odt) {
		return odt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss"));
	}
	
	public void welcCon() {
		System.out.println("#####   ######  #####   #  #####    ####   #######");
		System.out.println("#    #  #       #    #  #  #    #  #    #     #");
		System.out.println("#    #  #       #    #  #  #    #  #    #     #");
		System.out.println("#    #  #       #    #  #  #    #  #    #     #");
		System.out.println("#####   #####   #    #  #  #####   #    #     #");
		System.out.println("# #     #       #    #  #  #    #  #    #     #");
		System.out.println("#  #    #       #    #  #  #    #  #    #     #");
		System.out.println("#   #   #       #    #  #  #    #  #    #     #");
		System.out.println("#    #  #       #    #  #  #    #  #    #     #");
		System.out.println("#    #  ######  #####   #  #####    ####      #");
		YamlFile file = new YamlFile("configuration.yml");
		try {
			file.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		System.out.print("RediBot has been loaded. Version: " + file.getString("BotInfo.version") + " Javaversion: " + System.getProperty("java.version") + "\n");
	}
}