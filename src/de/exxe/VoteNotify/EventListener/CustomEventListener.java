package de.exxe.VoteNotify.EventListener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import de.exxe.VoteNotify.Lists.VoteListHandler;
import de.exxe.VoteNotify.Main.Main;


public class CustomEventListener implements Listener {
	
	public VoteListHandler voteListHandler;
	
	public CustomEventListener(Main main) {
		voteListHandler = new VoteListHandler(main);
	}
    
    @SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent event) {
        Vote vote = event.getVote();
        System.out.println(vote.getAddress());
        if(!Bukkit.getOnlinePlayers().toString().toUpperCase().contains(vote.getUsername())) {
        	OfflinePlayer p = Bukkit.getOfflinePlayer(vote.getUsername());
        	voteListHandler.add(p);
		}else {
			Player p = Bukkit.getPlayer(vote.getUsername());
			voteListHandler.add(p);
		}
        

    }
    
}