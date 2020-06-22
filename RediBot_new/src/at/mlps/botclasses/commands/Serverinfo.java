package at.mlps.botclasses.commands;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Serverinfo extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		String cont = e.getMessage().getContentRaw();
		if(cont.equalsIgnoreCase(Main.botprefix + "serverinfo")) {
			Member owner = e.getGuild().getMemberById(e.getGuild().getOwnerIdLong());
			int cats, text, audio, member, online = 0, human = 0, bot = 0, idle = 0, dnd = 0, offline = 0, staff = 0, booster, mbooster = 0;
			cats = e.getGuild().getCategories().size();
			text = e.getGuild().getTextChannels().size();
			audio = e.getGuild().getVoiceChannels().size();
			booster = e.getGuild().getBoostCount();
			member = e.getGuild().getMemberCount();
			Role staffrole = null;
			Role nbooster = null;
			if(e.getGuild().getIdLong() == 548136727697555496L) { // redimain
				staffrole = e.getGuild().getRoleById(552161168412639235L);
				nbooster = e.getGuild().getRoleById(585532995151200309L);
			}else if(e.getGuild().getIdLong() == 612372586386423824L) { //redistaff
				staffrole = e.getGuild().getRoleById(627956904684945448L); 
			}else if(e.getGuild().getIdLong() == 580465138230886430L) { //redilog
				staffrole = e.getGuild().getRoleById(670152985942163467L);
			}else if(e.getGuild().getIdLong() == 588548993026359313L) { //redifm
				staffrole = e.getGuild().getRoleById(656676898096676885L);
			}else if(e.getGuild().getIdLong() == 376924440321327115L) { //aragonairlines
				staffrole = e.getGuild().getRoleById(615873724067086381L);
				nbooster = e.getGuild().getRoleById(585553543927431178L);
			}else if(e.getGuild().getIdLong() == 641699416339906572L) { //friendsnfamily
				staffrole = e.getGuild().getRoleById(641834605577633793L);
			}else {
				staffrole = null;
			}
			
			for(Member m : e.getGuild().getMembers()) {
				String status = m.getOnlineStatus().toString();
				if(status.equals(OnlineStatus.ONLINE.toString())) {
					online++;
				}
				if(status.equals(OnlineStatus.IDLE.toString())) {
					idle++;
				}
				if(status.equals(OnlineStatus.DO_NOT_DISTURB.toString())) {
					dnd++;
				}
				if(status.equals(OnlineStatus.OFFLINE.toString())) {
					offline++;
				}
				if(m.getUser().isBot()) {
					bot++;
				}else {
					human++;
				}
				if(m.getRoles().contains(staffrole)) {
					staff++;
				}
				if(m.getRoles().contains(nbooster)) {
					mbooster++;
				}
			}
			List<Role> roles = e.getGuild().getRoles();
			int role = roles.size();
			StringBuilder sb = new StringBuilder();
			for(Role rs : roles) {
				sb.append(rs.getName());
				sb.append(", ");
			}
			String guildCreate = e.getGuild().getTimeCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss"));
			long guildid = e.getGuild().getIdLong();
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(e.getMember().getColor());
			eb.setTitle("Serverinfo for " + e.getGuild().getName());
			eb.setThumbnail(e.getGuild().getIconUrl());
			eb.setDescription("Displays all discord-server relevant informations.");
			eb.addField("Serverowner:", owner.getUser().getName() + "#" + owner.getUser().getDiscriminator(), false);
			eb.addField("Member-Status:", "<:monitoring:671772876436799508> (Staff) = " + staff + ", <:online:671772876482936862> = " + online + ", <:idle:671772876449251383> = " + idle + ", <:dnd:708982976838369320> = " + dnd + ", <:offline:671772876499582996> = " + offline, false);
			eb.addField("Member & Botcount:", "Members: " + human + ", Bots: " + bot + ", Total: " + member, false);
			eb.addField("Categories:", String.valueOf(cats), true);
			eb.addField("Text Channels:", String.valueOf(text), true);
			eb.addField("Voice Channels:", String.valueOf(audio), true);
			eb.addField("Nitro Boosters:", booster + " Boosts from " + mbooster + " Members", true);
			if(role <= 32) {
				eb.addField("Roles (**" + role + "**):", sb.toString(), false);
			}else {
				eb.addField("Roles (**" + role + "**):", "Too many roles to list.", false);
			}
			eb.setFooter("ID: " + guildid + " | Server created: " + guildCreate, e.getGuild().getIconUrl());
			chan.sendMessage(eb.build()).queue();
		}
		if(cont.equalsIgnoreCase(Main.botprefix + "guilds")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
			String time = sdf.format(new Date());
			List<Guild> guilds = e.getJDA().getGuilds();
			StringBuilder sb = new StringBuilder();
			for(Guild g : guilds) {
				sb.append("``" + g.getIdLong() + "`` -> " + g.getName() + " -> Members: " + g.getMembers().size());
				sb.append("\n ");
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Servers:");
			eb.setColor(e.getMember().getColor());
			eb.addField("Guilds (**" + guilds.size() + "**):", sb.toString(), false);
			eb.setFooter("Requested from: " + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + " at " + time, e.getAuthor().getAvatarUrl());
			chan.sendMessage(eb.build()).queue();
		}
	}
}