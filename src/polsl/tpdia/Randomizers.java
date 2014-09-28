package polsl.tpdia;

import java.util.Random;


/***
 * Class responsible for delivering methods for random based values.
 * 
 * All methods should be easily replaceable, if algorithm for generation of data should change.
 */

public class Randomizers {
	public Long deviationTimeToNextClient = 60l;
	public Long meanTimeToNextClient = 60l;
	public Long deviationAmountToTank = 15l;
	public Long meanAmountToTank = 35l;
	public Long minuteToMs = 60000l;
	private Random randomGenerator = new Random();
	
	public Randomizers setMeans(Long meanTimeToNextClient,Long meanAmountToTank){
		this.meanTimeToNextClient = meanTimeToNextClient;
		this.meanTimeToNextClient = meanAmountToTank;
		return this;
	}
	

	public Randomizers setDeviations(Long deviationAmountToTank,Long deviationTimeToNextClient){
		this.deviationAmountToTank = deviationAmountToTank;
		this.deviationTimeToNextClient = deviationTimeToNextClient;
		return this;
	}

	/**
	 * @return Amount of Fuel tanked in next customers visit
	 */
	public Double getRandValueToTankInL() {
		return randomGenerator.nextGaussian() * deviationAmountToTank
				+ meanAmountToTank;
	}
	
	/**
	 * @return Amount of time In ms till next customer
	 */
	public long nextTankInMs() {
		return Math.round((randomGenerator.nextGaussian() * deviationTimeToNextClient + meanTimeToNextClient)
				* minuteToMs);
	}

	
	/**
	 * @return Volume of water in tank - should be very small value as it is very unlikely situation
	 */
	public double getWaterHeight() {
		double returnValue = Math.max(randomGenerator.nextGaussian() - 4.4, 0) / 100;
		return returnValue;
	}
	
	
	/**
	 * @return Temperature measured in tank
	 */
	public double getTemperature() {
		double returnValue = randomGenerator.nextGaussian() * 0.01 + 5.33;
		return returnValue;
	}
}
