package de.exxe.VoteNotify.Main;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Logger;

import de.exxe.VoteNotify.Lists.VoteListHandler;

public class DailyResetTask extends TimerTask {
	
	private Logger log;
	public VoteListHandler voteListHandler;
	
	public DailyResetTask(Main main) {
		voteListHandler = new VoteListHandler(main);
		log = main.getLogger();
	}
	
	@Override
	public void run() {
		Date date = new Date();
		Calendar schedule = Calendar.getInstance();
		schedule.set(Calendar.HOUR_OF_DAY, 0);
		schedule.set(Calendar.MINUTE, 0);
		schedule.set(Calendar.SECOND, 0);
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
		now.set(Calendar.SECOND, now.get(Calendar.SECOND));
		
		if(schedule.equals(now)) {
			voteListHandler.removeAll();
			log.warning("VoteList successfully resetted.");
		}
	}

}
