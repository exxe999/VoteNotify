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
		List<String> list = getList();
		if(list.contains(uuid)) {
			list.remove(uuid);
		}else {
			list.add(uuid);
		}
		plugin.getCustomConfig().get().set("toggleList", list);
		plugin.getCustomConfig().saveConfig();
	}
	
	public boolean contains(Player p) {
		String uuid = p.getUniqueId().toString();
		try {
			@SuppressWarnings("unused")
			List<String> list = plugin.getCustomConfig().get().getStringList("toggleList");
		} catch (Exception e) {
			return false;
		}
		List<String> list = plugin.getCustomConfig().get().getStringList("toggleList");
		if(list.isEmpty()) return false;
		if(list.contains(uuid)) return true;
		return false;

	}
	
	public void remove(Player p) {
		String uuid = p.getUniqueId().toString();
		try {
			@SuppressWarnings("unused")
			List<String> list = plugin.getCustomConfig().get().getStringList("toggleList");
		} catch (Exception e) {
			return;
		}
		List<String> list = plugin.getCustomConfig().get().getStringList("toggleList");
		list.remove(uuid);
		
	}
	
	public List<String> getList(){
		try {
			List<String> list = plugin.getCustomConfig().get().getStringList("toggleList");
			return list;
		} catch (Exception e) {
			List<String> list = new ArrayList<>();
			return list;
		}
	}

}
