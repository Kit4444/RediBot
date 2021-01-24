package at.mlps.botclasses.guildlogging.guild;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoinWelcomer extends ListenerAdapter{
	
	String title;
	String text;
	String footer;
	Guild g;
	Member m;
	String guildname;
	int members;
	String membernamewodisc;
	String membernamewdisc;
	String membermention;
	long ruleschannel;
	long welcomechannel;
	TextChannel rulechannel;
	GuildLogEvents gl;
	String date;
	String color;
	String thumbnail;
	
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		g = e.getGuild();
		m = e.getMember();
		gl = new GuildLogEvents();
		if(gl.isRegistered(g.getIdLong())) {
			EmbedBuilder eb = new EmbedBuilder();
			guildname = g.getName();
			members = g.getMemberCount();
			membernamewodisc = m.getUser().getName();
			membernamewdisc = m.getUser().getName() + "#" + m.getUser().getDiscriminator();
			membermention = m.getAsMention();
			date = gl.getFormattedDate("dd/MM/yyyy - HH:mm:ss");
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog WHERE guildid = ?");
				ps.setLong(1, g.getIdLong());
				ResultSet rs = ps.executeQuery();
				rs.next();
				ruleschannel = rs.getLong("welcRuleChannel");
				if(ruleschannel == 0L) {
					rulechannel = g.getDefaultChannel();
				}else {
					rulechannel = g.getTextChannelById(ruleschannel);
				}
				color = rs.getString("welcomer_color");
				thumbnail = rs.getString("welcomer_thumbnail");
				welcomechannel = rs.getLong("welcomechannel");
				YamlFile cfg = new YamlFile("configs/guildsettings.yml");
				try {
					cfg.load();
				} catch (InvalidConfigurationException | IOException e1) {
					e1.printStackTrace();
				}
				title = cfg.getString("Welcomer." + g.getIdLong() + ".welcTitle");
				text = cfg.getString("Welcomer." + g.getIdLong() + ".welcText");
				footer = cfg.getString("Welcomer." + g.getIdLong() + ".welcFooter");
				if(cfg.contains("Joinroles." + g.getIdLong())) {
					List<Long> joinroles = cfg.getLongList("Joinroles." + g.getIdLong());
					for(Long l : joinroles) {
						Role role = g.getRoleById(l);
						if(role != null) {
							g.addRoleToMember(m.getUser().getIdLong(), role).queue();
						}
					}
				}
				rs.close();
			}catch (SQLException e1) {
			}
			if(welcomechannel != 0L && rulechannel != null && welcomechannel != 0L) {
				TextChannel chan = g.getTextChannelById(welcomechannel);
				eb.setTitle(formattedReplace(title));
				eb.setDescription(formattedReplace(text));
				eb.setFooter(formattedReplace(footer), g.getIconUrl());
				if(thumbnail.equalsIgnoreCase("useravatar")) {
					eb.setThumbnail(m.getUser().getAvatarUrl());
				}else if(thumbnail.equalsIgnoreCase("guildicon")) {
					eb.setThumbnail(g.getIconUrl());
				}else {
					eb.setThumbnail(thumbnail);
				}
				if(color.equalsIgnoreCase("membercolor")) {
					eb.setColor(m.getColor());
				}else if(color.equalsIgnoreCase("random")) {
					eb.setColor(new Color(getRGB(), getRGB(), getRGB()));
				}else if(color.startsWith("#")) {
					eb.setColor(Color.getColor(color.substring(1)));
				}else if(color.matches("^[0-9]+$")) {
					eb.setColor(new Color(Integer.valueOf(color)));
				}
				chan.sendMessage(eb.build()).queue();
			}
		}	
	}
	
	String formattedReplace(String text) {
		return text.replace("%servername", guildname).replace("%usernamewodisc", membernamewodisc).replace("%usernamewdisc", membernamewdisc).replace("%usermention", membermention).replace("%members", String.valueOf(members)).replace("%ruleschannel", rulechannel.getAsMention()).replace("%date", date);
	}
	
	int getRGB(){
		Random r = new Random();
		int number = r.nextInt(255);
		while(number < 0) {
			number = r.nextInt(255);
		}
		return number;
	}
}