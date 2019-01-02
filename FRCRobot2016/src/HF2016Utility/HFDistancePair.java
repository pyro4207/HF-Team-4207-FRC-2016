package HF2016Utility;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HFDistancePair {
	HFDistanceSensor distanceSensor;
	HFDistanceSensor distanceSensor2;
	
	public HFDistancePair(HFDistanceSensor d1,HFDistanceSensor d2) {
		
	} 
	
	public void enable(boolean newValue) {
		distanceSensor.enable(newValue);
	}
	public void enable(boolean newValue,boolean newValue2) {
		distanceSensor.enable(newValue);
		distanceSensor2.enable(newValue2);		  
	}
	public void smartInitialize() {
    	distanceSensor.smartInitialize();
    	distanceSensor2.smartInitialize();

	}
	
	public double getDistanceWithDisplay(boolean displayIndividualDistance, boolean displayAverage,boolean debugStatus) {
        double distance1 = 0.0;
        double distance2 = 0.0;
        double distanceAvg = 0;
		
    	distance1 = distanceSensor.getSmartDistance(debugStatus);
    	if(displayIndividualDistance) SmartDashboard.putString("Distance","" + distance1);

    	distance2 = distanceSensor2.getDistance(debugStatus);
    	if(displayIndividualDistance) SmartDashboard.putString("Distance2","" + distance2);
		if(distance1>HFDistanceSensor.flagOnErrorValue) {
			if(distance2>HFDistanceSensor.flagOnErrorValue) {
				distanceAvg = (distance1 + distance2)/2;
			} else {
				distanceAvg = distance1;
			}
		} else {
			if(distance2>HFDistanceSensor.flagOnErrorValue) {
				distanceAvg = distance2;
			}    		
		}
		if(displayAverage) SmartDashboard.putString("DistanceAvg","" + distanceAvg);

		return distanceAvg;
	}

	
	
	public double getDistance(boolean debugMessages) {
        double distance1 = 0.0;
        double distance2 = 0.0;
        double distanceAvg = 0;
		
    	distance1 = distanceSensor.getSmartDistance(debugMessages);

    	distance2 = distanceSensor2.getDistance(debugMessages);
		if(distance1>HFDistanceSensor.flagOnErrorValue) {
			if(distance2>HFDistanceSensor.flagOnErrorValue) {
				distanceAvg = (distance1 + distance2)/2;
			} else {
				distanceAvg = distance1;
			}
		} else {
			if(distance2>HFDistanceSensor.flagOnErrorValue) {
				distanceAvg = distance2;
			}    		
		}
		return distanceAvg;
	}
	
	public double getSmartDistance(boolean debugMessages) {
		return getDistance(debugMessages);
	}

}
