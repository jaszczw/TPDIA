import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.lang.model.type.PrimitiveType;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.descriptive.summary.Sum;


public class StreamDataGenerator {
	public Long deviationTimeToNextClient = 40l;
	public Long meanTimeToNextClient = 60l;
	public Long deviationAmountToTank = 15l;
	public Long meanAmountToTank = 35l;
	public Double msTimeFor1l = 1800d; 	// 1l/1.8s
	public Integer interval = 5000; // 5s
	public Long aggregationInterval = 900000l; // 15min
	public Long minuteToMs = 60000l;
	
	
	public StreamDataGenerator(){
		
	}

	public Double getNexValueToTankInL(Random randomGenerator) {
		return randomGenerator.nextGaussian() * deviationAmountToTank
				+ meanAmountToTank;
	}

	public Double nextTankInMinutes(Random randomGenerator) {
		return randomGenerator.nextGaussian() * deviationTimeToNextClient
				+ meanTimeToNextClient;
	}
	
	public Map<Calendar,Double> Generate(Calendar dateFrom, Calendar dateTo){

		Calendar from = (Calendar) dateFrom.clone();
		Calendar till = (Calendar) dateTo.clone();

		Long ticksAmount = (till.getTimeInMillis() - from.getTimeInMillis())
				/ interval;
		
		Random randomGenerator = new Random();
		Double nextTank = nextTankInMinutes(randomGenerator) * minuteToMs;
		
		Map<Calendar,Double> dict =  new HashMap<Calendar, Double>();

		Double amountOfFuelInTick= interval/msTimeFor1l;
		Double valueToTank = 0d;
		
		for (Integer i = 0; i < ticksAmount; i++, nextTank -= interval, from.add(Calendar.MILLISECOND, interval)) {
			if (nextTank <= 0 && valueToTank<=0) {
				valueToTank = Math.max(0,getNexValueToTankInL(randomGenerator));
				nextTank = nextTankInMinutes(randomGenerator) * minuteToMs;
			}
			
			if(valueToTank>0){
				System.out.print(Math.min(amountOfFuelInTick, valueToTank));
				System.out.println(" \t" + from.getTime());
				dict.put(from, Math.min(amountOfFuelInTick, valueToTank));
				valueToTank-= interval/msTimeFor1l;
			} else{
				System.out.println("0 \t" + from.getTime());
				dict.put(from, 0d);
			}
		}
		
		
		double[] primitiveArray = ArrayUtils.toPrimitive(dict.values().toArray(new Double[dict.values().size()]));

		int step = (int) (aggregationInterval/interval);
//		for(int i = 0; i<ticksAmount; i+=step){
//			System.out.println(sum.evaluate(primitiveArray,i,step) +" \t" + from.getTime());
//		}
		
		aggregateData(step,primitiveArray);
		
		return dict;
	}
	
	public double[] aggregateData(int step,double[] array){
		Sum sum = new Sum();
		
		ArrayList<Double> aggregatedData = new ArrayList<Double>();
		
		for(int i = 0; i<array.length; i+=step){
			double result = sum.evaluate(array,i,step);
			aggregatedData.add(result);
			System.out.println(result +" \t" + i);
		}
		
		return null;
	}

	
	
}
