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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import at.mlps.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PridePFP extends ListenerAdapter{
	
	int width = 512;
	int height = 512;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(g.getIdLong() == 732590658329509888L) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("scuk!pfp")) {
					registerFont();
					File old = new File("img/templates/scuk_ut.png");
					BufferedImage img = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
					try {
						img = ImageIO.read(old);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					BufferedImage tmp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics graphics = tmp.getGraphics();
					graphics.drawImage(img, 0, 0, null);
					graphics.setFont(new Font("Lato Black", Font.PLAIN, 20));
					String wm = m.getUser().getName();
					FontMetrics fm = graphics.getFontMetrics(new Font("Lato Black", Font.PLAIN, 20));
					Rectangle rectangle = new Rectangle(240, 16);
					int x = rectangle.x + (rectangle.width - fm.stringWidth(wm)) / 2;
					graphics.setColor(Color.WHITE);
					graphics.drawString(wm, x, 247);
					graphics.dispose();
					File out = new File("img/tmp/scuk_" + m.getUser().getId() + ".png");
					try {
						ImageIO.write(tmp, "png", out);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.getChannel().sendFile(out).queue();
					out.delete();
					
				}
			}
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "lgbtq")) {
				try {
					URL url = new URL(m.getUser().getAvatarUrl());
					String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
					URLConnection con = url.openConnection();
					con.addRequestProperty("User-Agent", USER_AGENT);
					InputStream is = con.getInputStream();
					OutputStream os = new FileOutputStream("img/tmp/avatar_" + m.getUser().getIdLong() + ".png");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	void registerFont() {
		try {
			Font mcfont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Lato_Black.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(mcfont);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

}