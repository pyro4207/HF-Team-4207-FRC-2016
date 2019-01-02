package HF2016Utility;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavXGyro extends HFGyro {
    private AHRS ahrs;
    
    public NavXGyro() { //int gyroAxis) {
    //	useGyroAxis = gyroAxis;
        ahrs = new AHRS(SPI.Port.kMXP);
    }
    public void reset() {
    	ahrs.reset();
    }
    public double getAngle() {
		return ahrs.getAngle();

    }

    public void showAllGyro() {
    	SmartDashboard.putString("XAxis",  "" + ahrs.getRawGyroX());
    	SmartDashboard.putString("YAxis",  "" + ahrs.getRawGyroY());
    	SmartDashboard.putString("ZAxis",  "" + ahrs.getRawGyroZ());
    	SmartDashboard.putString("Yaw",  "" + ahrs.getYaw());
    }
    public double getCompassHeading() {
    	if(0< 1 || ahrs.isMagnetometerCalibrated()) {
    		return ahrs.getCompassHeading();
    	} else {
    		return flagOnErrorValue;
    	}
    }
    public double getVelocityX() {
    	return ahrs.getVelocityX();
    }
    public double getVelocityY() {
    	return ahrs.getVelocityY();
    }
    public double getVelocityZ() {
    	return ahrs.getVelocityZ();
    }
    public double getDisplacementX() {
    	return ahrs.getDisplacementX();
    }
    public double getDisplacementY() {
    	return ahrs.getDisplacementY();
    }
    public double getDisplacementZ() {
    	return ahrs.getDisplacementZ();
    }
}