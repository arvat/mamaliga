package ketroy.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileService {

	public static List<String> findFileByName(String filename) {
		Collection<File> files = FileUtils.listFiles(new File("./"), TrueFileFilter.TRUE, TrueFileFilter.TRUE);
		File file = files.stream().filter(f -> f.isFile() && f.getName().equals(filename)).findFirst().orElse(null);
		return readLines(file);
	}
	
	public static List<String> readLines(File file) {
		try {
			return FileUtils.readLines(file, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
