/**
 * 
 */
package delivery.threads;

import java.util.Queue;

import delivery.store.Order_Item;
import delivery.utility.File_Writer;

/**
 * @author anubhav
 *
 */
public class Writer_Thread extends Thread {

	File_Writer f_inst;
	Queue<Order_Item> output_buffer;
	boolean STOP;

	public Writer_Thread(String name, File_Writer _f, Queue<Order_Item> _o) {
		super();
		this.f_inst = _f;
		this.output_buffer = _o;
		this.STOP = false;
	}
	
	public void stop_thread () {
		this.STOP = true;
	}
	
	@Override
	public void run() {
		if(this.STOP) {
			System.out.println("Stopping Writer Thread");
			this.f_inst.close_file();
			return;
		}
		while (!this.output_buffer.isEmpty()) {
//			System.out.println("Writing");
			this.f_inst.write(this.output_buffer.remove());
		}
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		while (this.output_buffer.size() < 3);
		this.run();
	}
}
