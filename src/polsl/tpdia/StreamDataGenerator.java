package polsl.tpdia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import polsl.tpdia.models.AggregatedNozzleData;
import polsl.tpdia.models.AggregatedTankData;
import polsl.tpdia.models.RawPrimaryData;

public class StreamDataGenerator {

	public Double msTimeFor1l = 1800d; // 1l/1.8s
	public double oneLtoFuelHeightProportion = 0.000379552d;
	public int tankId = 9650;
	public int nozzleId = 1640;
	public Randomizers randomizer;

	private int tankAggregationInterval;
	private Integer interval;
	private int nozzleAggregationInterval;

	public StreamDataGenerator(int rawDataInterval,
			int nozzleAggregationInterval, int tankAggregationInterval) {
		randomizer = new Randomizers();
		this.interval = rawDataInterval;
		this.nozzleAggregationInterval = nozzleAggregationInterval;
		this.tankAggregationInterval = tankAggregationInterval;
	}

	public ArrayList<RawPrimaryData> Generate(Calendar dateFrom, Calendar dateTo)
			throws IOException {
		Calendar startingPoint = (Calendar) dateFrom.clone();
		Random randomGenerator = new Random();

		ArrayList<RawPrimaryData> rawData = new ArrayList<RawPrimaryData>();

		Double amountOfFuelInTick = interval / msTimeFor1l;
		Double valueToTank = 0d;

		// Diff of time of period to generate divided by interval that data are
		// taken
		Long ticksAmount = (dateTo.getTimeInMillis() - startingPoint
				.getTimeInMillis()) / interval;
		Double nextTank = randomizer.nextTankInMs(randomGenerator);

		for (Integer i = 0; i < ticksAmount; i++, nextTank -= interval, startingPoint
				.add(Calendar.MILLISECOND, interval)) {
			Date pointInTime = startingPoint.getTime();

			if (nextTank <= 0 && valueToTank <= 0) {
				valueToTank = Math.max(0,
						randomizer.getRandValueToTankInL(randomGenerator));
				nextTank = randomizer.nextTankInMs(randomGenerator);
			}

			double tankedValue = Math.min(amountOfFuelInTick, valueToTank);
			valueToTank -= tankedValue;

			RawPrimaryData rawPoint = new RawPrimaryData(tankId, nozzleId,
					pointInTime, tankedValue, tankedValue
							* oneLtoFuelHeightProportion,
					randomizer.getWaterHeight(randomGenerator),
					randomizer.getTemperature(randomGenerator));

			rawData.add(rawPoint);
		}

		int aggergationStep = (int) (nozzleAggregationInterval / interval);

		NozzleDataHandler nozzleDataHandler = new NozzleDataHandler(
				nozzleAggregationInterval);
		List<AggregatedNozzleData> aggregatedData = nozzleDataHandler
				.aggregateNozzleData(aggergationStep, rawData,
						(Calendar) dateFrom.clone());

		TankDataHandler tankDataHandler = new TankDataHandler(tankAggregationInterval/interval);
		List<AggregatedTankData> aggregatedTankData = tankDataHandler
				.aggregateTankData(rawData);

		for (AggregatedNozzleData data : aggregatedData)
			data.printToConsole();

		for (AggregatedTankData data : aggregatedTankData)
			data.printToConsole();

		return rawData;
	}

}
