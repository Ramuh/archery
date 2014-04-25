package de.ramuh.game.engine.systems;
import java.util.Iterator;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ramuh.game.engine.process.Process;

public class ProcessManager {

	LinkedList<Process> processList;
	
	protected static final Logger LOG = LoggerFactory.getLogger(ProcessManager.class);
	
	public ProcessManager() {
		processList = new LinkedList<>();
	}
	
	public int updateProcesses(long deltaMs) {
		int success = 0;
		int failed = 0;
		
		Iterator<Process> it = processList.iterator();
		while(it.hasNext()) {
			Process p = it.next();
			//LOG.debug("Ticking process {}",p);
			
			if(p.getState() == Process.ProcessStates.UNINITIALIZED) {
				p.initialize();
			}
			
			if(p.getState() == Process.ProcessStates.RUNNING) {
				p.tick(deltaMs);
			}
			
			if(p.isDead()) {
				
				switch(p.getState()) {
				case SUCCEEDED:
					p.onSuccess();
					Process child = p.removeChild();
					if(child != null) {
						LOG.debug("Attaching ChildProcess {}", child);
						attachProcess(child);
					}else{
						++success;
					}
					break;
					
				case FAILED:
					p.onFail();
					++failed;
					break;
				case ABORTED:
					p.onAbort();
					++failed;
					break;
				default:
					LOG.error("Oops, this should not happen.");
					break;
				}

				processList.remove(p);
			}
		}
			
		return 0;
	}

	public void attachProcess(Process p) {
		LOG.debug("Attaching process {}",p);
		processList.add(0, p);
	}
	
	
}
