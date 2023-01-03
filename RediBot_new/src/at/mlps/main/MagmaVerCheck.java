package at.mlps.main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TimerTask;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

public class MagmaVerCheck extends TimerTask{
	
	public JDA jda;
	public MagmaVerCheck(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		String url = "https://api.magmafoundation.org/api/v2/1.12.2/latest";
		String versionName = returnName(url, "name");
		String newLink = returnName(url, "link");
		String newGitCommit = returnName(url, "git_commit_url");
		YamlFile cfg = new YamlFile("configs/magma.yml");
		if(!cfg.exists()) {
			try {
				cfg.createNewFile(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				cfg.load();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!versionName.equalsIgnoreCase("none")) {
			if(cfg.contains("version")) {
				String oldVer = cfg.getString("version");
				if(!oldVer.equalsIgnoreCase(versionName)) {
					cfg.set("version", versionName);
					sendEmbed(versionName, newLink, newGitCommit);
					try {
						cfg.save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else {
				cfg.set("version", versionName);
				sendEmbed(versionName, newLink, newGitCommit);
				try {
					cfg.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void sendEmbed(String version, String link, String git) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.decode("#fe4c1c"));
		eb.setThumbnail("https://cdn.discordapp.com/attachments/671772592851648514/1049107226452832287/magma.png");
		eb.setTitle("Version " + version + " has been released.", git);
		eb.setDescription("A new version for Magma 1.12.2 has been released!\nClick [here](" + link + ")");
		jda.getGuildById(612372586386423824l).getTextChannelById(673283972364763169l).sendMessageEmbeds(eb.build()).queue();
	}
	
	private String returnName(String uri, String node) {
		String s = "";
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(uri);
			URLConnection urlc = url.openConnection();
			BufferedReader bR = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			String line;
			while ((line = bR.readLine()) != null) {
				content.append(line + "\n");
			}
			bR.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		String lortu = content.toString();
		JSONParser parser = new JSONParser();
		JSONObject jo;
		try {
			jo = (JSONObject)parser.parse(lortu);
			if(jo.get(node) == null) {
				s = "None";
			}else {
				s = (String) jo.get(node).toString();
			}
		} catch (ParseException e) {
			s = "None";
			e.printStackTrace();
		}
		return s.replace("\"", "");
	}

}
