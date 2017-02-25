package util;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Icons {
	private static List<Image> images;

	public static synchronized List<Image> getIcons() {
		if (Objects.isNull(images)) {
			try {
				images = Arrays.asList(
					ImageIO.read(Icons.class.getResourceAsStream("icons/icon128.png")),
					ImageIO.read(Icons.class.getResourceAsStream("icons/icon64.png")),
					ImageIO.read(Icons.class.getResourceAsStream("icons/icon48.png")),
					ImageIO.read(Icons.class.getResourceAsStream("icons/icon32.png")),
					ImageIO.read(Icons.class.getResourceAsStream("icons/icon16.png"))
				);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return images;
	}
}
