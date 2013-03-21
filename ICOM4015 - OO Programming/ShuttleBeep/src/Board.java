import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import java.io.IOException;
import java.io.Serializable;


/**
 * The Board class set all panels size, creates object for each ocean,
 * determine the location in the ocean in terms of coordinates,
 * creates a log, determine the turn of each player, 
 * and show each name player in the frame.
 * @author Eduardo && David
 */
public class Board extends JComponent implements Serializable {


	private static final long serialVersionUID = -8406544967977312268L;
	private JFrame frame;  // The object for the main frame
	private Grid ocean1;  // The object for the first ocean that will be used by players
	private Grid ocean2;  // The object for the second ocean that will be used by players
	private JTextField coordField;  // The textField object is used to get coordinates manually
	private JTextArea logField; // The logField object is used to report every move
	private JPanel panelOcean1;  // This object present the first ocean in  the main frame
	private JPanel panelOcean2; // This object present the second ocean in the main frame
	private PlayerName playerName1; // Get the name of the player 1
	private JPanel panelPlayer1; // Put the name of player 1 in this panel
	private PlayerName playerName2; // Get the name of the player 2
	private JPanel panelPlayer2; // Put the name of player 2 in this panel
	private String name1; // Set the name of player 1
	private String name2; // Set the name of player 2
	private CoordinateNumbers verticalCoordNumbers1; // This object gets the vertical bar of coordinates for first ocean
	private CoordinateNumbers verticalCoordNumbers2; // This object gets the vertical bar of coordinates for second ocean
	private CoordinateNumbers horizontalCoordNumbers1; // This object gets the horizontal bar of coordinates for first ocean
	private CoordinateNumbers horizontalCoordNumbers2; // This object gets the horizontal bar of coordinates for second ocean
	private JPanel verticalCoords1; // Put corresponding CoordinateNumbers object in this panel
	private JPanel verticalCoords2; // Put corresponding CoordinateNumbers object in this panel
	private JPanel horizontalCoords1; // Put corresponding CoordinateNumbers object in this panel
	private JPanel horizontalCoords2; // Put corresponding CoordinateNumbers object in this panel
	private int gridSize; // Save the grid size of each ocean
	private JMenuBar menuBar; // The menu bar of the game
	private boolean state; // This boolean variable is used to check what ocean is being selected
	private boolean stateOceans; // This boolean variable is used to check player's turn. 
	private JButton turn;  // This button is used to let know who is player's turn
	private Point cursorLocation; // This object is used to get the coordinates
	private boolean rofongo; // Determine if rofongo will play or not
	private int turnCounter; // A counter for each turn
	private Random rand; // To generate random numbers for rofongo
	private int[][] checkOcean1;
	private int[][] checkOcean2;
	private Score score;
	private Save save;


	/**
	 * Construct objects by calling the Grid class with the size of the ocean.
	 * Set the dimension of each ocean, construct the coordinates.
	 * Set the state of each players turn.
	 */

	public Board(int GridSize, int ShipAmount, boolean level, boolean marked, boolean type, boolean rofongo) {
		this.gridSize = GridSize;
		this.rofongo = rofongo;

		ocean1 = new Grid(gridSize, ShipAmount, level, marked, type, rofongo);

		if(rofongo) {
			ocean2 = new Grid(gridSize, ShipAmount, level, marked, type, true);
		}
		else {
			ocean2 = new Grid(gridSize, ShipAmount, level, marked, type, false);
		}

		ocean1.setPreferredSize(new Dimension(450,450));
		ocean2.setPreferredSize(new Dimension(450,450));

		verticalCoordNumbers1 = new CoordinateNumbers(gridSize, 1);
		verticalCoordNumbers1.setPreferredSize(new Dimension(50,450));

		verticalCoordNumbers2 = new CoordinateNumbers(gridSize, 1);
		verticalCoordNumbers2.setPreferredSize(new Dimension(50,450));

		horizontalCoordNumbers1 = new CoordinateNumbers(1, gridSize);
		horizontalCoordNumbers1.setPreferredSize(new Dimension(450,50));

		horizontalCoordNumbers2 = new CoordinateNumbers(1, gridSize);
		horizontalCoordNumbers2.setPreferredSize(new Dimension(450,50));
		
		menuBar = new JMenuBar();

		state = true;
		stateOceans = false;	
		rand = new Random();
		turnCounter = 0;
		score = new Score();
		save = new Save();

		checkOcean1 = new int[gridSize][gridSize];
		checkOcean2 = new int[gridSize][gridSize];

		addTurn();
	}

	/**
	 * Creates the panels in which the ocean will be placed, add the ocean objects on each panel.
	 * Also add mouseListener to each ocean object
	 * @param width the width of the panels
	 * @param height the height of the panels
	 */

	public void addOceans(int width, int height) {

		// Creates the first panel for the first ocean
		panelOcean1 = new JPanel();
		panelOcean1.setBounds(60, 100, width, height);
		panelOcean1.setBackground(Color.LIGHT_GRAY);

		// Creates the second panel for the second ocean 
		panelOcean2 = new JPanel();
		panelOcean2.setBounds(590, 100, width, height);
		panelOcean2.setBackground(Color.LIGHT_GRAY);

		panelOcean1.add(ocean1);
		panelOcean2.add(ocean2);

		// Add Motion and Mouse Listener to get coordinates
		ocean1.addMouseMotionListener(new MouseMovingHandler());
		ocean1.addMouseListener(new MouseHandler());
		ocean2.addMouseMotionListener(new MouseMovingHandler());
		ocean2.addMouseListener(new MouseHandler());
	}

	/**
	 * This method add panels with the coordinates beside each ocean
	 */

	public void addCoordinates() {

		verticalCoords1 = new JPanel();
		verticalCoords1.setBounds(panelOcean1.getX() - ocean1.getTheWidth() - 5, 100, 50, 455);
		verticalCoords1.setBackground(Color.LIGHT_GRAY);
		verticalCoords1.add(verticalCoordNumbers1);

		verticalCoords2 = new JPanel();
		verticalCoords2.setBounds(panelOcean2.getX() - ocean2.getTheWidth() - 5, 100, 50, 455);
		verticalCoords2.setBackground(Color.LIGHT_GRAY);
		verticalCoords2.add(verticalCoordNumbers2);

		horizontalCoords1 = new JPanel();
		horizontalCoords1.setBounds(60, panelOcean1.getY() - ocean1.getTheHeight() - 5, 450, 50);
		horizontalCoords1.setBackground(Color.LIGHT_GRAY);
		horizontalCoords1.add(horizontalCoordNumbers1);

		horizontalCoords2 = new JPanel();
		horizontalCoords2.setBounds(590, panelOcean2.getY() - ocean2.getTheHeight() - 5, 450, 50);
		horizontalCoords2.setBackground(Color.LIGHT_GRAY);
		horizontalCoords2.add(horizontalCoordNumbers2);
	}

	/**
	 * This method add first player name above the first ocean
	 * @param name the name of player 1
	 */

	public void addPlayerOneName(String name) {
		if (name.equals("")) {
			name = "Player 1";
		}
		name1 = name;
		playerName1 = new PlayerName(name);
		playerName1.setPreferredSize(new Dimension(150,40));

		panelPlayer1 = new JPanel();
		panelPlayer1.setBackground(Color.LIGHT_GRAY);
		panelPlayer1.setBounds(215,20,150,40);
		panelPlayer1.add(playerName1);
	}

	/**
	 * This method add second player name above the second ocean
	 * @param name the name of player 2
	 */

	public void addPlayerTwoName(String name) {
		if (name.equals("")) {
			name = "Player 2";
		}
		name2 = name;
		playerName2 = new PlayerName(name);
		playerName2.setPreferredSize(new Dimension(150,40));

		panelPlayer2 = new JPanel();
		panelPlayer2.setBackground(Color.LIGHT_GRAY);
		panelPlayer2.setBounds(735,20,150,40);
		panelPlayer2.add(playerName2);
	}

	/**
	 * Get the frame or user interface that show the game
	 */

	public void getFrame() {
		// Creates the frame that represent the game
		frame = new JFrame();

		frame.getContentPane().add(panelOcean1);
		frame.getContentPane().add(panelOcean2);
		frame.getContentPane().add(verticalCoords1);
		frame.getContentPane().add(verticalCoords2);
		frame.getContentPane().add(horizontalCoords1);
		frame.getContentPane().add(horizontalCoords2);
		frame.getContentPane().add(panelPlayer1);
		frame.getContentPane().add(panelPlayer2);
		frame.add(turn);

		frame.getContentPane().add(this);
		frame.setSize(1350,625);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setUndecorated(true);
		frame.addKeyListener(new KeyListenerHandler());
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getLog(frame.getContentPane());
		frame.setVisible(true);
	}

	/**
	 * Creates and add the menu of the game.
	 * Also add action listener to each option.
	 */

	public void addMenuBarOption() {
		if (System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}

		frame.setJMenuBar(menuBar);

		JMenu game = new JMenu("Game");
		menuBar.add(game); 

		JMenuItem newGame = new JMenuItem("New Game");
		game.add(newGame);
		newGame.addActionListener(new ActionHandler() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GameMenu options = new GameMenu();
				options.showOptionPanel();
				frame.dispose();
			}
		});

		game.addSeparator();

		JMenuItem openGame = new JMenuItem("Open Game");
		game.add(openGame);
		newGame.addActionListener(new ActionHandler() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});


		JMenuItem saveGame = new JMenuItem("Save Game");
		game.add(saveGame);
		saveGame.addActionListener(new ActionHandler() {
			public void actionPerformed(ActionEvent e) {
				try {
					Object[] ob = new Object[17];
					ob[0] = ocean1;
					ob[1] = ocean2;
					ob[2] = logField;
					ob[3] = panelOcean1;
					ob[4] = panelOcean2;
					ob[5] = playerName1;
					ob[6] = playerName2;
					ob[7] = name1;
					ob[8] = name2;
					ob[9] = gridSize;
					ob[10] = state;
					ob[11] = stateOceans;
					ob[12] = checkOcean1;
					ob[13] = checkOcean2;
					ob[14] = rofongo;
					ob[15] = turnCounter;
					ob[16] = score;
					save.save(ob);
					JOptionPane.showMessageDialog(null, "Game Saved");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Game NOT Saved");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		game.addSeparator();
		JMenuItem scores = new JMenuItem("High Scores");
		game.add(scores);
		scores.addActionListener(new ActionHandler() {
			public void actionPerformed(ActionEvent e) {
				score.readFile();
				JOptionPane.showMessageDialog(frame, score.getScore());

				System.out.println("Higher!: "+ score.getScore());
			}

		});

		JMenuItem exit = new JMenuItem("Exit");
		game.add(exit);
		exit.addActionListener(new ActionHandler() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);

			}
		});
	}

	/**
	 * Creates the text field that receive coordinates from the end user.
	 * Also creates the try button to read the input.
	 */

	public void addTextAndButtonBar() {

		JPanel tryPanel = new JPanel();
		tryPanel.setBounds(1100,520,200,35);
		tryPanel.setBackground(Color.LIGHT_GRAY);

		coordField =  new JTextField();
		coordField.setPreferredSize(new Dimension(100,25));

		JButton btnTry = new JButton("Try!");
		btnTry.setPreferredSize(new Dimension(75,25));

		tryPanel.add(coordField);
		tryPanel.add(btnTry);
		frame.add(tryPanel);

		/**
		 * Determine player's turn. 
		 * Add action listener to try button that takes coordinates as a string 
		 * and arranges each part in an array to make movements
		 */
		btnTry.addActionListener(new ActionHandler() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				

				if (state) {

					if (!stateOceans) {
						String s = coordField.getText();
						coordField.setText("");
						s = s.replace(",", "");
						s = s.trim();
						String[] coords = s.split(" ");

						int x = Integer.parseInt(coords[0]);
						int y = Integer.parseInt(coords[1]);


						if(checkOcean1[x - 1][y - 1] != 1){

							ocean1.tryShip(x - 1, y - 1);

							cursorLocation = new Point(x,y);
							turnCounter++;
							logField.append("Turn " + turnCounter + ": Player 1 try: " +"(" +cursorLocation.x + "-" + cursorLocation.y +")\n");
							logField.setCaretPosition(logField.getText().length());

							if(rofongo) {
								int x1 = rand.nextInt(gridSize);
								int y1 = rand.nextInt(gridSize);

								ocean2.tryShip(x1, y1);
								cursorLocation = new Point(x1, y1);

								logField.append("Turn " + turnCounter +": Rofongo try: " +"(" + (cursorLocation.x+1) + "-" + (cursorLocation.y+1) +")\n");
								logField.setCaretPosition(logField.getText().length());
								return;
							}				
							stateOceans = true;
							state = false;
							changeTurn(695, 20);
						}
						else if (checkOcean1[x-1][y-1] == 1) {
							logField.append("Player 1 needs to enter other coordinates\n");
							logField.setCaretPosition(logField.getText().length());
						}

						checkOcean1[x-1][y-1] = 1;	
					}
				}
				else {
					if (stateOceans) {
						String s = coordField.getText();
						coordField.setText("");
						s = s.replace(",", "");
						s = s.trim();
						String[] coords = s.split(" ");

						int x = Integer.parseInt(coords[0]);
						int y = Integer.parseInt(coords[1]);
						cursorLocation = new Point(x,y);

						if(checkOcean2[x - 1][y - 1] != 1) {

							ocean2.tryShip(x - 1, y - 1);
							turnCounter++;
							logField.append("Turn " + turnCounter +": Player 2 try: " +"(" +cursorLocation.x + "-" + cursorLocation.y +")\n");
							logField.setCaretPosition(logField.getText().length());
							stateOceans = false;
							state = true;
							changeTurn(175, 20);
						}

						else if (checkOcean2[x-1][y-1] == 1) {
							logField.append("Player 2 needs to enter other coordinates\n");
							logField.setCaretPosition(logField.getText().length());
						}

						checkOcean2[x-1][y-1] = 1;
					}	
				}
			}
		});
	}

	/**
	 * Gets the status report of the game, that is a log box which show every move in the game
	 */

	public void getLog(Container pane) {

		pane.setLayout(null);

		logField = new JTextArea("----Welcome to Shuttle Beep Game!----\n");
		logField.append("Player 1 Start:\n");
		logField.setEditable(false);

		JPanel logPanel;

		(logPanel = new JPanel()).setLayout(new BorderLayout());
		logPanel.setBounds(1050, 85, 300, 425);
		logPanel.setBorder(new TitledBorder("Game Report"));
		logPanel.add(new JScrollPane(logField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		logPanel.setBackground(Color.LIGHT_GRAY);

		frame.getContentPane().add(logPanel);	
	}

	/**
	 * Set an image to know who is player's turn.
	 */

	public void addTurn() {
		ImageIcon arrow = new ImageIcon("Cells/General/flecha.png");
		arrow = new ImageIcon(arrow.getImage().getScaledInstance(40, 30, java.awt.Image.SCALE_SMOOTH));

		turn = new JButton();
		turn.setBounds(175, 20, 40, 30);
		turn.setIcon(arrow);
		//turn.setEnabled(false);
		turn.setBackground(Color.YELLOW);
	}

	/**
	 * This method make visible the change of the player's turn
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void changeTurn (int x, int y) {
		turn.setLocation(x, y);
		winner();
	}

	/**
	 * Determine if the game is finished, who is the winner. 
	 */
	public void winner() {
		if(ocean1.finished()) {
			JOptionPane.showMessageDialog(frame, name1 + " Win!! Congratulations!");
			score.submitScore(name1, turnCounter/2);
			score.readFile();
			System.exit(0);

		}
		else if(ocean2.finished()) {
			JOptionPane.showMessageDialog(frame, name2 + " Win!! Congratulations!");
			score.submitScore(name2, turnCounter/2);
			score.readFile();
			System.exit(0);
		}
	}

	/**
	 * This method implements the key listener when press Alt + F4
	 */
	public class KeyListenerHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();
			int keyCodeF4 = e.getKeyCode();

			if ((keyCode | keyCodeF4) == (KeyEvent.VK_ALT | KeyEvent.VK_F4)) {
				JOptionPane.showMessageDialog(null, "Bye, Bye!");
				System.exit(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * This class get the name of each player and paint it above each ocean
	 * @author Eduardo && David
	 */
	private class PlayerName extends JPanel {	

		private static final long serialVersionUID = 1L;
		private String playerName; // The player name

		/**
		 * Get the player name
		 * @param name the name of the player
		 */
		public PlayerName(String name) {
			playerName = name;
		}

		/**
		 * Set the font size, and paint each name above each ocean
		 */
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  // Make numbers smooth
			g2.setFont(new Font("", Font.BOLD, 25));  // Font of the numbers

			FontMetrics fm   = g.getFontMetrics();
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(playerName, g2);

			int textHeight = (int)(rect.getHeight()); 
			int textWidth  = (int)(rect.getWidth());
			int panelHeight= this.getHeight();
			int panelWidth = this.getWidth();

			// Center text horizontally and vertically
			int x = (panelWidth  - textWidth)  / 2;
			int y = (panelHeight - textHeight) / 2;

			g2.setColor(Color.BLUE);
			g2.drawString(playerName,  x, y + 15);

		}

	}

	/**
	 * The CoordinateNumbers class paint the coordinates beside each oceans
	 * @author Eduardo && David
	 */

	private class CoordinateNumbers extends JPanel {		

		private static final long serialVersionUID = 1L;
		private int parameter1; 
		private int parameter2;

		/**
		 * Get the parameters for each coordinates number
		 * @param parameter1 the number of times to iterate the coordinates
		 * @param parameter2 the number of times to iterate the coordinates
		 */
		public CoordinateNumbers (int parameter1, int parameter2) {
			this.parameter1 = parameter1;
			this.parameter2 = parameter2;
		}

		/**
		 * Set the font size, create a rectangles and paint above each rectangle a number to display coordinates
		 */
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			int fontSize = ocean1.getTheHeight()/2;

			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  // Make numbers smooth
			g2.setFont(new Font("", Font.BOLD, fontSize));  // Font of the numbers

			int counter = 1;
			for (int i = 0; i < parameter1; i++) {
				int y = i * ocean1.getTheHeight();

				for (int j = 0; j < parameter2; j++) {
					int x = j * ocean1.getTheWidth();

					g2.setColor(new Color(25,150,250));
					g2.fill3DRect(x, y, ocean1.getTheWidth(), ocean1.getTheHeight(), true);

					g2.setColor(Color.WHITE);

					/**
					 * If the number has two digits, set this location
					 * Draw a number on top each rectangle
					 * Else, the number has one digit, set this location
					 */
					if (counter/10 > 0) {   
						g2.drawString("" + counter, x + 2*ocean1.getTheWidth()/9, y + 2*ocean1.getTheHeight()/3);  
					}
					else {  
						g2.drawString("" + counter, x + 3*ocean1.getTheWidth()/8, y + 2*ocean1.getTheHeight()/3);  
					}	
					counter++;
				}
			}
		}
	}

	/**
	 * The ActionHandler class that implements ActionListener interface 
	 * determine specific actions for each buttons, etc.
	 * @author Eduardo && David
	 */

	private class ActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * The MouseMovingHandler compute the coordinates when the mouse is moved over an ocean
	 * @author Eduardo && David
	 *
	 */

	private class MouseMovingHandler extends MouseMotionAdapter
	{
		/**
		 * This method determine the cursor location in each ocean.
		 */
		public void mouseMoved(MouseEvent e){
			winner();

			int x = (int)((e.getPoint().getX())/ocean1.getTheWidth()) + 1;
			int y = (int)((e.getPoint().getY())/ocean1.getTheHeight()) + 1;

			if(x <= gridSize && y <= gridSize)
			{
				cursorLocation = new Point(x,y);
			}
		}
	}

	/**
	 * This method makes rofongo's move randomly
	 */
	public void rofongo() {
		int auxX = rand.nextInt(gridSize);
		int auxY = rand.nextInt(gridSize);

		System.out.println(auxX);

		ocean2.tryShip(auxX, auxY);
		turnCounter++;
		logField.append("Turn " + turnCounter + ": Rofongo try: " +"(" +(auxX+1) + "-" + (auxY+1) +")\n");
		logField.setCaretPosition(logField.getText().length());

	}

	/**
	 * The MouseHandler class determine the state of each player's turn.
	 * Makes move when a mouse is clicked over an ocean according to player's turn.
	 * @author Eduardo && David
	 *
	 */
	private class MouseHandler extends MouseAdapter {

		public void mousePressed(MouseEvent e) {			
			state = false;
			if (e.getSource() == ocean1) {
				state = true;
			}


			if(ocean1.finished()) {
				System.out.println("Player on left board won!");
				JOptionPane.showMessageDialog(frame, "Player 1 won!!");
				System.exit(0);
			}
			else if(ocean2.finished()) {
				System.out.println("Player on right board won!");
				JOptionPane.showMessageDialog(frame,"Player 2 won!!");
				System.exit(0);
			}

			if(e.getModifiers() == InputEvent.BUTTON1_MASK)
			{		

				// Change turn for each player

				if (state) {
					if (!stateOceans) {
						Point selected = cursorLocation;

						if(checkOcean1[cursorLocation.x - 1][cursorLocation.y - 1] != 1){
							turnCounter++;
							logField.append("Turn " + turnCounter +": Player 1 try: " +"(" +selected.x + "-" + selected.y +")\n");
							logField.setCaretPosition(logField.getText().length());
							ocean1.tryShip((int)cursorLocation.getX() - 1, (int)cursorLocation.getY() - 1);

							if(rofongo) {
								rofongo();
								return;
							}

							state = false;
							stateOceans = true;
							changeTurn(695, 20);
						}

						else if (checkOcean1[selected.x-1][selected.y-1] == 1) {
							logField.append("Player 1 needs to enter other coordinates\n");
							logField.setCaretPosition(logField.getText().length());
							//state = true;
							//stateOceans = false;
						}

						checkOcean1[selected.x-1][selected.y-1] = 1;
					}	
				}

				else {
					if (stateOceans) {
						Point selected = cursorLocation;

						if(checkOcean2[cursorLocation.x - 1][cursorLocation.y - 1] != 1){
							turnCounter++;
							logField.append("Turn " + turnCounter + ": Player 2 try: " +"(" +selected.x + "-" + selected.y +")\n");
							logField.setCaretPosition(logField.getText().length());
							ocean2.tryShip((int)cursorLocation.getX() - 1, (int)cursorLocation.getY() - 1);
							state = true;
							stateOceans = false;
							changeTurn(175, 20);
						}

						else if  (checkOcean2[selected.x-1][selected.y-1] == 1) {
							logField.append("Player 2 needs to enter other coordinates\n");
							logField.setCaretPosition(logField.getText().length());
							//state = false;
							//stateOceans = true;
						}

						checkOcean2[selected.x-1][selected.y-1] = 1;
					}
				}
			}
		}
	}	
}
