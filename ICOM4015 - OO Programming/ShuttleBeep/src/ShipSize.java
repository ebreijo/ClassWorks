import java.io.Serializable;


public class ShipSize implements Serializable{

	
	private static final long serialVersionUID = -6067964476525062730L;
	private  int amountS = 0;
	private  int[] ships;
	private int s = 0;
	
	public ShipSize(int amount){
		if(amount >= 5 && amount <= 8)
			amountS = amount;
		else
			amountS = 8;
		setShipsSize();
	}
	private void setShipsSize(){
		ships = new int[amountS];
		int a = 2;
		for(int i = 0; i<amountS; i++){
			//System.out.println(i);
			ships[i] = a;
			//System.out.println("\t"+ships[i]);
			a++;
		}
	}

	public int getShipSize(int i){
		return ships[i];
	}

	public int getSize(){
		return amountS;
	}
	
	public int getNext(){
		int actual = ships[s];
		s++;
		return actual;	
	}
	
	
	//public static void main(String[]args){
		//ShipSize a = new ShipSize(8);
		
		//for(int i = 0; i<a.getSize(); i++)
			//System.out.print(a.getNext());
	//}
}
