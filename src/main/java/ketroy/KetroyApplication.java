package ketroy;

import java.util.List;

import ketroy.service.BddService;
import ketroy.service.FileService;

public class KetroyApplication {

	public static void main(String[] args) {
		
		List<String> lines = FileService.findFileByName("generic.txt");
		
		BddService.triggerRunnerQueue(lines);
	}
}
