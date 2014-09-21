package polsl.tpdia.models;
import java.util.Date;


	
	public class AggregatedNozzleData{
		public AggregatedNozzleData(int nozzleId, int tankId, Date aggStart, Date aggEnd,
				double rawValue, double temperature) {
			NozzleId = nozzleId;
			TankId = tankId;
			DateFrom = aggStart;
			DateTo = aggEnd;
			ValueRaw = rawValue;
			Temperature = temperature;
		}
		
		public int NozzleId;
		public int TankId;
		public Date DateFrom;
		public Date DateTo;
		public double ValueRaw;
		public double Temperature;
		
		
		public void printToConsole(){
			System.out.println(NozzleId + " " + TankId + " " +DateFrom + " " +DateTo + " " +ValueRaw + " " +Temperature);
		}
	}