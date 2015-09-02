package ui;

import java.io.File;

import javax.swing.JFileChooser;

import util.Writer;

@SuppressWarnings("serial")
public class FileSaveDialog extends JFileChooser {
	@SuppressWarnings("unused")
	private Window owner;

	public FileSaveDialog(Window owner) {
		super(new File("."));
		this.owner = owner;
		this.addActionListener((al) -> {
			File selectedFile = getSelectedFile();
			Writer.write(owner.getTextArea().getText(), selectedFile.getAbsolutePath());
			owner.setTitle(selectedFile.getName());
		});
	}
}