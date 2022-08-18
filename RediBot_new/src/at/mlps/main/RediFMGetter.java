package at.mlps.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimerTask;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import at.mlps.rc.mysql.lb.MySQL;

public class RediFMGetter extends TimerTask{

	@Override
	public void run() {
		//current Playlist
		String pl = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "name");
		String startedAt = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "started_at");
		String endAt = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "ends_at");
		String desc = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "description");
		String color = returnRadio1("https://api.laut.fm/station/redifm", "current_playlist", "color");
		
		//approaching Playlist
		String Apl = returnRadio1("https://api.laut.fm/station/redifm", "next_playlist", "name");
		String AstartAt = returnRadio1("https://api.laut.fm/station/redifm", "next_playlist", "starts_at");
		String AendAt = returnRadio1("https://api.laut.fm/station/redifm", "next_playlist", "ends_at");
		String Adesc = returnRadio1("https://api.laut.fm/station/redifm", "next_playlist", "description");
		
		String stationImage = returnRadio1("https://api.laut.fm/station/redifm", "images", "station");
		
		String art = returnRadio1("https://api.laut.fm/station/redifm/current_song", "artist", "name");
		String tra = returnRadio("https://api.laut.fm/station/redifm/current_song", "title");
		String alb = returnRadio("https://api.laut.fm/station/redifm/current_song", "album");
		String listeners = returnRadioListeners();
		
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redifm_current SET track = ?, artist = ?, album = ?, playlist = ?, current_listener = ?, startedAt = ?, endAt = ?, description = ?, a_pl = ?, a_startAt = ?, a_endAt = ?, a_description = ?, stationImage = ?, color = ? WHERE id = ?");
			ps.setString(1, tra);
			ps.setString(2, art);
			ps.setString(3, alb);
			ps.setString(4, pl);
			ps.setString(5, listeners);
			ps.setString(6, startedAt);
			ps.setString(7, endAt);
			ps.setString(8, desc);
			ps.setString(9, Apl);
			ps.setString(10, AstartAt);
			ps.setString(11, AendAt);
			ps.setString(12, Adesc);
			ps.setString(13, stationImage);
			ps.setString(14, color);
			ps.setInt(15, 1);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String returnRadio(String uri, String node) {
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
	
	private String returnRadio1(String uri, String node, String subnode) {
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
		try {
			JSONObject jo = (JSONObject) parser.parse(lortu);
			if(jo.get(node) == null) {
				s = "None";
			}else {
				JSONObject sub = (JSONObject) jo.get(node);
				if(sub.get(subnode) != null) {
					s = (String) sub.get(subnode).toString();
				}else {
					s = "none";
				}
			}
		}catch(ParseException e) {
			s = "None";
			e.printStackTrace();
		}
		return s.replace("\"", "");
	}
	
	private  String returnRadioListeners() {
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
		return s.replace("\"", "");
	}
}