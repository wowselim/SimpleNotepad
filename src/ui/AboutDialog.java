package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Icons;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	private final String EMAIL = "wowselim@gmail.com";
	private final String COMPANY = "\u00A9 Selim Dincer";
	private final String YEAR = "2015-2017";
	private final String INFO = "<html>This is a simple clone of the default Windows Notepad.<br>"
			+ "It features support for cross-platform line-endings.</html>";

	private final JLabel companyLabel = new JLabel(COMPANY + " - " + YEAR + " - " + EMAIL);
	private final JLabel infoLabel = new JLabel(INFO);
	private final JPanel buttonPanel = new JPanel();
	private final JButton closeButton = new JButton("Close");

	public AboutDialog(Window owner) {
		super(owner, "About SimpleNotepad", true);

		setLayout(new BorderLayout(5, 5));

		companyLabel.setIcon(new ImageIcon(Icons.getIcons().get(3)));
		companyLabel.setBorder(BorderFactory.createEmptyBorder(15, 16, 0, 8));
		add(companyLabel, BorderLayout.NORTH);
		add(infoLabel, BorderLayout.CENTER);
		infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(closeButton);
		add(buttonPanel, BorderLayout.SOUTH);
		closeButton.addActionListener((al) -> this.dispose());
		pack();
		setResizable(false);
		setLocationRelativeTo(owner);
	}
}
