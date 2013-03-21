import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * The Grid class creates the oceans depending on the size, 
 * set different images to mark the progress of the game. 
 * @author Eduardo && David
 */
public class Grid extends JComponent implements Serializable {

	private static final long serialVersionUID = -1525139336252828796L;
	private int gridSize;  // Save the grid size of each ocean
	private int width;  // To compute the width of each tile
	private int height; // To compute the height of each tile
	private ImageIcon[][] imageGrid; // To save each images in a matrix
	private GridGenerator currentGrid;  // This object connect the GUI with the
	private int[][] matrix;  // Copy a matrix of GridGenerator class
	private ImageIcon ocean_empty; // An image of the empty ocean
	private ImageIcon ocean_try; // An image when a miss is made
	private ImageIcon ocean_discovery; // An image when a ship is discovered
	private ImageIcon H1_icon; // Horizontal front part of the ship
	private ImageIcon H2_icon; // Horizontal body part of the ship
	private ImageIcon H3_icon; // Horizontal back part of the ship
	private ImageIcon V1_icon; // Vertical front part of the ship
	private ImageIcon V2_icon; // Vertical body part of the ship
	private ImageIcon V3_icon; // Vertical back part of the ship
	private ImageIcon D1_icon; // Diagonal front part of the ship
	private ImageIcon D2_icon; // Diagonal body part of the ship
	private ImageIcon D3_icon; // Diagonal body part of the ship
	private boolean marked; // Determine if marked is on or off

	/**
	 * Construct the initial oceans, the width and height of each tiles, set the currentGrid and imageGrid objects
	 * @param GridSize The size of the oceans (matrix)
	 * @param ShipAmount The amount of ships in the game
	 * @param level The level of the game, false level 1 and true level 2
	 * @param marked The marked mode, true if is on and false if it is off
	 * @param type The mode of the game, if it is randomly or not
	 * @param rofongo To play versus rofongo or not
	 */
	public Grid (int GridSize, int ShipAmount, boolean level, boolean marked, boolean type, boolean rofongo)  {

		this.gridSize = GridSize;
		currentGrid = new GridGenerator(gridSize, ShipAmount, level, type);
		imageGrid = new ImageIcon[gridSize][gridSize];
		matrix = new int[gridSize][gridSize];
		this.marked = marked;

		width = (int) ((int)(450)/gridSize);
		height = (int) ((int)(450)/gridSize);
	
		setIcons();
		initialImageGrid();
	}

	/**
	 * Set the images for the oceans, ships (horizontal, vertical and diagonal), tries, and discoveries.
	 */
	public void setIcons() {

		ocean_empty = new ImageIcon("Cells/General/empty.png");
		ocean_empty = new ImageIcon(ocean_empty.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		ocean_try = new ImageIcon("Cells/General/missed.png");
		ocean_try = new ImageIcon(ocean_try.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		if(!marked) {
			ocean_try = ocean_empty;
		}

		ocean_discovery = new ImageIcon("Cells/General/progress.png");
		ocean_discovery = new ImageIcon(ocean_discovery.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		H1_icon = new ImageIcon("Cells/Horizontal/frontH.png");
		H1_icon = new ImageIcon(H1_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		H2_icon = new ImageIcon("Cells/Horizontal/bodyH.png");
		H2_icon = new ImageIcon(H2_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		H3_icon = new ImageIcon("Cells/Horizontal/backH.png");
		H3_icon = new ImageIcon(H3_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		V1_icon = new ImageIcon("Cells/Vertical/frontV.png");
		V1_icon = new ImageIcon(V1_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		V2_icon = new ImageIcon("Cells/Vertical/bodyV.png");
		V2_icon = new ImageIcon(V2_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		V3_icon = new ImageIcon("Cells/Vertical/backV.png");
		V3_icon = new ImageIcon(V3_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		D1_icon = new ImageIcon("Cells/Diagonal/frontD.png");
		D1_icon = new ImageIcon(D1_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		D2_icon = new ImageIcon("Cells/Diagonal/bodyD.png");
		D2_icon = new ImageIcon(D2_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

		D3_icon = new ImageIcon("Cells/Diagonal/backD.png");
		D3_icon = new ImageIcon(D3_icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
	}

	/**
	 * The paint component call draw method to paint the oceans, ships, tries and discoveries.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}	

	/**
	 * This method paint all the images of the game in determine pixels
	 * @param g2 the Graphics2D variable
	 */
	public void draw(Graphics2D g2){
		MoveImageGrid2();
		for(int i = 0; i < gridSize; i++){
			int y = i * height;
			for(int j = 0; j < gridSize; j++){
				int x = j * width;
				imageGrid[i][j].paintIcon(this, g2, x, y);
			}
		}
	}

	/**
	 * This method try each move, call MoveImageGrid and make repaints.
	 * @param x the x coordinate of the ocean
	 * @param y the y coordinate of the ocean
	 */
	public void tryShip(int x, int y){
		currentGrid.TryInShip(x, y);
		MoveImageGrid2();
		repaint();
	}
	
	/**
	 * This method set the empty ocean
	 */
	public void initialImageGrid(){
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				imageGrid[i][j] = ocean_empty;
			}
		}
	}
	
	/**
	 * This method get the matrix or moves of the game
	 */
	public void getMatrix() {
		matrix = currentGrid.getBoard();
	}

	/**
	 * This method move each image in the imageGrid object when coordinates are entered.
	 * Move images depending on the direction of the ships.
	 * If the direction is 0, that is the ship is horizontal.
	 * If the direction is 1, that is the ship is vertical.
	 * If the direction is 2, that is the ship is diagonal.
	 */
	public void MoveImageGrid2() {
		
		for(int a = 0; a < gridSize; a++) {
			for(int b = 0; b < gridSize; b++) {
				getMatrix();

				if(matrix[a][b] == 4) {
					imageGrid[a][b] = ocean_try;
				}
				
				/**
				 * Check the direction of each ship, and set the discovery image when a part of a ship 
				 * is discovered, and set ship image after the ship is completely discovered.
				 */
				for(int i = 0; i < currentGrid.getShips().size(); i++) {
					for(int j = 0; j < currentGrid.getShips().get(i).getSize(); j++) {
						
						if(currentGrid.getShips().get(i).getDirection() == 0){
							
							if(currentGrid.getShips().get(i).getBody(j) == 1) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = ocean_discovery;

							else if(currentGrid.getShips().get(i).getBody(j) == 11) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = H1_icon; 

							else if(currentGrid.getShips().get(i).getBody(j) == 13)
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = H2_icon;

							else if(currentGrid.getShips().get(i).getBody(j) == 12)
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = H3_icon;

							else 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = ocean_empty;
						}

						else if(currentGrid.getShips().get(i).getDirection() == 1) {
							
							if(currentGrid.getShips().get(i).getBody(j) == 1)
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = ocean_discovery; 
							
							else if(currentGrid.getShips().get(i).getBody(j) == 12) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = V1_icon; 

							else if(currentGrid.getShips().get(i).getBody(j) == 13) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = V2_icon;

							else if(currentGrid.getShips().get(i).getBody(j) == 11) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = V3_icon;

							else 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = ocean_empty;

						}
						else {
							
							if(currentGrid.getShips().get(i).getBody(j) == 1) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = ocean_discovery;								

							else if(currentGrid.getShips().get(i).getBody(j) == 12)
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = D1_icon;

							else if(currentGrid.getShips().get(i).getBody(j) == 13) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = D2_icon;

							else if(currentGrid.getShips().get(i).getBody(j) == 11) 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = D3_icon;

							else 
								imageGrid[currentGrid.getShips().get(i).getY(j)][currentGrid.getShips().get(i).getX(j)] = ocean_empty;
						}
					}
				}
			}
		}
	}

	/**
	 * Get the width of each tile of the ocean
	 * @return the width of each tile
	 */
	public int getTheWidth() {
		return width;
	}

	/**
	 * Get the height of each tile of the ocean
	 * @return the height of each tile
	 */
	public int getTheHeight() {
		return height;
	}

	/**
	 * Determine if the game is finished or not
	 * @return true if is finished, false if not.
	 */
	public boolean finished() {
		return currentGrid.boardFinished();
	}
	
	public int totalHits() {
		return currentGrid.totalHits();
	}
}
