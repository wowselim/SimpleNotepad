package ui;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import say.swing.JFontChooser;

public class Window extends JFrame {
	private MenuBar menu;
	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	private JFontChooser fontChooser;
	
	public Window() {
		super("Untitled - SimpleNotepad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		init();
	}
	
	private void init() {
		menu = new MenuBar();
		textArea = new JTextArea();
		textAreaScrollPane = new JScrollPane(textArea);
		fontChooser = new JFontChooser();
		
		setJMenuBar(menu);
		add(textAreaScrollPane, BorderLayout.CENTER);
		add(new JEditorPane(), BorderLayout.SOUTH);
		add(new javax.swing.JTextPane(), BorderLayout.NORTH);
	}
}