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
	String distance_regex = "\\d+";
	Pattern pattern;
	SimpleDateFormat format;

	public File_Parser(String _f) {
		this.file_name = "files/" + _f;
		this.file_handle = new File(this.file_name);
		try {
			this.file_scanner = new Scanner(this.file_handle);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.pattern = Pattern.compile(this.distance_regex);
		this.format = new SimpleDateFormat("HH:mm:ss");
	}
	
	public void close_file () {
		System.out.println("Closing Input File");
		this.file_scanner.close();
	}

	public List<Order_Item> readFile(Date _d) {
		List<Order_Item> _res = new ArrayList<>();
		Matcher _m;
		while (this.file_scanner.hasNextLine()) {
			String line = this.file_scanner.nextLine();
			String[] words = line.split(" ");
			double _dist;
			_m = pattern.matcher(words[1]);
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
			_res.add(new Order_Item(words[0], _dist, _dt));
		}
		return _res;
	}
}
