/**
 * 
 */
package delivery.threads;

import java.util.Queue;
import java.util.NoSuchElementException;

import delivery.store.Order_Item;
import delivery.store.Order_Warehouse;

/**
 * @author anubhav
 *
 */
public class Drone_Thread extends Thread {

	Order_Warehouse w_inst;
	Queue<Order_Item> output_buffer;
	boolean STOP;

	public Drone_Thread(String name, Order_Warehouse _w, Queue<Order_Item> _o) {
		super(name);
		this.w_inst = _w;
		this.output_buffer = _o;
		this.STOP = false;
	}
	
	public void stop_thread () {
		this.STOP = true;
	}

	@Override
	public void run() {
		if(this.STOP) {
			System.out.println("Interrupting Drone Thread");
			return;
		}
		try {
			this.output_buffer.add(this.w_inst.remove_order());
		} catch (NoSuchElementException _e) {
			_e.printStackTrace();
			this.stop_thread();
		}
		try {
			sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
}
