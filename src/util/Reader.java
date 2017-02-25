package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Reader {
	public static String readFile(String directory) {
		Path path = Paths.get(directory);
		String fileContent = "";
		try {
			if (Files.size(path) > Integer.MAX_VALUE)
				throw new RuntimeException("File is too large.");

			fileContent = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileContent;
	}

}
