package ketroy.model;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Context {

	private boolean running = true;
	
	private WebDriver driver = new FirefoxDriver();
	
	private List<String> oldPaths = new ArrayList<>();
	
	private List<String> newPaths = new ArrayList<>();
	
    private String url;

	public Context(List<String> newPaths, List<String> oldPaths) {
		url = newPaths.get(0);
		this.newPaths = newPaths;
		this.oldPaths = oldPaths;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getOldPaths() {
		return oldPaths;
	}

	public void setOldPaths(List<String> oldPaths) {
		this.oldPaths = oldPaths;
	}

	public List<String> getNewPaths() {
		return newPaths;
	}

	public void setNewPaths(List<String> newPaths) {
		this.newPaths = newPaths;
	}
}
