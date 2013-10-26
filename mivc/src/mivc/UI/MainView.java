package mivc.UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import mivc.System.CommandHandler;
import mivc.System.IStudyImage;
import mivc.System.SelectStudyCommand;
import mivc.System.StartUpCommand;
import mivc.System.Study;


@SuppressWarnings("serial")
public class MainView extends JFrame implements StudyView, ActionListener {

	private JPanel toolbar;
	private JPanel imageView;
	private JPanel singleView;
	private JPanel quadView;
	private ViewType currentView;
	private boolean viewingSingle = true;
	private StudyList studyList = new StudyList();
	private CommandHandler invoker;
	private Map<String, Study> studies;
	private Study currentStudy;
	private int imageInterval = 0;
	private int singleViewIndex = 0;
	private IStudyImage[] currentImages = new IStudyImage[4];
	
	/**
	 * The main view to be used for displaying MIVC data
	 */
	public MainView(CommandHandler invoker) {
		this.invoker = invoker;
		setLayout(new BorderLayout());
		initializeComponents();
		layoutComponents();
		
		// Setup JFrame deets.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // Pack before setting location (this determines size)
		
		// Get the current screen's size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// Compute and set the location so the frame is centered
		int x = screen.width/2-getSize().width/2;
		int y = screen.height/2-getSize().height/2;
		setLocation(x, y);
		setTitle("Medical Image Viewing Console");
		
		// Add undo and redo key commands
		addUndoRedo();
		
		studyList.btnOpen.addActionListener(this);
		invoker.addCommand(new StartUpCommand(this));
		setVisible(true);
	}
	
	/**
	 * Creates the components used in the GUI
	 */
	private void initializeComponents() {
		toolbar = new Toolbar(this, invoker);
		imageView = new JPanel(new CardLayout());
		singleView = new SingleView();
		quadView = new QuadView();
	}
	
	/**
	 * Lays the components out on the GUI
	 */
	private void layoutComponents() {
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(imageView, BorderLayout.CENTER);
		imageView.add(singleView, "SV");
		currentView = ViewType.SINGLE_VIEW;
		imageView.add(quadView, "QV");
	}
	
	private void addUndoRedo() {
		// Apply the Undo and Redo keystrokes
		KeyStroke undoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, 
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke redoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, 
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		Action undoRedoAction = new AbstractAction("UNDOREDO") {
			@Override
			public void actionPerformed(ActionEvent e) {	
				System.out.println(e.getActionCommand());
				switch (e.getActionCommand().charAt(0)-0) {
				case 26: 
				case 'z':
					invoker.undo();
					break;
				case 25:
				case 'y':
					invoker.redo();
					break;
				default:
					// Do nothing
				}
			}
		};
		// UNDO
		singleView.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				undoStroke, Action.NAME);
		singleView.getInputMap(JComponent.WHEN_FOCUSED).put(undoStroke, Action.NAME);
		singleView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				undoStroke, Action.NAME);
		quadView.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				undoStroke, Action.NAME);
		quadView.getInputMap(JComponent.WHEN_FOCUSED).put(undoStroke, Action.NAME);
		quadView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				undoStroke, Action.NAME);
		toolbar.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				undoStroke, Action.NAME);
		toolbar.getInputMap(JComponent.WHEN_FOCUSED).put(undoStroke, Action.NAME);
		toolbar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				undoStroke, Action.NAME);
		
		// REDO
		singleView.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				redoStroke, Action.NAME);
		singleView.getInputMap(JComponent.WHEN_FOCUSED).put(redoStroke, Action.NAME);
		singleView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				redoStroke, Action.NAME);
		quadView.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				redoStroke, Action.NAME);
		quadView.getInputMap(JComponent.WHEN_FOCUSED).put(redoStroke, Action.NAME);
		quadView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				redoStroke, Action.NAME);
		toolbar.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				redoStroke, Action.NAME);
		toolbar.getInputMap(JComponent.WHEN_FOCUSED).put(redoStroke, Action.NAME);
		toolbar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				redoStroke, Action.NAME);
		
		// Apply to the actionMap
		singleView.getActionMap().put(Action.NAME, undoRedoAction);
		quadView.getActionMap().put(Action.NAME, undoRedoAction);
		toolbar.getActionMap().put(Action.NAME, undoRedoAction);
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#toggleView()
	 */
	@Override
	public void toggleView() {
		CardLayout cl = (CardLayout)imageView.getLayout();
		if (viewingSingle) {
			cl.show(imageView, "QV");
			currentView = ViewType.QUAD_VIEW;
		} else {
			cl.show(imageView, "SV");
			currentView = ViewType.SINGLE_VIEW;
		}
		viewingSingle = !viewingSingle;
		updateViewImages();
	}

	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#addSaveStudyListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addSaveStudyListener(ActionListener al) {
		((Toolbar)toolbar).addSaveStudyListener(al);
	}

	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#addSaveViewListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addSaveViewListener(ActionListener al) {
		((Toolbar)toolbar).addSaveViewListener(al);
	}

	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#addPrevListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addPrevListener(ActionListener al) {
		((Toolbar)toolbar).addPrevListener(al);
	}

	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#addNextListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addNextListener(ActionListener al) {
		((Toolbar)toolbar).addNextListener(al);
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#setImages(mivc.System.IStudyImage[])
	 */
	@Override
	public void setImages(IStudyImage... images) {
		((SingleView)this.singleView).setImages(images);
		((QuadView)this.quadView).setImages(images);
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#updateStudies(java.util.List)
	 */
	@Override
	public void updateStudies(List<Study> studies) {
		if (this.studies == null) {
			this.studies = new HashMap<String, Study>();
		}
		for (Study s : studies) {
			this.studies.put(s.getName(), s);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#showList(java.util.List)
	 */
	@Override
	public void showList() {
		String[] names = new String[studies.size()];
		int i = 0;
		for (Map.Entry<String, Study> e : studies.entrySet()) {
			names[i++] = e.getKey();
		}
		studyList.updateList(names);
		studyList.setVisible(true);
	}
	
	/**
	 * Update the status bar with a message
	 * @param value the message to show on the status bar
	 */
	private void updateStatusBar(String value) {
		((Toolbar)toolbar).setStatus(value);
	}

	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#getInput(java.lang.String)
	 */
	@Override
	public String getInput(String prompt) {
		return JOptionPane.showInputDialog(this, prompt, JOptionPane.OK_OPTION);
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#getWarningConfirmation(java.lang.String)
	 */
	@Override
	public boolean getWarningConfirmation(String prompt) {
		boolean retVal = false;
		int dialogResult = JOptionPane.showConfirmDialog (null, 
				prompt,
				"Warning",
				JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION) {
			retVal = true;
		}
		return retVal;
	}
	
	
	/**
	 * Creates the appropriate objecst and shows the GUI
	 */
	public static void createAndShowGUI() {
		new MainView(new CommandHandler());
	}
	
	/**
	 * Driver method, ensures the GUI is running on the event thread
	 */
	public static void main(String[] args) {
        // Set the Nimbus look and feel because it's new and cool looking
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Will be set to default LAF
        }
        
		if (!System.getProperty("java.version").startsWith("1.7")) {
			JOptionPane.showMessageDialog(null, "Please download Java version 7", "Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                createAndShowGUI();
            }
        });
	}

	public void setCurrentStudy(String name) {
		this.currentStudy = studies.get(name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == studyList.btnOpen) {
			invoker.addCommand(new SelectStudyCommand(this, 
					studyList.getSelectedStudy(), 
					studyList.isDefaultSelected()));
		}
	}

	@Override
	public void setImageIndexing(int interval, int index) {
		this.imageInterval = interval;
		this.singleViewIndex = index;
		updateViewImages();
	}
	
	/**
	 * Updates the status bar on the view. Should be called any time the 
	 * view, study or images are changed.
	 */
	private void updateViewStatus() {
		// Get current view (send either one or four images)
		int totalImages = currentStudy.getImageCount();
		String status = "";
		
		if (currentView == ViewType.SINGLE_VIEW) {
			int imgIndex = imageInterval*4 + singleViewIndex;
			// Update the status
			status = "Viewing " + currentStudy.getName() + " image " + 
					(imgIndex + 1) + " of " + totalImages;
		} else if (currentView == ViewType.QUAD_VIEW) {
			// Update the status
			int lastImage = 0;
			if (totalImages - imageInterval*4 <= 4) {
				lastImage = totalImages;
			} else {
				lastImage = imageInterval*4 + 4;
			}
			status = "Viewing " + currentStudy.getName() + 
					" images (" + (imageInterval*4 + 1) + " - " + lastImage + 
					") of " + currentStudy.getImageCount();
		}

		// update the status bar
		updateStatusBar(status);
	}
	
	/**
	 * Updates the images on the GUI, should be called any time imageInterval
	 * or singleViewIndex are changed.
	 * @param forceUpdate should the update of images be forced
	 * @precondition imageInterval and singleViewIndex must be set previously
	 */
	private void updateViewImages() {
		
		// This used to be modified to only run on specific occasions.
		// TODO see if we can revert back to old code when this was in Controller.
		int totalImages = currentStudy.getImageCount();
		for (int i = 0; i < 4; i++) {
			int imgIndex = imageInterval*4 + i;
			if (imgIndex >= totalImages) {
				currentImages[i] = null;
			} else {
				currentImages[i] = currentStudy.getImage(imgIndex);
			}
		}
		
		
		if (currentView == ViewType.SINGLE_VIEW) {
			// Update the image(s)
			setImages(currentImages[singleViewIndex]);
		} else if (currentView == ViewType.QUAD_VIEW) {
			// Update the image(s)
			setImages(currentImages);
		}

		updateViewStatus();
	}

	@Override
	public ViewType getCurrentView() {
		return currentView;
	}

	@Override
	public int getImageInterval() {
		return imageInterval;
	}

	@Override
	public int getSingleViewIndex() {
		return singleViewIndex;
	}

	@Override
	public Study getCurrentStudy() {
		return currentStudy;
	}


}
