import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class containing GUI: board + buttons
 */
public class GUI extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private final int length = 1024;
	private final int height = 768;
	private int panelHeight;
	private Timer timer;
	private Board board;
	private JButton start;
	private JButton switcher;
	private JButton clear;
	private JSlider pred;
	private JFrame frame;
	private int iterNum = 0;
	private final int maxDelay = 500;
	private final int initDelay = 100;
	private boolean running = false;
	private boolean rain = true;
	private Container container;

	public GUI(JFrame jf) {
		frame = jf;
		timer = new Timer(initDelay, this);
		timer.stop();
	}

	/**
	 * @param container to which GUI and board is added
	 */
	public void initialize(Container container) {
		this.container = container;
		container.setLayout(new BorderLayout());
		container.setSize(new Dimension(1024, 768));

		JPanel buttonPanel = new JPanel();

		start = new JButton("Start");
		start.setActionCommand("Start");
		start.setToolTipText("Starts clock");
		start.addActionListener(this);

		switcher = new JButton("Game Of Life");
		switcher.setActionCommand("Switch");
		switcher.setToolTipText("Switches between modes Rain and Game Of Life");
		switcher.addActionListener(this);

		clear = new JButton("Clear");
		clear.setActionCommand("clear");
		clear.setToolTipText("Clears the board");
		clear.addActionListener(this);

		pred = new JSlider();
		pred.setMinimum(0);
		pred.setMaximum(maxDelay);
		pred.setToolTipText("Time speed");
		pred.addChangeListener(this);
		pred.setValue(maxDelay - timer.getDelay());

		buttonPanel.add(start);
		buttonPanel.add(switcher);
		buttonPanel.add(clear);
		buttonPanel.add(pred);

		this.panelHeight = buttonPanel.getHeight();

		board = new Board(1024, 768 - panelHeight, rain);
		container.add(board, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * handles clicking on each button
	 * @see ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(timer)) {
			iterNum++;
			if (rain) {
				frame.setTitle("Rain simulation (" + Integer.toString(iterNum) + " iteration)");
			}
			else {
				frame.setTitle("Game of life (" + Integer.toString(iterNum) + " iteration)");
			}
			board.iteration();
		} else {
			String command = e.getActionCommand();
			if (command.equals("Start")) {
				if (!running) {
					timer.start();
					start.setText("Pause");
				} else {
					timer.stop();
					start.setText("Start");
				}
				running = !running;
				clear.setEnabled(true);

			}
			else if (command.equals("clear")) {
				iterNum = 0;
				timer.stop();
				start.setEnabled(true);
				board.clear();
				frame.setTitle("Cellular Automata Toolbox");
				running = false;
			}
			else if (command.equals("Switch")) {
				if (running) {
					start.setText("Start");
				}
				iterNum = 0;
				timer.stop();
				start.setEnabled(true);
				board.clear();
				running = false;
//				frame.setTitle("Cellular Automata Toolbox");
				if (rain) {
					board.setMode(false);
					board.initialize(length, height);
					switcher.setText("Rain");
				}
				else {
					board.setMode(true);
					board.initialize(length, height);
					switcher.setText("Game Of Life");
				}
				rain = !rain;
			}

		}
	}

	/**
	 * slider to control simulation speed
	 * @see ChangeListener#stateChanged(ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		timer.setDelay(maxDelay - pred.getValue());
	}
}
