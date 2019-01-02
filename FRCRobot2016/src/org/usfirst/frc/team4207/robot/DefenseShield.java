package org.usfirst.frc.team4207.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

public class DefenseShield {
   SpeedController motorLeftArm;
   SpeedController motorRightArm;
   boolean movingUp = false;
   boolean movingDown = false;

   public DefenseShield(SpeedController leftMotor,SpeedController rightMotor) {
		motorLeftArm = leftMotor;
		motorRightArm = rightMotor;
	}

/*   
   public BallTrapArm(int leftMotorID,int rightMotorID) {
		motorLeftArm = new CANTalon(leftMotorID);
		motorRightArm = new CANTalon(rightMotorID);
	}
	*/
	public void setMoveUp() {
		final double speed = .7;
		motorLeftArm.set(speed);
		motorRightArm.set(-speed);
		movingUp = true;
		movingDown = false;
	}
	public void setMoveDown() {
		final double speed = .6;
		motorLeftArm.set(-speed);
		motorRightArm.set(speed);
		movingUp = false;
		movingDown = true;
	}
	public void stop() {
		motorLeftArm.set(0);
		motorRightArm.set(0);		
		movingUp = false;
		movingDown = false;
	}
	
	public void putDownFromStart() {
		setMoveDown();
		Timer.delay(1.7);
		stop();
	}
	public void putUpFromStart() {
		setMoveUp();
		Timer.delay(1.7);
		stop();
	}
}
