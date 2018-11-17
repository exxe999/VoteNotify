package de.exxe.VoteNotify.Lists;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.exxe.VoteNotify.Main.Main;

public class ToggleListHandler {
	
private Main plugin;
	
	public ToggleListHandler(Main main) {
		this.plugin = main;
	}
	
	public void toggle(Player p) {
		String uuid = p.getUniqueId().toString();
		List<String> voteList = getList();
		if(voteList.contains(uuid)) {
			voteList.remove(uuid);
		}else {
			voteList.add(uuid);
		}
		plugin.getCustomConfig().get().set("toggleList", voteList);
		plugin.getCustomConfig().saveConfig();
	}
	
	public boolean contains(Player p) {
		String uuid = p.getUniqueId().toString();
		try {
			@SuppressWarnings("unused")
			List<String> voteList = plugin.getCustomConfig().get().getStringList("toggleList");
		} catch (Exception e) {
			return false;
		}
		List<String> voteList = plugin.getCustomConfig().get().getStringList("toggleList");
		if(voteList.isEmpty()) return false;
		if(voteList.contains(uuid)) return true;
		return false;

	}
	
	public void remove(Player p) {
		String uuid = p.getUniqueId().toString();
		try {
			@SuppressWarnings("unused")
			List<String> voteList = plugin.getCustomConfig().get().getStringList("toggleList");
		} catch (Exception e) {
			return;
		}
		List<String> voteList = plugin.getCustomConfig().get().getStringList("toggleList");
		voteList.remove(uuid);
		
	}
	
	public List<String> getList(){
		try {
			List<String> voteList = plugin.getCustomConfig().get().getStringList("toggleList");
			return voteList;
		} catch (Exception e) {
			List<String> voteList = new ArrayList<>();
			return voteList;
		}
	}

}
