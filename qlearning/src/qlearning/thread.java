package qlearning;

import javax.swing.ImageIcon;

public class thread extends Thread{
	static int i =0;
		thread(){}
		
		public static synchronized void go() throws InterruptedException {
			oyun.move();
			
		}
		public  void run() {while(true)
		{	
			try {
				go();thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}	
		}static String sa;
}
