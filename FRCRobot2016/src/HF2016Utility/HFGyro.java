package HF2016Utility;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HFGyro {
	public static final int flagOnErrorValue = -4207;
	public String lastErrorMessage = "";
	public String debugStep;
	public boolean sensorEnabled = true;
	
	public void enable(boolean setEnabled) {
		sensorEnabled = setEnabled;
	}
	
    public double getAngle() {
    	return flagOnErrorValue;
    }
    public double getAngleRelativeTo(double angle) {
    	double curReading = getAngle();
//		SmartDashboard.putString("Gyro reading","" + curReading);
    	return getAngleDifference1minus2(curReading,angle);
    }
    
    public static double getAngleDifference1minus2(double angle1, double angle2) {
    	double result = (angle1 - angle2)%360;    	
//		SmartDashboard.putString("Gyro Angle 1","" + angle1);
//		SmartDashboard.putString("Gyro Angle 2","" + angle2);
//		SmartDashboard.putString("Gyro Angle diff","" + result);

    	if(result > 180) {
    		return result - 360;
    	} else if(result < -180) {
    		return result + 360;
    	} 
    	return result;
    }
}
