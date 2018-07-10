package de.exxe.VoteNotify.Commands;

import java.util.ArrayList;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import de.exxe.VoteNotify.Main.Main;
 
public class CommandAutoCompleter implements TabCompleter {
   
            public Main plugin;
   
            public CommandAutoCompleter(Main main) {
       
                this.plugin = main;
                
            }
            
    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String cmdLabel, String[] args) {
    	
    	boolean toggleallow = plugin.getConfig().getBoolean("notification.toggleallow");
    	
        ArrayList<String> cmdList = new ArrayList<String>();
        
        cmdList.add("notify");
        cmdList.add("add");
        cmdList.add("removeall");
        cmdList.add("toggle");
        cmdList.add("toggleall");
        cmdList.add("info");
        
       
        List<String> l = new ArrayList<String>();
       
        if(args.length == 0) {
       
                for(String s : cmdList) {
                	if(cs.hasPermission("votenotify.admin")) {
                		l.add(s);
                    }
                }
                if(!(cs.hasPermission("votenotify.admin"))) {
                	l.add("info");
                	if(toggleallow) {
                		l.add("toggle");
                	}
                }              

        }
       
        else if(args.length == 1) {
           
                for(String s : cmdList) {
                	if(s.startsWith(args[0].toLowerCase()) && cs.hasPermission("votenotify.admin")) {
                    	l.add(s);
                    }
                }
                
                if(!(cs.hasPermission("votenotify.admin"))) {
                	l.add("info");
                	if(toggleallow) {
                		l.add("toggle");
                	}
                }        
        }
       
        else {
           
                if(!(cs instanceof Player)) {
                   
                        for(Player p : Bukkit.getOnlinePlayers())
                       
                                if(p.getName().toLowerCase().startsWith(args[args.length -1]))
                               
                                        l.add(p.getName());
                }
               
                else {
                       
                        for(Player p : Bukkit.getOnlinePlayers())
                           
                                if(((Player) cs).canSee(p))
                               
                                        if(p.getName().toLowerCase().startsWith(args[args.length -1]))
                               
                                                l.add(p.getName());
                }
        }
       
        return l;
    }
 
}