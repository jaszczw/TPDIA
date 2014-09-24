package polsl.tpdia.models;

import java.util.Date;

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
	public double RawFuelVolume; //diff - need to add starting level
	public double RawWaterVolume;
	public double RawFuelNozzleAmount;
	public double Temperature;

	public int TankId;
	public int NozzleId;
}
