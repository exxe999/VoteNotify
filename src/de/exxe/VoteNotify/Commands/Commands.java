package de.exxe.VoteNotify.Commands;

import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.exxe.VoteNotify.Lists.ToggleListHandler;
import de.exxe.VoteNotify.Lists.VoteListHandler;
import de.exxe.VoteNotify.Main.Main;
import de.exxe.VoteNotify.Notifier.Notifier;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	private Main main;
	private Notifier notifier;
	private VoteListHandler voteListHandler;
	private ToggleListHandler toggleListHandler;
	private boolean toggleallow;
	private String servername;
	private Logger log;

	public Commands(Main main, Notifier notifier) {
		this.main = main;
		this.notifier = notifier;
		voteListHandler = new VoteListHandler(main);
		toggleListHandler = new ToggleListHandler(main);
		toggleallow = main.getConfig().getBoolean("notification.toggleallow");
		servername = main.getConfig().getString("general.servername");
		log = main.getLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&7[&cVoteNotify&7] Du bist kein Spieler!"));
			return true;
		}
		
		Player senderPlayer = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("votenotify")) {	
			if(args.length == 1) {
				//notify
				if(args[0].equalsIgnoreCase("notify")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						if(main.getConfig().getBoolean("general.enabled")) {
							notifier.voteNotify(voteListHandler, toggleListHandler);
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&6Allen Spielern wurde eine Votebenachrichtigung geschickt."));
							return true;
						}else {
							log.info("Notifications are currently disabled!!");
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&cBenachrichtigungen sind deaktiviert! &b/votenotify toggleall"));
							return true;
						}
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}
				}
				//removeall
				if(args[0].equalsIgnoreCase("removeall")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						voteListHandler.removeAll();
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Die Voteliste wurde gelöscht."));
						return true;
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}	
				}
				//toggle
				if(args[0].equalsIgnoreCase("toggle")) {
					if(senderPlayer.hasPermission("votenotify.admin") || toggleallow) {
						if(toggleListHandler.contains(senderPlayer)) {
							toggleListHandler.toggle(senderPlayer);
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&7Du wirst nun wieder benachrichtigt."));
							return true;
						}else {
							toggleListHandler.toggle(senderPlayer);
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&aDu wirst nicht mehr benachrichtigt."));
							return true;
						}
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&cDiese Option ist auf " + servername + " blockiert."));
						return true;
					}
				}
				//add
				if(args[0].equalsIgnoreCase("add")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&cKeinen Spieler angegeben! &b/votenotify add &3<player>"));
						return true;
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}
					
				}
				//dis/enable
				if(args[0].equalsIgnoreCase("toggleall")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						System.out.println(main.getConfig().getBoolean("general.enabled"));
						if(main.getConfig().getBoolean("general.enabled") == true) {
							main.getConfig().set("general.enabled", false);
							main.saveConfig();
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&cBenachrichtigungen deaktiviert!"));
							return true;
						}else {
							main.getConfig().set("general.enabled", true);
							main.saveConfig();
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&aBenachrichtigungen wieder aktiviert."));
							return true;
						}
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}
					
				}
				//info
				if(args[0].equalsIgnoreCase("info")) {
					String message = "";
					boolean voted = false;
					if(voteListHandler.contains(senderPlayer)){
						message += ChatColor.GREEN + "Du hast heute schon gevotet! Super :)";
						voted = true;
					}else {
						notifier.sendVoteMessage(senderPlayer);
					}
					if(toggleListHandler.contains(senderPlayer)){
						message += ChatColor.GRAY + "\nFür dich sind die Benachrichtigungen deaktiviert.";
					}
					if(voted) senderPlayer.sendMessage(message);
					return true;
				}
				//list
				if(args[0].equalsIgnoreCase("list")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						String onlineList = "";
						int votes = 0;
						Collection<? extends Player> online = Bukkit.getOnlinePlayers();
						Player[] onlinearray = online.toArray(new Player[online.size()]);
						for(int i = 0; i < onlinearray.length; i++) {
							Player onlinePlayer = onlinearray[i].getPlayer();
							if(voteListHandler.contains(onlinePlayer)) {
								onlineList += (ChatColor.GREEN + "[\u2714]");
								votes++;
							}else {
								onlineList += (ChatColor.RED + "[\u2718]");
							}
							onlineList += (onlinePlayer.getDisplayName() + ", ");
							if(onlineList.length() > 30) {
								onlineList += "\n";
							}
						}
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&c" + onlinearray.length +" &6Spieler sind online. &c" + votes + " &6haben schon gevotet."));
						senderPlayer.sendMessage(onlineList.substring(0, onlineList.length() -2));
						return true;
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}
				}
			}
			if(args.length == 2) {
				//add player
				if(args[0].equalsIgnoreCase("add")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						if(!Bukkit.getOnlinePlayers().toString().toUpperCase().contains(args[1].toUpperCase())) {
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&cDieser Spieler ist nicht online."));
							return true;
						}
						Player p = Bukkit.getPlayer(args[1]);						
						voteListHandler.add(p);
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&c" + p.getName() + "&6 wurde zur Voteliste hinzugefügt."));
						return true;
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}
					
				}
				//add player
				if(args[0].equalsIgnoreCase("toggle")) {
					if(senderPlayer.hasPermission("votenotify.admin")) {
						if(!Bukkit.getOnlinePlayers().toString().toUpperCase().contains(args[1].toUpperCase())) {
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&cDieser Spieler ist nicht online."));
							return true;
						}
						Player p = Bukkit.getPlayer(args[1]);
						if(toggleListHandler.contains(p)) {
							toggleListHandler.toggle(p);
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&6Der Spieler&c " + p.getName() + " &6wird nun wieder benachrichtigt."));
							return true;
						}else {
							toggleListHandler.toggle(p);
							senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&6Der Spieler&c " + p.getName() + " &6wird nicht mehr benachrichtigt."));
							return true;
						}
					}else {
						senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&4Du hast keinen Zugriff auf diesen Befehl!"));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("test")) {
					System.out.println(senderPlayer.getAddress().getAddress().getHostAddress());
					System.out.println(senderPlayer.getAddress().getAddress());
					System.out.println(senderPlayer.getAddress().getHostString());
					System.out.println(senderPlayer.getAddress().getHostName());
				}
			}	
		}
		if(senderPlayer.hasPermission("votenotify.admin")) {
			senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&b/votenotify notify &7Benachrichtigt jeden Spieler zu voten.\n&b/votenotify info &7Prüfe, ob du schon gevotet hast.\n&b/votenotify list &7Zeigt eine Liste mit Statistiken.\n&b/votenotify toggle &3<player> &7Setzt einen Spieler dauerhaft auf die Toggleliste.\n&b/votenotify add &3<player> &7Fügt einen Spieler zur 'schon gevotet' Liste hinzu.\n&b/votenotify removeall &7Löscht die 'schon gevotet' Liste. (don't do that)\n&b/votenotify toggleall &7Schaltet die Benachrichtigung für den ganzen Server ein/aus."));
			return true;
		}else if(toggleallow){
			senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&b/votenotify info &7Prüfe, ob du schon gevotet hast.\n&b/votenotify toggle &7Empfange keine Votebenachrichtigung mehr."));
			return true;
		}else {
			senderPlayer.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&b/votenotify info &7Prüfe, ob du schon gevotet hast."));
			return true;
		}

	}
}
