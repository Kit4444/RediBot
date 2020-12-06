package at.mlps.botclasses.commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FAQ extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		String content = e.getMessage().getContentRaw();
		if(content.equalsIgnoreCase(Main.botprefix + "faq")) {
			if(e.getGuild().getIdLong() == 548136727697555496L) {
				Role pm = e.getGuild().getRoleById(548175887179186191L);
				TextChannel faqchannel = e.getGuild().getTextChannelById(548187217726537738L);
				if(e.getMember().getRoles().contains(pm)) {
					setEmbed_serverinfo(faqchannel);
					setEmbed_invite(faqchannel);
					setEmbed_platform(faqchannel);
					setEmbed_hierarchy(faqchannel);
					setEmbed_partnerships(faqchannel);
					setEmbed_beta(faqchannel);
					setEmbed_report(faqchannel);
					setEmbed_apply(faqchannel);
					setEmbed_serverlock(faqchannel);
					setEmbed_team(faqchannel);
				}
			}else {
				chan.sendMessage("Error! This guild is not registered for a FAQ for RediCraft!").queue(msg1 -> {
					msg1.delete().completeAfter(10, TimeUnit.SECONDS);
				});
			}
		}else if(content.equalsIgnoreCase(Main.botprefix + "rules")) {
			if(e.getGuild().getIdLong() == 548136727697555496L) {
				Role pm = e.getGuild().getRoleById(548175887179186191L);
				TextChannel ruleschannel = e.getGuild().getTextChannelById(548187134687838218L);
				TextChannel ruleschangelog = e.getGuild().getTextChannelById(552932676890394624L);
				if(e.getMember().getRoles().contains(pm)) {
					setRulesEmbed_1(ruleschannel);
					setRulesEmbed_2(ruleschannel);
					setRulesEmbed_3(ruleschannel);
					setRulesEmbed_4(ruleschannel);
					//changelog
					SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			        String stime = time.format(new Date());
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(Color.decode("#55ff55"));
					eb.setFooter("", e.getGuild().getIconUrl());
					eb.setTitle("Ruleset Change at " + stime, e.getAuthor().getAvatarUrl());
					eb.setDescription("Changes: \n- §5 | Advertising is now forbidden. \n- §7 | Please avoid any disturbing noises making annoying sounds like burping, squeeking or any other noises.\n- §8 | The unapproved usage of any selfbots is not allowed and forbidden in the ToS of Discord and leads to a permanent ban on this server.\nSpamming in bot-commands is prohibited.\n- §11 | Dyno has been changed to RediBot\n Additions: \n- §13 | Discussing web reports, appeals, feedback tickets, punishments received in-game, on the forum or on discord is not allowed.\r\n" + 
							"Requesting Game Moderators to moderate in game is not allowed.\r\n" + 
							"Please use instead the ingame report system or use the web report system.");
					ruleschangelog.sendMessage(eb.build()).queue();
				}
			}else {
				chan.sendMessage("Error! This guild is not registered for a ruleset of RediCraft!").queue(msg1 -> {
					msg1.delete().completeAfter(10, TimeUnit.SECONDS);
				});
			}
		}
	}
	
	private EmbedBuilder Embed() {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.decode("#5555ff"));
		eb.setFooter("", "http://pi.mauricebailey.at/rc_logo1024.png");
		return eb;
	}
	
	private EmbedBuilder Rulesembed(String color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.decode("#" + color));
		return eb;
	}
	
	private void setEmbed_report(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: I want to report someone. How can I do this?\nAnswer: You can report Users through ModMail or pinging a Moderator.");
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_team(TextChannel chan) {
		EmbedBuilder eb = Embed();
		//eb.addField("<Team>", "<Name in Listformat>", false);
		eb.addField("Project Manager", "MarvinAch\nMaurice Bailey", false);
		eb.addField("Human Resources", "Inferno", false);
		eb.addField("Community Manager", "thelottiattack", false);
		eb.addField("Game Moderation Manager", "RookieCookie", false);
		eb.addField("Developer", "Krisi\nMarvinAch\nMaurice Bailey", false);
		eb.addField("Game Moderator", "Marc26075", false);
		eb.addField("Moderator", "KingGiGi\nMarius\nMaxi", false);
		eb.addField("Content Team", "KingGiGi", false);
		eb.addField("Support", "Marius", false);
		eb.addField("Builder", "Mephistopheles", false);
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_platform(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: Do we have another social media platforms?\nAnswer: For sure we have another social media platforms.");
		eb.addField("Website:", "https://www.redicraft.eu/", false);
		eb.addField("Forum:", "https://forum.redicraft.eu/", false);
		eb.addField("Instagram:", "https://www.instagram.com/redicrafteu/", false);
		eb.addField("Facebook:", "Site: https://www.facebook.com/redicrafteu/ \nGroup: https://www.facebook.com/groups/370322470364195/", false);
		eb.addField("YouTube:", "https://www.youtube.com/channel/UCBJhuPBSaucwk_TthujYrBw", false);
		eb.addField("Twitch:", "https://www.twitch.tv/redicrafteu", false);
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_serverlock(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: The Server is released, but I can't play on it! Why? \nAnswer: As some people already though about destroying and grief the Server, we will whitelist our server. \nYou want to play with us? Then fill this Google Forms out truthfully in order to play again on it.\n URL: https://forms.gle/AaQfmEHMo4fGR1VX8");
	}
	
	private void setEmbed_serverinfo(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: What's about the Minecraft-Server? \nAnswer: RediCraft is a Minecraft Network with preference in building.\nOur server is Java-Based and runs on the latest version. Current version: ***1.16.3***.");
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_invite(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: How can I invite my friends? \nAnswer: Just create one by yourself with ``rb!createinvite`` - The bot will DM you.");
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_hierarchy(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: How does the team hierarchy looks like? \nAnswer: It's very simple. You can see it from the following graphic:");
		eb.setImage("http://pi.mauricebailey.at/redicraft_hierarchie.png");
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_partnerships(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: Does RediCraft also accept partnerships? \nAnswer: Yes, we do! We accept partnerships. Currently 'only' via Google Forms, but as it was implemented in the homepage, everything will be done via it.\nLink: https://docs.google.com/forms/d/e/1FAIpQLSdtjPsI2LWSzRB67txJT9CzBxacNVhp_kZ4UIqHhBez8x_kvw/viewform");
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setEmbed_beta(TextChannel chan) {
		EmbedBuilder eb = Embed();
		//eb.setDescription("Question: Does RediCraft also accepts beta applications? \nAnswer: Yes, we do! We accept beta applicants. At the moment only via Google Forms.\nLink: https://docs.google.com/forms/d/e/1FAIpQLSdY5xw0SROB947-0CIi_7ElbPGm4aR6-3mhtcl0zHP0kqoM0A/viewform");
		eb.setDescription("Question: Does RediCraft also accepts beta application? \nAnswer: We currently don't. We will announce it, when we are searching beta testers again.");
		chan.sendMessage(eb.build()).queue();
	}

	private void setEmbed_apply(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: How can I apply for the staff team? \nAnswer: Use the following Google Forms: https://forms.gle/tsemeZadQhNrp1Lx6");
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setRulesEmbed_1(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("55FF55");
		eb.addField("§1 General Informations and Warnings", "Please keep in mind that the chats are archived in order to investigate any incidents.\r\n" + 
				"Please be aware that the investigation of such incidents may take some time.\r\n" + 
				"The staff is not responsible for the things that happens on the server!\r\n" + 
				"You as the user have a certain amount of individual responsibility!\r\n" + 
				"We reserve the right to remove users from the chat who are disturbing the chat climate, please respect that!\r\n" + 
				"\r\n" + 
				"The Discord Terms of Service and Community Guidelines are strictly enforced.\r\n" + 
				"\r\n" + 
				"Exceptions to the rules can be made by upper staff.", false);
		eb.addField("§2 Ranks, Rights and Roles", "Begging for rights nor for ranks is forbidden.\r\n" + 
				"You can claim some ranks yourself.\r\n" + 
				"To claim yourself a role, go to #roles and tick the corresponding reaction under the message.", false);
		eb.addField("§3 Nicknames", "Nickfakes are not allowed.\r\n" + 
				"Please do not make the appearance of a person you are not.\r\n" + 
				"Nicknames have to be clearly understandable.\r\n" + 
				"Therefore, they must not contain any ASCII-Art, Zalgo-Text or other unreadable characters.\r\n" + 
				"Nicknames are not allowed to contain any parts like Staff, Moderator or Admin.\r\n" + 
				"That may confuse other users.", false);
		eb.addField("§4 Profile pictures", "Avoid any extreme right, gloryfying violent or pornographic content.\r\n" + 
				"If we have any objections towards your profile pictures as because they contravene these guidelines,\r\n" + 
				"we will contact you in order to change your picture.", false);
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setRulesEmbed_2(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("FF5555");
		eb.addField("§5 Advertisments", "Advertising other servers (e.g. Discord, Minecraft, TeamSpeak, Streams, Radios, etc.) or projects are not permitted.\r\n" + 
				"Wooing away other users is strictly prohibited.", false);
		eb.addField("§6 Chat content, pictures, voice channels", "This server's preferred language is english.\r\n" + 
				"Other languages are only allowed in the specific language channels.\r\n" + 
				"You can claim the role(s) in #roles \r\n" + 
				"Spam is forbidden in any condition.\r\n" + 
				"Bullying, secual allusions, insults or hostilities as well as racial, extremly right or gloryfying violent statements are strictly prohibited.\r\n" + 
				"The usage of here and everyone is not allowed.\r\n" + 
				"If you are using those anyways you risk getting kicked from the server.\r\n" + 
				"Pictures or videos are allowed in the designated language channels and in #general as long as theys refer to the current discussion and the current content of the chat.\r\n" + 
				"Random pictures or videos that do not refer to the current discussion must be posted in #shares .", false);
		eb.addField("§7 Voice channels and channel hopping", "Frequent changing between server voice channels is strictly prohibited.\r\n" + 
				"When you are in a voice channel, do not interrupt each other!\r\n" + 
				"Please manage a comfy voice quality.\r\n" + 
				"Please avoid any disturbing noises making annoying sounds like burping, squeeking or any other noises.\r\n" + 
				"The usage of voice changers is not allowed.", false);
		eb.addField("§8 Bots", "The unapproved usage of any selfbots is not allowed and forbidden in the ToS of Discord and leads to a permanent ban on this server.\r\n" + 
				"The use of bots is only allowed in #bot-commands .\r\n" + 
				"Spamming in bot-commands is prohibited.", false);
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setRulesEmbed_3(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("55FF55");
		eb.addField("§9 Privacy", "Our staff-members are not 24/7 available as they also have a private life.\r\n" + 
				"Please avoid unnecessary messages and/or pings.\r\n" + 
				"If you want to contact any staff private, please ask for permission first in order to do that.\r\n" + 
				"If this permission is not granted, this decision is final!\r\n" + 
				"Violating this decision may cause a warn!\r\n" + 
				"Please have also a look at staff's online-status:\r\n" + 
				"<:online:671772876482936862> = Available\r\n" + 
				"<:idle:671772876449251383> = Absent/AFK - an answer might take a while\r\n" + 
				"<:dnd:708982976838369320>  = Do not Disturb\r\n" + 
				"<:offline:671772876499582996> = Offline/Invisible or do not disturb", false);
		eb.addField("§10 Staff instructions", "Staff instructions are absolutely to obey.\r\n" + 
				"Muting staffs or ignoring staff instructions is not allowed.\r\n" + 
				"Provoking staff-members or other users is to avoid.\r\n" + 
				"Not following staff-instructions will lead to consequences as warns, kicks or bans.", false);
		eb.addField("§11 Moderation", "Redi Bot takes over an amount of server moderation yet the moderators and admins are still watchful!\r\n" + 
				"It is very important to follow their instructions.\r\n" + 
				"If you have a problem with a warn, kick or ban as it might be an invalid one or wrong, do not hesitate to contact a community manager.", false);
		eb.addField("§12 Data protection", "Data protection is very important for all of us.\r\n" + 
				"Therefore we would like you to pay attention on the following:\r\n" + 
				"Do not share private information such as phone numbers, addresses, passwords, etc. in public channels.\r\n" + 
				"For your own safety our moderators will remove such content, please understand that.", false);
		eb.addField("§13 Bans, Events and Applications", "Discussing web reports, appeals, feedback tickets, punishments received in-game, on the forum or on discord is not allowed.\r\n" + 
				"Requesting Game Moderators to moderate in game is not allowed.\r\n" + 
				"Please use instead the ingame report system or use the web report system.", false);
		chan.sendMessage(eb.build()).queue();
	}
	
	private void setRulesEmbed_4(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("FF5555");
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        String stime = time.format(new Date());
		eb.setDescription("**We wish you all a lot of fun on the RediCraft Discord server!**\n *The RediCraft - Team* \n \nLast Updated: " + stime);
		chan.sendMessage(eb.build()).queue();
	}
}