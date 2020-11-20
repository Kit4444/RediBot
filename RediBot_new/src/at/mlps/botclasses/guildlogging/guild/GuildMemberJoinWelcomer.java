package at.mlps.botclasses.guildlogging.guild;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
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
	String membername;
	String membermention;
	long ruleschannel;
	long welcomechannel;
	TextChannel rulechannel;
	GuildLogEvents gl;
	String date;
	
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		g = e.getGuild();
		m = e.getMember();
		gl = new GuildLogEvents();
		if(gl.isRegistered(g.getIdLong())) {
			EmbedBuilder eb = new EmbedBuilder();
			guildname = g.getName();
			members = g.getMemberCount();
			membername = m.getUser().getName();
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
				welcomechannel = rs.getLong("welcomechannel");
				YamlFile cfg = new YamlFile("guildsettings.yml");
				try {
					cfg.load();
				} catch (InvalidConfigurationException | IOException e1) {
					e1.printStackTrace();
				}
				title = cfg.getString("Welcomer." + g.getIdLong() + ".welcTitle");
				text = cfg.getString("Welcomer." + g.getIdLong() + ".welcText");
				footer = cfg.getString("Welcomer." + g.getIdLong() + ".welcFooter");
				rs.close();
			}catch (SQLException e1) {
			}
			if(welcomechannel != 0L && rulechannel != null && welcomechannel != 0L) {
				TextChannel chan = g.getTextChannelById(welcomechannel);
				eb.setTitle(formattedReplace(title));
				eb.setDescription(formattedReplace(text));
				eb.setFooter(formattedReplace(footer), g.getIconUrl());
				eb.setThumbnail(m.getUser().getAvatarUrl());
				chan.sendMessage(eb.build()).queue();
			}
		}	
	}
	
	String formattedReplace(String text) {
		return text.replace("%servername", guildname).replace("%username", membername).replace("%usermention", membermention).replace("%members", String.valueOf(members)).replace("%ruleschannel", rulechannel.getAsMention()).replace("%date", date);
	}
}