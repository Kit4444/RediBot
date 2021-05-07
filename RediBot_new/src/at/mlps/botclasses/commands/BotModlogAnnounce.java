package at.mlps.botclasses.commands;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotModlogAnnounce extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		TextChannel chan = e.getChannel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss a z");
		if(g.getIdLong() == 671772592390144061L) {
			if(m.getIdLong() == 228145889988837385L) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase(Main.botprefix + "guildannounce")) {
						chan.sendMessage("Use this: rb!guildannounce <Text>").queue();
					}
				}else {
					if(args[0].equalsIgnoreCase(Main.botprefix + "guildannounce")) {
						StringBuilder sb = new StringBuilder();
						for(int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String text = sb.toString();
						EmbedBuilder eb = new EmbedBuilder();
						eb.setAuthor(m.getUser().getName(), null, m.getUser().getAvatarUrl());
						eb.setTitle("RediBot Announcement");
						eb.setColor(Color.decode("#5555ff"));
						eb.setFooter("Guild: " + g.getName() + " / " + g.getIdLong() + " | Date: " + sdf.format(new Date()));
						try {
							PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redibot_guildlog");
							ResultSet rs = ps.executeQuery();
							while(rs.next()) {
								long cid = rs.getLong("channelid");
								long gid = rs.getLong("guildid");
								Guild guild = e.getJDA().getGuildById(gid);
								TextChannel channel = guild.getTextChannelById(cid);
								if(text.contains("-nomention")) {
									eb.setDescription(text.replace("-nomention", ""));
									channel.sendMessage(":newspaper:").embed(eb.build()).queue();
								}else {
									eb.setDescription(text);
									channel.sendMessage(":newspaper: | " + g.getOwner().getAsMention()).embed(eb.build()).queue();
								}
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}
}
