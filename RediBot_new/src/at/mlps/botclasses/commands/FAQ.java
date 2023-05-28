package at.mlps.botclasses.commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.mlps.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FAQ extends ListenerAdapter{
	
	static long chanId = 548187217726537738L;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		MessageChannel chan = e.getChannel();
		String[] args = e.getMessage().getContentRaw().split(" ");
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "faq")) {
				if(e.getGuild().getIdLong() == 548136727697555496L) {
					Role pm = e.getGuild().getRoleById(548175887179186191L);
					TextChannel faqchannel = e.getGuild().getTextChannelById(chanId);
					if(e.getMember().getRoles().contains(pm)) {
						setEmbed_invite(faqchannel);
						setEmbed_platform(faqchannel);
						setEmbed_partnerships(faqchannel);
						setEmbed_beta(faqchannel);
						setEmbed_report(faqchannel);
						setEmbed_apply(faqchannel);
						setEmbed_team(faqchannel, e.getGuild());
					}
				}else {
					chan.sendMessage("Error! This guild is not registered for a FAQ for RediCraft!").queue(msg1 -> {
						msg1.delete().completeAfter(10, TimeUnit.SECONDS);
					});
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "rules")) {
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
						eb.setAuthor("Rules Changed at: " + stime, null, e.getAuthor().getAvatarUrl());
						eb.setDescription("Changes: \n- �6 Added: ``Any language is allowed for the channels regarding Minecraft <-> Discord (Preferred language is still english) / The usage of the global chat should be kept as a minimum, please choose always the designated serverchannel to avoid spam.`` \r\n"
								+ "- �9 Added: ``Please also do not spam them regarding Ingame things, use the official ways in order to not risk to getting punished.``\r\n" +
								"- �9 Changed: ``Streaming means Do not Disturb`` - Don't disturb People who are streaming");
						ruleschangelog.sendMessageEmbeds(eb.build()).queue();
					}
				}else {
					chan.sendMessage("Error! This guild is not registered for a ruleset of RediCraft!").queue(msg1 -> {
						msg1.delete().completeAfter(10, TimeUnit.SECONDS);
					});
				}
			}else if(args[0].equalsIgnoreCase(Main.botprefix + "updateTeamEmbed")) {
				chan.sendMessage("Right Usage: rb!updateTeamEmbed <MessageID>").queue();
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Main.botprefix + "updateTeamEmbed")) {
				if(e.getGuild().getIdLong() == 548136727697555496L) {
					Role pm = e.getGuild().getRoleById(548175887179186191L);
					if(e.getMember().getRoles().contains(pm)) {
						if(args[1].matches("^[0-9]+$")) {
							Long newId = Long.parseLong(args[1]);
							updateEmbed_team(e.getGuild(), newId);
							chan.sendMessage("The Embed has been updated!").queue();
						}
					}
				}
			}
		}
	}
	
	private EmbedBuilder Embed() {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.decode("#5555ff"));
		eb.setFooter("RediCraft FAQ", "https://i.imgur.com/8Ol1sjr.png");
		return eb;
	}
	
	private EmbedBuilder Rulesembed(String color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.decode("#" + color));
		return eb;
	}
	
	private void setEmbed_report(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: I want to report someone. How can I do this?\nAnswer: You can report Users through ModMail or pinging a Community Moderator.");
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void updateEmbed_team(Guild g, long msgid) {
		TextChannel chan = g.getTextChannelById(chanId);
		chan.retrieveMessageById(msgid).queue(msg -> {
			Role owner = g.getRoleById(548175887179186191L); //owner
			List<Member> owner1 = g.getMembersWithRoles(owner);
			Role coowner = g.getRoleById(962408062302433360l); //co-owner
			List<Member> coowner1 = g.getMembersWithRoles(coowner);
			Role supervisor = g.getRoleById(779322052175462400l); //supervisor
			List<Member> supervisor1 = g.getMembersWithRoles(supervisor);
			Role mod = g.getRoleById(548175915054792704L); //moderator
			List<Member> mod1 = g.getMembersWithRoles(mod);
			Role sup = g.getRoleById(548175909291819008L); //support
			List<Member> sup1 = g.getMembersWithRoles(sup);
			MessageEmbed me = msg.getEmbeds().get(0);
			EmbedBuilder eb = new EmbedBuilder(me);
			eb.clearFields();
			if(owner1.size() != 0) {
				eb.addField("Owner (" + owner1.size() + ")", getFromList(owner1), false);
			}
			if(coowner1.size() != 0) {
				eb.addField("Co-Owner (" + coowner1.size() + ")", getFromList(coowner1), false);
			}
			if(supervisor1.size() != 0) {
				eb.addField("Supervisor (" + supervisor1.size() + ")", getFromList(supervisor1), false);
			}
			if(mod1.size() != 0) {
				eb.addField("Moderator (" + mod1.size() + ")", getFromList(mod1), false);
			}
			if(sup1.size() != 0) {
				eb.addField("Supporter (" + sup1.size() + ")", getFromList(sup1), false);
			}
			msg.editMessageEmbeds(eb.build()).queue();
		});
	}
	
	private void setEmbed_team(TextChannel chan, Guild g) {
		Role owner = g.getRoleById(548175887179186191L); //owner
		List<Member> owner1 = g.getMembersWithRoles(owner);
		Role coowner = g.getRoleById(962408062302433360l); //co-owner
		List<Member> coowner1 = g.getMembersWithRoles(coowner);
		Role supervisor = g.getRoleById(779322052175462400l); //supervisor
		List<Member> supervisor1 = g.getMembersWithRoles(supervisor);
		Role mod = g.getRoleById(548175915054792704L); //moderator
		List<Member> mod1 = g.getMembersWithRoles(mod);
		Role sup = g.getRoleById(548175909291819008L); //support
		List<Member> sup1 = g.getMembersWithRoles(sup);
		EmbedBuilder eb = Embed();
		if(owner1.size() != 0) {
			eb.addField("Owner (" + owner1.size() + ")", getFromList(owner1), false);
		}
		if(coowner1.size() != 0) {
			eb.addField("Co-Owner (" + coowner1.size() + ")", getFromList(coowner1), false);
		}
		if(supervisor1.size() != 0) {
			eb.addField("Supervisor (" + supervisor1.size() + ")", getFromList(supervisor1), false);
		}
		if(mod1.size() != 0) {
			eb.addField("Moderator (" + mod1.size() + ")", getFromList(mod1), false);
		}
		if(sup1.size() != 0) {
			eb.addField("Supporter (" + sup1.size() + ")", getFromList(sup1), false);
		}
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private String getFromList(List<Member> list) {
		StringBuilder sb = new StringBuilder();
		for(Member m : list) {
			sb.append(m.getAsMention());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private void setEmbed_platform(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: Do we have another social media platforms?\nAnswer: For sure we have another social media platforms.");
		eb.addField("Instagram:", "https://www.instagram.com/redicrafteu/", false);
		eb.addField("Facebook:", "Site: https://www.facebook.com/redicrafteu/ \nGroup: https://www.facebook.com/groups/370322470364195/", false);
		eb.addField("YouTube:", "https://www.youtube.com/channel/UCBJhuPBSaucwk_TthujYrBw", false);
		eb.addField("Twitch:", "https://www.twitch.tv/redicrafteu", false);
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setEmbed_invite(TextChannel chan) {
		EmbedBuilder eb = Embed();
		eb.setDescription("Question: How can I invite my friends? \nAnswer: Use the following invite link to invite your friends: https://discord.gg/sHDF9WR");
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setEmbed_partnerships(TextChannel chan) {
		EmbedBuilder eb = Embed();
		//eb.setDescription("Question: Does RediCraft also accept partnerships? \nAnswer: Yes, we do! We accept partnerships. Link: https://forms.gle/HKZgBi6E2L84yUSJ8");
		eb.setDescription("Question: Does RediCraft also accept partnerships? \nAnswer: Unfortunately, we don't. Since the Minecraft Server has been closed, this guild is only here to communicate.");
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setEmbed_beta(TextChannel chan) {
		EmbedBuilder eb = Embed();
		//eb.setDescription("Question: Does RediCraft also accepts beta applications? \nAnswer: Yes, we do! We accept beta applicants. Apply here: https://forms.gle/xLBiZqPShb6sYqK47");
		eb.setDescription("Question: Does RediCraft also accepts beta application? \nAnswer: We currently don't. We will announce it, when we are searching beta testers again.");
		chan.sendMessageEmbeds(eb.build()).queue();
	}

	private void setEmbed_apply(TextChannel chan) {
		EmbedBuilder eb = Embed();
		//eb.setDescription("Question: How can I apply for the staff team? \nAnswer: Use the following Google Forms: https://forms.gle/MjVWATgubHVRUhS1A");
		eb.setDescription("Question: How can I apply for the staff team?\nAnswer: You can't. If you'd like to join our team, just ask a supervisor of your choice. However, we don't look for new staff members.");
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setRulesEmbed_1(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("55FF55");
		eb.addField("�1 General Informations and Warnings", "Please keep in mind that the chats are archived in order to investigate any incidents.\r\n" + 
				"Please be aware that the investigation of such incidents may take some time.\r\n" + 
				"The staff is not responsible for the things that happens on the server!\r\n" + 
				"You as the user have a certain amount of individual responsibility!\r\n" + 
				"We reserve the right to remove users from the chat who are disturbing the chat climate, please respect that!\r\n" + 
				"\r\n" + 
				"The Discord Terms of Service and Community Guidelines are strictly enforced.\r\n" + 
				"\r\n" + 
				"Exceptions to the rules can be made by upper staff.", false);
		eb.addField("�2 Ranks, Rights and Roles", "Begging for rights nor for ranks is forbidden.\r\n" + 
				"You can claim some ranks yourself.\r\n" + 
				"To claim yourself a role, go to #roles and tick the corresponding reaction under the message.", false);
		eb.addField("�3 Nicknames", "Nickfakes are not allowed.\r\n" + 
				"Please do not make the appearance of a person you are not.\r\n" + 
				"Nicknames have to be clearly understandable.\r\n" + 
				"Therefore, they must not contain any ASCII-Art, Zalgo-Text or other unreadable characters.\r\n" + 
				"Nicknames are not allowed to contain any parts like Staff, Moderator or Admin.\r\n" + 
				"That may confuse other users.", false);
		eb.addField("�4 Profile pictures", "Avoid any extreme right, gloryfying violent or pornographic content.\r\n" + 
				"If we have any objections towards your profile pictures as because they contravene these guidelines,\r\n" + 
				"we will contact you in order to change your picture.", false);
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setRulesEmbed_2(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("FF5555");
		eb.addField("�5 Advertisments", "Advertising other servers (e.g. Discord, Minecraft, TeamSpeak, Streams, Radios, etc.) or projects are not permitted.\r\n" + 
				"Wooing away other users is strictly prohibited.", false);
		eb.addField("�6 Chat content, pictures and voice channels", "This server's preferred language is english.\r\n" + 
				"Other languages are only allowed in the specific language channels.\r\n" + 
				"You can claim the role(s) in #roles \r\n" + 
				"Spam is forbidden in any condition.\r\n" + 
				"Bullying, secual allusions, insults or hostilities as well as racial, extremly right or gloryfying violent statements are strictly prohibited.\r\n" + 
				"The usage of here and everyone is not allowed.\r\n" + 
				"If you are using those anyways you risk getting kicked from the server.\r\n" + 
				"Pictures or videos are allowed in the designated language channels and in #general as long as theys refer to the current discussion and the current content of the chat.\r\n" + 
				"Random pictures or videos that do not refer to the current discussion must be posted in #shares .\r\n" + 
				"Any language is allowed for the channels regarding Minecraft <-> Discord (Prefered Language is still english)\r\n" + 
				"The usage of the global chat should be kept as a minimum, please choose always the designated serverchannel to avoid spam.", false);
		eb.addField("�7 Voice channels and channel hopping", "Frequent changing between server voice channels is strictly prohibited.\r\n" + 
				"When you are in a voice channel, do not interrupt each other!\r\n" + 
				"Please manage a comfy voice quality.\r\n" + 
				"Please avoid any disturbing noises making annoying sounds like burping, squeeking or any other noises.\r\n" + 
				"The usage of voice changers is not allowed.", false);
		eb.addField("�8 Bots", "The unapproved usage of any selfbots is not allowed and forbidden in the ToS of Discord and leads to a permanent ban on this server.\r\n" + 
				"The use of bots is only allowed in #bot-commands .\r\n" + 
				"Spamming in bot-commands is prohibited.", false);
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setRulesEmbed_3(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("55FF55");
		eb.addField("�9 Privacy", "Our staff-members are not 24/7 available as they also have a private life.\r\n" + 
				"Please avoid unnecessary messages and/or pings.\r\n" + 
				"If you want to contact any staff private, please ask for permission first in order to do that.\r\n" + 
				"If this permission is not granted, this decision is final!\r\n" + 
				"Violating this decision may cause a warn!\r\n" + 
				"Please also do not spam them regarding Ingame things, use the official ways in order to not risk to getting warned.\r\n" + 
				"Please have also a look at staff's online-status:\r\n" + 
				"<:online:671772876482936862> = Available\r\n" + 
				"<:idle:671772876449251383> = Absent/AFK - an answer might take a while\r\n" + 
				"<:dnd:708982976838369320> = Do not Disturb\r\n" + 
				"<:offline:671772876499582996> = Offline/Invisible or do not disturb\r\n" +
				"<:streaming:671772876713492510> = Do not Disturb (Streaming)", false);
		eb.addField("�10 Staff instructions", "Staff instructions are absolutely to obey.\r\n" + 
				"Muting staffs or ignoring staff instructions is not allowed.\r\n" + 
				"Provoking staff-members or other users is to avoid.\r\n" + 
				"Not following staff-instructions will lead to consequences as warns, kicks or bans.", false);
		eb.addField("�11 Moderation", "Redi Bot takes over an amount of server moderation yet the moderators and admins are still watchful!\r\n" + 
				"It is very important to follow their instructions.\r\n" + 
				"If you have a problem with a warn, kick or ban as it might be an invalid one or wrong, do not hesitate to contact a community manager.", false);
		eb.addField("�12 Data protection", "Data protection is very important for all of us.\r\n" + 
				"Therefore we would like you to pay attention on the following:\r\n" + 
				"Do not share private information such as phone numbers, addresses, passwords, etc. in public channels.\r\n" + 
				"For your own safety our moderators will remove such content, please understand that.", false);
		eb.addField("�13 Bans, Events and Applications", "Discussing web reports, appeals, feedback tickets, punishments received in-game, on the forum or on discord is not allowed.\r\n" + 
				"Requesting Game Moderators to moderate in game is not allowed.\r\n" + 
				"Please use instead the ingame report system or use the web report system.", false);
		chan.sendMessageEmbeds(eb.build()).queue();
	}
	
	private void setRulesEmbed_4(TextChannel chan) {
		EmbedBuilder eb = Rulesembed("FF5555");
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        String stime = time.format(new Date());
		eb.setDescription("**We wish you all a lot of fun on the RediCraft Discord server!**\n *The RediCraft - Team* \n \nLast Updated: " + stime);
		chan.sendMessageEmbeds(eb.build()).queue();
	}
}