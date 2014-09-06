package boot;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.Window;

public class Boot {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Could not set look and feel.");
		}
		final Window w = new Window();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				w.setVisible(true);
			}
		});
	}
}
