package polsl.tpdia.models;
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
	
	

	public void printToConsole(){
		System.out.println(TankId + " " + DatePoint + " " +RawGasolineVolume + " " +RawWaterVolume + " " + Temperature);
	}
}
