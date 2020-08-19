package at.mlps.botclasses.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AnnounceCMD extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		TextChannel chan = e.getChannel();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		List<Attachment> att = e.getMessage().getAttachments();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "announce")) {
				if(m.hasPermission(Permission.ADMINISTRATOR) || m.hasPermission(Permission.MANAGE_SERVER)) {
					chan.sendMessage("<:deny:678036504702091278> | Usage: " + Main.botprefix + "announce <Role> <Channel> <Text>").queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> | Insufficent Permissions.").queue();
				}
			}
		}else {
			if(args[0].equalsIgnoreCase(Main.botprefix + "announce")) {
				if(m.hasPermission(Permission.ADMINISTRATOR) || m.hasPermission(Permission.MANAGE_SERVER)) {
					long rid = 0L;
					Role role = null;
					if(args[1].matches("^[0-9]+$")) {
						rid = Long.parseLong(args[1]);
						role = g.getRoleById(rid);
					}else {
						role = e.getMessage().getMentionedRoles().get(0);
					}
					long uid = 0L;
					TextChannel achan = null;
					if(args[2].matches("^[0-9]+$")) {
						uid = Long.parseLong(args[2]);
						achan = g.getTextChannelById(uid);
					}else {
						achan = e.getMessage().getMentionedChannels().get(0);
					}
					StringBuilder sb = new StringBuilder();
					for(int i = 3; i < args.length; i++) {
						sb.append(args[i]);
						sb.append(" ");
					}
					String msg = sb.toString();
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle(g.getName() + "» Announcement");
					eb.setAuthor(m.getUser().getName(), null, m.getUser().getAvatarUrl());
					eb.setDescription(msg);
					SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy ● HH:mm:ss a z");
			        String stime = time.format(new Date());
					eb.setFooter(stime, g.getIconUrl());
					String att1 = "";
					if(!att.isEmpty()) {
						if(att.get(0).isImage()) {
							att1 = att.get(0).getUrl();
							eb.setImage(att1);
						}else {
							att1 = att.get(0).getUrl();
						}
					}
					achan.sendMessage(":newspaper: | " + role.getAsMention() + " \n" + att1).embed(eb.build()).queue();
				}else {
					chan.sendMessage("<:deny:678036504702091278> | Insufficent Permissions.").queue();
				}
			}
		}
	}
	
}
