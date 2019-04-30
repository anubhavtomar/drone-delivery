/**
 * 
 */
package delivery.store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import delivery.utility.*;

/**
 * @author anubhav
 *
 */
public class Order_Warehouse implements delivery.interafces.Order_Warehouse {

	Date current_delivery_time;
	PriorityQueue<Order_Item> orders_q;
	List<Order_Item> orders_list;
	int _q_size;
	File_Parser _f;
	SimpleDateFormat format;
	double NPS;
	int num_promoters;
	int num_detractors;

	private Date add_delta(Date _d, int _m) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(_d);
		calendar.add(Calendar.MILLISECOND, _m);
		return calendar.getTime();
	}

	private Order_Category compute_order_category(long _h) {
		if (_h > 0 && _h < 2) {
			return Order_Category.Promoter;
		} else if (_h > 0 && _h < 2) {
			return Order_Category.Neutral;
		} else {
			return Order_Category.Detractor;
		}
	}

	private void compute_NPS(Order_Item _o) {
		if (_o.get_category() == Order_Category.Promoter) {
			this.num_promoters++;
		} else if (_o.get_category() == Order_Category.Detractor) {
			this.num_detractors++;
		}
		int total = this.orders_list.size();
		this.NPS = ((this.num_promoters - this.num_detractors) / total) * 100;
	}

	public Order_Warehouse(String file_name) {
		this.format = new SimpleDateFormat("HH:mm:ss");
		try {
			this.current_delivery_time = this.format.parse("06:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.orders_q = new PriorityQueue<Order_Item>(new Order_Item_Comparator(this.current_delivery_time));
		this.orders_list = new ArrayList<Order_Item>();
		this._q_size = 0;
		this._f = new File_Parser(file_name);
		this.num_promoters = 0;
		this.num_detractors = 0;
	}

	@Override
	public Date get_delivery_time() {
		return this.current_delivery_time;
	}

	public double get_NPS() {
		return this.NPS;
	}

	public boolean is_empty() {
		return this._q_size == 0;
	}

	@Override
	public void update_size() {
		this._q_size = this.orders_q.size();
	}

	@Override
	public synchronized Order_Item remove_order() {
		if (this.orders_q.isEmpty()) {
			throw new NoSuchElementException("Warehouse is Empty");
		}
		Order_Item _res = this.orders_q.remove();
		_res.set_delivery_time_stamp(this.current_delivery_time);
		double _d = 2 * _res.get_distance();
		int _m = (int) (_d * 60 * 1000);
		this.current_delivery_time = this.add_delta(this.current_delivery_time, _m);
		_d /= 2;
		_m = (int) (_d * 60 * 1000);
		long delta = this.add_delta(this.current_delivery_time, -_m).getTime()
				- _res.get_receive_time_stamp().getTime();
		delta = delta / (60 * 60 * 1000) % 24;
		_res.set_category(this.compute_order_category(delta));
		this.orders_list.add(_res);
		this.compute_NPS(_res);
		this.update_size();
		return _res;
	}

	@Override
	public synchronized int add_order() {
		List<Order_Item> _l = this._f.readFile(this.current_delivery_time);
		try {
			boolean status = this.orders_q.addAll(_l);
			this.update_size();
			System.out
					.println(status ? "New items are inserted in the queue" : "Got error while inserting in the queue");
			System.out.println("Queue Size : " + this._q_size);
			return _l.size();
		} catch (Error err) {
			System.out.println(err);
			System.out.println("Got error while inserting in the queue");
			return 0;
		}
	}

}
