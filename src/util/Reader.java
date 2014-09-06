package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Reader {
	public static String readFile(String directory) {
		Path path = Paths.get(directory);
		StringBuilder content = new StringBuilder();
		try {
			List<String> lines = Files.readAllLines(path);
			for(String line : lines)
				content.append(line + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int length = content.toString().length();
		return content.toString().substring(0, length - 1);
	}
	
	public static String readUltraFast(String directory) {
		FileChannel inChannel = null;
		MappedByteBuffer buffer = null;
		try {
			inChannel = new RandomAccessFile(directory, "r").getChannel();
			buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(Objects.isNull(inChannel) || Objects.isNull(buffer)) {
			System.err.println("Could not find file.");
			return "";
		}
		
		StringBuilder content = new StringBuilder();
		buffer.load();
		while(buffer.hasRemaining()) {
			content.append((char) buffer.get());
		}
		try {
			buffer.clear();
			inChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
}
