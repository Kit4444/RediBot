package at.mlps.botclasses.commands;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ImgManClassCMD extends ListenerAdapter{
	
	int width = 300;
	int height = 100;
	
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "imagetest")) {
				BufferedImage image = null;
				File f = null;
				InputStream is = null;
				OutputStream os = null;
				try {
					URL url = new URL(e.getAuthor().getAvatarUrl());
					String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
					URLConnection con = url.openConnection();
					con.setRequestProperty("User-Agent", USER_AGENT);
					is = con.getInputStream();
					os = new FileOutputStream("img/tmp/avatar_" + e.getAuthor().getIdLong() + ".png");
					byte[] buffer = new byte[2048];
					int length;
					while ((length = is.read(buffer)) != -1) {
						os.write(buffer, 0, length);
					}
				} catch (MalformedURLException e3) {
					e3.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					
				File input = new File("img/templates/yellow_levelup.png");
				File pfp = new File("img/tmp/avatar_" + e.getAuthor().getIdLong() + ".png");
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				try {
					image = ImageIO.read(input);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				BufferedImage pfp_tmp = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
				try {
					pfp_tmp = ImageIO.read(pfp);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//Minecraftia.ttf - 12 / 24px
				Font mcfont = null;
				try {
					mcfont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Minecraftia.ttf"));
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(mcfont);
				} catch (FontFormatException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				//text immer unten-links anordnen via pixel. bild immer von oben-links.
				BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics graphics = tmp.getGraphics();
				graphics.drawImage(image, 0, 0, null);
				graphics.drawImage(pfp_tmp, 6, 34, 128, 128, null);
				graphics.setFont(new Font("Minecraftia", Font.PLAIN, 24));
				graphics.setColor(new Color(170, 0, 0));
				String watermark = e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator();
				graphics.drawString(watermark, 156, 156);
				graphics.dispose();
				f = new File("img/tmp/out.png");
				try {
					ImageIO.write(tmp, "png", f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.getChannel().sendFile(new File("img/tmp/out.png")).queue();
				new File("img/tmp/out.png").delete();
				new File("img/tmp/avatar_" + e.getAuthor().getIdLong() + ".png").delete();
			}
		}
	}

}
