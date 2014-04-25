package de.ramuh.game.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ramuh.game.engine.systems.EventManager;
import de.ramuh.game.engine.systems.ProcessManager;

public class ProcessManagerTest {

	protected static final Logger LOG = LoggerFactory.getLogger(EventManager.class);
	
	private class Process1 extends de.ramuh.game.engine.process.Process {

		private int totalTime;
		
		@Override
		public void tick(long deltaMs) {
			//LOG.info("Tick, {}ms", deltaMs);
			totalTime += deltaMs;
			if(totalTime > 2000) {
				success();
			}
		}

		@Override
		public void onSuccess() {
			LOG.info("Success");
		}

		@Override
		public void onFail() {
			LOG.info("Fail");
		}

		@Override
		public void onAbort() {
			LOG.info("Abort");
		}
		
	}
	
	public static void main(String[] args) {
		ProcessManagerTest x = new ProcessManagerTest();
		
		final ProcessManager pm = new ProcessManager();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					//LOG.info("Tick");
					long s = System.currentTimeMillis();
					pm.updateProcesses(20);
					s = System.currentTimeMillis() - s;
					try {
						Thread.sleep(20 - s);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		

		Process1 p1 = x.new Process1();
		Process1 p2 = x.new Process1();
		p1.attachChild(p2);
		pm.attachProcess(p1);
		
		t1.start();
		
		try {
			//t1.join();
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
