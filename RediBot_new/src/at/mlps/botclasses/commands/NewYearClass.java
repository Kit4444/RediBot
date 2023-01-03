package at.mlps.botclasses.commands;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.ocpsoft.prettytime.PrettyTime;

import at.mlps.main.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NewYearClass extends ListenerAdapter{
	
	int width = 384;
	int height = 192;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "countdown")) {
				File input = null;
				long current = 1672484400000l; //System.currentTimeMillis();
				long xmas = 1671901200000l;
				long newyear = 1672527599000l;
				int id = 0;
				String curDate = new SimpleDateFormat("dd-MM").format(new Date(current));
				if(curDate.equalsIgnoreCase("25-12")) {
					id = 1;
				}else if(curDate.equalsIgnoreCase("26-12")) {
					id = 2;
				}else if(curDate.equalsIgnoreCase("27-12")) {
					id = 3;
				}else if(curDate.equalsIgnoreCase("28-12")) {
					id = 4;
				}else if(curDate.equalsIgnoreCase("29-12")) {
					id = 5;
				}else if(curDate.equalsIgnoreCase("30-12")) {
					id = 6;
				}else if(curDate.equalsIgnoreCase("31-12")) {
					id = 7;
				}
				switch(id) {
				case 1: input = new File("img/templates/feuerwerk1.png"); break;
				case 2: input = new File("img/templates/feuerwerk2.png"); break;
				case 3: input = new File("img/templates/feuerwerk3.png"); break;
				case 4: input = new File("img/templates/feuerwerk4.png"); break;
				case 5: input = new File("img/templates/feuerwerk5.png"); break;
				case 6: input = new File("img/templates/feuerwerk6.png"); break;
				case 7: input = new File("img/templates/feuerwerk7.png"); break;
				}
				registerFont();
				File picture = editFile(input, newyear);
				if(picture == null) {
					e.getChannel().sendMessage("Fehler bei dem bearbeiten des Bildes.").queue();
				}else {
					e.getChannel().sendFile(picture).queue();
				}
			}
		}
	}
	
	File editFile(File input, long futureTime) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			image = ImageIO.read(input);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//text wird unten-links angesetzt, bilder werden von oben links eingefügt
		//80, 141 für text pos
		BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = tmp.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.setFont(new Font("Minecraftia", Font.PLAIN, 16));
		PrettyTime pt = new PrettyTime();
		String strOut = pt.format(new Date(futureTime));
		FontMetrics fm = graphics.getFontMetrics(new Font("Minecraftia", Font.PLAIN, 16));
		Rectangle rect = new Rectangle(384, 16);
		int x = rect.x + (rect.width - fm.stringWidth(strOut)) / 2;
		graphics.setColor(Color.decode("#000000"));
		graphics.drawString(strOut, x, 141);
		graphics.dispose();
		File f = new File("img/tmp/fwout.png");
		try {
			ImageIO.write(tmp, "png", f);
		} catch (IOException e1) {
			e1.printStackTrace();
			f = null;
		}
		return f;
	}
	
	void registerFont() {
		try {
			Font mcfont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/minecraftia.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(mcfont);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
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