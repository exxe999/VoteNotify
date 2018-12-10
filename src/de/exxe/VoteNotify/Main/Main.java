package de.exxe.VoteNotify.Main;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.exxe.VoteNotify.Commands.CommandAutoCompleter;
import de.exxe.VoteNotify.Commands.Commands;
import de.exxe.VoteNotify.CustomConfig.CustomConfig;
import de.exxe.VoteNotify.EventListener.CustomEventListener;
import de.exxe.VoteNotify.Lists.ToggleListHandler;
import de.exxe.VoteNotify.Lists.VoteListHandler;
import de.exxe.VoteNotify.Notifier.Notifier;

public class Main extends JavaPlugin {

	private CustomConfig voteList;
	private Notifier notifier;
	private VoteListHandler voteListHandler;
	private ToggleListHandler toggleListHandler;

	@Override
	public void onEnable() {

		notifier = new Notifier(this);
		voteListHandler = new VoteListHandler(this);
		toggleListHandler = new ToggleListHandler(this);
		int timer = this.getConfig().getInt("notification.timer");
		voteList = new CustomConfig(this);

		getCommand("votenotify").setExecutor(new Commands(this, notifier));
		getCommand("votenotify").setTabCompleter(new CommandAutoCompleter(this));
		getServer().getPluginManager().registerEvents(new CustomEventListener(this), this);

		reloadAllConfigs();

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		Timer timer2 = new Timer();
		timer2.scheduleAtFixedRate(new DailyResetTask(this), today.getTime(),
				TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day

		new BukkitRunnable() {

			@Override
			public void run() {
				callNotifier();
			}
		}.runTaskTimer(this, 20 * 60 * 1, timer * 20 * 60);
	}

	@Override
	public void onDisable() {

	}

	public CustomConfig getCustomConfig() {
		return voteList;
	}

	public void callNotifier() {
		notifier.voteNotify(voteListHandler, toggleListHandler);
	}

	public void reloadAllConfigs() {
		reloadConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
