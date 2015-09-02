package ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import util.Writer;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	private JMenu file, edit, format, view, help;
	private JMenuItem newFile, open, save, saveAs, pageSetup, print, exit;
	private JMenuItem undo, cut, copy, paste, delete, find, findNext, replace, goTo, selectael, timeDate;
	private JMenuItem wordWrap, font;
	private JMenuItem statusBar;
	private JMenuItem about;

	private Window owner;

	public MenuBar(Window owner) {
		this.owner = owner;
		init();
	}

	private void init() {
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		newFile = new JMenuItem("New");
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		open = new JMenuItem("Open...");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAs = new JMenuItem("Save as...");
		pageSetup = new JMenuItem("Page Setup");
		print = new JMenuItem("Print");
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		exit = new JMenuItem("Exit");

		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		cut = new JMenuItem("Cut");
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		delete = new JMenuItem("Delete");
		// TODO: check for better option to replace "DELETE"
		delete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		find = new JMenuItem("Find...");
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		findNext = new JMenuItem("Find Next");
		findNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F3)));
		replace = new JMenuItem("Replace...");
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		goTo = new JMenuItem("Go To...");
		goTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		selectael = new JMenuItem("Select all");
		selectael.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		timeDate = new JMenuItem("Time/Date");
		timeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F5)));

		format = new JMenu("Format");
		format.setMnemonic(KeyEvent.VK_O);
		wordWrap = new JMenuItem("Word Wrap");
		font = new JMenuItem("Font...");

		view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		statusBar = new JMenuItem("Status Bar");

		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		about = new JMenuItem("About");

		file.add(newFile);
		file.add(open);
		open.addActionListener((ae) -> owner.getFileOpenDialog().showOpenDialog(owner));
		file.add(save);
		save.addActionListener((ae) -> {
			if (Objects.isNull(owner.getCurrentFilePath()))
				owner.getFileSaveDialog().showSaveDialog(owner);
			else
				Writer.write(owner.getTextArea().getText(), owner.getCurrentFilePath());
		});
		file.add(saveAs);
		saveAs.addActionListener((ae) -> owner.getFileSaveDialog().showSaveDialog(owner));
		file.addSeparator();
		file.add(pageSetup);
		file.add(print);
		file.addSeparator();
		file.add(exit);
		exit.addActionListener((ae) -> owner.dispose());

		edit.add(undo);
		edit.addSeparator();
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(delete);
		edit.addSeparator();
		edit.add(find);
		edit.add(findNext);
		edit.add(replace);
		edit.add(goTo);
		edit.addSeparator();
		edit.add(selectael);
		edit.add(timeDate);
		timeDate.addActionListener((ae) -> {
			JTextArea textArea = owner.getTextArea();
			int caretPos = textArea.getCaretPosition();
			LocalDateTime timePoint = LocalDateTime.now();
			String hour = String.format("%02d", timePoint.getHour());
			String minute = String.format("%02d", timePoint.getMinute());
			String day = String.format("%02d", timePoint.getDayOfMonth());
			String month = String.format("%02d", timePoint.getMonth().getValue());
			String year = String.valueOf(timePoint.getYear());
			String dateAsString = String.format("%s:%s %s.%s.%s", hour, minute, day, month, year);
			textArea.insert(dateAsString, caretPos);
		});

		format.add(wordWrap);
		wordWrap.addActionListener((ae) -> owner.getTextArea().setLineWrap(!owner.getTextArea().getLineWrap()));
		format.add(font);
		font.addActionListener((ae) -> {
			JFontChooser fc = owner.getFontChooser();
			if (fc.showDialog(owner) == JFontChooser.OK_OPTION)
				owner.getTextArea().setFont(fc.getSelectedFont());
		});

		view.add(statusBar);

		help.add(about);
		about.addActionListener((ae) -> new AboutDialog(owner).setVisible(true));

		add(file);
		add(edit);
		add(format);
		add(view);
		add(help);
	}
}
