package main;
/**
 * @author Ludo
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class OceanImpl implements Ocean {
	private ShipInter[][] shipArray; //TODO maybe a static field could make it safer !
	private int shotFired;
	private int hitCount;
	private int shipSunk;
	//private String ocean;
	//private String[][] oceanGrid;
	private List<ShipInter> fleet;
	private int fleetInitSize;
	private int maxGrid;

	public OceanImpl(){
		//Create an empty ocean fills the ship  array with EmptySeas.
		// Also initialises game variables such as how many shots have been fired.
		maxGrid = 10;
		//this.initOcean();
		shipArray = new ShipInter[maxGrid][maxGrid];//added by G
		this.initShipArray();
		fleet = new ArrayList<ShipInter>();
		this.admiral();
		shotFired = 0;
		hitCount = 0;
		shipSunk = 0;
		fleetInitSize = fleet.size();
	}
	
	private void admiral(){
		//Setting the fleet
		//TODO ******** To set as FACTORY ******************************
		//TODO ************ WARNING ********* IF THE LIST BECOME OBSOLETE **** CHECK placeAllShipsRandomly METHOD TO MODIFY ACCORDINGLY
		//TODO ************ WARNING ********* IF THE LIST BECOME OBSOLETE **** CHECK isGameOver METHOD TO MODIFY ACCORDINGLY
		ShipInter carrier = new AircraftCarrier();
		fleet.add(carrier);
		ShipInter battleship1 = new Battleship();
		fleet.add(battleship1);
		ShipInter battleship2 = new Battleship();
		fleet.add(battleship2);
		ShipInter submarine1 = new Submarine();
		fleet.add(submarine1);
		ShipInter submarine2 = new Submarine();
		fleet.add(submarine2);
		ShipInter destroyer1 = new Destroyer();
		fleet.add(destroyer1);
		ShipInter destroyer2 = new Destroyer();
		fleet.add(destroyer2);
		ShipInter patrol1 = new PatrolBoat();
		fleet.add(patrol1);
		ShipInter patrol2 = new PatrolBoat();
		fleet.add(patrol2);
		ShipInter patrol3 = new PatrolBoat();
		fleet.add(patrol3);
		ShipInter patrol4 = new PatrolBoat();
		fleet.add(patrol4);
	}
	
	private int randInt(int min, int max){
		// the max is excluded, but the min is included from the random generation 
		// i.e. call random from 0 to 10 to fill up a grid [0 - 9]
		Random rand = new Random();
		return rand.nextInt(max-min);
	}
	
	public void placeAllShipsRandomly(){
		//place all the ships randomly on the initially empty ocean.
		//Place larger ships before smaller ones to avoid "no legal move"
		//Use Random class Java.util
		
		Iterator<ShipInter> it = fleet.iterator();
		while (it.hasNext()){
		ShipInter obj = it.next(); //TODO test the iterator is actually starting with the big boat
			boolean place = false;
			boolean horiz = false;
			int row = -1, column = -1; // initialised at -1 to make sure the random setting is working
			while (!place){
				row = randInt(0, maxGrid);
				column = randInt(0, maxGrid);
				horiz = (randInt(0,2)>0)? true:false;
				place = obj.okToPlaceShipAt(row, column, horiz, this); //TODO Can Ocean class refer to itself with the keyword this????
			}
			obj.placeShipAt(row, column, horiz, this);
			//TODO check if below setShipArray for the bow is done in Ship Class
			for (int i = 0;i<obj.getLength();i++){
				if(horiz){
					setShipArray(obj, row, column+i);
				}else{
					setShipArray(obj, row+i, column);
				}
			}
		}	
	}
	
	public boolean isOccupied(int row, int column){
		//returns True if the given location contains a ship, false if not
		ShipInter es = new EmptySea();
		return(shipArray[row][column].equals(es))?false:true;
		//return(oceanGrid[row][column]=="S" || oceanGrid[row][column]=="x")?true:false;
	}
	
	public boolean shootAt(int row, int column){
		//returns True if given location contains a ship still afloat, false if it does not
		//returns True if several shot fired at the same location as long as the ship is afloat, false otherwise
		//update the number of shot fired
		shotFired++;
		ShipInter s = shipArray[row][column];
		ShipInter es = new EmptySea();
		if(s.getClass().equals((es.getClass()))){
			s.shootAt(row, column);
			return false;
		}else{
			hitCount++;
			s.shootAt(row, column);
			if(s.isSunk()){
				fleet.remove(s);
			}
			return true;
		}
	}
	
	public int getShotsFired(){
		//returns the number of shot fired
		return shotFired;
	}
	
	public int getHitCount(){
		//returns the number of hit recorded (even several shot at the same place)
		return hitCount;
	}
	
	public int getShipsSunk(){
		//returns the number of ship sunk;
		shipSunk = fleetInitSize - fleet.size();
		return shipSunk;
	}
	
	public boolean isGameOver(){
		return (fleet.isEmpty())? true:false;
	}
	
	public ShipInter[][] getShipArray(){
		//returns the grid of ship
		return shipArray;
	}
	
	@Override
	public String toString(){
		//TODO finalise the toString
		String ocean = "";
		//return the string representing the ocean
		// row number on the left from 0 to 9
		// column number on the top from 0 to 9
		// S: hit on ship
		// -: hit on water
		// x: sunken ship
		// .: location that has not been fired at
		
		return ocean;
	}
	
	/*private void initOcean(){
		oceanGrid = new String[maxGrid][maxGrid];
		for(int i = 0; i<maxGrid-1;i++){
			for (int j=0;j<maxGrid-1;j++)
				oceanGrid[i][j]=".";
		}
	}*/
	
	private void initShipArray(){
		ShipInter es = new EmptySea();
		for(int i = 0; i<maxGrid-1;i++){
			for (int j=0;j<maxGrid-1;j++)
				setShipArray(es, i, j);
		}
	}
	
	public void setShipArray(ShipInter ship, int row, int column){
		this.shipArray[row][column]= ship;
	}
	
	public ShipInter identifyShip(int row, int column){
		//TODO Check if it used, if not to be deleted from Interface also !!!!!!!!
		Iterator<ShipInter> it = fleet.iterator();
		while (it.hasNext()){
			ShipInter obj = it.next(); 
			for(int i = 0; i<obj.getLength();i++){
				if (obj.isHorizontal()){
					if (obj.getBowRow() == row && obj.getBowColumn()+i == column){
						return obj;
					}
				}else{
					if (obj.getBowRow()+i == row && obj.getBowColumn() == column){
						return obj;
					}
				}
			}
		}
		ShipInter es= new EmptySea(); //TODO replace new Emptysea by the actual Emptysea from that location
		return es;
	}
}
