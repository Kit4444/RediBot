package at.mlps.botclasses.commands;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import at.mlps.main.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NewYearClass extends ListenerAdapter{
	
	int width = 300;
	int height = 100;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "countdown")) {
				File input = null;
				long current = System.currentTimeMillis()/1000;
				long newyear = 1613861999;
				long diff = (newyear - current);
				int id = random(1, 4);
				switch(id) {
				case 1: input = new File("img/templates/feuerwerk1.png"); break;
				case 2: input = new File("img/templates/feuerwerk2.png"); break;
				case 3: input = new File("img/templates/feuerwerk3.png"); break;
				case 4: input = new File("img/templates/feuerwerk4.png"); break;
				}
				Font mcfont = null;
				try {
					mcfont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Minecraftia.ttf"));
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(mcfont);
				} catch (FontFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				try {
					image = ImageIO.read(input);
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
				BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics graphics = tmp.getGraphics();
				graphics.drawImage(image, 0, 0, null);
				graphics.setFont(new Font("Minecraftia", Font.PLAIN, 16));
				graphics.setColor(Color.decode("#000000"));
				graphics.drawString(gTime(diff), 17, 89);
				graphics.dispose();
				File f = new File("img/tmp/fwout.png");
				try {
					ImageIO.write(tmp, "png", f);
					e.getChannel().sendMessage("This countdown is set to: 20th February 2021 23:59:59").addFile(f).queue();
					f.delete();
				} catch (IOException e1) {
					e1.printStackTrace();
					e.getChannel().sendMessage(e1.getMessage()).queue();
				}
			}
		}
	}
	
	private String gTime(long time) {
		long seconds = time;
		long mins = 0l;
		long hours = 0l;
		long days = 0;
		while(seconds > 60) {
			seconds -= 60;
			mins++;
		}
		while(mins > 60) {
			mins -= 60;
			hours++;
		}
		while(hours > 24) {
			hours -= 24;
			days++;
		}
		if(days == 0) {
			return hours + ":" + mins + ":" + seconds;
		}else {
			return days + " Days, " + hours + ":" + mins + ":" + seconds;
		}
	}
	
	private int random(int low, int max) {
		Random r = new Random();
		int number = r.nextInt(max);
		while(number < low) {
			number = r.nextInt(max);
		}
		return number;
	}
}