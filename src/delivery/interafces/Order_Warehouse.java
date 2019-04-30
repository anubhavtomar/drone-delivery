/**
 * 
 */
package delivery.interafces;

import java.util.Date;

import delivery.store.Order_Item;

/**
 * @author anubhav
 *
 */
public interface Order_Warehouse {

	public void update_size();

	public Date get_delivery_time();

	public Order_Item remove_order();

	public int add_order();
	
	public void close_file ();
	
	public double get_NPS();
	
	public boolean is_empty();
}
