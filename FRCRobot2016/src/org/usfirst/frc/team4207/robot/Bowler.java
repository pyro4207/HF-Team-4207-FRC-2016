package org.usfirst.frc.team4207.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Bowler {
	   SpeedController motorIntake;
	   SpeedController motorIntakeWheel;
	   boolean bowlingIn;
	   boolean bowlingOut;
	   boolean diagnosticsOn = true;

	   public void initialize() {
		   bowlingOut = false;
		   bowlingIn = false;
		   
	   }
	   
	   /*
	   public Bowler(int intakeID,int intakeWheelID) {
		   bowlingOut = false;
		   bowlingIn = false;
		   motorIntake = new CANTalon(intakeID);
		   motorIntakeWheel = new CANTalon(intakeWheelID);
		   
		   motorIntakeWheel.setExpiration(1.5);
		   motorIntake.setExpiration(1.5);

	   }

*/	   public Bowler(SpeedController intake,SpeedController intakeWheel) {
		   bowlingOut = false;
		   bowlingIn = false;
		   motorIntake = intake;
		   motorIntakeWheel = intakeWheel;
		   
//		   motorIntakeWheel.setExpiration(1.5);
//		   motorIntake.setExpiration(1.5);

	   }
	   

	   
	   public void reset() {
		   motorIntake.set(0.0);
		   motorIntakeWheel.set(0.0);
		   bowlingOut = false;
		   bowlingIn = false;
		   SmartDashboard.putString("INTAKE", "OFF");
	   }
	   
	   public void setFromJoystick(Joystick stick) {
		   if(stick.getRawButton(6)) {
			   bowlingIn = true;
			   motorIntake.set(-.8);
			   motorIntakeWheel.set(-.6);
			   SmartDashboard.putString("INTAKE", "IN");
		   } else if(stick.getRawAxis(3) > .5) {
			   if(!bowlingOut) {
				   bowlOutStart();
				   bowlingOut = true;				   
			   } else {
    			//already started
				   motorIntake.set(.7);
				   motorIntakeWheel.set(.6);
				   SmartDashboard.putString("INTAKE", "OUT");
			   }
		   } else if(bowlingIn || bowlingOut) {
			   motorIntake.set(0);
			   motorIntakeWheel.set(0);			   
			   bowlingOut = false;
			   bowlingIn = false;
			   SmartDashboard.putString("INTAKE", "OFF");
		   }

	   }
	   private void bowlOutStart() {
		   motorIntakeWheel.set(-.6);
		   Timer.delay(.75);
		   motorIntake.set(1);
		   Timer.delay(.7);
		   motorIntakeWheel.set(0);
		   Timer.delay(.05);
		   motorIntakeWheel.set(.4);        			
		   bowlingOut = true;
		   SmartDashboard.putString("INTAKE", "OUT");		   
	   }
	   
	public void bowlOutAutonomous(Robot robo) {
		   bowlOutStart(); // allow intake motor to get up to speed
		   
		   motorIntake.set(1);
		   motorIntakeWheel.set(.6);
		   long endTime = System.currentTimeMillis()  + 900; //1000=1 second 
		   while(System.currentTimeMillis() < endTime && robo.isEnabled() && robo.isAutonomous()) {
			   Timer.delay(0.005); // wait for a motor update time
		   }
		   motorIntake.set(0.0);
		   motorIntakeWheel.set(0.0);		   
	   }
}
