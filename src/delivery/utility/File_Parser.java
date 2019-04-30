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
	String distance_regex = "[NSEW](\\d+)[NSEW](\\d+)";
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
		this.pattern = Pattern.compile(distance_regex);
		this.format = new SimpleDateFormat("HH:mm:ss");
	}

	public List<Order_Item> readFile(Date _d) {
		List<Order_Item> _res = new ArrayList<>();
		Matcher matcher;
		while (this.file_scanner.hasNextLine()) {
			String line = this.file_scanner.nextLine();
			String[] words = line.split(" ");
			double _dist;
			matcher = pattern.matcher(words[1]);
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			_dist = Math.sqrt((x * x) + (y * y));
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
