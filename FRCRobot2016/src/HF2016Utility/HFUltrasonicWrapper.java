package HF2016Utility;

import edu.wpi.first.wpilibj.Ultrasonic;

public class HFUltrasonicWrapper extends HFDistanceSensor {
	Ultrasonic ultra = new Ultrasonic(5,4);
	public double getDistance() {
		return ultra.getRangeInches();
	}
	
}
