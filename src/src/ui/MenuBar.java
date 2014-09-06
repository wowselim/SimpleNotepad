package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {
	private JMenu file, edit, format, view, help;
	private JMenuItem newFile, open, save, saveAs, pageSetup, print, exit;
	private JMenuItem undo, cut, copy, paste, delete, find, findNext, replace, goTo, selectAll, timeDate;
	private JMenuItem wordWrap, font;
	private JMenuItem statusBar;
	private JMenuItem about;
	
	public MenuBar() {
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
		selectAll = new JMenuItem("Select All");
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		timeDate = new JMenuItem("Time/Date");
		timeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F5)));
		
		format = new JMenu("Format");
		format.setMnemonic(KeyEvent.VK_F);
		wordWrap = new JMenuItem("Word Wrap");
		font = new JMenuItem("Font...");
		
		view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		statusBar = new JMenuItem("Status Bar");
		
		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		about = new JMenuItem("About");
		
		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		format = new JMenu("Format");
		format.setMnemonic(KeyEvent.VK_F);
		view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		
		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(pageSetup);
		file.add(print);
		file.addSeparator();
		file.add(exit);
		
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
		edit.add(selectAll);
		edit.add(timeDate);
		
		format.add(wordWrap);
		format.add(font);
		
		view.add(statusBar);
		
		help.add(about);
		
		add(file);
		add(edit);
		add(format);
		add(view);
		add(help);
	}
}
