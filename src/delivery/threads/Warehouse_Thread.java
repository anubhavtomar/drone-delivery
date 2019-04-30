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

	}

	@Override
	public void run() {
		int o_count = this.w_inst.add_order();
		this.current_time_stamp = this.w_inst.get_delivery_time();
		if (o_count == 0) {
			this.interrupt();
		} else {
			while (this.w_inst.get_delivery_time()
					.compareTo(this.add_delta(this.current_time_stamp, 30 * 60 * 1000)) < 0)
				;
			this.run();
		}
	}

}
