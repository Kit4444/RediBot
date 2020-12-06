package at.mlps.botclasses.commands;

import java.time.OffsetDateTime;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AragonLoyalCommand extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		TextChannel chan = e.getChannel();
		if(g.getIdLong() == 376924440321327115L) {
			String[] args = e.getMessage().getContentRaw().split(" ");
			Role loyal = g.getRoleById(617799926306570250L);
			Role AAStaff = g.getRoleById(722970792924676129L);
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase(Main.botprefix + "aragonloyal")) {
					if(m.getRoles().contains(AAStaff)) {
						int membCount = 0;
						int botCount = 0;
						for(Member members : g.getMembers()) {
							OffsetDateTime now = OffsetDateTime.now();
							OffsetDateTime oneYear = now.minusYears(1);
							OffsetDateTime memberJoin = members.getTimeJoined();
							
							if(oneYear.isAfter(memberJoin)) {
								if(!members.getRoles().contains(loyal)) {
									if(!members.getUser().isBot()) {
										if(members.getUser().getIdLong() == 529805043541606406L) {
											botCount++;
										}else {
											g.addRoleToMember(members, loyal).queue();
											membCount++;
										}
									}else {
										
									}
								}
							}
						}
						if(botCount == 0) {
							if(membCount == 0) {
								chan.sendMessage("<:approved:678036504391581730> **|** Found 0 Members.").queue();
							}else {
								chan.sendMessage("<:approved:678036504391581730> **|** Found " + membCount + " Members and assigned the Loyal Role.").queue();
							}
						}else {
							if(membCount == 0) {
								chan.sendMessage("<:approved:678036504391581730> **|** Found 0 Members and skipped " + botCount + " Bots.").queue();
							}else {
								chan.sendMessage("<:approved:678036504391581730> **|** Found " + membCount + " Members and assigned the Loyal Role and skipped " + botCount + " Bots.").queue();
							}
						}
					}else {
						chan.sendMessage("<:deny:678036504702091278> **|** You don't have the permission to execute that.").queue();
					}
				}
			}
		}
	}
}