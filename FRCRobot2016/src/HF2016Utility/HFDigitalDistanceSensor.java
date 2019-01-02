package HF2016Utility;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HFDigitalDistanceSensor extends HFDistanceSensor {
//	private int pingChannel;
//	private int echoChannel;
//	protected double[]  distanceValues;

	DigitalOutput digitalOutput;
	DigitalInput digitalInput;
    private double lastRawReading = -4207;
    
	
	public HFDigitalDistanceSensor(int pingChannel,int echoChannel) {
//		this.pingChannel = pingChannel;
//		this.echoChannel = echoChannel;
		digitalOutput = new DigitalOutput(pingChannel);
		digitalInput = new DigitalInput(echoChannel);
		this.feedbackLength = 1;
//		distanceValues = new double[feedbackLength];
		rejectedValues = 0;
	}
	public HFDigitalDistanceSensor(int pingChannel,int echoChannel, int feedbackLength) {
//		this.pingChannel = pingChannel;
//		this.echoChannel = echoChannel;
		digitalOutput = new DigitalOutput(pingChannel);
		digitalInput = new DigitalInput(echoChannel);
		this.feedbackLength = feedbackLength;
	//	distanceValues = new double[feedbackLength];
		rejectedValues = 0;
	}
	
    private boolean sendTriggerSignal(){
    	try {
    		// Send 5V for 10 uS.
//        	debugStep = "T1a";
        	digitalOutput.set(false);
			Timer.delay(0.000010);
			digitalOutput.set(true);
			Timer.delay(0.000010);
  			digitalOutput.set(false);      	// Set signal back to 0V.
    	} catch(Exception ex)  {
    		lastErrorMessage = "T" + ex.getMessage();
    		 SmartDashboard.putString("Distance Error", lastErrorMessage);
    		return false;
    	}
    	return true;
    }
	
    private double takeDistanceReading()
    {
    	try {
    		//   SmartDashboard.putString("DB/String 0", "start sense     " );
    		long startEcho =0;
//       		 SmartDashboard.putString("statusdist", "hello 0");
    		SmartDashboard.putString("statusdist", "starting");

    		if(!sendTriggerSignal()) return flagOnErrorValue;
    		// Wait for Echo signal.
//    		 SmartDashboard.putString("statusdistance", "hello 1");
    		int cnt = 0;
    	//	SmartDashboard.putString("Display", "ThatLine6");
//    		SmartDashboard.putString("statusdist", "starting");

    		while(!digitalInput.get()){
    			if (cnt>2000)  {    // purpose is to handle sensor not responding
    	    		SmartDashboard.putString("statusdist", "no echo b");
    	    		SmartDashboard.putString("Display", "ThatLine7");

    				return flagOnErrorValue;
    			}
    			//SmartDashboard.putString("Display", "ThatLine8");

    			cnt++;
    		}
    		//SmartDashboard.putString("Display", "ThatLine9");

//    		SmartDashboard.putString("statusdist", "hello 2a  " + cnt);
    		//start of return pulse
    		startEcho = System.nanoTime();   
    		cnt = 0;
    		while(digitalInput.get()){
    			if (cnt>35000)  {    // purpose is to handle sensor not responding
    	    		SmartDashboard.putString("statusdist", "no echo b");
    	    		//SmartDashboard.putString("Display", "ThatLine10");

    				return flagOnErrorValue;
    			}
    		//	SmartDashboard.putString("Display", "ThatLine11");

    			cnt++;
    		}
    		//SmartDashboard.putString("Display", "ThatLine12");

    		//end of return pulse   		
    //		SmartDashboard.putString("statusdist", "hello 2  done " + cnt);
      		return convertPulseLengthToInches(System.nanoTime() - startEcho);
    		
    	} catch(Exception ex) {
        	debugStep = "S5";
    		lastErrorMessage = ex.getMessage();    		
    		return flagOnErrorValue;
    	}
    }

    public double convertPulseLengthToInches(long echoTime) {
    	if(echoTime == 38000) return flagOnErrorValue;
		return 1.0*(echoTime)/148000;
    }
    public double convertPulseLengthToCM(long echoTime) {
		return 1.0*(echoTime)/148000; // wrong time for centimeters
    }
    
    public double getDistance() {  	
    	if(sensorEnabled) {
    	//	SmartDashboard.putString("distance stat", "distance enabled");

    		return takeDistanceReading();
    	}
		SmartDashboard.putString("distance stat", "distance disabled");
    	return flagOnErrorValue;
    }


    public double smartInitialize() {
    	if(!sensorEnabled) return flagOnErrorValue;
    	double distance = getDistance();
//    	for(int i = 0; i < feedbackLength; i++) {
//			distanceValues[i] = distance;
 //   	}
    	
    	smartInitialized = true;
    	curSmartAverage = distance;
    	lastRawReading = distance;
    	return distance;
    }

    
    public double getSmartDistance(boolean debugStatus) {
    	return getSafeDistance(debugStatus);
    	/*
    	if(!smartInitialized) {
    		smartInitialize();
    	}
    	double smartDistanceSum = 0;
    	double distance = getDistance();
    	if(distance == flagOnErrorValue) return curSmartAverage;

    	double tolerance = 5; 
    	double rTolerance = 5;

    	SmartDashboard.putNumber("DistanceRaw",distance);
    	System.out.print(distance);
		if(Math.abs(distance - curSmartAverage) > tolerance && Math.abs(distance - lastRawReading) > rTolerance) {
//			rejectedValues++;
	    	SmartDashboard.putString("DistanceSmartStatus","rejecting " + distance);
	    	System.out.println("\trejecting\t" + distance);
			lastRawReading = distance;
			return curSmartAverage;
		}
    	SmartDashboard.putString("DistanceSmartStatus","accepting " + distance);
		lastRawReading = distance;
//		rejectedValues = 0;
		smartDistanceSum = curSmartAverage*feedbackLength - distanceValues[curIndex] + distance;
		distanceValues[curIndex] = distance;
		curIndex = (curIndex+1) % feedbackLength;

    	curSmartAverage = smartDistanceSum/feedbackLength;

    	SmartDashboard.putString("DistanceSmart","" + curSmartAverage);
    	System.out.println("\taccepting\t" + distance + "\t" + curSmartAverage);
    	return curSmartAverage;
    	*/
    }

    public double getSafeDistance(boolean debugStatus) {
    	if(!sensorEnabled) return flagOnErrorValue;
    	if(!smartInitialized) {
    	//	SmartDashboard.putString("Display", "ThatLine3");

    		smartInitialize();
    	}
		//SmartDashboard.putString("Display", "ThatLine4");

    	double distance = getDistance();
		//SmartDashboard.putString("Display", "ThatLine5");

    	if(distance == flagOnErrorValue) return curSmartAverage;

    	double rTolerance = 3;

    	if(debugStatus) SmartDashboard.putNumber("DistanceRaw",distance);
    	System.out.print(distance);
		if(Math.abs(distance - lastRawReading) > rTolerance) {
			if(debugStatus) SmartDashboard.putString("DistanceSafeStatus","rejecting " + distance);
	    	System.out.println("\trejecting\t" + distance);
			lastRawReading = distance;
			return curSmartAverage;
		}
		if(debugStatus) SmartDashboard.putString("DistanceSafeStatus","accepting " + distance);
		lastRawReading = distance;

		curSmartAverage = distance;
		if(debugStatus) SmartDashboard.putString("DistanceSmart","" + curSmartAverage);
    	return curSmartAverage;
    }

}
