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

	public Writer_Thread(String name, File_Writer _f, Queue<Order_Item> _o) {
		super();
		this.f_inst = _f;
		this.output_buffer = _o;
	}

	@Override
	public void run() {
		while (!this.output_buffer.isEmpty()) {
			this.f_inst.write(this.output_buffer.remove());
		}
		while (this.output_buffer.size() < 10)
			;
		this.run();
	}
}
