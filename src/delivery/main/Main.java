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
//		Drone_Wrapper drone = new Drone_Wrapper(args[0], args[1]);
		Drone_Wrapper drone = new Drone_Wrapper("input.txt", "output.txt");
		drone.start_drone_delivery();

	}

}
