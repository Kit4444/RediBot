package at.mlps.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.TimerTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.mlps.rc.mysql.lb.MySQL;

public class Runner extends TimerTask{
	
	static int gctimer = 0;

	@Override
	public void run() {
		
		String pl = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "name");
		String art = returnRadio1("https://api.laut.fm/station/redifm/current_song", "artist", "name");
		String tra = returnRadio("https://api.laut.fm/station/redifm/current_song", "title");
		String alb = returnRadio("https://api.laut.fm/station/redifm/current_song", "album");
		String listeners = returnRadioListeners();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redifm_current SET track = ?, artist = ?, album = ?, playlist = ?, current_listener = ?  WHERE id = ?");
			ps.setString(1, tra);
			ps.setString(2, art);
			ps.setString(3, alb);
			ps.setString(4, pl);
			ps.setString(5, listeners);
			ps.setInt(6, 1);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int code1 = random(0, 5000);
		int code2 = random(5001, 10000);
		int code3 = random(10001, 15000);
		int code4 = random(15001, 20000);
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE useless_testtable SET toupdate = ? WHERE type = ?");
			ps.setString(2, "bot_my1");
			String gcode1 = code1 + "-" + code2;
			ps.setString(1, gcode1);
			ps.executeUpdate();
			
			String gcode2 = code3 + "-" + code4;
			Main.mysql.update("UPDATE useless_testtable SET toupdate = '" + gcode2 + "' WHERE type = 'bot_my2';");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	int random(int low, int max) {
		Random r = new Random();
		int number = r.nextInt(max);
		while(number < low) {
			number = r.nextInt(max);
		}
		return number;
	}
	
	private static String returnRadio(String uri, String node) {
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
		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject)parser.parse(lortu);
		if(jo.get(node) == null) {
			s = "None";
		}else {
			s = (String) jo.get(node).toString();
		}
		return s;
	}
	
	private static String returnRadio1(String uri, String node, String subnode) {
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
		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject)parser.parse(lortu);
		if(jo.get(node) == null) {
			s = "None";
		}else {
			JsonObject sub = (JsonObject) jo.get(node);
			s = (String) sub.get(subnode).toString();
		}
		return s;
	}
	
	private static String returnRadioListeners() {
		String s = "";
		try {
			URL url = new URL("https://api.laut.fm/station/redifm/listeners");
			URLConnection urlc = url.openConnection();
			BufferedReader bR = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			s = bR.readLine();
			bR.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}