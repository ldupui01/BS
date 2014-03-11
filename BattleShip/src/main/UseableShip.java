/**
 * 
 */
package main;

/**
 * @author Guilherme
 *
 */
public class UseableShip extends Ship {

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public String getShipType() {//TODO only use this is if it can go under the abstract class. otherwise just use the actual name.
		String shipName[] = this.getClass().getSimpleName().split("(?=\\p{Upper})");
		String shipType = shipName[1] + " " + shipName[2];
		return shipType;
	}
	
	@Override
	public String toString(){
		String state = "";
		
		if(this.isSunk())
			state = "x";
		else
			state = "S";
		
		return state;
	}

}
