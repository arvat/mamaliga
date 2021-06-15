package ketroy.thread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ketroy.model.Context;

public class RunnerQueue extends Thread {

    private Context context;
    private String host;
    
	public RunnerQueue(String url) throws IOException {
		context = new Context(url);
		host = new URL(url).getHost();
    }
    
    public void run() {
        while (context.isRunning()) {
        	context.getDriver().get(context.getUrl());
        	By by = By.xpath("//body//a");
        	List<WebElement> webElements = context.getDriver().findElements(by);
        	if (webElements != null && !webElements.isEmpty()) {
        		for (WebElement webElement : webElements) {
        			String path = webElement.getAttribute("href");
        			try {
        				if (path != null) {
							String pathHost = new URL(path).getHost();
		        			if (pathHost != null && pathHost.equals(host) && !context.getPaths().contains(path)) {
		        				context.getPaths().add(path);
		        			}
        				}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
        		}
        	}
        	
        	getNextUrl();
        }

        if (context.getDriver() != null) {
        	context.getDriver().quit();
        }
    }

	private void getNextUrl() {
		int index = context.getPaths().indexOf(context.getUrl());
    	if (context.getPaths().size() > index) {
    		context.setUrl(context.getPaths().get(++index));
    	} else {
    		context.setRunning(false);
    	}
	}
}
