/**
 * 
 */
package delivery.threads;

import java.util.Queue;

import delivery.store.Order_Item;
import delivery.store.Order_Warehouse;
import delivery.utility.File_Writer;

/**
 * @author anubhav
 *
 */
public class Master_Thread extends Thread {

	Order_Warehouse warehouse;
	Queue<Order_Item> output_buffer;
	Drone_Thread drone_th;
	Warehouse_Thread warehouse_th;
	Writer_Thread writer_th;
	boolean START;

	public Master_Thread(String name, Order_Warehouse _w, File_Writer _f, Queue<Order_Item> _o) {
		super();
		this.warehouse = _w;
		this.output_buffer = _o;
		this.drone_th = new Drone_Thread("Drone Thread", this.warehouse, this.output_buffer);
		this.warehouse_th = new Warehouse_Thread("Warehouse Thread", this.warehouse);
		this.writer_th = new Writer_Thread("Writer Thread", _f, this.output_buffer);
		this.START = false;
	}

	@Override
	public void run() {
		if (!this.START) {
			this.warehouse_th.start();
			this.drone_th.start();
			while (this.output_buffer.size() < 10)
				;
			this.writer_th.start();
			this.START = true;
		}
		if (this.warehouse.is_empty() && this.warehouse_th.isInterrupted()) {
			this.drone_th.interrupt();
			if (this.output_buffer.isEmpty()) {
				this.writer_th.f_inst.write("NPS " + this.warehouse.get_NPS());
				this.writer_th.interrupt();
			}
		}
		if (this.writer_th.isInterrupted()) {
			this.interrupt();
			return;
		}
		this.run();
	}
}
