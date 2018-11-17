package de.exxe.VoteNotify.Lists;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.exxe.VoteNotify.Main.Main;

public class VoteListHandler {
	
	private Main plugin;
	
	public VoteListHandler(Main main) {
		this.plugin = main;
	}
	
	public void add(Player p) {
		String uuid = p.getUniqueId().toString();
		try {
			List<String> voteList = plugin.getCustomConfig().get().getStringList("voteList");
			if(voteList.contains(uuid)) return;
			voteList.add(uuid);
			plugin.getCustomConfig().get().set("voteList", voteList);
			plugin.getCustomConfig().saveConfig();
		} catch (Exception e) {
			List<String> voteList = new ArrayList<>();
			if(voteList.contains(uuid)) return;
			voteList.add(uuid);
			plugin.getCustomConfig().get().set("voteList", voteList);
			plugin.getCustomConfig().saveConfig();
		}
	}
	
	public void add(OfflinePlayer p) {
		String uuid = p.getUniqueId().toString();
		try {
			List<String> voteList = plugin.getCustomConfig().get().getStringList("voteList");
			if(voteList.contains(uuid)) return;
			voteList.add(uuid);
			plugin.getCustomConfig().get().set("voteList", voteList);
			plugin.getCustomConfig().saveConfig();
		} catch (Exception e) {
			List<String> voteList = new ArrayList<>();
			if(voteList.contains(uuid)) return;
			voteList.add(uuid);
			plugin.getCustomConfig().get().set("voteList", voteList);
			plugin.getCustomConfig().saveConfig();
		}
	}
	
	public boolean contains(Player p) {
		String uuid = p.getUniqueId().toString();
		try {
			@SuppressWarnings("unused")
			List<String> voteList = plugin.getCustomConfig().get().getStringList("voteList");
		} catch (Exception e) {
			return false;
		}
		List<String> voteList = plugin.getCustomConfig().get().getStringList("voteList");
		if(voteList.isEmpty()) return false;
		if(voteList.contains(uuid)) return true;
		
		return false;

	}
	
	public void removeAll() {
		plugin.getCustomConfig().get().set("voteList", null);
		plugin.getCustomConfig().saveConfig();
	}
}
