/**
 * 
 */
package delivery.utility;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import delivery.store.Order_Item;

/**
 * @author anubhav
 *
 */
public class Order_Item_Comparator implements Comparator<Order_Item> {

	Date current_delivery_time;
	Map<String, Integer> _priority_map;

	private String compute_map_key(long _h) {
		String res = "";
		if (_h < 0.5) {
			res = "<0.5";
		} else if (_h < 1.0) {
			res = "<1.0";
		} else if (_h < 1.5) {
			res = "<1.5";
		} else if (_h < 2.0) {
			res = "<2.0";
		} else if (_h < 2.5) {
			res = "<2.5";
		} else if (_h < 3.0) {
			res = "<3.0";
		} else if (_h < 3.5) {
			res = "<3.5";
		} else if (_h < 4.0) {
			res = "<4.0";
		} else {
			res = ">4.0";
		}
		return res;
	}

	public Order_Item_Comparator(Date _t) {
		super();
		this.current_delivery_time = _t;
		_priority_map = new HashMap<String, Integer>();
		this._priority_map.put("<0.5", 2); // Promoter
		this._priority_map.put("<1.0", 3); // Promoter
		this._priority_map.put("<1.5", 4); // Promoter
		this._priority_map.put("<2.0", 5); // Promoter
		this._priority_map.put("<2.5", 2); // Neutral
		this._priority_map.put("<3.0", 3); // Neutral
		this._priority_map.put("<3.5", 4); // Neutral
		this._priority_map.put("<4.0", 5); // Neutral
		this._priority_map.put(">4.5", 1); // Detractor
	}

	public void set_current_delivery_time(Date _t) {
		this.current_delivery_time = _t;
	}

	@Override
	public int compare(Order_Item o1, Order_Item o2) {
		long delta_t_1 = this.current_delivery_time.getTime() - o1.get_receive_time_stamp().getTime();
		long delta_t_2 = this.current_delivery_time.getTime() - o2.get_receive_time_stamp().getTime();
		delta_t_1 = delta_t_1 / (60 * 60 * 1000) % 24; // hours
		delta_t_2 = delta_t_2 / (60 * 60 * 1000) % 24; // hours
		String key_1 = this.compute_map_key(delta_t_1);
		String key_2 = this.compute_map_key(delta_t_2);
		int priority_1 = this._priority_map.get(key_1);
		int priority_2 = this._priority_map.get(key_2);
		if (priority_1 > priority_2) {
			return -1;
		} else if (priority_1 < priority_2) {
			return 1;
		} else {
			return o1.get_distance() < o2.get_distance() ? -1 : 1;
		}
	}
}
