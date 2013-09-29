package mivc.UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


@SuppressWarnings("serial")
public class MainView extends JFrame implements StudyView {

	private JPanel toolbar;
	private JPanel imageView;
	private JPanel singleView;
	private JPanel quadView;
	private boolean viewingSingle = true;
	private StudyList studyList = new StudyList();
	
	/**
	 * The main view to be used for displaying MIVC data
	 */
	public MainView() {
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
		setTitle("MIVC");
		
		setVisible(true);
	}
	
	/**
	 * Creates the components used in the GUI
	 */
	private void initializeComponents() {
		toolbar = new Toolbar();
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
		imageView.add(quadView, "QV");
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
		} else {
			cl.show(imageView, "SV");
		}
		viewingSingle = !viewingSingle;
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#addViewListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addViewListener(ActionListener al) {
		((Toolbar)toolbar).addViewListener(al);
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#addOpenListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addOpenListener(ActionListener al) {
		((Toolbar)toolbar).addOpenListener(al);
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
	
	@Override
	public void addStudySelectionListener(ActionListener al) {
		studyList.addSelectionListener(al);
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#setImages(java.awt.image.BufferedImage[])
	 */
	@Override
	public void setImages(BufferedImage... images) {
		System.out.println("---Setting image---");
		JLabel label = new JLabel(new ImageIcon(images[0]));
		this.singleView.add(label);
		this.pack();
	}
	
	/**
	 * (non-Javadoc)
	 * @see mivc.UI.StudyView#showList(java.lang.String[])
	 */
	@Override
	public void showList(String[] studies) {
		studyList.updateList(studies);
		studyList.setVisible(true);
	}
	
	@Override
	public String getSelectedStudy() {
		return studyList.getSelectedStudy();
	}

	@Override
	public boolean isDefaultSelected() {
		return studyList.isDefaultSelected();
	}
	
	
	/**
	 * Creates the appropriate objecst and shows the GUI
	 */
	public static void createAndShowGUI() {
		JFrame view = new MainView();
		
		// Create and set the ProxyController
		new ProxyController((StudyView)view);
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
            public void run() {
                createAndShowGUI();
            }
        });
	}


}
