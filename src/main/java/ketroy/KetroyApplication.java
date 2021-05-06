package ketroy;

import java.io.File;

import ketroy.model.RunnerQueue;
import ketroy.service.BddService;
import ketroy.service.FileService;

public class KetroyApplication {

	public static void main(String[] args) {
		
		File file = FileService.findFileByName("generic.txt");
		
		RunnerQueue runnerQueue = new RunnerQueue();
		
		BddService.triggerRunnerQueue(runnerQueue);
	}
}
