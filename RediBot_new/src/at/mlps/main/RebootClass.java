package at.mlps.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class RebootClass extends TimerTask{

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(new Date());
		if(time.matches("03:30:00")) {
			System.exit(0);
		}
	}
}