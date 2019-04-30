/**
 * 
 */
package delivery.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import delivery.store.Order_Item;

/**
 * @author anubhav
 *
 */
public class File_Writer {

	String file_name;
	File file_handle;
	BufferedWriter out;
	SimpleDateFormat format;

	public File_Writer(String _f) {
		this.file_name = "files/" + _f;
		this.file_handle = new File(this.file_name);
		try {
			this.out = new BufferedWriter(new FileWriter(this.file_handle));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.format = new SimpleDateFormat("HH:mm:ss");
	}

	public void write(Order_Item _o) {
		this.write(_o.get_id() + " " + _o.get_delivery_time_stamp().toString());
	}

	public void write(String _s) {
		try {
			this.out.write(_s);
			this.out.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
