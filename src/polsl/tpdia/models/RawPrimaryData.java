package polsl.tpdia.models;

import java.util.Date;

public class RawPrimaryData {
	public RawPrimaryData(int tankId, int nozzleId, Date pointInTime, double rawFuelNozzleAmount,
			double rawFuelVolume, double rawWaterVolume) {
		TankId = tankId;
		NozzleId = nozzleId;
		PointInTime = pointInTime;
		RawWaterVolume = rawWaterVolume;
		RawFuelNozzleAmount = rawFuelNozzleAmount;
		RawFuelVolume = rawFuelVolume;
	}

	public Date PointInTime;
	public double RawFuelVolume; //diff - need to add starting level
	public double RawWaterVolume;
	public double RawFuelNozzleAmount;

	public int TankId;
	public double NozzleId;
}
