/**
 * 
 */
package delivery.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import delivery.store.Order_Item;

/**
 * @author anubhav
 *
 */
public class File_Parser {

	String file_name;
	File file_handle;
	Scanner file_scanner;
	String order_input_regex = "^WM\\d+\\s([NSEW]\\d+){1,2}\\s([0-9]{2}+[:]){2}[0-9]{2}$";
	String distance_regex = "\\d+";
	Pattern order_pattern;
	Pattern distance_pattern;
	SimpleDateFormat format;
	String prev_str_read;

	public File_Parser(String _f) {
//		this.file_name = "files/" + _f;
		this.file_name = _f;
		this.file_handle = new File(this.file_name);
		try {
			this.file_scanner = new Scanner(this.file_handle);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.distance_pattern = Pattern.compile(this.distance_regex);
		this.order_pattern = Pattern.compile(this.order_input_regex);
		this.format = new SimpleDateFormat("HH:mm:ss");
		this.prev_str_read = "";
	}
	
	public void close_file () {
//		System.out.println("Closing Input File");
		this.file_scanner.close();
	}
	
	private Order_Item parse_and_add_line (String line , Date _d) {
		Matcher _m;
		String[] words = line.split(" ");
		double _dist;
		_m = this.distance_pattern.matcher(words[1]);
		int x = 0;
		int y = 0;
		if(_m.find()) {
			x = Integer.parseInt(_m.group());
		}
		if(_m.find()) {
			y = Integer.parseInt(_m.group());
		}
		_dist = Math.sqrt(x * x + y * y);
		Date _dt = new Date();
		try {
			_dt = this.format.parse(words[2]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(_dt.after(_d)) {
			this.prev_str_read = line;
			return null;
		}
		return new Order_Item(words[0], _dist, _dt);
	}

	public List<Order_Item> readFile(Date _d) {
		List<Order_Item> _res = new ArrayList<>();
		Order_Item _o;
		if(this.prev_str_read.length() != 0) {
			_o = this.parse_and_add_line(this.prev_str_read , _d);
			_res.add(_o);
			this.prev_str_read = "";
		}
		while (this.file_scanner.hasNextLine()) {
			String line = this.file_scanner.nextLine();
			if(!this.order_pattern.matcher(line).matches()) {
				continue;
			}
			_o = this.parse_and_add_line(line , _d);
			if(_o == null) {
				break;
			}
			_res.add(_o);
		}
		return _res;
	}
}
