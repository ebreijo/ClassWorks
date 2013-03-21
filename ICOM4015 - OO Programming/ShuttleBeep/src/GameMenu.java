import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/**
 * The GameOptions class creates a panel with all options of the game.
 * The game mode, name of players, level of game, marked mode, grid size, 
 * number of shuttles and place boat randomly or not.
 * @author David & Eduardo
 */
public class GameMenu implements ActionListener{


	private JFrame optionPanel; // The frame that present the game menu
	private Color backgroundColor = new Color(25,150,250);  // The color for the background of the frame
	private JRadioButton humVsHum; // The Human Versus Human button
	private JRadioButton humVsRof; // The Human Versus Rofongo button
	private JTextField player1; // The text field for entry player 1 name
	private JTextField player2; // The text field for entry player 2 name
	private JRadioButton level1; // Tick on check box to select level 1 game mode
	private JRadioButton level2; // Tick on check box to select on level 2 game mode
	private JRadioButton markedOn; // Tick to select marked mode on
	private JRadioButton markedOff; // Tick to select marked mode off
	private JRadioButton gridSize1; // The 10x10 grid size button
	private JRadioButton gridSize2; // The 15x15 grid size button
	private JRadioButton gridSize3; // The 20x20 grid size button
	private JRadioButton numbShuttles5; // Tick on radio button to select the number of shuttles, in this case, 5 shuttles
	private JRadioButton numbShuttles6; // Tick on radio button to select the number of shuttles, in this case, 6 shuttles
	private JRadioButton numbShuttles7; // Tick on radio button to select the number of shuttles, in this case, 7 shuttles
	private JRadioButton numbShuttles8; // Tick on radio button to select the number of shuttles, in this case, 8 shuttles
	private JButton randomButton; // The random button that place shuttles randomly
	private JButton manualButton; // The manual button that place shuttles manually
	private JButton startButton;  // The start button to begin the game
	private String playerName1; // To save the player 1 name 
	private String playerName2; // To save the player 2 name
	private static int size;  // To save the grid size of each ocean
	private int numberOfShuttles; // To save the number or amount of ships in the game
	private boolean type; // Automated or manual ships
	private boolean rofongo; // Second player as rofongo or human
	private boolean level; // Determine the level of the game
	private boolean marked; // Determine marked mode


	/**
	 * Set default values for each option of the game
	 */
	public GameMenu() {
		size = 10;
		numberOfShuttles = 8;
		marked = true;
		level = false;
		rofongo = true;
		type = true;
	}

	/**
	 * This method add all labels and buttons to a container
	 * @param pane a pane that has all labels and buttons
	 */
	public void addComponents(Container pane) {

		pane.setLayout(null);
		int widthPanel = optionPanel.getWidth();
		int heightPanel = optionPanel.getHeight();

		JLabel label1 = new JLabel("Game Mode:");
		pane.add(label1);
		Dimension size = label1.getPreferredSize();
		label1.setBounds(widthPanel/4, heightPanel/80, size.width, size.height);

		humVsHum = new JRadioButton("Human Vs Human");
		pane.add(humVsHum);
		size = humVsHum.getPreferredSize();
		humVsHum.setBounds(widthPanel/3, heightPanel/16, size.width, size.height);
		humVsHum.setBackground(backgroundColor);
		humVsHum.addActionListener(this);


		humVsRof = new JRadioButton("Human Vs Rofongo");
		pane.add(humVsRof);
		size = humVsRof.getPreferredSize();
		humVsRof.setBounds(widthPanel/3, heightPanel/8, size.width, size.height);
		humVsRof.setBackground(backgroundColor);
		humVsRof.addActionListener(this);

		/**
		 * Add humVsHum and humVsRof buttons into a first group of buttons
		 */
		ButtonGroup group1 = new ButtonGroup();
		group1.add(humVsHum);
		group1.add(humVsRof);


		JLabel label2 = new JLabel("Enter Player(s) Name:");
		pane.add(label2);
		size = label2.getPreferredSize();
		label2.setBounds(widthPanel/4, (int) (heightPanel/5.3), size.width, size.height);

		JLabel label3 = new JLabel("Player 1 Name");
		pane.add(label3);
		size = label3.getPreferredSize();
		label3.setBounds(widthPanel/3, (int) (heightPanel/4), size.width, size.height);

		player1 = new JTextField(7);
		pane.add(player1);
		size = player1.getPreferredSize();
		player1.setBounds(widthPanel/3, (int) (heightPanel/3.2), size.width, size.height);
		player1.addActionListener(this);

		JLabel label4 = new JLabel("Player 2 Name");
		pane.add(label4);
		size = label4.getPreferredSize();
		label4.setBounds((int) (widthPanel/1.7), (int) (heightPanel/4), size.width, size.height);

		player2 = new JTextField(7);
		pane.add(player2);
		size = player2.getPreferredSize();
		player2.setBounds((int) (widthPanel/1.7), (int) (heightPanel/3.2), size.width, size.height);
		player2.addActionListener(this);

		JLabel label5 = new JLabel("Select Level:");
		pane.add(label5);
		size = label5.getPreferredSize();
		label5.setBounds(widthPanel/4, (int) (heightPanel/2.7), size.width, size.height);


		level1 = new JRadioButton("Level 1");
		pane.add(level1);
		size = level1.getPreferredSize();
		level1.setBounds(widthPanel/3, (int) (heightPanel/2.4), size.width, size.height);
		level1.setBackground(backgroundColor);
		level1.addActionListener(this);

		level2 = new JRadioButton("Level 2");
		pane.add(level2);
		size = level2.getPreferredSize();
		level2.setBounds((int) (widthPanel/1.9), (int) (heightPanel/2.4), size.width, size.height);
		level2.setBackground(backgroundColor);
		level2.addActionListener(this);

		/**
		 * Add level1 and level2 buttons into a second group of buttons
		 */
		ButtonGroup group2 = new ButtonGroup();
		group2.add(level1);
		group2.add(level2);

		JLabel label6 = new JLabel("Marked Mode:");
		pane.add(label6);
		size = label6.getPreferredSize();
		label6.setBounds(widthPanel/4, (int) (heightPanel/2.1), size.width, size.height);

		markedOn = new JRadioButton("On");
		pane.add(markedOn);
		size = markedOn.getPreferredSize();
		markedOn.setBounds(widthPanel/3, (int) (heightPanel/1.95), size.width, size.height);
		markedOn.setBackground(backgroundColor);
		markedOn.addActionListener(this);

		markedOff = new JRadioButton("Off");
		pane.add(markedOff);
		size = markedOff.getPreferredSize();
		markedOff.setBounds((int) (widthPanel/1.9), (int) (heightPanel/1.95), size.width, size.height);
		markedOff.setBackground(backgroundColor);
		markedOff.addActionListener(this);

		/**
		 * Add markedOn and markedOff buttons into a third group of buttons
		 */
		ButtonGroup group3 = new ButtonGroup();
		group3.add(markedOn);
		group3.add(markedOff);


		JLabel label7 = new JLabel("Custom Grid Size:");
		pane.add(label7);
		size = label7.getPreferredSize();
		label7.setBounds(widthPanel/4, (int) (heightPanel/1.75), size.width, size.height);

		gridSize1 = new JRadioButton("10x10");
		pane.add(gridSize1);
		size = gridSize1.getPreferredSize();
		gridSize1.setBounds(widthPanel/3, (int) (heightPanel/1.65), size.width, size.height);
		gridSize1.setBackground(backgroundColor);
		gridSize1.addActionListener(this);

		gridSize2 = new JRadioButton("15x15");
		pane.add(gridSize2);
		size = gridSize2.getPreferredSize();
		gridSize2.setBounds(widthPanel/2, (int) (heightPanel/1.65), size.width, size.height);
		gridSize2.setBackground(backgroundColor);
		gridSize2.addActionListener(this);

		gridSize3 = new JRadioButton("20x20");
		pane.add(gridSize3);
		size = gridSize3.getPreferredSize();
		gridSize3.setBounds((int) (widthPanel/1.5), (int) (heightPanel/1.65), size.width, size.height);
		gridSize3.setBackground(backgroundColor);
		gridSize3.addActionListener(this);

		/**
		 * Add gridSize1 and gridSize2 buttons into a fourth group of buttons
		 */
		ButtonGroup group4 = new ButtonGroup();
		group4.add(gridSize1);
		group4.add(gridSize2);
		group4.add(gridSize3);


		JLabel label8 = new JLabel("Number of Shuttles: (5-8)");
		pane.add(label8);
		size = label8.getPreferredSize();
		label8.setBounds(widthPanel/4, (int) (heightPanel/1.50), size.width, size.height);

		numbShuttles5 = new JRadioButton("5");
		pane.add(numbShuttles5);
		size = numbShuttles5.getPreferredSize();
		numbShuttles5.setBounds((int) (widthPanel/3), (int) (heightPanel/1.425), size.width, size.height);
		numbShuttles5.setBackground(backgroundColor);
		numbShuttles5.addActionListener(this);

		numbShuttles6 = new JRadioButton("6");
		pane.add(numbShuttles6);
		size = numbShuttles6.getPreferredSize();
		numbShuttles6.setBounds((int) (widthPanel/2.4), (int) (heightPanel/1.425), size.width, size.height);
		numbShuttles6.setBackground(backgroundColor);
		numbShuttles6.addActionListener(this);

		numbShuttles7 = new JRadioButton("7");
		pane.add(numbShuttles7);
		size = numbShuttles7.getPreferredSize();
		numbShuttles7.setBounds((int) (widthPanel/2), (int) (heightPanel/1.425), size.width, size.height);
		numbShuttles7.setBackground(backgroundColor);
		numbShuttles7.addActionListener(this);

		numbShuttles8 = new JRadioButton("8");
		pane.add(numbShuttles8);
		size = numbShuttles8.getPreferredSize();
		numbShuttles8.setBounds((int) (widthPanel/1.75), (int) (heightPanel/1.425), size.width, size.height);
		numbShuttles8.setBackground(backgroundColor);
		numbShuttles8.addActionListener(this);

		/**
		 * Add numbShuttles buttons into a fifth group of buttons
		 */
		ButtonGroup group5 = new ButtonGroup();
		group5.add(numbShuttles5);
		group5.add(numbShuttles6);
		group5.add(numbShuttles7);
		group5.add(numbShuttles8);


		JLabel placeRandomly = new JLabel("Place Boats:");
		pane.add(placeRandomly);
		size = placeRandomly.getPreferredSize();
		placeRandomly.setBounds(widthPanel/4, (int) (heightPanel/1.325) , size.width, size.height);

		randomButton = new JButton("Randomly");
		pane.add(randomButton);
		size = randomButton.getPreferredSize();
		randomButton.setBounds((int) (widthPanel/3),(int) (heightPanel/1.25), size.width, size.height);
		randomButton.addActionListener(this);

		manualButton = new JButton("Manually");
		pane.add(manualButton);
		size = manualButton.getPreferredSize();
		manualButton.setBounds((int) (widthPanel/1.7),(int) (heightPanel/1.25), size.width, size.height);
		manualButton.addActionListener(this);

		startButton = new JButton("Start");
		pane.add(startButton);
		size = startButton.getPreferredSize();
		startButton.setBounds((int) (widthPanel/1.3),(int) (heightPanel/1.15), size.width, size.height);
		startButton.addActionListener(this);
	}

	/**
	 * This method creates a frame that show the option panel and call the addComponents method
	 */
	public void showOptionPanel() {

		optionPanel = new JFrame();
		optionPanel.setSize(375,400);
		optionPanel.setResizable(false);
		optionPanel.setLocation(5,5);
		optionPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponents(optionPanel.getContentPane());
		optionPanel.getContentPane().setBackground(backgroundColor);
		optionPanel.setVisible(true);
	}

	/**
	 * This method add functionally to each button of the option panel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == humVsHum) {
			player2.setText("");
			player2.setEnabled(true);
			rofongo = false;
		}
		else if (e.getSource() == humVsRof) {
			player2.setText("Rofongo");
			player2.setEnabled(false);
			rofongo = true;
		}	
		
		if(e.getSource() == level1) {
			level = false;
		}
		
		else if(e.getSource() == level2){
			level = true;
		}
		
		if(e.getSource() == markedOn) {
			marked = true;
		}
		
		else if (e.getSource() == markedOff) {
			marked = false;
		}

		if (e.getSource() == gridSize1) {
			size = 10;
		}
		else if (e.getSource() == gridSize2) {
			size = 15;
		}
		else if (e.getSource() == gridSize3) {
			size = 20;
		}

		if (e.getSource() == numbShuttles5) {
			numberOfShuttles = 5;
		}
		else if (e.getSource() == numbShuttles6) {
			numberOfShuttles = 6;
		}
		else if (e.getSource() == numbShuttles7) {
			numberOfShuttles = 7;
		}
		else if (e.getSource() == numbShuttles8){
			numberOfShuttles = 8;
		}

		if(e.getSource() == randomButton) {
			type = true;
		}
		
		else if(e.getSource() == manualButton) {
			type = false;
		}

		if (e.getSource() == startButton) {
			playerName1 = player1.getText();
			playerName2 = player2.getText();
			
			GameMenuDebugger();

			Board gameBoard = new Board(size, numberOfShuttles, level, marked, type, rofongo);

			gameBoard.addOceans(450, 455);
			gameBoard.addCoordinates();
			gameBoard.addPlayerOneName(playerName1);
			gameBoard.addPlayerTwoName(playerName2);
			gameBoard.getFrame();
			gameBoard.addMenuBarOption();
			gameBoard.addTextAndButtonBar();
			optionPanel.dispose();
		}
		
	}

	public void GameMenuDebugger() {
		System.out.println("\nPREFERENCE SELECTION:");
		System.out.println("Human Vs Rofongo?: "+ rofongo);
		if(level)
			System.out.println("Level 1");
		else
			System.out.println("Level 2");
		if(marked)
			System.out.println("Marked Mode");
		else
			System.out.println("Un-Marked Mode");

		System.out.println("GridSize = "+size);
		System.out.println("Number of Shuttles: "+numberOfShuttles);
		if(type)
			System.out.println("Place Boats Randomly");
		else
			System.out.println("Place Boats Manualy");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GameMenu options = new GameMenu();
		options.showOptionPanel();

	}
}
