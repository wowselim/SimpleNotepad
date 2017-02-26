package ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

import ui.dnd.FileDrop;
import util.Icons;
import util.Reader;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private MenuBar menu;
	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	private JFontChooser fontChooser;
	private FileOpenDialog fileOpenDialog;
	private FileSaveDialog fileSaveDialog;
	private String currentFilePath;
	private UndoManager um;

	public Window() {
		super("Untitled - SimpleNotepad");
		List<Image> icons = null;
		icons = Icons.getIcons();
		setIconImages(icons);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		menu = new MenuBar(this);
		textArea = new JTextArea();
		um = new UndoManager();
		textArea.getDocument().addUndoableEditListener(um);
		new FileDrop(textArea, files -> {
			File f = files[0];
			textArea.setText(Reader.readFile(f.getAbsolutePath()));
			setCurrentFilePath(f.getAbsolutePath());
			setTitle(f.getName());
		});
		textAreaScrollPane = new JScrollPane(textArea);
		fontChooser = new JFontChooser();
		fileOpenDialog = new FileOpenDialog(this);
		fileSaveDialog = new FileSaveDialog(this);

		setJMenuBar(menu);
		add(textAreaScrollPane, BorderLayout.CENTER);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public FileOpenDialog getFileOpenDialog() {
		return fileOpenDialog;
	}

	public FileSaveDialog getFileSaveDialog() {
		return fileSaveDialog;
	}

	public JFontChooser getFontChooser() {
		return fontChooser;
	}

	public UndoManager getUndoManager() {
		return um;
	}

	public void setTitle(String title) {
		super.setTitle(title + " - SimpleNotepad");
	}

	public String getCurrentFilePath() {
		return currentFilePath;
	}

	public void setCurrentFilePath(String currentFilePath) {
		this.currentFilePath = currentFilePath;
	}
}
