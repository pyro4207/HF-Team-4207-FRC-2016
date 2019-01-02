package HF2016Utility;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HFDistanceAnalog extends HFDistanceSensor {
    AnalogInput rangeFinder;
    
    public HFDistanceAnalog(int analogChannel) {
    	rangeFinder = new AnalogInput(analogChannel);

    }
    public double getDistance() {
    	double distance;
    	final double ULTRRASONICRANGEVOLTAGE = 5.0;
   // 	if(USEDISTANCEANALOG) {
    	
    		distance = 512 * rangeFinder.getVoltage()/ULTRRASONICRANGEVOLTAGE;
    		SmartDashboard.putString("DB/String 4", "A= " + distance);
            Timer.delay(0.05);		// wait for a motor update time

    //	} else {
    //		distance = ultra.getRangeInches();
      //		SmartDashboard.putString("DB/String 5", "D= " + distance);
   		
    	//}
    	return distance;
    }
}
