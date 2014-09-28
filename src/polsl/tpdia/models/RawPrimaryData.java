package polsl.tpdia.models;

import java.util.Date;


/**
 * Class representing one row of generated Data it has highest granularity, 
 * as all aggregations are made from data contained here.
 * 
 * Amount, and Volume data store difference to last measured (generated), data
 */
public class RawPrimaryData {
	public RawPrimaryData(int tankId, int nozzleId, Date pointInTime, double rawFuelNozzleAmount,
			double rawFuelVolume, double rawWaterVolume, double temperature) {
		TankId = tankId;
		NozzleId = nozzleId;
		PointInTime = pointInTime;
		RawWaterVolume = rawWaterVolume;
		RawFuelNozzleAmount = rawFuelNozzleAmount;
		RawFuelVolume = rawFuelVolume;
		Temperature = temperature;
	}

	public Date PointInTime;
	public double RawFuelVolume;
	public double RawWaterVolume;
	public double RawFuelNozzleAmount;
	public double Temperature;

	public int TankId;
	public int NozzleId;
}
