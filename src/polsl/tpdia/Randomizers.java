package polsl.tpdia;

import java.util.Random;

public class Randomizers {
	public Long deviationTimeToNextClient = 60l;
	public Long meanTimeToNextClient = 60l;
	public Long deviationAmountToTank = 15l;
	public Long meanAmountToTank = 35l;
	public Long minuteToMs = 60000l;
	
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

	public Double getRandValueToTankInL(Random randomGenerator) {
		return randomGenerator.nextGaussian() * deviationAmountToTank
				+ meanAmountToTank;
	}

	public Double nextTankInMs(Random randomGenerator) {
		return (randomGenerator.nextGaussian() * deviationTimeToNextClient + meanTimeToNextClient)
				* minuteToMs;
	}

	public double getWaterHeight(Random randomGenerator) {
		double returnValue = Math.max(randomGenerator.nextGaussian() - 4.4, 0) / 100;
		return returnValue;
	}

	public double getTemperature(Random randomGenerator) {
		double returnValue = randomGenerator.nextGaussian() * 0.01 + 5.33;
		return returnValue;
	}
}
