package at.mlps.botclasses.guildlogging.privat;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import at.mlps.main.NewYearScheduler;
import at.mlps.main.PCHMG_MemberCount;
import at.mlps.main.RebootClass;
import at.mlps.main.Runner;
import at.mlps.rc.mysql.lb.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter{
	
	public void onReady(ReadyEvent e) {
		GuildLogEvents gl = new GuildLogEvents();
		gl.welcCon();
		
		EmbedBuilder ebwelc = new EmbedBuilder();
		ebwelc.setDescription("```####################################\n#                                  #\n# ###  #### ###  # ###   ##  ##### #\n# #  # #    #  # # #  # #  #   #   #\n# #  # #    #  # # #  # #  #   #   #\n# ###  ###  #  # # ###  #  #   #   #\n# # #  #    #  # # #  # #  #   #   #\n# #  # #    #  # # #  # #  #   #   #\n# #  # #### ###  # ###   ##    #   #\n#                                  #\n####################################```");
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
		String stime = time.format(new Date());
		ebwelc.setTitle("Bot is now online!");
		ebwelc.setFooter(stime);
		ebwelc.setColor(Color.magenta);
		String ver = file.getString("BotInfo.version");
		ebwelc.addField("Botversion:", ver, false);
		ebwelc.addField("Javaversion:", System.getProperty("java.version"), false);
		ebwelc.addField("JDA-Version:", JDAInfo.VERSION, false);
		if(ver.equalsIgnoreCase(retVer())) {
			ebwelc.addField("Info", "The bot is on the latest version!", false);
		}else {
			ebwelc.addField("Info", "The bot is not on the latest version!", false);
		}
		
		Runner runner = new Runner(e.getJDA());
		Timer t = new Timer();
		t.scheduleAtFixedRate(runner, 1000, 60000);
		RebootClass rc = new RebootClass();
		//300000 equals to 300 seconds or 5 mins
		t.scheduleAtFixedRate(rc, 0, 1000);
		
		//PCHMG Member Count in Voice Channel
		PCHMG_MemberCount pchmg = new PCHMG_MemberCount(e.getJDA());
		t.scheduleAtFixedRate(pchmg, 0, 3600000);
	}
	
	private String retVer() {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_versions WHERE type = ?");
			ps.setString(1, "bot");
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString("version");
		}catch (SQLException e) {
			return "ERROR";
		}
	}
}