/**
 * 
 */
package delivery.main;

import delivery.wrappers.Drone_Wrapper;

/**
 * @author anubhav
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Error: Invalid input file argument");
			return;
		}
		Drone_Wrapper drone = new Drone_Wrapper(args[0]);
//		Drone_Wrapper drone = new Drone_Wrapper("/Users/anubhav/repo/drone-delivery/files/input.txt");
//		Drone_Wrapper drone = new Drone_Wrapper("/Users/anubhav/Desktop/input.txt");
		drone.start_drone_delivery();
	}
}
