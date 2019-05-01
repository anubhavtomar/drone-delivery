/**
 * 
 */
package delivery.threads;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import delivery.store.Order_Item;
import delivery.store.Order_Warehouse;

/**
 * @author anubhav
 *
 */
public class Warehouse_Thread extends Thread {

	Order_Warehouse w_inst;
	Date current_time_stamp;
	List<Order_Item> output_buffer;
	boolean STOP;

	private Date add_delta(Date _d, int _m) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(_d);
		calendar.add(Calendar.MILLISECOND, _m);
		return calendar.getTime();
	}

	public Warehouse_Thread(String name, Order_Warehouse _w) {
		super();
		this.w_inst = _w;
		this.current_time_stamp = this.w_inst.get_delivery_time();
		this.STOP = false;
	}
	
	public void stop_thread () {
		this.STOP = true;
	}

	@Override
	public void run() {
		if(this.STOP) { 
			System.out.println("Stopping Warehouse Thread");
			this.w_inst.close_file();
			return;
		}
		boolean is_end_of_file = this.w_inst.add_order();
		this.current_time_stamp = this.w_inst.get_delivery_time();
		if (is_end_of_file) {
			this.stop_thread();
		} else {
			while (this.w_inst.get_delivery_time().compareTo(this.add_delta(this.current_time_stamp, 30 * 60 * 1000)) < 0) {
				if(this.w_inst.is_empty()) {
					break;
				}
			};
		}
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}

}
