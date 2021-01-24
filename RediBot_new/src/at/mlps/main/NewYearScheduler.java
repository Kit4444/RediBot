package at.mlps.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class NewYearScheduler extends TimerTask{
	
	public JDA jda;
	public NewYearScheduler(JDA jda) {
		this.jda = jda;
	}
	
	@Override
	public void run() {
		Guild g = jda.getGuildById(548136727697555496L);
		TextChannel chan = g.getTextChannelById(552163655366475786L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String date = sdf.format(new Date());
		if(date.matches("16:00:00")) {
			chan.sendMessage("8 Hours remaining until new year!").queue();
		}else if(date.matches("17:00:00")){
			chan.sendMessage("7 Hours remaining until new year!").queue();
		}else if(date.matches("18:00:00")){
			chan.sendMessage("6 Hours remaining until new year!").queue();
		}else if(date.matches("19:00:00")){
			chan.sendMessage("5 Hours remaining until new year!").queue();
		}else if(date.matches("20:00:00")){
			chan.sendMessage("4 Hours remaining until new year!").queue();
		}else if(date.matches("21:00:00")){
			chan.sendMessage("3 Hours remaining until new year!").queue();
		}else if(date.matches("22:00:00")){
			chan.sendMessage("2 Hours remaining until new year!").queue();
		}else if(date.matches("23:00:00")){
			chan.sendMessage("1 Hour remaining until new year!").queue();
		}else if(date.matches("23:30:00")){
			chan.sendMessage("30 mins remaining until new year!").queue();
		}else if(date.matches("23:45:00")){
			chan.sendMessage("15 mins remaining until new year!").queue();
		}else if(date.matches("23:55:00")){
			chan.sendMessage("5 mins remaining until new year!").queue();
		}else if(date.matches("23:56:00")){
			chan.sendMessage("4 mins remaining until new year!").queue();
		}else if(date.matches("23:57:00")){
			chan.sendMessage("3 mins remaining until new year!").queue();
		}else if(date.matches("23:58:00")){
			chan.sendMessage("2 mins remaining until new year!").queue();
		}else if(date.matches("23:59:00")){
			chan.sendMessage("1 min remaining until new year!").queue();
		}else if(date.matches("23:59:50")){
			chan.sendMessage("***10*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:51")){
			chan.sendMessage("***9*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:52")){
			chan.sendMessage("***8*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:53")){
			chan.sendMessage("***7*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:54")){
			chan.sendMessage("***6*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:55")){
			chan.sendMessage("***5*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:56")){
			chan.sendMessage("***4*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:57")){
			chan.sendMessage("***3*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:58")){
			chan.sendMessage("***2*** seconds remaining until new year!").queue();
		}else if(date.matches("23:59:59")){
			chan.sendMessage("***1*** second remaining until new year!").queue();
		}else if(date.matches("00:00:00")){
			chan.sendMessage("***HAPPY NEW YEAR 2021!***").queue();
		}
	}
}