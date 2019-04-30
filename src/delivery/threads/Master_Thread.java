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
	boolean STOP;

	public Master_Thread(String name, Order_Warehouse _w, File_Writer _f, Queue<Order_Item> _o) {
		super();
		this.warehouse = _w;
		this.output_buffer = _o;
		this.drone_th = new Drone_Thread("Drone Thread", this.warehouse, this.output_buffer);
		this.warehouse_th = new Warehouse_Thread("Warehouse Thread", this.warehouse);
		this.writer_th = new Writer_Thread("Writer Thread", _f, this.output_buffer);
		this.START = false;
		this.STOP = false;
	}
	
	public void stop_thread () {
		this.STOP = true;
	}

	@Override
	public void run() {
		if(this.STOP) {
			System.out.println("Interrupting Master Thread");
			return;
		}
		if (!this.START) {
			System.out.println("Starting Warehouse Thread");
			this.warehouse_th.start();
			System.out.println("Starting Drone Thread");
			this.drone_th.start();
			System.out.println("Starting Writer Thread");
			this.writer_th.start();
			this.START = true;
		}
		if (this.warehouse.is_empty() && !this.warehouse_th.isAlive()) {
			this.drone_th.stop_thread();
			if (this.output_buffer.isEmpty()) {
				this.writer_th.f_inst.write("NPS " + this.warehouse.get_NPS());
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.writer_th.stop_thread();
				try {
					sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (!this.writer_th.isAlive()) {
			this.stop_thread();
			return;
		}
		try {
			sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
}
