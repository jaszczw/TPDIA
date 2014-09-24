package polsl.tpdia.models;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;

public class AggregatedNozzleData {
	public AggregatedNozzleData(int nozzleId, int tankId, Date aggStart,
			Date aggEnd, double rawValue, double temperature) {
		NozzleId = nozzleId;
		TankId = tankId;
		DateFrom = aggStart;
		DateTo = aggEnd;
		ValueRaw = rawValue;
		Temperature = temperature;
	}

	public int NozzleId;
	public int TankId;
	public Date DateFrom;
	public Date DateTo;
	public double ValueRaw;
	public double Temperature;

	public void printToConsole() throws FileNotFoundException, IOException {
		System.out.println(NozzleId + " | " + TankId + " | " + DateFrom + " | "
				+ DateTo + " | " + ValueRaw + " | " + Temperature);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");

		CSVWriter nozzleMeasures = new CSVWriter(new FileWriter(
				"NozzleMeasures.csv", true), ';', CSVWriter.NO_QUOTE_CHARACTER);
		String[] entries = (NozzleId + "#" + TankId + "#"
				+ simpleDateFormat.format(DateFrom) + "#"
				+ simpleDateFormat.format(DateTo) + "#" + ValueRaw + "###" + Temperature)
				.split("#");
		nozzleMeasures.writeNext(entries);
		nozzleMeasures.close();

	}
}