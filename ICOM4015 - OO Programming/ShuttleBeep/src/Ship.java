import java.io.Serializable;

/**
 * The Ship class set all the ships 
 * @author David && Eduardo
 */
public class Ship implements Serializable{

	
	private static final long serialVersionUID = -6665649466662150088L;
	private int x = 0; // Ship's X position
	private int y = 0; // Ship's Y position
	private int[] body; // Know where the ships are placed in the matrix
	private int[][] crd; // Cell coordinates of the ship
	private int size; // Ship Size
	private int direction; // Ship direction
	private int indexj = 0; //index for getting stored status
	private int auxS; // aux variable for returning cell status
	private Sound hitSound; // A sound when a ship is discovered
	private Sound missSound; // A sound when a miss is made.
	
	/**
	 * Construct objects for the sound, initialize fields
	 * @param x 
	 * @param y 
	 * @param size the size of each ship
	 * @param direction the direction of each ship
	 */
	public Ship(int x, int y, int size, int direction){
		this.x = x;
		this.y = y;
		this.size = size;
		this.direction = direction;
		body = new int[size];
		crd = new int[size][2];
		initialBody();
		
		hitSound = new Sound();
		hitSound.soundEffect("Sounds/hit.wav");
		
		missSound = new Sound();
		missSound.soundEffect("Sounds/miss.wav");		
	}

	/**
	 * Verify if the ship has been discovered
	 * @return
	 */
	public boolean innerDiscovery() {
		boolean aux = true;
		for(int i = 0; i < size; i++) {
			if(body[i] == 0)
				aux = false;
		}
		return aux;
	}

	/**
	 * This method change the numbers of the body array when the entire ship is discovered
	 */
	public void Discovered() {
		if(innerDiscovery()) {
			//discoverySound.play();
			body[0] = 12;
			body[size-1] = 11;
			for(int i = 1; i < size-1; i++) {
				body[i] = 13;
			}
		}	
	}

	/**
	 * Set the initial body of each ship
	 */
	public void initialBody() {
		for(int i = 0; i < size; i++){
			body[i] = 0;
		}
	}

	/**
	 * Get the direction of ships
	 * @return  a number that represent direction
	 * @uml.property  name="direction"
	 */
	public int getDirection(){
		return direction;
	}

	/**
	 * Get each part of the ship
	 * @param i an index
	 * @return a number that represent each part of the ship
	 */
	public int getBody(int i) {
		return body[i];
	}

	/**
	 * Get the size of the ship
	 * @return  the size
	 * @uml.property  name="size"
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the coordinates of the game
	 */
	public void setCoordenates(){
		int aux;
		int aux2;
		if(direction == 0){
			aux = x;

			for(int i = 0; i < size; i++){
				crd[i][1] = y;
				crd[i][0] = aux;
				aux++;
			}
		}

		else if(direction == 1){
			aux = y;
			for(int j = 0; j < size; j++){
				crd[j][0] = x;
				crd[j][1] = aux;
				aux++;
			}
		}
		else{
			aux = y;
			aux2 = x;
			for(int k = 0; k < size; k++){
				crd[k][0] = aux2;
				crd[k][1] = aux;
				aux++;
				aux2++;
			}
		}
	}

	/**
	 * Try the game by entering the coordinate
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void tryMe(int x, int y) {
		//System.out.println("Trying " + "{"+x+"-"+y+"}");
		System.out.println("");
		for(int i = 0; i < size; i++) {
			if(x == crd[i][0] && y == crd[i][1]) {
				body[i] = 1;
				hitSound.play();
			}
			Discovered();
		}
		printShip();
	}

	public int cellStatus(){
		auxS = body[indexj];
		indexj++;
		return auxS;		
	}


	public int getX(int i){	
		return crd[i][0];
	}


	public int getY(int j){
		return crd[j][1];
	}

	public void printShip(){
		for(int i = 0; i < size; i++){
			System.out.print(body[i]+"-");
		}
		if(direction == 0)
			System.out.print(" H");
		else if(direction == 1)
			System.out.print(" V");
		else
			System.out.print(" D");
		
	}

	public void printCrds() {
		System.out.println("");
		for(int i = 0; i < size; i++){
			System.out.println("("+crd[i][0]+"-"+crd[i][1]+")");
		}
	}
	public static void main(String[]args){
		Ship test = new Ship(3, 0, 8, 2);
		test.printShip();
		test.initialBody();
		test.setCoordenates();
		test.printCrds();
		test.tryMe(6, 3);
		System.out.println("");
	}
}

