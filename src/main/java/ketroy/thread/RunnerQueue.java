package ketroy.thread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ketroy.model.Context;

public class RunnerQueue extends Thread {

    private Context context;
    private String host;
    private Random rand = new Random();
    
	public RunnerQueue(List<String> newPaths, List<String> oldPaths) throws IOException {
		context = new Context(newPaths, oldPaths);
		host = new URL(newPaths.get(0)).getHost();
    }
    
    public void run() {
        while (context.isRunning()) {
        	context.getDriver().get(context.getUrl());
        	List<WebElement> webElements = context.getDriver().findElements(By.xpath("//body//a"));
        	if (webElements != null && !webElements.isEmpty()) {
        		for (WebElement webElement : webElements) {
        			if (isUniqueElement(webElement)) {
		        		context.getNewPaths().add(webElement.getAttribute("href"));
		        	}
        		}
        	}
        	
        	getNextUrl();
        }

        if (context.getDriver() != null) {
        	context.getDriver().quit();
        }
    }

    private boolean isUniqueElement(WebElement webElement) {
		try {
			String path = webElement.getAttribute("href");
			if (path != null) {
				String pathHost = new URL(path).getHost();
				return pathHost != null && pathHost.equals(host) && !context.getNewPaths().contains(path) && !context.getOldPaths().contains(path);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	
		return false;
    }
    
	private void getNextUrl() {
		
		if (!context.getOldPaths().contains(context.getUrl())) {
			context.getOldPaths().add(context.getUrl());
		}
		if (!context.getNewPaths().isEmpty()) {
			context.setUrl(context.getNewPaths().get(rand.nextInt(context.getNewPaths().size())));
			context.getNewPaths().remove(context.getUrl());
    	} else {
    		context.setRunning(false);
    	}
	}
}
