package idv.eason.test;

import java.util.Timer;
import java.util.TimerTask;

public class tThread extends Thread{

    public void run() {
        TimerTask timertask = new TimerTask() {
          public void run() {
            System.out.println("hello");
          }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timertask, 0, 3000);
        try {
          Thread.sleep(6000);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    public static void main(String[] args) {
      // TODO Auto-generated method stub
      tThread t = new tThread();
      t.start();
    }

}
