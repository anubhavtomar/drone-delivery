/**
 * 
 *
 */
package delivery.interafces;

import java.util.Date;

import delivery.utility.Order_Category;

/**
 * @author anubhav
 *
 */
public interface Order_Item {

	public boolean set_id(String _id);

	public boolean set_distance(double _d);

	public boolean set_receive_time_stamp(Date _t);

	public boolean set_delivery_time_stamp(Date _t);

	public boolean set_category(Order_Category _c);

	public String get_id();

	public double get_distance();

	public Date get_receive_time_stamp();

	public Date get_delivery_time_stamp();

	public Order_Category get_category();
}
