package ketroy;

import java.io.IOException;

import ketroy.thread.RunnerQueue;

public class KetroyApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		try {
			RunnerQueue runnerQueue1 = new RunnerQueue("");
			RunnerQueue runnerQueue2 = new RunnerQueue("");
			runnerQueue1.start();
			runnerQueue2.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
