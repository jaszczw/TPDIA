package polsl.tpdia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import polsl.tpdia.models.AggregatedNozzleData;
import polsl.tpdia.models.AggregatedTankData;
import polsl.tpdia.models.RawPrimaryData;


/**
 * Main class for generating aggregations and raw data
 * Contains method for generating and saving to a file fuel station data.
 */
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

	///Generates and aggregated Fuel Station Data
	public ArrayList<RawPrimaryData> Generate(Calendar dateFrom, Calendar dateTo)
			throws IOException {
		Calendar startingPoint = (Calendar) dateFrom.clone();

		ArrayList<RawPrimaryData> rawData = new ArrayList<RawPrimaryData>();

		Double amountOfFuelInTick = interval / msTimeFor1l;
		Double valueToTank = 0d;

		// Diff of time of period to generate divided by interval that data are
		// taken
		Long ticksAmount = (dateTo.getTimeInMillis() - startingPoint
				.getTimeInMillis()) / interval;
		Long nextTank = randomizer.nextTankInMs();

		for (Integer i = 0; i < ticksAmount; i++, nextTank -= interval, startingPoint
				.add(Calendar.MILLISECOND, interval)) {
			Date pointInTime = startingPoint.getTime();

			if (nextTank <= 0 && valueToTank <= 0) {
				valueToTank = Math.max(0,
						randomizer.getRandValueToTankInL());
				nextTank = randomizer.nextTankInMs();
			}

			double tankedValue = Math.min(amountOfFuelInTick, valueToTank);
			valueToTank -= tankedValue;

			RawPrimaryData rawPoint = new RawPrimaryData(tankId, nozzleId,
					pointInTime, tankedValue, tankedValue
							* oneLtoFuelHeightProportion,
					randomizer.getWaterHeight(),
					randomizer.getTemperature());

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
			data.printToFile();

		for (AggregatedTankData data : aggregatedTankData)
			data.printToFile();

		return rawData;
	}

}
