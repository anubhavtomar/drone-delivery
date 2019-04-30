/**
 * 
 */
package delivery.store;

import java.util.Date;

import delivery.utility.Order_Category;

/**
 * @author anubhav
 *
 */
public class Order_Item implements delivery.interafces.Order_Item {

	String id;
	double dist;
	Date receive_time_stamp;
	Date delivery_time_stamp;
	Order_Category category;

	public Order_Item(String _id, double _d, Date _t) {
		this.id = _id;
		this.dist = _d;
		this.receive_time_stamp = _t;
	}

	@Override
	public boolean set_id(String _id) {
		try {
			this.id = _id;
			return true;
		} catch (Error err) {
			System.out.println(err);
			return false;
		}
	}

	@Override
	public boolean set_distance(double _d) {
		try {
			this.dist = _d;
			return true;
		} catch (Error err) {
			System.out.println(err);
			return false;
		}
	}

	@Override
	public boolean set_receive_time_stamp(Date _t) {
		try {
			this.receive_time_stamp = _t;
			return true;
		} catch (Error err) {
			System.out.println(err);
			return false;
		}
	}

	@Override
	public boolean set_delivery_time_stamp(Date _t) {
		try {
			this.delivery_time_stamp = _t;
			return true;
		} catch (Error err) {
			System.out.println(err);
			return false;
		}
	}

	@Override
	public boolean set_category(Order_Category _c) {
		try {
			this.category = _c;
			return true;
		} catch (Error err) {
			System.out.println(err);
			return false;
		}
	}

	@Override
	public String get_id() {
		return this.id;
	}

	@Override
	public double get_distance() {
		return this.dist;
	}

	@Override
	public Date get_receive_time_stamp() {
		return this.receive_time_stamp;
	}

	@Override
	public Date get_delivery_time_stamp() {
		return this.delivery_time_stamp;
	}

	@Override
	public Order_Category get_category() {
		return this.category;
	}

}
