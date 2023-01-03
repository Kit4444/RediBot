package at.mlps.main;

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
import java.util.TimerTask;

import javax.imageio.ImageIO;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class NewYearScheduler extends TimerTask{
	
	public JDA jda;
	public NewYearScheduler(JDA jda) {
		this.jda = jda;
	}
	
	int width = 384;
	int height = 192;
	
	@Override
	public void run() {
		Guild g = jda.getGuildById(548136727697555496L);
		TextChannel chan = g.getTextChannelById(552163655366475786L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String date = sdf.format(new Date());
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM");
		String date1 = sdf1.format(new Date());
		File file = null;
		String text = "";
		if(date.matches("00:30:00")) {
			switch(date1) {
			case "01.12": file = new File("img/templates/christmas1.png"); text = "23 Days"; break;
			case "02.12": file = new File("img/templates/christmas4.png"); text = "22 Days"; break;
			case "03.12": file = new File("img/templates/christmas2.png"); text = "21 Days"; break;
			case "04.12": file = new File("img/templates/christmas1.png"); text = "20 Days"; break;
			case "05.12": file = new File("img/templates/christmas2.png"); text = "19 Days"; break;
			case "06.12": file = new File("img/templates/christmas4.png"); text = "18 Days"; break;
			case "07.12": file = new File("img/templates/christmas1.png"); text = "17 Days"; break;
			case "08.12": file = new File("img/templates/christmas3.png"); text = "16 Days"; break;
			case "09.12": file = new File("img/templates/christmas5.png"); text = "15 Days"; break;
			case "10.12": file = new File("img/templates/christmas4.png"); text = "14 Days"; break;
			case "11.12": file = new File("img/templates/christmas6.png"); text = "13 Days"; break;
			case "12.12": file = new File("img/templates/christmas2.png"); text = "12 Days"; break;
			case "13.12": file = new File("img/templates/christmas3.png"); text = "11 Days"; break;
			case "14.12": file = new File("img/templates/christmas1.png"); text = "10 Days"; break;
			case "15.12": file = new File("img/templates/christmas6.png"); text = "9 Days"; break;
			case "16.12": file = new File("img/templates/christmas5.png"); text = "8 Days"; break;
			case "17.12": file = new File("img/templates/christmas4.png"); text = "7 Days"; break;
			case "18.12": file = new File("img/templates/christmas1.png"); text = "6 Days"; break;
			case "19.12": file = new File("img/templates/christmas4.png"); text = "5 Days"; break;
			case "20.12": file = new File("img/templates/christmas5.png"); text = "4 Days"; break;
			case "21.12": file = new File("img/templates/christmas6.png"); text = "3 Days"; break;
			case "22.12": file = new File("img/templates/christmas3.png"); text = "2 Days"; break;
			case "23.12": file = new File("img/templates/christmas1.png"); text = "1 Day"; break;
			case "24.12": file = new File("img/templates/christmas5.png"); text = "Merry Christmas!"; break;
			case "25.12": file = new File("img/templates/feuerwerk1.png"); text = "6 Days"; break;
			case "26.12": file = new File("img/templates/feuerwerk2.png"); text = "5 Days"; break;
			case "27.12": file = new File("img/templates/feuerwerk3.png"); text = "4 Days"; break;
			case "28.12": file = new File("img/templates/feuerwerk4.png"); text = "3 Days"; break;
			case "29.12": file = new File("img/templates/feuerwerk5.png"); text = "2 Days"; break;
			case "30.12": file = new File("img/templates/feuerwerk6.png"); text = "1 Day"; break;
			}
			File fileEdit = editFile(file, text);
			if(fileEdit != null) {
				chan.sendFile(fileEdit).queue();
			}
		}
		if(date1.matches("31.12")) {
			switch(date) {
			case "01:00:00": chan.sendMessage("23 Hours until new year!").queue(); break;
			case "02:00:00": chan.sendMessage("22 Hours until new year!").queue(); break;
			case "03:00:00": chan.sendMessage("21 Hours until new year!").queue(); break;
			case "04:00:00": chan.sendMessage("20 Hours until new year!").queue(); break;
			case "05:00:00": chan.sendMessage("19 Hours until new year!").queue(); break;
			case "06:00:00": chan.sendMessage("18 Hours until new year!").queue(); break;
			case "07:00:00": chan.sendMessage("17 Hours until new year!").queue(); break;
			case "08:00:00": chan.sendMessage("16 Hours until new year!").queue(); break;
			case "09:00:00": chan.sendMessage("15 Hours until new year!").queue(); break;
			case "10:00:00": chan.sendMessage("14 Hours until new year!").queue(); break;
			case "11:00:00": chan.sendMessage("13 Hours until new year!").queue(); break;
			case "12:00:00": chan.sendMessage("12 Hours until new year!").queue(); break;
			case "13:00:00": chan.sendMessage("11 Hours until new year!").queue(); break;
			case "14:00:00": chan.sendMessage("10 Hours until new year!").queue(); break;
			case "15:00:00": chan.sendMessage("9 Hours until new year!").queue(); break;
			case "16:00:00": chan.sendMessage("8 Hours until new year!").queue(); break;
			case "17:00:00": chan.sendMessage("7 Hours until new year!").queue(); break;
			case "18:00:00": chan.sendMessage("6 Hours until new year!").queue(); break;
			case "19:00:00": chan.sendMessage("5 Hours until new year!").queue(); break;
			case "20:00:00": chan.sendMessage("4 Hours until new year!").queue(); break;
			case "21:00:00": chan.sendMessage("3 Hours until new year!").queue(); break;
			case "22:00:00": chan.sendMessage("2 Hours until new year!").queue(); break;
			case "23:00:00": chan.sendMessage("1 Hour until new year!").queue(); break;
			case "23:15:00": chan.sendMessage("45 Minutes until new year!").queue(); break;
			case "23:30:00": chan.sendMessage("30 Minutes until new year!").queue(); break;
			case "23:45:00": chan.sendMessage("15 Minutes until new year!").queue(); break;
			case "23:50:00": chan.sendMessage("10 Minutes until new year!").queue(); break;
			case "23:55:00": chan.sendMessage("5 Minutes until new year!").queue(); break;
			case "23:56:00": chan.sendMessage("4 Minutes until new year!").queue(); break;
			case "23:57:00": chan.sendMessage("3 Minutes until new year!").queue(); break;
			case "23:58:00": chan.sendMessage("2 Minutes until new year!").queue(); break;
			case "23:59:00": chan.sendMessage("1 Minute until new year!").queue(); break;
			case "23:59:15": chan.sendMessage("45 Seconds until new year!").queue(); break;
			case "23:59:30": chan.sendMessage("30 Seconds until new year!").queue(); break;
			case "23:59:45": chan.sendMessage("15 Seconds until new year!").queue(); break;
			case "23:59:50": chan.sendMessage("10 Seconds until new year!").queue(); break;
			case "23:59:51": chan.sendMessage("9 Seconds until new year!").queue(); break;
			case "23:59:52": chan.sendMessage("8 Seconds until new year!").queue(); break;
			case "23:59:53": chan.sendMessage("7 Seconds until new year!").queue(); break;
			case "23:59:54": chan.sendMessage("6 Seconds until new year!").queue(); break;
			case "23:59:55": chan.sendMessage("5 Seconds until new year!").queue(); break;
			case "23:59:56": chan.sendMessage("4 Seconds until new year!").queue(); break;
			case "23:59:57": chan.sendMessage("3 Seconds until new year!").queue(); break;
			case "23:59:58": chan.sendMessage("2 Seconds until new year!").queue(); break;
			case "23:59:59": chan.sendMessage("1 Second until new year!").queue(); break;
			case "00:00:00": text = "HAPPY NEW YEAR 2023 EVERYONE!"; file = new File("img/templates/feuerwerk7.png"); break;
			}
			if(text.equalsIgnoreCase("HAPPY NEW YEAR 2023 EVERYONE!")) {
				File out = editFile(file, text);
				if(out != null) {
					chan.sendFile(out).queue();
				}
			}
		}
	}
	
	File editFile(File input, String text) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			image = ImageIO.read(input);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		registerFont();
		//text wird unten-links angesetzt, bilder werden von oben links eingefügt
		//80, 141 für text pos
		BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = tmp.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.setFont(new Font("Minecraftia", Font.PLAIN, 16));
		FontMetrics fm = graphics.getFontMetrics(new Font("Minecraftia", Font.PLAIN, 16));
		Rectangle rect = new Rectangle(384, 16);
		int x = rect.x + (rect.width - fm.stringWidth(text)) / 2;
		graphics.setColor(Color.decode("#000000"));
		graphics.drawString(text, x, 141);
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
}