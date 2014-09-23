package polsl.tpdia.models;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

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
	
	

	public void printToConsole() throws IOException{
		System.out.println(TankId + " " + DatePoint + " " +RawGasolineVolume + " " +RawWaterVolume + " " + Temperature);
                
                CSVWriter tankMeasures = new CSVWriter(new FileWriter("TankMeasures.csv", true), ';');
                String[] entries = (TankId + "#" + DatePoint + "#" +RawGasolineVolume + "##" +RawWaterVolume + "#" + Temperature).split("#");
                tankMeasures.writeNext(entries);
                tankMeasures.close();
	}
}
