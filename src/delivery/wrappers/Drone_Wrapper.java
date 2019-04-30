/**
 * 
 */
package delivery.wrappers;

import java.util.ArrayDeque;
import java.util.Queue;

import delivery.store.Order_Item;
import delivery.store.Order_Warehouse;
import delivery.utility.File_Writer;
import delivery.threads.Master_Thread;

/**
 * @author anubhav
 *
 */
public class Drone_Wrapper {

	Order_Warehouse warehouse;
	File_Writer _writer;
	Queue<Order_Item> output_buffer;
	Master_Thread master_th;

	public Drone_Wrapper(String f_in, String f_out) {
		this.warehouse = new Order_Warehouse(f_in);
		this._writer = new File_Writer(f_out);
		this.output_buffer = new ArrayDeque<Order_Item>(10);
		this.master_th = new Master_Thread("Master Thread", this.warehouse, this._writer, this.output_buffer);
	}

	public void start_drone_delivery() {
		try {
			this.master_th.start();
		} catch (Error err) {
			System.out.println(err);
		}
	}
}
