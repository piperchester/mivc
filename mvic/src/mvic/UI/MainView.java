package mvic.UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import mvic.System.StudyList;

@SuppressWarnings("serial")
public class MainView extends JFrame implements StudyView {

	private JPanel toolbar;
	private JPanel imageView;
	private JPanel singleView;
	private JPanel quadView;
	private boolean viewingSingle = true;
	
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
	
	private void initializeComponents() {
		toolbar = new Toolbar();
		imageView = new JPanel(new CardLayout());
		singleView = new SingleView();
		quadView = new QuadView();
	}
	
	private void layoutComponents() {
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(imageView, BorderLayout.CENTER);
		imageView.add(singleView, "SV");
		imageView.add(quadView, "QV");
	}
	
	public void toggleView() {
		CardLayout cl = (CardLayout)imageView.getLayout();
		if (viewingSingle) {
			cl.show(imageView, "QV");
		} else {
			cl.show(imageView, "SV");
		}
		viewingSingle = !viewingSingle;
	}
	
	public void addViewListener(ActionListener al) {
		((Toolbar)toolbar).addViewListener(al);
	}
	
	@Override
	public void addOpenListener(ActionListener al) {
		((Toolbar)toolbar).addOpenListener(al);
	}

	@Override
	public void addSaveStudyListener(ActionListener al) {
		((Toolbar)toolbar).addSaveStudyListener(al);
	}

	@Override
	public void addSaveViewListener(ActionListener al) {
		((Toolbar)toolbar).addSaveViewListener(al);
	}

	@Override
	public void addPrevListener(ActionListener al) {
		((Toolbar)toolbar).addPrevListener(al);
	}

	@Override
	public void addNextListener(ActionListener al) {
		((Toolbar)toolbar).addNextListener(al);
	}
	
	@Override
	public void setImages(BufferedImage... images) {
		// Call to the views to show the first on the SingleView
		// and show four on the QuadView
	}
	
	@Override
	public void showList(String[] studies) {
		new StudyList(studies);
	}
	
	public static void createAndShowGUI() {
		JFrame view = new MainView();
		
		// Create and set the ProxyController
		new ProxyController((StudyView)view);
	}
	
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
