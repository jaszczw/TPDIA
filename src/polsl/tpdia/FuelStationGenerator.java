package polsl.tpdia;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Set;

import au.com.bytecode.opencsv.CSVWriter;

public class FuelStationGenerator {

	public static void main(String[] args) throws ParseException, IOException {
		
		
		int rawDataInterval = 5000; //5s
		int nozzleAggInterval = 900000; //15min
		int tankAggInterval = 240000; //4min
		
		//Set parameters from args
		if(args.length==3){
			rawDataInterval = Integer.parseInt(args[0]);
			nozzleAggInterval = Integer.parseInt(args[1]);
			tankAggInterval = Integer.parseInt(args[2]);
		}
		
		StreamDataGenerator generator = new StreamDataGenerator(rawDataInterval, nozzleAggInterval, tankAggInterval);

		Calendar from = Calendar.getInstance();
		from.set(2014, 4, 10, 0, 0, 0);
		Calendar till = Calendar.getInstance();
		till.set(2014, 4, 10, 24, 0, 0);

		//Clear Files before saving
		CSVWriter nozzleMeasures = new CSVWriter(new FileWriter(
				"NozzleMeasures.csv"), ';');
		nozzleMeasures.close();
		CSVWriter tankMeasures = new CSVWriter(new FileWriter(
				"TankMeasures.csv"), ';');
		tankMeasures.close();

		generator.Generate(from, till);
	}

	public static Double aggregateValues(Set<Double> doubles) {
		return 2d;
	}

}