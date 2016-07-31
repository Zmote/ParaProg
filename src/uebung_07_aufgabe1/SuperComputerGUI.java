package uebung_07_aufgabe1;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SuperComputerGUI extends JPanel implements Observer{
	private static final long serialVersionUID = 4998293627753886206L;
	private JLabel statusLabel;
	private JLabel resultLabel;
	private final TheSupercomputer theSupercomputer;

	public SuperComputerGUI(TheSupercomputer theSupercomputer) {
		this.theSupercomputer = theSupercomputer;
		theSupercomputer.addObserver(this);
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		addImg();
		addLabel("Confused? google the result.");
		addStartButton();
		statusLabel = addLabel("Status: ?");
		resultLabel = addLabel("Result: ?");
		add(Box.createRigidArea(new Dimension(0, 5)));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		statusLabel.setText(((TheSupercomputer)arg0).getStatus());
	}

	private void addStartButton() {
		add(Box.createRigidArea(new Dimension(0, 5)));
		JButton startButton = new JButton("Start");
		startButton.setToolTipText("Starts the calculation to find the answer to the Ultimate Question of Life, the Universe, and Everything");
		startButton.addActionListener(event -> {
			new Thread(()->{
				String result = theSupercomputer.calculateUltimateAnswerToTheUltimateQuestionOfLifeTheUniverseAndEverything();
				SwingUtilities.invokeLater(()->{
					resultLabel.setText("Result: " + result);
				});
			}).start();
		});
		add(startButton);
	}

	private JLabel addLabel(String text) {
		add(Box.createRigidArea(new Dimension(0, 5)));
		JLabel label = new JLabel(text);
		add(label);
		return label;
	}

	private void addImg() {
		java.net.URL imgURL = getClass().getResource("supercomputer.jpg");
		if (imgURL != null) {
			add(new JLabel(new ImageIcon(imgURL)));
		}
	}

	private static void createAndShowGUI(TheSupercomputer theSupercomputer) {
		JFrame frame = new JFrame("The Supercomputer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SuperComputerGUI contentPane = new SuperComputerGUI(theSupercomputer);
		frame.setContentPane(contentPane);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		TheSupercomputer theSupercomputer = new TheSupercomputer();
		SwingUtilities.invokeLater(() -> createAndShowGUI(theSupercomputer));
	}
}