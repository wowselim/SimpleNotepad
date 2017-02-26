package ui;

import java.io.File;

import javax.swing.JFileChooser;

import util.Reader;

@SuppressWarnings("serial")
public class FileOpenDialog extends JFileChooser {
	public FileOpenDialog(Window owner) {
		super(new File(System.getProperty("user.home")));
		this.addActionListener((ae) -> {
			File selectedFile = getSelectedFile();
			if (selectedFile != null) {
				owner.getTextArea().setText(Reader.readFile(selectedFile.getAbsolutePath()));
				owner.setCurrentFilePath(selectedFile.toPath().toAbsolutePath().toString());
				owner.setTitle(selectedFile.getName());
			}
		});
	}
}
