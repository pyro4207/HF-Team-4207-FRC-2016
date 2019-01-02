package org.usfirst.frc.team4207.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.CANTalon;

public class HFRobotDrive extends RobotDrive {
	boolean usingCANTalons = true;
	private SpeedController frontLeftMotor;
	private SpeedController rearLeftMotor;
	private SpeedController frontRightMotor;
	private SpeedController rearRightMotor;

	public HFRobotDrive(SpeedController frontLeftMotor,
						SpeedController rearLeftMotor,
						SpeedController frontRightMotor,
						SpeedController rearRightMotor,
						boolean usingCANTalons) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		this.frontLeftMotor = frontLeftMotor;
		this.rearLeftMotor = rearLeftMotor;
		this.frontRightMotor = frontRightMotor;
		this.rearRightMotor = rearRightMotor;
		this.usingCANTalons = usingCANTalons;
		
	}
	
	public void setInverted(boolean frontLeft,boolean rearLeft, boolean frontRight, boolean rearRight) {
		frontLeftMotor.setInverted(frontLeft);
		rearLeftMotor.setInverted(rearLeft);
		frontRightMotor.setInverted(frontRight);
		rearRightMotor.setInverted(rearRight);
	}
	
	public void setBrakeMode(boolean enabled) {
		if(!usingCANTalons) return;
		
		((CANTalon) frontLeftMotor).enableBrakeMode(enabled);
		((CANTalon) rearLeftMotor).enableBrakeMode(enabled);
		((CANTalon) frontRightMotor).enableBrakeMode(enabled);
		((CANTalon) rearRightMotor).enableBrakeMode(enabled);
	}

}
