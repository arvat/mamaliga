package ketroy.thread;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;

import ketroy.model.Context;

public class RunnerQueue extends Thread {

    private Context context;
    private Random rand = new Random();
	
    private static final String STORAGE_PATH = "./static/";
	
	public RunnerQueue(List<String> newPaths, List<String> oldPaths) throws IOException {
		context = new Context(newPaths, oldPaths);
    }
    
    public void run() {
        while (context.isRunning()) {
        	getPage();
        	savePage();
        	saveUniqueLinks();
        	getNextUrl();
        }

        quitDriver();
    }

    private void quitDriver() {
    	if (context.getDriver() != null) {
    		context.getDriver().quit();
    	}
    }
    
    private void saveUniqueLinks() {
    	List<WebElement> webElements = context.getDriver().findElements(By.xpath("//body//a"));
    	if (webElements != null && !webElements.isEmpty()) {
    		for (WebElement webElement : webElements) {
    			String path = webElement.getAttribute("href");
    			if (isUniqueElement(path)) {
	        		context.getNewPaths().add(path);
	        	}
    		}
    	}
    }
    
    private void getPage() {
    	context.getDriver().get(context.getUrl());
    }
    
    private void savePage() {
    	URL url;
		try {
			url = new URL(context.getUrl());
	    	String md = FlexmarkHtmlConverter.builder().build().convert(context.getDriver().getPageSource());
	    	File file = new File(STORAGE_PATH + url.getHost() + "/" + url.getPath() + "/index.txt");
			FileUtils.writeStringToFile(file, md, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private boolean isUniqueElement(String path) {
		try {
			if (path != null) {
				URL pathUrl = new URL(path);
				if (pathUrl != null) {
					return pathUrl.getHost() != null && pathUrl.getHost().equals(context.getHost())
						&& !context.getNewPaths().contains(path) && !context.getOldPaths().contains(path);
				}
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
			context.setUrl(getRandomUrl());
			context.getNewPaths().remove(context.getUrl());
    	} else {
    		stopRunning();
    	}
	}
	
	private String getRandomUrl() {
		return context.getNewPaths().get(rand.nextInt(context.getNewPaths().size()));
	}
	
	private void stopRunning() {
		context.setRunning(false);
	}
}
