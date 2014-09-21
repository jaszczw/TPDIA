import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.descriptive.summary.Sum;

public class HelloWorld {


	public static void main(String[] args) throws ParseException {
		StreamDataGenerator generator = new StreamDataGenerator();
		
		Calendar from = Calendar.getInstance();
		from.set(2014, 4, 10, 0, 0);
		Calendar till = Calendar.getInstance();
		till.set(2014, 4, 10, 24, 0);

		
		Map<Calendar,Double> measures = generator.Generate(from,till);
		
		
	}

	public static Double aggregateValues(Set<Double> doubles){
		return 2d;
	}
	
	

}