package boot;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.Window;

public class Boot {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignore) {
		}
		final Window w = new Window();
		SwingUtilities.invokeLater(() -> {
			w.setVisible(true);
		});
	}
}
