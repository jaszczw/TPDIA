package polsl.tpdia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import polsl.tpdia.models.AggregatedNozzleData;
import polsl.tpdia.models.AggregatedTankData;


public class TankDataHandler {
    
    public int tankVolume = 10000;

    public List<AggregatedTankData> aggregateTankData(List<AggregatedNozzleData> aggregatedNozzleData) {
        List<AggregatedTankData> aggregations = new ArrayList<AggregatedTankData>();
        
        double rawGasolineVolume = tankVolume;
        double tankedVolume = 0;
        
        for (int i = 0; i<aggregatedNozzleData.size(); i+=1) {
            tankedVolume += aggregatedNozzleData.get(i).ValueRaw;
            
            // measures maked every 2 hours
            if (((i + 1) % 8 == 0) || (i == aggregatedNozzleData.size() - 1)) {
                int tankId = aggregatedNozzleData.get(i).TankId;
                Date datePoint = aggregatedNozzleData.get(i).DateTo;
                rawGasolineVolume -= tankedVolume;
                AggregatedTankData aggregation = new AggregatedTankData(tankId, datePoint, rawGasolineVolume, 0d, 15d);
                aggregations.add(aggregation);
            }
        }
        
        return aggregations;
    }
}
