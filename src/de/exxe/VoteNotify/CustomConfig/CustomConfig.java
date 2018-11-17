package de.exxe.VoteNotify.CustomConfig;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.exxe.VoteNotify.Main.Main;

public class CustomConfig {
	private File f;
	private FileConfiguration cfg;
	
	public CustomConfig(Main pl) {
		f = new File(pl.getDataFolder(), "VoteToggleList.yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		
		if(!f.exists()) 
			try {
				f.createNewFile();
				} catch (IOException e) {
					System.err.println("[VOTENOTIFY] RESTART THE SERVER TO FIX THIS PROBLEM!!!");
					e.printStackTrace();
				}
	}
	
	public void saveConfig() {
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration get() {
		return cfg;
	}

}
