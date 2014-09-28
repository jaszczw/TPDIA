package polsl.tpdia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import polsl.tpdia.models.AggregatedTankData;
import polsl.tpdia.models.RawPrimaryData;


public class TankDataHandler {
    
    public double tankVolume = 7.5;
	public double oneLtoFuelHeightProportion = 0.000379552d;
	private int interval = 48; // 4minutes
	
	public TankDataHandler(int interval){
		Random rand =new Random();
		tankVolume = rand.nextGaussian()*2 + 7.5;
		this.interval = interval;
	}

	/**
	 * Aggregates generated data into AggregatedTankData Records
	 * @param rawData - records generated 
	 * @return list of aggregated records.
	 */
    public List<AggregatedTankData> aggregateTankData(List<RawPrimaryData> rawData) {
        List<AggregatedTankData> aggregations = new ArrayList<AggregatedTankData>();
        
        double rawGasolineVolume = tankVolume;
        double tankedVolume = 0;
        double waterVolume = 0;
        
        for (int i = 0; i<rawData.size(); i+=1) {
            tankedVolume += rawData.get(i).RawFuelVolume;
            waterVolume += rawData.get(i).RawWaterVolume;
            
            //Interval - amount of primarty records taken into one aggregation
            if (((i + 1) % interval == 0) || (i == rawData.size() - 1)) {
                int tankId = rawData.get(i).TankId;
                Date datePoint = rawData.get(i).PointInTime;
                rawGasolineVolume -= tankedVolume;
                AggregatedTankData aggregation = new AggregatedTankData(tankId, datePoint, rawGasolineVolume, waterVolume, rawData.get(i).Temperature);
                aggregations.add(aggregation);
            	tankedVolume=0;
            }
        }
        
        return aggregations;
    }
}


