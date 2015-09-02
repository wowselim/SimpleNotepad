package ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	private final String email = "wowselim@gmail.com";
	private final String COMPANY = "\u00A9 Selim Dincer";
	private final int YEAR = 2015;
	private final String INFO = "This is a simple clone of the default Windows Notepad.\n"
			+ "It features support for cross-platform line-endings.";

	private final JLabel companyLabel = new JLabel(COMPANY + " - " + YEAR + " - " + email);
	private final JTextArea infoPane = new JTextArea(INFO);
	private final JPanel buttonPanel = new JPanel();
	private final JButton closeButton = new JButton("Close");

	public AboutDialog(Window owner) {
		super(owner, "About SimpleNotepad", true);

		// I don't know why this works:
		setLayout(new GridLayout(0, 1));

		companyLabel.setIcon(new ImageIcon(Base64Icons.getIcon(Base64Icons.icon32)));
		companyLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		add(companyLabel);
		add(infoPane);
		infoPane.setEditable(false);
		infoPane.setBackground(this.getBackground());
		infoPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(closeButton);
		add(buttonPanel);
		closeButton.addActionListener((al) -> this.dispose());
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(owner);
	}
}
