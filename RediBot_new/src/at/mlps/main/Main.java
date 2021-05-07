package at.mlps.main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import at.mlps.botclasses.commands.AnnounceCMD;
import at.mlps.botclasses.commands.AragonLoyalCommand;
import at.mlps.botclasses.commands.BotModlogAnnounce;
import at.mlps.botclasses.commands.ChatLevelCMD;
import at.mlps.botclasses.commands.ChatLeveling;
import at.mlps.botclasses.commands.CreateInvite;
import at.mlps.botclasses.commands.DiscordSugg_Voter;
import at.mlps.botclasses.commands.EmojiList;
import at.mlps.botclasses.commands.FAQ;
import at.mlps.botclasses.commands.HelpCMD;
import at.mlps.botclasses.commands.ImgManClassCMD;
import at.mlps.botclasses.commands.LOA;
import at.mlps.botclasses.commands.MCServerinfo;
import at.mlps.botclasses.commands.NewYearClass;
import at.mlps.botclasses.commands.Punishments;
import at.mlps.botclasses.commands.PurgeCommand;
import at.mlps.botclasses.commands.RegisterGuilds;
import at.mlps.botclasses.commands.Serverinfo;
import at.mlps.botclasses.commands.SetStatesCMD;
import at.mlps.botclasses.commands.StreamAdvCMD;
import at.mlps.botclasses.commands.Tags;
import at.mlps.botclasses.commands.UserCommands;
import at.mlps.botclasses.commands.UserInfo;
import at.mlps.botclasses.commands.WhoisCMD;
import at.mlps.botclasses.commands.settings.InviteChannelCMD;
import at.mlps.botclasses.commands.settings.JoinRolesCMD;
import at.mlps.botclasses.commands.settings.MessageExemptCMD;
import at.mlps.botclasses.commands.settings.SettingsCommand;
import at.mlps.botclasses.commands.settings.SettingsGuildlog;
import at.mlps.botclasses.commands.settings.SettingsPrefixCMD;
import at.mlps.botclasses.commands.settings.SettingsWelcomerCMD;
import at.mlps.botclasses.commands.settings.SettingsXPLevel;
import at.mlps.botclasses.commands.settings.SuggestionVoterAddCMD;
import at.mlps.botclasses.guildlogging.emote.EmoteAdd;
import at.mlps.botclasses.guildlogging.emote.EmoteRemove;
import at.mlps.botclasses.guildlogging.emote.EmoteUpdateName;
import at.mlps.botclasses.guildlogging.guild.GuildInviteCreate;
import at.mlps.botclasses.guildlogging.guild.GuildInviteDelete;
import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import at.mlps.botclasses.guildlogging.guild.GuildMemberJoin;
import at.mlps.botclasses.guildlogging.guild.GuildMemberJoinWelcomer;
import at.mlps.botclasses.guildlogging.guild.GuildMemberRemove;
import at.mlps.botclasses.guildlogging.guild.GuildMemberRoleAdd;
import at.mlps.botclasses.guildlogging.guild.GuildMemberRoleRemove;
import at.mlps.botclasses.guildlogging.guild.GuildMemberUpdateBoostTime;
import at.mlps.botclasses.guildlogging.guild.GuildMemberUpdateNickname;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateBoostCount;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateBoostTier;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateExplicitContent;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateIcon;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateName;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateOwner;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateRegion;
import at.mlps.botclasses.guildlogging.guild.GuildUpdateVerificationLevel;
import at.mlps.botclasses.guildlogging.guild.MessageLogging;
import at.mlps.botclasses.guildlogging.member.UserUpdateAvatar;
import at.mlps.botclasses.guildlogging.member.UserUpdateDiscriminator;
import at.mlps.botclasses.guildlogging.member.UserUpdateName;
import at.mlps.botclasses.guildlogging.member.UserUpdateOnlineStatus;
import at.mlps.botclasses.guildlogging.privat.GuildJoin;
import at.mlps.botclasses.guildlogging.privat.GuildLeave;
import at.mlps.botclasses.guildlogging.privat.GuildUnavailable;
import at.mlps.botclasses.guildlogging.privat.PrivateMessageReceived;
import at.mlps.botclasses.guildlogging.privat.Ready;
import at.mlps.botclasses.guildlogging.role.RoleCreate;
import at.mlps.botclasses.guildlogging.role.RoleDelete;
import at.mlps.botclasses.guildlogging.role.RoleUpdateColor;
import at.mlps.botclasses.guildlogging.role.RoleUpdateHoisted;
import at.mlps.botclasses.guildlogging.role.RoleUpdateMentionable;
import at.mlps.botclasses.guildlogging.role.RoleUpdateName;
import at.mlps.botclasses.guildlogging.text.TextChannelCreate;
import at.mlps.botclasses.guildlogging.text.TextChannelDelete;
import at.mlps.botclasses.guildlogging.text.TextChannelUpdateNSFW;
import at.mlps.botclasses.guildlogging.text.TextChannelUpdateName;
import at.mlps.botclasses.guildlogging.text.TextChannelUpdateParent;
import at.mlps.botclasses.guildlogging.text.TextChannelUpdateSlowmode;
import at.mlps.botclasses.guildlogging.text.TextChannelUpdateTopic;
import at.mlps.botclasses.guildlogging.voice.VoiceChannelCreate;
import at.mlps.botclasses.guildlogging.voice.VoiceChannelDelete;
import at.mlps.botclasses.guildlogging.voice.VoiceChannelUpdateBitrate;
import at.mlps.botclasses.guildlogging.voice.VoiceChannelUpdateName;
import at.mlps.botclasses.guildlogging.voice.VoiceChannelUpdateParent;
import at.mlps.botclasses.guildlogging.voice.VoiceChannelUpdateUserLimit;
import at.mlps.botclasses.guildlogging.voicejoinleavemove.GuildVoiceJoin;
import at.mlps.botclasses.guildlogging.voicejoinleavemove.GuildVoiceLeave;
import at.mlps.botclasses.guildlogging.voicejoinleavemove.GuildVoiceMove;
import at.mlps.rc.mysql.lpb.MySQL;
import at.mlps.reactionroles.RR_Command;
import at.mlps.reactionroles.RR_Listener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main implements EventListener{
	
	public static String host = "207.180.202.73";
	public static String port = "3306";
	public static String DB = "RediCraft";
	public static String user = "mauriceb";
	public static String pw = "MauriceB2400";
	public static Main instance;
	public static MySQL mysql;
	
	public static String botprefix = "rb!";

	public static void main(String[] args) {
		YamlFile file = new YamlFile("configs/configuration.yml");
		if(!file.exists()) {
			try {
				file.createNewFile(true);
				System.out.println("Config Files Created.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				file.load();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		setCFG();
		String host = file.getString("MySQL.Host");
		int port = file.getInt("MySQL.Port");
		String db = file.getString("MySQL.Database");
		String user = file.getString("MySQL.Username");
		String pass = file.getString("MySQL.Password");
		try {
			at.mlps.rc.mysql.lb.MySQL.connect(host, port, db, user, pass);
			mysql = new MySQL(host, port, db, user, pass);
			mysql.connect();
			onLog(1, "Connected to database.");
			System.out.println("Connected to DB");
		} catch (ClassNotFoundException e) {
			onLog(3, "An error occured. Error: " + e);
		} catch (SQLException e) {
			onLog(3, "An error occured while connecting to the database. Error: " + e);
		}
		try {
			startBot();
		} catch (InvalidConfigurationException | IOException | LoginException e) {
			e.printStackTrace();
		}
	}
	
	static void setCFG() {
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
			onLog(1, "Loaded Config file");
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss a z");
		String stime = time.format(new Date());
		file.addDefault("BotConfig.Activity.Type", "PLAYING");
		file.addDefault("BotConfig.Activity.Text", "Being a baby.");
		file.addDefault("BotConfig.Activity.StreamURL", "https://www.youtube.com/channel/UCBJhuPBSaucwk_TthujYrBw");
		file.addDefault("BotConfig.Activity.Onlinestatus", "ONLINE");
		file.addDefault("BotInfo.version", "19700101_v0.0.0abs");
		file.addDefault("MySQL.Host", "localhost");
		file.addDefault("MySQL.Port", 3306);
		file.addDefault("MySQL.Database", "database");
		file.addDefault("MySQL.Username", "username");
		file.addDefault("MySQL.Password", "password");
		file.addDefault("BotConfig.Bottoken", "the bottoken goes here");
		file.addDefault("BotConfig.Bootlogo", 0);
		file.set("BotInfo.online.ts", System.currentTimeMillis());
		file.set("BotInfo.online.string", stime);
		file.options().copyDefaults(true);
		try {
			file.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void startBot() throws InvalidConfigurationException, IOException, LoginException {
		YamlFile file = new YamlFile("configs/configuration.yml");
		file.load();
		JDABuilder builder = JDABuilder.createDefault(file.getString("BotConfig.Bottoken"));
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		builder.enableIntents(GatewayIntent.GUILD_BANS);
		builder.enableIntents(GatewayIntent.GUILD_EMOJIS);
		builder.enableIntents(GatewayIntent.GUILD_INVITES);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
		builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
		builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGES);
		builder.setMemberCachePolicy(MemberCachePolicy.ALL);
		builder.addEventListeners(new LOA());
		builder.addEventListeners(new PurgeCommand());
		builder.addEventListeners(new Serverinfo());
		builder.addEventListeners(new SetStatesCMD());
		builder.addEventListeners(new UserCommands());
		builder.addEventListeners(new GuildLogEvents());
		builder.addEventListeners(new FAQ());
		builder.addEventListeners(new UserInfo());
		builder.addEventListeners(new Punishments());
		builder.addEventListeners(new RegisterGuilds());
		builder.addEventListeners(new UserUpdateAvatar());
		builder.addEventListeners(new UserUpdateDiscriminator());
		builder.addEventListeners(new UserUpdateName());
		builder.addEventListeners(new UserUpdateOnlineStatus());
		builder.addEventListeners(new GuildJoin());
		builder.addEventListeners(new GuildLeave());
		builder.addEventListeners(new GuildUnavailable());
		builder.addEventListeners(new PrivateMessageReceived());
		builder.addEventListeners(new Ready());
		builder.addEventListeners(new RoleCreate());
		builder.addEventListeners(new RoleDelete());
		builder.addEventListeners(new RoleUpdateColor());
		builder.addEventListeners(new RoleUpdateHoisted());
		builder.addEventListeners(new RoleUpdateMentionable());
		builder.addEventListeners(new RoleUpdateName());
		builder.addEventListeners(new TextChannelCreate());
		builder.addEventListeners(new TextChannelDelete());
		builder.addEventListeners(new TextChannelUpdateName());
		builder.addEventListeners(new TextChannelUpdateNSFW());
		builder.addEventListeners(new TextChannelUpdateParent());
		builder.addEventListeners(new TextChannelUpdateSlowmode());
		builder.addEventListeners(new TextChannelUpdateTopic());
		builder.addEventListeners(new VoiceChannelCreate());
		builder.addEventListeners(new VoiceChannelDelete());
		builder.addEventListeners(new VoiceChannelUpdateBitrate());
		builder.addEventListeners(new VoiceChannelUpdateName());
		builder.addEventListeners(new VoiceChannelUpdateParent());
		builder.addEventListeners(new VoiceChannelUpdateUserLimit());
		builder.addEventListeners(new EmoteAdd());
		builder.addEventListeners(new EmoteRemove());
		builder.addEventListeners(new EmoteUpdateName());
		builder.addEventListeners(new GuildInviteCreate());
		builder.addEventListeners(new GuildInviteDelete());
		builder.addEventListeners(new GuildMemberJoin());
		builder.addEventListeners(new GuildMemberRemove());
		builder.addEventListeners(new GuildMemberRoleAdd());
		builder.addEventListeners(new GuildMemberRoleRemove());
		builder.addEventListeners(new GuildMemberUpdateNickname());
		builder.addEventListeners(new GuildUpdateBoostCount());
		builder.addEventListeners(new GuildUpdateBoostTier());
		builder.addEventListeners(new GuildUpdateExplicitContent());
		builder.addEventListeners(new GuildUpdateIcon());
		builder.addEventListeners(new GuildUpdateName());
		builder.addEventListeners(new GuildUpdateOwner());
		builder.addEventListeners(new GuildUpdateRegion());
		builder.addEventListeners(new GuildUpdateVerificationLevel());
		builder.addEventListeners(new GuildVoiceJoin());
		builder.addEventListeners(new GuildVoiceLeave());
		builder.addEventListeners(new GuildVoiceMove());
		builder.addEventListeners(new Tags());
		builder.addEventListeners(new MCServerinfo());
		builder.addEventListeners(new CreateInvite());
		builder.addEventListeners(new StreamAdvCMD());
		builder.addEventListeners(new WhoisCMD());
		builder.addEventListeners(new HelpCMD());
		builder.addEventListeners(new DiscordSugg_Voter());
		builder.addEventListeners(new AnnounceCMD());
		builder.addEventListeners(new GuildMemberUpdateBoostTime());
		builder.addEventListeners(new SettingsCommand());
		builder.addEventListeners(new ChatLeveling());
		builder.addEventListeners(new ChatLevelCMD());
		builder.addEventListeners(new MessageLogging()); //enabled with hashmaps as temporary solution
		builder.addEventListeners(new GuildMemberJoinWelcomer());
		builder.addEventListeners(new AragonLoyalCommand());
		builder.addEventListeners(new EmojiList());
		builder.addEventListeners(new SettingsWelcomerCMD());
		builder.addEventListeners(new ImgManClassCMD());
		builder.addEventListeners(new SettingsPrefixCMD());
		builder.addEventListeners(new MessageExemptCMD());
		builder.addEventListeners(new JoinRolesCMD());
		builder.addEventListeners(new InviteChannelCMD());
		builder.addEventListeners(new NewYearClass());
		builder.addEventListeners(new SettingsGuildlog());
		builder.addEventListeners(new RR_Command());
		builder.addEventListeners(new RR_Listener());
		builder.addEventListeners(new SettingsXPLevel());
		builder.addEventListeners(new SuggestionVoterAddCMD());
		builder.addEventListeners(new BotModlogAnnounce());
		builder.setLargeThreshold(256);
		builder.build();
	}
	
	@Override
	public void onEvent(GenericEvent arg0) {
	}
	
	public static void onLog(int log, String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		if(log == 1) { //info
			System.out.println(sdf.format(new Date()) + " [INFO] " + msg);
		}else if(log == 2) { //warning
			System.out.println(sdf.format(new Date()) + " [WARNING] " + msg);
		}else if(log == 3) { //critical
			System.out.println(sdf.format(new Date()) + " [CRITICAL] " + msg);
		}else {
			System.out.println(sdf.format(new Date()) + " | " + msg);
		}
	}
}