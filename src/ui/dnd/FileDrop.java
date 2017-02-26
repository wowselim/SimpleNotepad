package ui.dnd;

import static java.awt.datatransfer.DataFlavor.stringFlavor;

import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.border.Border;

/**
 * <p>
 * I'm releasing this code into the Public Domain. Enjoy.
 * </p>
 * <p>
 * <em>Original author: Robert Harder, rharder@usa.net</em>
 * </p>
 * <p>
 * 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
 * </p>
 *
 * @author Robert Harder
 * @author rharder@users.sf.net
 * @version 1.0.1
 */
public class FileDrop {
	private transient DropTargetListener dropListener;

	/** Discover if the running JVM is modern enough to have drag and drop. */
	private static Boolean supportsDnD;

	/**
	 * Constructs a {@link FileDrop} with a default light-blue border and, if
	 * <var>c</var> is a {@link java.awt.Container}, recursively sets all
	 * elements contained within as drop targets, though only the top level
	 * container will change borders.
	 *
	 * @param c
	 *            Component on which files will be dropped.
	 * @param listener
	 *            Listens for <tt>filesDropped</tt>.
	 * @since 1.0
	 */
	public FileDrop(final Component c, final Listener listener) {
		this(c, javax.swing.BorderFactory.createEmptyBorder(), true, listener);
	}

	/**
	 * Full constructor with a specified border and debugging optionally turned
	 * on. With Debugging turned on, more status messages will be displayed to
	 * <tt>out</tt>. A common way to use this constructor is with
	 * <tt>System.out</tt> or <tt>System.err</tt>. A <tt>null</tt> value for the
	 * parameter <tt>out</tt> will result in no debugging output.
	 *
	 * @param out
	 *            PrintStream to record debugging info or null for no debugging.
	 * @param c
	 *            Component on which files will be dropped.
	 * @param dragBorder
	 *            Border to use on <tt>JComponent</tt> when dragging occurs.
	 * @param recursive
	 *            Recursively set children as drop targets.
	 * @param listener
	 *            Listens for <tt>filesDropped</tt>.
	 * @since 1.0
	 */
	public FileDrop(final Component c, final Border dragBorder, final boolean recursive, final Listener listener) {

		if (supportsDnD()) {
			dropListener = new java.awt.dnd.DropTargetListener() {
				public void dragEnter(java.awt.dnd.DropTargetDragEvent evt) {
					if (isDragOk(evt)) {
						evt.acceptDrag(DnDConstants.ACTION_COPY);
					} else {
						evt.rejectDrag();
					}
				}

				public void dragOver(DropTargetDragEvent evt) {
				}

				public void drop(DropTargetDropEvent evt) {
					try {
						Transferable tr = evt.getTransferable();
						if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
							evt.acceptDrop(DnDConstants.ACTION_COPY);
							@SuppressWarnings("unchecked")
							List<File> fileList = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
							File[] filesTemp = new File[fileList.size()];
							fileList.toArray(filesTemp);
							final File[] files = filesTemp;
							if (listener != null)
								listener.filesDropped(files);
							evt.getDropTargetContext().dropComplete(true);
						} else {
							DataFlavor[] flavors = tr.getTransferDataFlavors();
							boolean handled = false;
							for (int zz = 0; zz < flavors.length; zz++) {
								if (flavors[zz].isRepresentationClassReader()) {
									evt.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);

									Reader reader = flavors[zz].getReaderForText(tr);

									BufferedReader br = new BufferedReader(reader);

									if (listener != null)
										listener.filesDropped(createFileArray(br));
									evt.getDropTargetContext().dropComplete(true);
									handled = true;
									break;
								}
							}
							if (!handled) {
								evt.rejectDrop();
							}
						}
					} catch (IOException io) {
						evt.rejectDrop();
						throw new RuntimeException(io);
					} catch (UnsupportedFlavorException ufe) {
						evt.rejectDrop();
						throw new RuntimeException(ufe);
					}
				}

				public void dragExit(DropTargetEvent evt) {
				}

				public void dropActionChanged(DropTargetDragEvent evt) {
					if (isDragOk(evt)) {
						evt.acceptDrag(DnDConstants.ACTION_COPY);
					} else {
						evt.rejectDrag();
					}
				}
			};
			makeDropTarget(c, recursive);
		} else {
			throw new RuntimeException("Drag and Drop not supported.");
		}
	}

	private static boolean supportsDnD() {
		if (supportsDnD == null) {
			boolean support = false;
			try {
				@SuppressWarnings("unused")
				Class<?> arbitraryDndClass = Class.forName("java.awt.dnd.DnDConstants");
				support = true;
			} catch (Exception e) {
				support = false;
			}
			supportsDnD = new Boolean(support);
		}
		return supportsDnD.booleanValue();
	}

	private static String ZERO_CHAR_STRING = "" + (char) 0;

	private static File[] createFileArray(BufferedReader bReader) {
		try {
			List<File> list = new ArrayList<>();
			java.lang.String line = null;
			while ((line = bReader.readLine()) != null) {
				try {
					if (ZERO_CHAR_STRING.equals(line))
						continue;

					File file = new File(new URI(line));
					list.add(file);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}

			return (File[]) list.toArray(new File[list.size()]);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void makeDropTarget(final Component c, boolean recursive) {
		final DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(dropListener);
		} catch (TooManyListenersException e) {
			throw new RuntimeException(e);
		}
		c.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent evt) {
				Component parent = c.getParent();
				if (parent == null) {
					c.setDropTarget(null);
				} else {
					new DropTarget(c, dropListener);
				}
			}
		});
		if (c.getParent() != null)
			new java.awt.dnd.DropTarget(c, dropListener);

		if (recursive && (c instanceof Container)) {
			Container cont = (Container) c;
			Component[] comps = cont.getComponents();
			for (int i = 0; i < comps.length; i++)
				makeDropTarget(comps[i], recursive);
		}
	}

	/** Determine if the dragged data is a file list. */
	private boolean isDragOk(final DropTargetDragEvent evt) {
		boolean ok = false;
		DataFlavor[] flavors = evt.getCurrentDataFlavors();
		int i = 0;
		while (!ok && i < flavors.length) {
			final DataFlavor curFlavor = flavors[i];
			if (curFlavor.equals(DataFlavor.javaFileListFlavor) || curFlavor.isRepresentationClassReader()) {
				ok = true;
			}
			i++;
		}

		return ok;
	}

	/* ******** I N N E R I N T E R F A C E L I S T E N E R ******** */

	/**
	 * Implement this inner interface to listen for when files are dropped. For
	 * example your class declaration may begin like this: <code><pre>
	 *      public class MyClass implements FileDrop.Listener
	 *      ...
	 *      public void filesDropped( java.io.File[] files )
	 *      {
	 *          ...
	 *      }
	 *      ...
	 * </pre></code>
	 *
	 * @since 1.1
	 */
	public static interface Listener {

		/**
		 * This method is called when files have been successfully dropped.
		 *
		 * @param files
		 *            An array of <tt>File</tt>s that were dropped.
		 * @since 1.0
		 */
		public abstract void filesDropped(File[] files);

	}

	/* ******** I N N E R C L A S S ******** */

	/**
	 * This is the event that is passed to the
	 * {@link FileDropListener#filesDropped filesDropped(...)} method in your
	 * {@link FileDropListener} when files are dropped onto a registered drop
	 * target.
	 *
	 * <p>
	 * I'm releasing this code into the Public Domain. Enjoy.
	 * </p>
	 * 
	 * @author Robert Harder
	 * @author rob@iharder.net
	 * @version 1.2
	 */
	@SuppressWarnings("serial")
	public static class Event extends EventObject {

		private File[] files;

		/**
		 * Constructs an {@link Event} with the array of files that were dropped
		 * and the {@link FileDrop} that initiated the event.
		 *
		 * @param files
		 *            The array of files that were dropped
		 * @source The event source
		 * @since 1.1
		 */
		public Event(File[] files, Object source) {
			super(source);
			this.files = files;
		}

		/**
		 * Returns an array of files that were dropped on a registered drop
		 * target.
		 *
		 * @return array of files that were dropped
		 * @since 1.1
		 */
		public File[] getFiles() {
			return files;
		}

	}

	/* ******** I N N E R C L A S S ******** */

	/**
	 * At last an easy way to encapsulate your custom objects for dragging and
	 * dropping in your Java programs! When you need to create a
	 * {@link java.awt.datatransfer.Transferable} object, use this class to wrap
	 * your object. For example:
	 * 
	 * <pre>
	 * <code>
	 *      ...
	 *      MyCoolClass myObj = new MyCoolClass();
	 *      Transferable xfer = new TransferableObject( myObj );
	 *      ...
	 * </code>
	 * </pre>
	 * 
	 * Or if you need to know when the data was actually dropped, like when
	 * you're moving data out of a list, say, you can use the
	 * {@link TransferableObject.Fetcher} inner class to return your object Just
	 * in Time. For example:
	 * 
	 * <pre>
	 * <code>
	 *      ...
	 *      final MyCoolClass myObj = new MyCoolClass();
	 *
	 *      TransferableObject.Fetcher fetcher = new TransferableObject.Fetcher()
	 *      {   public Object getObject(){ return myObj; }
	 *      };
	 *
	 *      Transferable xfer = new TransferableObject( fetcher );
	 *      ...
	 * </code>
	 * </pre>
	 *
	 * The {@link java.awt.datatransfer.DataFlavor} associated with
	 * {@link TransferableObject} has the representation class
	 * <tt>net.iharder.dnd.TransferableObject.class</tt> and MIME type
	 * <tt>application/x-net.iharder.dnd.TransferableObject</tt>. This data
	 * flavor is accessible via the static {@link #DATA_FLAVOR} property.
	 *
	 *
	 * <p>
	 * I'm releasing this code into the Public Domain. Enjoy.
	 * </p>
	 * 
	 * @author Robert Harder
	 * @author rob@iharder.net
	 * @version 1.2
	 */
	public static class TransferableObject implements Transferable {
		/**
		 * The MIME type for {@link #DATA_FLAVOR} is
		 * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
		 *
		 * @since 1.1
		 */
		public final static String MIME_TYPE = "application/x-net.iharder.dnd.TransferableObject";

		/**
		 * The default {@link java.awt.datatransfer.DataFlavor} for
		 * {@link TransferableObject} has the representation class
		 * <tt>net.iharder.dnd.TransferableObject.class</tt> and the MIME type
		 * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
		 *
		 * @since 1.1
		 */
		public final static DataFlavor DATA_FLAVOR = new DataFlavor(FileDrop.TransferableObject.class, MIME_TYPE);

		private Fetcher fetcher;
		private Object data;

		private DataFlavor customFlavor;

		/**
		 * Creates a new {@link TransferableObject} that wraps <var>data</var>.
		 * Along with the {@link #DATA_FLAVOR} associated with this class, this
		 * creates a custom data flavor with a representation class determined
		 * from <code>data.getClass()</code> and the MIME type
		 * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
		 *
		 * @param data
		 *            The data to transfer
		 * @since 1.1
		 */
		public TransferableObject(Object data) {
			this.data = data;
			this.customFlavor = new DataFlavor(data.getClass(), MIME_TYPE);
		}

		/**
		 * Creates a new {@link TransferableObject} that will return the object
		 * that is returned by <var>fetcher</var>. No custom data flavor is set
		 * other than the default {@link #DATA_FLAVOR}.
		 *
		 * @see Fetcher
		 * @param fetcher
		 *            The {@link Fetcher} that will return the data object
		 * @since 1.1
		 */
		public TransferableObject(Fetcher fetcher) {
			this.fetcher = fetcher;
		}

		/**
		 * Creates a new {@link TransferableObject} that will return the object
		 * that is returned by <var>fetcher</var>. Along with the
		 * {@link #DATA_FLAVOR} associated with this class, this creates a
		 * custom data flavor with a representation class <var>dataClass</var>
		 * and the MIME type
		 * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
		 *
		 * @see Fetcher
		 * @param dataClass
		 *            The {@link java.lang.Class} to use in the custom data
		 *            flavor
		 * @param fetcher
		 *            The {@link Fetcher} that will return the data object
		 * @since 1.1
		 */
		public TransferableObject(Class<?> dataClass, Fetcher fetcher) {
			this.fetcher = fetcher;
			this.customFlavor = new DataFlavor(dataClass, MIME_TYPE);
		}

		/**
		 * Returns the custom {@link java.awt.datatransfer.DataFlavor}
		 * associated with the encapsulated object or <tt>null</tt> if the
		 * {@link Fetcher} constructor was used without passing a
		 * {@link java.lang.Class}.
		 *
		 * @return The custom data flavor for the encapsulated object
		 * @since 1.1
		 */
		public DataFlavor getCustomDataFlavor() {
			return customFlavor;
		}

		/* ******** T R A N S F E R A B L E M E T H O D S ******** */

		/**
		 * Returns a two- or three-element array containing first the custom
		 * data flavor, if one was created in the constructors, second the
		 * default {@link #DATA_FLAVOR} associated with
		 * {@link TransferableObject}, and third the
		 * {@link java.awt.datatransfer.DataFlavor.stringFlavor}.
		 *
		 * @return An array of supported data flavors
		 * @since 1.1
		 */
		public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors() {
			if (customFlavor != null)
				return new DataFlavor[] { customFlavor, DATA_FLAVOR, stringFlavor };
			else
				return new DataFlavor[] { DATA_FLAVOR, stringFlavor };
		}

		/**
		 * Returns the data encapsulated in this {@link TransferableObject}. If
		 * the {@link Fetcher} constructor was used, then this is when the
		 * {@link Fetcher#getObject getObject()} method will be called. If the
		 * requested data flavor is not supported, then the
		 * {@link Fetcher#getObject getObject()} method will not be called.
		 *
		 * @param flavor
		 *            The data flavor for the data to return
		 * @return The dropped data
		 * @since 1.1
		 */
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (flavor.equals(DATA_FLAVOR))
				return fetcher == null ? data : fetcher.getObject();
			if (flavor.equals(stringFlavor))
				return fetcher == null ? data.toString() : fetcher.getObject().toString();
			throw new UnsupportedFlavorException(flavor);
		}

		/**
		 * Returns <tt>true</tt> if <var>flavor</var> is one of the supported
		 * flavors. Flavors are supported using the <code>equals(...)</code>
		 * method.
		 *
		 * @param flavor
		 *            The data flavor to check
		 * @return Whether or not the flavor is supported
		 * @since 1.1
		 */
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			if (flavor.equals(DATA_FLAVOR))
				return true;
			if (flavor.equals(stringFlavor))
				return true;
			return false;
		}

		/* ******** I N N E R I N T E R F A C E F E T C H E R ******** */

		/**
		 * Instead of passing your data directly to the
		 * {@link TransferableObject} constructor, you may want to know exactly
		 * when your data was received in case you need to remove it from its
		 * source (or do anyting else to it). When the {@link #getTransferData
		 * getTransferData(...)} method is called on the
		 * {@link TransferableObject}, the {@link Fetcher}'s {@link #getObject
		 * getObject()} method will be called.
		 *
		 * @author Robert Harder
		 * @copyright 2001
		 * @version 1.1
		 * @since 1.1
		 */
		public static interface Fetcher {
			/**
			 * Return the object being encapsulated in the
			 * {@link TransferableObject}.
			 *
			 * @return The dropped object
			 * @since 1.1
			 */
			public abstract Object getObject();
		}

	}

}
