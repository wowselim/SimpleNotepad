package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Writer {
	
	public static void write(String data, String directory) {
		Path path = Paths.get(directory);
		try {
			Files.write(path, data.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
