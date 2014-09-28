package polsl.tpdia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import polsl.tpdia.models.AggregatedNozzleData;
import polsl.tpdia.models.RawPrimaryData;

public class NozzleDataHandler {
	public NozzleDataHandler( int aggregationInterval){
		AggregationInterval = aggregationInterval;
	}
	
	public int AggregationInterval;
	
	/***
	 * Method returns aggregations of Generated data with settings passed to it
	 * @param step - Amount of Primary data aggregated into one in each step
	 * @param rawData - List of Generated Primary data
	 * @param startingPoint
	 * @return Aggregated Data
	 */
	public List<AggregatedNozzleData> aggregateNozzleData(int step,ArrayList<RawPrimaryData> rawData, Calendar startingPoint){
		List<AggregatedNozzleData> aggregations = new ArrayList<AggregatedNozzleData>();
		
		for(int i = 0; i<rawData.size(); i+=step){
			List<RawPrimaryData> subList = rawData.subList(i,i+ step);
			
			double sum = 0;
			double sumTemperature = 0;
			int nozzleId = 0;
			int tankId = 0;
			
			for(RawPrimaryData data : subList){
				sum+=data.RawFuelNozzleAmount;
				sumTemperature+=data.Temperature;
				tankId=data.TankId;
				nozzleId=data.NozzleId;
			}
			
			Date aggStart = startingPoint.getTime();
			startingPoint.add(Calendar.MILLISECOND, AggregationInterval);
			Date aggEnd = startingPoint.getTime();
			
			AggregatedNozzleData aggregation =  new AggregatedNozzleData(nozzleId,tankId,aggStart,aggEnd,sum,sumTemperature/step);
			aggregations.add(aggregation);
		}
		
		return aggregations;
	}
}
