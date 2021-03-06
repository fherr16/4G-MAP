package application;
import java.util.Timer;
import java.util.TimerTask;


public class ApplicationTimer {
	 Timer timer;

	 public ApplicationTimer(int seconds) {
	        timer = new Timer();
	        timer.schedule(new RemindTask(), seconds*1000);
	}

	class RemindTask extends TimerTask {
	    public void run() {
	        System.out.format("Time's up!%n");
	        timer.cancel(); //Terminate the timer thread
	    }
	}

	public static void main(String args[]) {
	     new ApplicationTimer(5);
	     System.out.format("Task scheduled.%n");
	}
}
