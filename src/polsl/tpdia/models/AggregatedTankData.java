package polsl.tpdia.models;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class representing one row of aggregated Tank Data
 */
public class AggregatedTankData {
	public AggregatedTankData(int tankId, Date datePoint, double rawGasolineVolume, double rawWaterVolume, double temperature){
		TankId= tankId;
		DatePoint= datePoint;
		RawGasolineVolume= rawGasolineVolume;
		RawWaterVolume= rawWaterVolume;
		Temperature= temperature;
	}
	
	public int TankId;
	public Date DatePoint;
	public double RawGasolineVolume;
	public double RawWaterVolume;
	public double Temperature;
	
	
	/**
	 * Appends aggregation file with data from object
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void printToFile() throws IOException{
		System.out.println(TankId + " " + DatePoint + " " +RawGasolineVolume + " " +RawWaterVolume + " " + Temperature);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		
        CSVWriter tankMeasures = new CSVWriter(new FileWriter("TankMeasures.csv", true), ';', CSVWriter.NO_QUOTE_CHARACTER);
        String[] entries = (TankId + "#" + simpleDateFormat.format(DatePoint) + "#" +RawGasolineVolume + "##" +RawWaterVolume + "#" + Temperature).split("#");
        tankMeasures.writeNext(entries);
        tankMeasures.close();
	}
}
