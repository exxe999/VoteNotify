package de.exxe.VoteNotify.Notifier;

import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.exxe.VoteNotify.Lists.ToggleListHandler;
import de.exxe.VoteNotify.Lists.VoteListHandler;
import de.exxe.VoteNotify.Main.Main;

public class Notifier {
	
	private Main main;
	private int slowness;
	private int jump;
	private String votemessage;
	private String notifymessage;
	private String votelink;
	private Logger log;
	
	public Notifier(Main main) {
		this.main = main;
		slowness = main.getConfig().getInt("notifybuff.slownessduration");
		jump = main.getConfig().getInt("notifybuff.jumpduration");
		votemessage = main.getConfig().getString("notification.votemessage");
		notifymessage = main.getConfig().getString("notification.notifymessage");
		votelink = main.getConfig().getString("general.votelink");
		log = main.getLogger();
	}
	
	
	public void sendVoteMessage(Player p) {
		if(!(votemessage.equals(""))) {
			p.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',votemessage));
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), generateLink(p));
		}
	}
	
	public void giveBuffeffect(Player p) {
		if(slowness != 0) p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,slowness,255));
		if(jump != 0) p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,jump,128));
		if(!(notifymessage.equals(""))) p.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',notifymessage));
	}
	
	public void voteNotify(VoteListHandler voteList, ToggleListHandler toggleList) {
		if(main.getConfig().getBoolean("general.enabled")) {
			Collection<? extends Player> online = Bukkit.getOnlinePlayers();
			Player[] onlinearray = online.toArray(new Player[online.size()]);
			for(int i = 0; i < onlinearray.length; i++) {
				Player p = onlinearray[i].getPlayer();
				if(!(voteList.contains(p)) && !(toggleList.contains(p))) {
					this.sendVoteMessage(p);
					this.giveBuffeffect(p);
				}
			}
		}else {
			log.info("Notifications are currently disabled!!");
		}
	}
	
	public String generateLink(Player p) {
		String ret = "tellraw " + p.getName() + " [\"\",{\"text\":\"Hier klicken: \",\"color\":\"green\"},{\"text\":\"[VoteLink]\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\""+ votelink +"\"}}]";                                              
		return ret;
	}
	
}
