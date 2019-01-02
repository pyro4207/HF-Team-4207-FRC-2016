package org.usfirst.frc.team4207.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

public class Tail {
	   SpeedController motor;
	   boolean movingUp = false;
	   boolean movingDown = false;
	   
	   public Tail(SpeedController tailMotor) {
			motor = tailMotor;
		}
	   
	   public void setMoveUp(double speed) {
			//final double speed = .6;
			motor.set(-speed);
			movingUp = true;
			movingDown = false;
		}
	   
	   public void setMoveDown() {
			final double speed = .6;
			motor.set(speed);
			movingUp = false;
			movingDown = true;
		}
	   
	   public void stop() {
			motor.set(0);
			movingUp = false;
			movingDown = false;
		}
	   
	   public void putDownFromStart() {
			setMoveDown();
			Timer.delay(.3);
			stop();
		}
	   
	   public void putUpFromStart() {
			setMoveUp(0.6);
			Timer.delay(.3);
			stop();
		}

}
