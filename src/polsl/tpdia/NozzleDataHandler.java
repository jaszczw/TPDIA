package polsl.tpdia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.math.stat.descriptive.summary.Sum;

import polsl.tpdia.models.AggregatedNozzleData;

public class NozzleDataHandler {
	public NozzleDataHandler( int aggregationInterval){
		AggregationInterval = aggregationInterval;
	}
	
	public int AggregationInterval;
	
	public List<AggregatedNozzleData> aggregateNozzleData(int step,double[] array, Calendar startingPoint,int nozzleId,int tankId,double temperature){
		Sum sum = new Sum();
		List<AggregatedNozzleData> aggregations = new ArrayList<AggregatedNozzleData>();
		
		for(int i = 0; i<array.length; i+=step){
			double result = sum.evaluate(array,i,step);
			Date aggStart = startingPoint.getTime();
			startingPoint.add(Calendar.MILLISECOND, AggregationInterval);
			Date aggEnd = startingPoint.getTime();
			
			AggregatedNozzleData aggregation =  new AggregatedNozzleData(nozzleId,tankId,aggStart,aggEnd,result,temperature);
			aggregations.add(aggregation);
		}
		
		return aggregations;
	}
}
