import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The GridGenerator class determine the direction of each ship and place them randomly in the matrix.
 * Makes each move inside the grid or matrix.
 * @author David && Eduardo
 */
public class GridGenerator implements Serializable{

	
	private static final long serialVersionUID = -3085363890470528632L;
	private Random rand = new Random(); //Random generator to get random X and Y and direction
	private int GridSize; // rid size
	public int grid[][]; // Grid matrix
	private String debug[][]; 
	private int shipAmount; // Ship amounts
	private ShipSize shipsSize; // Ships objects
	private int currentSize; // Ship current size
	private int x; // X location
	private int y; // Y location
	private int direction; // Direction (0 = horizontal, 1 = vertical, 2 = diagonal)
	private boolean level; // boolean value for level (false = 1, true = 2)
	private boolean set; // boolean value to verify if the ship has been set on the grid
	private boolean type; // boolean value to determine if ships will be placed randomly or not
	private int[][] game; // Game grid made after all the ships has been set
	private int HitsAmount; // The amount of hits that is made on ships
	private int currentHits; // Count the hits that is made on ships
	private Scanner input; // Input coordinates to place ships manually
	ArrayList<Ship> Ships = new ArrayList<Ship>(); // ArrayList of ships of the grid

	/**
	 * Construct shipSize object, matrix, get type of game and start a new game
	 * @param GridSize The size of the grid or matrix
	 * @param shipAmount The amount of ships in the game
	 * @param level The level of the game, false if it is level 1 and true if it is level 2
	 * @param type The type of the game, to place boat randomly or manually
	 */
	public GridGenerator(int GridSize, int shipAmount, boolean level, boolean type) {
		shipsSize = new ShipSize(shipAmount);
		this.GridSize = GridSize;
		grid = new int[GridSize][GridSize];
		debug = new String[GridSize][GridSize];
		game = new int[GridSize][GridSize];
		this.shipAmount = shipAmount;
		this.level = level;
		this.type = type;
		input = new Scanner(System.in);
		cleanBoard();
		newGame();
	}

	/**
	 * Set the ships into the matrix
	 */
	public void setShips() {
		int i = 0;
		while(i < shipAmount) {
			currentSize = shipSize(); //llama en cada iteraci—n al pr—ximo barco
			changeDirection();
			getXY();
			MasterVerifier();
			//printGrid();
			i++;
		}
	}
	/**
	 * Method that change direction for ships to be placed
	 */
	private void changeDirection() {
		if(type) {
			if(!level)
				direction = rand.nextInt(2);
			else
				direction = rand.nextInt(3);
		}
		else {
			int limit = 2;
			if(level)
				limit = 3;
			System.out.print("\nSet Ship "+ currentSize +" direction");
			do {
				System.out.print("\n0) Horizontal\n1) Vertical\n2) Diagonal\n ");
				direction = input.nextInt();
			}
			while(direction < 0 || direction > limit);
		}
	}

	/**
	 * Get new X & Y values to set ships
	 */
	public void getXY() {
		if(type) { 
			switch (direction) {
			case 0: //horizontal
				x = rand.nextInt(GridSize-currentSize);
				y = rand.nextInt(GridSize);
				//MasterVerifier();

				break;
			case 1: //vertical
				y = rand.nextInt(GridSize-currentSize);
				x = rand.nextInt(GridSize);
				//MasterVerifier();

				break;
			case 2: //diagonal
				x = rand.nextInt(GridSize-currentSize);
				y = rand.nextInt(GridSize-currentSize);
				//MasterVerifier();	
				break;
			}
		}
		else { 
			do {
				System.out.println("----CURRENT GRID----");
				printGrid();
				System.out.print("Enter Ship " + currentSize+" position");
				System.out.print("\nEnter X coordinate: ");
				x = input.nextInt();
				System.out.print("\nEnter y coordinate: ");	
				y = input.nextInt();
				//MasterVerifier();
			}
			while(x > GridSize || y > GridSize || x < 0 || y < 0);
		}
	}

	/**
	 * Verify according to the direction of the ship if exist conflicts when place boat randomly
	 */
	private void MasterVerifier() {
		try {
			switch(direction){
			case 0:	
				verifyH();
				break;
			case 1:
				verifyV();
				break;
			case 2:
				verifyD();
				break;

			}
		}catch(Exception e){
			newGame();
		}
	}

	/**
	 * Method to call methods to set ships on a specific direction
	 */
	private void MasterSet(){

		switch(direction){
		case 0:
			horizontal();
			break;
		case 1:
			vertical();
			break;
		case 2:
			diagonal();
			break;
		}

	}

	/**
	 * Hardcore Verifier that sum the expected covered cells 
	 * with the actual ones and decided if are the same
	 */
	private void HardcoreVerifier(){
		int tTotal = 2;
		int tt = 0;
		for(int i = 0; i<shipAmount; i++){
			tt += tTotal;
			tTotal++;
		}

		int gridtrue = 0;
		for(int i = 0; i<GridSize; i++){
			for(int j = 0; j<GridSize; j++){
				if(grid[i][j] == 1)
					gridtrue++;
			}	
		}

		if(gridtrue == tt){
			System.out.println("Good Board!");
			System.out.println(gridtrue +" == " + tt);
			HitsAmount = tt;
		}

		else{
			System.out.println(gridtrue +" != " + tt);
			cleanBoard();
			newGame();
		}
	}

	/**
	 * Vertical Verifier for conflicts in Ships
	 */
	private void verifyV(){
		set = true;
		System.out.println("---VERTICAL VERIFIER---");
		try{
			for(int a = y; a<y+GridSize - (GridSize - currentSize); a++){
				System.out.println("\tOn " + x +"-"+ a + " is " + grid[a][x]);
				if(grid[a][x] == 1){
					System.out.println("Ship: "+currentSize+" conflict position: " + x + "-"+y+":V");
					System.out.println("Conflict point " + x + "-" + a);
					set = false;
					changeDirection();
					getXY();
					MasterVerifier();
					return;
				}
			}
			if(set)
				MasterSet();

		}catch(Exception e){}
	}

	/**
	 * Horizontal Verifier for conflicts in Ships
	 */
	private void verifyH(){
		System.out.println("--HORIZONTAL VERIFIER--");
		set = true;
		try{
			for(int a = x; a < x + GridSize - (GridSize - currentSize); a++){
				System.out.println("\tOn " + a +"-"+ y + " is " + grid[y][a]);
				if(grid[y][a] == 1){
					System.out.println("Ship: "+currentSize+" conflicted location: " + x + "-"+y+":H");
					System.out.println("Conflict point " + a + "-" + y);
					set = false;
					changeDirection();
					getXY();
					MasterVerifier();
					return;
				}
			}
			if(set)
				MasterSet();
		}
		catch(Exception e){}
	}

	/**
	 * Diagonal Verifier for conflicts in Ships
	 */
	private void verifyD(){
		System.out.println("---DIAGONAL VERIFIER---");
		try{
			int b = y;
			set = true;
			for(int a = x; a<x+GridSize - (GridSize - currentSize); a++){
				System.out.println("\tOn " + a +"-"+ y + " is " + grid[b][a]);

				if(grid[b][a] == 1){
					System.out.println("Ship Size: "+currentSize+" conflicted location: " + x + "-"+y+":D");
					System.out.println("Conflict point " + a + "-" + b);
					set = false;
					changeDirection();
					getXY();
					MasterVerifier();
					return;
				}
				b++;
			}
			if(set)
				MasterSet();
		}catch(Exception e){}
	}

	/**
	 * Set current ship vertically
	 */
	private void vertical(){
		System.out.print("------VERTICAL-----\n");
		Ship vertical = new Ship(x,y,currentSize,direction);
		if(y <= GridSize-currentSize){
			System.out.println("I'm SETTING SHIPS!");
			for(int i = y; i<y+currentSize; i++){
				System.out.println("  Setting at: "+x+"-"+i);
				grid[i][x] = 1; 
				debug[i][x] = "V  ";
				vertical.setCoordenates();
			}
			Ships.add(vertical);
		}
	}
	/**
	 * Set current ship horizontally
	 */
	private void horizontal(){
		System.out.print("-----HORIZONTAL-----\n");
		Ship horizontal = new Ship(x,y,currentSize,direction);
		if(x <= GridSize-currentSize){
			System.out.println("I'm SETTING SHIPS!");
			for(int i = x; i<x+currentSize; i++){
				System.out.println("  Setting at: "+i+"-"+y);
				grid[y][i] = 1; 
				debug[y][i] = "H  ";
				horizontal.setCoordenates();
			}
			Ships.add(horizontal);
		}
	}
	/**
	 * set current ship diagonally
	 */
	private void diagonal(){
		System.out.print("-----DIAGONAL-----\n");
		Ship diagonal = new Ship(x,y,currentSize,direction);
		if(x<GridSize-currentSize && y < GridSize-currentSize){
			int j = x;
			for(int i = y; i<y+currentSize; i++){
				System.out.println("  Setting at: "+j+"-"+i);
				grid[i][j] = 1; 
				debug[i][j] = "D  ";
				diagonal.setCoordenates();
				j++;
			}
			Ships.add(diagonal);
		}
	}

	/**
	 * Get next ship size
	 * @return next ship size
	 */
	private int shipSize(){
		return shipsSize.getNext();
	}

	/**
	 * Grid printer
	 */
	public void printGrid(){
		System.out.println("\n");
		for(int z = 0; z<GridSize; z++){
			if(z>9)
				System.out.print(z-10*(z/10)+"  ");
			else
				System.out.print(z+"  ");
		}
		System.out.println("");

		for(int i = 0; i<GridSize; i++){
			for(int j = 0; j<GridSize; j++){
				System.out.print(grid[i][j]+"  ");
				//if(grid[i][j] == 1)
				//System.out.print("1  ");
				//if(grid[i][j] == 0)
				//	System.out.print("-  ");
			}
			System.out.print(i+"\n");
		}
	}

	/**
	 * Clean Grid Debugger
	 */
	public void cleanDebugger() {
		for(int i = 0; i < GridSize; i++) {
			for(int j = 0; j < GridSize; j++) {
				debug[i][j] = "-  ";

			}
		}
	}

	/**
	 * Clean GridBoard
	 */
	private void cleanBoard() {
		cleanDebugger();
		for(int i = 0; i < GridSize; i++) {
			for(int j = 0; j < GridSize; j++) {
				grid[i][j] = 0;				
			}
		}
	}

	/**
	 * Generates a new Game
	 */
	public void newGame() {
		shipsSize = new ShipSize(shipAmount);
		cleanBoard();
		cleanDebugger();
		setShips();
		HardcoreVerifier();
		//printGrid();
		//printDebugger();
		currentHits = 0;
	}

	/**
	 * Get the board (matrix) in the console
	 * @return the matrix or grid
	 */
	public int[][] getBoard(){
		return grid;
	}

	/**Make moves in current grid
	 * 
	 * @param x coordinate value
	 * @param y coordinate value
	 */
	private void moveInGrid(int x, int y) {
		printGrid();
		if(grid[y][x] == 1){
			currentHits++;
			System.out.println("----HIT----");
			grid[y][x] = 2;
		}
		else if(!(grid[y][x] == 2)){
			System.out.println("----MISS----");
			grid[y][x] = 4;
		}
	}
	/**
	 * Pass try movements to Ships
	 * @param x coordinate value
	 * @param y coordinate value
	 */
	public void TryInShip(int x, int y) {
		moveInGrid(x, y);
		for(int i = 0; i < Ships.size(); i++) {
			Ships.get(i).tryMe(x, y);
		}
	}

	/**
	 * Determine if the games is finished or not
	 * Determine if the total hits is equal to the hits made by each players
	 * @return true if the hits are equal, false if not
	 */
	public boolean boardFinished() {
		if(currentHits == HitsAmount) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get the grid size of the game
	 * @return  the grid size
	 * @uml.property  name="gridSize"
	 */
	public int getGridSize() {
		return GridSize;
	}

	/**
	 * Get each ship from the array list
	 * @return each ship
	 */
	public ArrayList<Ship> getShips() {
		return Ships;
	}
	
	public int totalHits() {
		return HitsAmount;
	}

	/**
	 * Print the direction of each ship in the console.
	 * If direction is 0, print H (horizontal) and so on.
	 */
	public void printShips(){
		cleanDebugger();
		System.out.println("");
		for(int i = 0; i < Ships.size(); i++){
			for(int j = 0; j < Ships.get(i).getSize()-1; j++){
				if(Ships.get(i).getDirection() == 0)
					debug[Ships.get(i).getY(j)][Ships.get(i).getX(j)] = "H  ";
				else if(Ships.get(i).getDirection() == 1)
					debug[Ships.get(i).getY(j)][Ships.get(i).getX(j)] = "V  ";
				else if(Ships.get(i).getDirection() == 2)
					debug[Ships.get(i).getY(j)][Ships.get(i).getX(j)] = "D  ";
			}
		}
	}

	/**
	 * This method get the board by each index after ships have been set.
	 * @param i the i index of the matrix
	 * @param j the j index of the matrix
	 * @return a number that is assigned of the board
	 */
	public int getBoardbyIndex(int i, int j){
		return game[i][j];
	}

	
	/**
	 * Print current grid using the debugger
	 */
	public void printDebugger() {

		System.out.println("\n");
		for(int z = 0; z < GridSize; z++){
			if(z>9)
				System.out.print(z-10*(z/10) + "  ");
			else
				System.out.print(z + "  ");
		}
		System.out.println();
		for(int i = 0; i < GridSize; i++) {
			for(int j = 0; j < GridSize; j++) {
				System.out.print(debug[i][j]);
			}
			System.out.print(i+"\n");
		}
	}
	/**
	 * Main tester
	 * @param args
	 */
	public static void main(String[]args){
		GridGenerator g = new GridGenerator(10, 8, true, true);
		Random rand = new Random();
		g.printGrid();

		for(int i = 0; i < 100; i++){
			g.TryInShip(rand.nextInt(10), rand.nextInt(10));
		}
	}
}