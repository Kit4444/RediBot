package at.mlps.botclasses.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LOA extends ListenerAdapter{
	
	static long approvalId = 887770018815827998l;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		Guild g = e.getGuild();
		Guild rcmain = e.getJDA().getGuildById(548136727697555496l);
		Role rcm_loa = rcmain.getRoleById(574642929289789440l);
		Role rcs_loa = g.getRoleById(784900192175783977l);
		TextChannel chan = e.getChannel();
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "loa")) {
				chan.sendMessage("Please use this command only, if you are staff on RediCraft. If you are RC-Staff, use it on the Staffserver!\n**Command Usage:** rb!loa <Date from> <Date to> <Reason> | Date use dd.MM.yyyy or dd/MM/yyyy - example: 27.03.2022").queue();
			}
		}else {
			if(g.getIdLong() == 612372586386423824l) {
				String dateFrom = args[1];
				String dateTo = args[2];
				if(dateFrom.length() == 0 && dateTo.length() == 0) {
					chan.sendMessage("").queue();
				}else {
					
				}
			}else {
				chan.sendMessage("Use this command only on the RediCraft Staffserver, otherwise this command wont work.").queue();
			}
			
		}
		/*if(cont.equalsIgnoreCase(Main.botprefix + "loa")) {
			if(g.getIdLong() == 612372586386423824l) {
				
			}else {
				chan.sendMessage("Please execute this command in the RediCraft Staffserver. ``You don't know which server? Then you are not part of the RediCraft Team!``").queue(ra -> {
					ra.delete().queueAfter(10, TimeUnit.SECONDS);
				});
			}
		}*/
	}
	
	String parseDate(String input, String inputPattern, String newPattern) {
		String output = "";
		SimpleDateFormat sdf = new SimpleDateFormat(newPattern);
		try {
			Date date = new SimpleDateFormat(inputPattern).parse(input);
			output = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return output;
	}
}