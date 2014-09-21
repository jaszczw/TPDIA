import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.lang.model.type.PrimitiveType;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.descriptive.summary.Sum;


public class StreamDataGenerator {
	public Long deviationTimeToNextClient = 60l;
	public Long meanTimeToNextClient = 60l;
	public Long deviationAmountToTank = 15l;
	public Long meanAmountToTank = 35l;
	public Double msTimeFor1l = 1800d; 	// 1l/1.8s
	public Integer interval = 5000; // 5s
	public int aggregationInterval = 900000; // 15min
	public Long minuteToMs = 60000l;
	
	
	public StreamDataGenerator(){
		
	}

	public Double getRandValueToTankInL(Random randomGenerator) {
		return randomGenerator.nextGaussian() * deviationAmountToTank
				+ meanAmountToTank;
	}

	public Double nextTankInMinutes(Random randomGenerator) {
		return randomGenerator.nextGaussian() * deviationTimeToNextClient
				+ meanTimeToNextClient;
	}
	
	public Map<Calendar,Double> Generate(Calendar dateFrom, Calendar dateTo){

		Calendar startingDate = (Calendar) dateFrom.clone();
		Calendar endDate = (Calendar) dateTo.clone();

		Long ticksAmount = (endDate.getTimeInMillis() - startingDate.getTimeInMillis())
				/ interval;
		
		Random randomGenerator = new Random();
		Double nextTank = nextTankInMinutes(randomGenerator) * minuteToMs;
		
		Map<Calendar,Double> dict =  new HashMap<Calendar, Double>();
		ArrayList<Double> values = new ArrayList<Double>();

		Double amountOfFuelInTick= interval/msTimeFor1l;
		Double valueToTank = 0d;
		
		for (Integer i = 0; i < ticksAmount; i++, nextTank -= interval, startingDate.add(Calendar.MILLISECOND, interval)) {
			if (nextTank <= 0 && valueToTank<=0) {
				valueToTank = Math.max(0,getRandValueToTankInL(randomGenerator));
				nextTank = nextTankInMinutes(randomGenerator) * minuteToMs;
			}
			
			if(valueToTank>0){
				System.out.print(Math.min(amountOfFuelInTick, valueToTank));
				System.out.println(" \t" + startingDate.getTime());
				double tankedValue = Math.min(amountOfFuelInTick, valueToTank);
				dict.put(startingDate, tankedValue);
				values.add(tankedValue);
				valueToTank-= tankedValue;
			} else{
				System.out.println("0 \t" + startingDate.getTime());
				dict.put(startingDate, 0d);
				values.add(0d);
			}
		}
		
		//.toArray()
//		double[] primitiveArray = ArrayUtils.toPrimitive(dict.values().toArray(new Double[dict.values().size()]));
		double[] primitiveArray = ArrayUtils.toPrimitive(values.toArray(new Double[values.size()]));

		
		int step = (int) (aggregationInterval/interval);
		
		aggregateData(step,primitiveArray,(Calendar)dateFrom.clone());
		
		return dict;
	}
	
	public double[] aggregateData(int step,double[] array, Calendar startDate){
		Sum sum = new Sum();
		
		ArrayList<Double> aggregatedData = new ArrayList<Double>();
		
		for(int i = 0; i<array.length; i+=step){
			double result = sum.evaluate(array,i,step);
			aggregatedData.add(result);
			System.out.println(result +" \t" + startDate.getTime());
			startDate.add(Calendar.MILLISECOND, aggregationInterval);
		}
		
		return null;
	}

	
	
}
