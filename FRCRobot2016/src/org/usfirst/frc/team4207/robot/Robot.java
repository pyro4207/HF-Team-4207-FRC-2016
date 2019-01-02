
		
		
package org.usfirst.frc.team4207.robot;


import com.ctre.CANTalon;

//import com.kauailabs.navx.frc.AHRS;





import com.kauailabs.navx.frc.AHRS;

import HF2016Utility.ADXRS453Gyro;
import HF2016Utility.HFCameraPairSwitchable;
import HF2016Utility.HFDigitalDistanceSensor;
import HF2016Utility.HFDistanceSensor;
import HF2016Utility.NavXGyro;

//import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
	//pwm ids
    boolean mainRobot;  

    // main bot ids
    final int DRIVE_LEFT_FRONT = 16;
	final int DRIVE_LEFT_BACK = 14;
	final int DRIVE_RIGHT_FRONT = 17;
	final int DRIVE_RIGHT_BACK = 20;
	final int BALL_INTAKE = 19;
	final int BALL_INTAKE_WHEEL = 15;
	final int RIGHT_ARM = 21;
	final int LEFT_ARM = 18;
	final int WEDGE = 13;
	/* TALON CONFIGURATION
	 * (left by power distribution board)		(right by roboRio)
	 * TALON #	|	TALON ID					TALON #		|	TALON ID
	 * 1		|	13							1			|	12
	 * 2		|	18							2			|	21
	 * 3		|	19							3			|	15
	 * 4		|	16							4			|	17
	 * 5		|	14							5			|	20
	 */

    
    //practicebotids
    final int PRACTICE_DRIVE_LEFT_FRONT = 0;
	final int PRACTICE_DRIVE_LEFT_BACK = 1;
	final int PRACTICE_DRIVE_RIGHT_FRONT = 2;
	final int PRACTICE_DRIVE_RIGHT_BACK = 3;
	final int PRACTICE_BALL_INTAKE = 5;
	final int PRACTICE_BALL_INTAKE_WHEEL = 7;
	final int PRACTICE_RIGHT_ARM = 4;
	final int PRACTICE_LEFT_ARM = 6;
	final int PRACTICE_WEDGE = 8;
	

	final int DIRECTION_FORWARD = 1;
	final int DIRECTION_BACKWARDS = -1;
	double directionMultiplier = DIRECTION_FORWARD; 

	//digital id
//	final int GYRO = 1;
	   
	final int AUTO = 0;
	final int TELE = 1;
	final int TEST = 2;
	
	final int defaultAuto = -1;
	final int customAuto = -1;

	
	//status variables

	HFCameraPairSwitchable cameraPair;
	DefenseChooser chooserDefense;
	PositionChooser chooserPosition;
		
	HFRobotDrive myRobot;
	Joystick stick;
    
	//AnalogGyro gyro;
//	ADXRS453Gyro gyro = new ADXRS453Gyro();
	NavXGyro gyro;

	//gyroSPI2.getRateBin();
//	HFDistancePair distanceSensor = new HFDistancePair(new HFDigitalDistanceSensor(5,4,2),new HFDigitalDistanceSensor(3,2,2));	
   HFDigitalDistanceSensor distanceSensor = new HFDigitalDistanceSensor(5,4,2); 
   
   

  /*
   boolean usingCanTalons = false;  
   Talon driveLeftFront = new Talon(DRIVE_LEFT_FRONT);
   Talon driveLeftBack = new Talon(DRIVE_LEFT_BACK);
   Talon driveRightFront = new Talon(DRIVE_RIGHT_FRONT);
   Talon driveRightBack = new Talon(DRIVE_RIGHT_BACK);
  */
   Bowler bowler;
   DefenseShield defenseShield;
  // SpeedController wedge;
   Tail tail;
//Servo cameraArm = new Servo(9);
   
   
   public Robot() {
     SmartDashboard.putString("DEBUG", "step 0");
	   mainRobot = true;

	   if(mainRobot) {
		   defenseShield = new DefenseShield(new CANTalon(LEFT_ARM),new CANTalon(RIGHT_ARM));
		   bowler = new Bowler(new CANTalon(BALL_INTAKE),new CANTalon(BALL_INTAKE_WHEEL));

		   myRobot = new HFRobotDrive( new CANTalon(DRIVE_LEFT_FRONT), new CANTalon(DRIVE_LEFT_BACK),
				   					 new CANTalon(DRIVE_RIGHT_FRONT), new CANTalon(DRIVE_RIGHT_BACK),true);
		   tail = new Tail(new CANTalon(WEDGE));
	   } else {		   
		   defenseShield = new DefenseShield(new Talon(PRACTICE_LEFT_ARM),new Jaguar(PRACTICE_RIGHT_ARM));		   
		   bowler = new Bowler(new Talon(PRACTICE_BALL_INTAKE),new Talon(PRACTICE_BALL_INTAKE_WHEEL));
		   myRobot = new HFRobotDrive( new Talon(PRACTICE_DRIVE_LEFT_FRONT), new Talon(PRACTICE_DRIVE_LEFT_BACK),
 					 					new Talon(PRACTICE_DRIVE_RIGHT_FRONT), new Talon(PRACTICE_DRIVE_RIGHT_BACK),true);
		   tail = new Tail(new Talon(PRACTICE_WEDGE));
	   }
	   
	   myRobot.setInverted(true,true,true,true);
       myRobot.setExpiration(0.3);

       gyro = new NavXGyro();//NavXGyro.GyroAxisX);
	   cameraPair = new HFCameraPairSwitchable("cam0","cam2",true);
	   
       stick = new Joystick(1);
    }	

   public void robotInit() {
//       SmartDashboard.putString("DEBUG", "step 1");
	   distanceSensor.enable(true);

        chooserDefense = new DefenseChooser();      
        chooserPosition = new PositionChooser();
           
        SmartDashboard.putData("AutoModes", chooserDefense);
        SmartDashboard.putData("AutoPositions", chooserPosition);

        	
        SmartDashboard.putString("Gyro Angle","no reading");
        SmartDashboard.putString("Distance","no reading");

        
    	SmartDashboard.putString("defensee", "?");
    	SmartDashboard.putString("start", "?");
    	
    	SmartDashboard.putString("Display", "ThisLine");

     //   gyro = new AHRS(SPI.Port.kMXP);   
    //    gyro.initGyro();
        	//gyro.setSensitivity(.02);
  //  	gyro.reset();

//        gyro.getRateBin();
  	//  	gyro.startThread(); 
    }

    public void autonomous() {    	
    	gyro.reset();
    	Timer.delay(.2);
    	double startingAngle = gyro.getAngle();
    	try {
        	if(distanceSensor.smartInitialize() == distanceSensor.flagOnErrorValue) {
        		distanceSensor.enable(false);
        	}
    	} catch (Exception ex)
    	{
    		distanceSensor.enable(false);
    	}
    	//if(usingCanTalons) {
    	//	drive1.enableBrakeMode(true);
    	//	drive2.enableBrakeMode(true);  	
    //	}
    	int defenseProgram = (int) chooserDefense.getSelected();
    	int startPosition = (int) chooserPosition.getSelected();

    	SmartDashboard.putString("defensee", ""+ defenseProgram);
    	SmartDashboard.putString("start", "" + startPosition);

    	
    	SmartDashboard.putString("autostep", "1");

//        displaySensorData(true,true);

    	
    	switch(defenseProgram) {
    		case DefenseChooser.DEFENSE_PORTCULLIS:
    			tail.putDownFromStart();	    	
    	    	driveForTime(0.4, 1000);
    	    	tail.putUpFromStart();
    	    	driveForTime(0.4, 1000);
    			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
    			break;
    		case DefenseChooser.DEFENSE_CHEVAL:
    			driveForTime(-.6, 2000);
    			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
    			break;
    		case DefenseChooser.DEFENSE_DRAWBRIDGE:
//    			myRobot.setLeftRightMotorOutputs(.4, .4);
//    			turnDegree(45,.5,AUTO);

    			// Timer.delay(100); 
    			
/*    			int x;
    			for(x=0;x<40;x=x+1)
    			{
    				myRobot.setLeftRightMotorOutputs(.4, .4);
    				Timer.delay(.05);
    			}
    			turnDegree(90,.5,AUTO);
  */  			
       			driveForTime(-1.0, 2000);
       			startPosition = PositionChooser.POSITION_NOBOWLING;
       			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
    			break;
    		case DefenseChooser.DEFENSE_MOAT:  //verified
       			driveForTime(-0.65, 2500);
       			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
    			break;
    		case DefenseChooser.DEFENSE_LOWBAR:
//    	    	ballTrapArm.putDownFromStart();
    	    	tail.putDownFromStart();  	

    	    	driveForTime(-0.8, 1706);
    	    	tail.putUpFromStart();
  //  	    	ballTrapArm.putUpFromStart();
//       			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);

       			break;
    		case DefenseChooser.DEFENSE_RAMPART:
       			//driveForTime(-1.0, 2000);
    			turnDegree(-10, .5, AUTO);
    			driveForTime(-0.91, 1300);
       			driveForTime(-0.65, 900);
       			//turnDegree(10, .5, AUTO);
    			break;
    		case DefenseChooser.DEFENSE_ROCKWALL:
       			driveForTime(-0.91, 1150);
       			driveForTime(-0.65, 1400);
       			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
  			break;
    		case DefenseChooser.DEFENSE_ROUGHTERRAIN:
    			driveForTime(-0.75, 2200);
       			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
    			break;
    		case DefenseChooser.DEFENSE_SALLYPORT:
       			driveForTime(-1.0, 2000);
       			startPosition = PositionChooser.POSITION_NOBOWLING;
       			turnDegree(-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()), .6, AUTO);
    			break;
    		default:
    	}
   

//    	displaySensorData(true,true);

    	SmartDashboard.putString("autostep", "2");
    	switch(startPosition) {
    		case PositionChooser.POSITION_1_FARLEFT: //Tested
    			if(!distanceSensor.getEnabled()) break;
    	    	
    	    	SmartDashboard.putString("autostep", "A-2");
    	    	turnDegree(8-gyro.getAngleRelativeTo(startingAngle - gyro.getAngle()),.6,AUTO);
    	    	SmartDashboard.putString("autostep", "A-3");
    	    	
    			driveWithinDistance(35, .452);

    			turnDegree(67, .8, AUTO);
    			driveForTime(-.6,1500);
    			bowler.bowlOutAutonomous(this);
    			break;
    		case PositionChooser.POSITION_2_MIDLEFT: //somewhat tested
    			if(!distanceSensor.getEnabled()) break;
    	    	
    	    	turnDegree(6,.65,AUTO);
    	    	SmartDashboard.putString("autostep", "A-1");
    	    	
    			driveWithinDistance(31, .6);

    			turnDegree(62, .8, AUTO);
    			driveForTime(-.6,400);
    			bowler.bowlOutAutonomous(this);
    			break;
    		case PositionChooser.POSITION_3_MIDDLE: //not tested
if(!distanceSensor.getEnabled()) break;
    	    	
    	    	turnDegree(-45,.7,AUTO);
    	    	SmartDashboard.putString("autostep", "A-1");
    			driveForTime(-.65,1250);
    	    	turnDegree(90,.7,AUTO);
    			driveForTime(-.6,500);    	    	
    			bowler.bowlOutAutonomous(this);
    			break;
    		case PositionChooser.POSITION_4_MIDRIGHT: //not tested
    			if(!distanceSensor.getEnabled()) break;
    	    	
    	    	turnDegree(45,.7,AUTO);
    	    	SmartDashboard.putString("autostep", "A-1");
    			driveForTime(-.65,1250);
    	    	turnDegree(-90,.7,AUTO);
    			driveForTime(-.6,500);    	    	
    			bowler.bowlOutAutonomous(this);
    			break;
    		case PositionChooser.POSITION_5_FARRIGHT: // tested
    			if(!distanceSensor.getEnabled()) break;
//    			ballTrapArm.putUpFromStart();
    			driveWithinDistance(51, .8);
    			turnDegree(-59, .7, AUTO);
    			driveForTime(-.6, 750); 
    			bowler.bowlOutAutonomous(this);
    			break;
    	}
    	myRobot.setLeftRightMotorOutputs(0.0, 0.0);
		while(isAutonomous() && isEnabled()) {
		}    			
      }
            
	
    
    public void displaySensorData(boolean includeGyro,boolean includeDistance) {
        double temp;

        if(includeGyro) {
        	temp = gyro.getAngle();
        	SmartDashboard.putString("Gyro Angle","" + temp);
        }
        
        if(includeDistance) {
        	temp = distanceSensor.getSafeDistance(false);
        	SmartDashboard.putString("Distance","" + temp);
        }
    }
       
    final int POV_FASTMODE = 0;
    final int POV_SLOWMODE = 180;

	 int errorCount = 0;		

//	DigitalInput diHall = new DigitalInput(8);
    public void operatorControl() {
//    	gyro.reset();
        SmartDashboard.putString("Display Message", "TELOP Started");
//    	distanceSensor.smartInitialize();
        myRobot.setSafetyEnabled(true);
        myRobot.setExpiration(1.0);
    	//drive1.enableBrakeMode(false);
        
        final double LOWSPEED = .8;
        double speedMultiplier = LOWSPEED;

		SmartDashboard.putString("SpeedMode", "Slow Mode unchanged");
		int count = 0;

/*        while (isOperatorControl() && isEnabled()) {
    		SmartDashboard.putString("SpeedMode", "hi");
   			myRobot.tankDrive(0, 0, true);
        }
        */
//		double CAangle = cameraArm.getAngle();
//		SmartDashboard.putString("CameraArm", "" + CAangle);
		SmartDashboard.putString("mode status", "got here 1");
		SmartDashboard.putString("ERROR CATCH", "" + errorCount);
        while (isOperatorControl() && isEnabled()) {
        	try {     		
//    		SmartDashboard.putString("mode status", "got here 2");
        	//if(count%3==0) 
        		cameraPair.displayImages();
        	
        	if(stick.getPOV() == POV_FASTMODE) {
        		speedMultiplier = 0.9;
        		SmartDashboard.putString("SpeedMode", "Fast Mode");
        	} 
        	else if (stick.getPOV() == POV_SLOWMODE) {
        		speedMultiplier = LOWSPEED;
        		SmartDashboard.putString("SpeedMode", "Slow Mode");
        	}

        	
/*        	if(stick.getPOV() == 90) {
        		CAangle += 5;
        		cameraArm.setAngle(CAangle);
        		SmartDashboard.putString("CameraArm", "" + CAangle);
        	} 
        	else if (stick.getPOV() == 270) {
        		CAangle -= 5;
        		cameraArm.setAngle(CAangle);
        		SmartDashboard.putString("CameraArm", "" + CAangle);
        	}
*/
        	if(stick.getRawButton(4)) {
//        		directionMultiplier = 1;
        		cameraPair.setCamera(HFCameraPairSwitchable.CAMERA_TWO);
        	}
        	else if (stick.getRawButton(1)) {
 //       		directionMultiplier = -1;
           		cameraPair.setCamera(HFCameraPairSwitchable.CAMERA_ONE);
        	}
        	
       		if(cameraPair.activeCamera == HFCameraPairSwitchable.CAMERA_TWO) {
       			myRobot.tankDrive(-speedMultiplier*stick.getRawAxis(5), -speedMultiplier*stick.getRawAxis(1), true);
       		}
       		else {
       			myRobot.tankDrive(speedMultiplier*stick.getRawAxis(1), speedMultiplier*stick.getRawAxis(5), true);
       		}
        	 
        	if(stick.getRawButton(5)) {
        		defenseShield.setMoveUp();
        	}
        	else if(stick.getRawAxis(2) > .5) {
        		defenseShield.setMoveDown();
        	}
        	else {
        		defenseShield.stop();
        	}

        	bowler.setFromJoystick(stick);
        	
        	if(stick.getRawButton(3)) {
        		tail.setMoveDown();
        	}
        	else if(stick.getRawButton(2)) {
        		tail.setMoveUp(1);
        	}
        	else {
        		tail.stop();
        	}
        	
/*        	SmartDashboard.putString("XDist","" + gyro.getDisplacementX());
        	SmartDashboard.putString("YDist","" + gyro.getDisplacementY());
        	SmartDashboard.putString("ZDist","" + gyro.getDisplacementZ());
        	SmartDashboard.putString("XVel","" + gyro.getVelocityX());
        	SmartDashboard.putString("YVel","" + gyro.getVelocityY());
        	SmartDashboard.putString("ZVel","" + gyro.getVelocityZ());
  */      	
        	count =(count + 1) % 1000;
        	} catch (Exception e) {
        		String ret = e.getMessage();
        		if(ret.length() > 20) ret = ret.substring(0, 19);
        		errorCount++;
        		SmartDashboard.putString("ERROR CATCH", "" + errorCount + "  " + ret);
        	}
//            Timer.delay(0.01);		// wait for a motor update time
        }
        
	}

    /**
     * Runs during test mode
     */
    public void test() {
//    	gyro.reset();
    	turnDegree(90,.6,TEST);
    	while(isEnabled()) {
    		displaySensorData(true,true);    		
    	}

    
    }
    
    
    public void disabled() {
//    	gyro.reset();
    	boolean cameraDisabledDisabled = false;
		SmartDashboard.putString("Display", "ThatLine");
		SmartDashboard.putString("HERE", "ON - b");
    	while(!isEnabled()) {
    		if(!cameraDisabledDisabled) cameraPair.displayImages();
    		//SmartDashboard.putString("Display", "ThatLine1");

    		displaySensorData(true,true);
    		
    		//SmartDashboard.putString("Display", "ThatLine2");

    		
    		double comp = gyro.getCompassHeading();
			SmartDashboard.putString("Compass", "" + comp);
    		
       
    		if(stick.getRawButton(4)) {
    			cameraPair.setCamera(HFCameraPairSwitchable.CAMERA_TWO);
    			cameraDisabledDisabled = false;
    			SmartDashboard.putString("Camera", "ON");
    		}
    		else if (stick.getRawButton(1)) {
    			cameraPair.setCamera(HFCameraPairSwitchable.CAMERA_ONE);
    			cameraDisabledDisabled = false;
    			SmartDashboard.putString("Camera", "ON");
    		} else if(cameraDisabledDisabled && stick.getRawButton(2)) {
    			cameraDisabledDisabled = false;
    			SmartDashboard.putString("Camera", "ON");
    		} else if(!cameraDisabledDisabled && stick.getRawButton(3)) {
    			cameraDisabledDisabled = true;
    			SmartDashboard.putString("Camera", "OFF");
    		}
    		Timer.delay(.1);
    	}   	 
    }
   
// autonomous steps    
    
    public boolean modeEnabled(int mode) {
    	if(mode == AUTO) {
    		return isEnabled() && isAutonomous();
    	} else if(mode == TELE) {
    		return isEnabled() && isOperatorControl();    		
    	} else if(mode == TEST) {
    		return isEnabled();    		    		
    	}
   		return false;
    }
    
    public boolean turnDegree(double angle, double maxSpeed, int mode) {
    	double angleReading = 0;//startAngle;
		SmartDashboard.putString("Gyro Turn To","" + angle);
	   	double startAngle = gyro.getAngle();
	  	maxSpeed = Math.abs(maxSpeed);
	  	double speed = maxSpeed;
//		double correctionSpeed = .62*speed;
//		if(correctionSpeed < .42) correctionSpeed = .42;

	  	int attempts = 0;
		double minSpeed = .35;
		angleReading = gyro.getAngleRelativeTo(startAngle);
		while(attempts < 2 && Math.abs(angleReading - angle)>0.99) {
			if(angle > 0) {
				SmartDashboard.putString("Gyro Angle",""+startAngle);//Reading);
				angleReading = gyro.getAngleRelativeTo(startAngle);
				while(modeEnabled(mode) &&  angleReading < angle) {
					speed = minSpeed + (angle-angleReading)/100;
					if(speed > maxSpeed) speed = maxSpeed;
					myRobot.setLeftRightMotorOutputs(-speed, speed);
					Timer.delay(0.001); // wait for a motor update time
					angleReading = gyro.getAngleRelativeTo(startAngle);
					SmartDashboard.putString("Gyro Angle","A "+angleReading);
				}
				myRobot.setLeftRightMotorOutputs(0,0);
				Timer.delay(.01);
				angleReading = gyro.getAngleRelativeTo(startAngle);
//				SmartDashboard.putString("TURN DEGREE","FINISH STAGE 1b " +angleReading);
				while(modeEnabled(mode) && angleReading > angle) {
					speed = minSpeed + (angleReading-angle)/100;
					if(speed > maxSpeed) speed = maxSpeed;
					myRobot.setLeftRightMotorOutputs(speed, -speed);
					//myRobot.setLeftRightMotorOutputs(correctionSpeed, -correctionSpeed);
					Timer.delay(0.001); // wait for a motor update time
					angleReading = gyro.getAngleRelativeTo(startAngle);
					SmartDashboard.putString("Gyro Angle","B "+angleReading);
				}
				myRobot.setLeftRightMotorOutputs(0,0);
//				SmartDashboard.putString("TURN DEGREE","FINISH STAGE 2 " +angleReading);
				Timer.delay(.01);
			} else {
				SmartDashboard.putString("Gyro Angle",""+startAngle);//Reading);
				angleReading = gyro.getAngleRelativeTo(startAngle);
				while(modeEnabled(mode) && angleReading > angle ) {
						speed = minSpeed + (angleReading-angle)/70;
						if(speed > maxSpeed) speed = maxSpeed;
					myRobot.setLeftRightMotorOutputs(speed, -speed);
					Timer.delay(0.001); // wait for a motor update time
					angleReading = gyro.getAngleRelativeTo(startAngle);
					SmartDashboard.putString("Gyro Angle","C "+angleReading);
				}
				myRobot.setLeftRightMotorOutputs(0,0);
				Timer.delay(.01);
				angleReading = gyro.getAngleRelativeTo(startAngle);
				while(modeEnabled(mode) && angleReading < angle) {
					speed = minSpeed + (angle-angleReading)/70;
					if(speed > maxSpeed) speed = maxSpeed;
					myRobot.setLeftRightMotorOutputs(-speed, speed);
					//myRobot.setLeftRightMotorOutputs(-correctionSpeed, correctionSpeed);
					Timer.delay(0.001); // wait for a motor update time
					angleReading = gyro.getAngleRelativeTo(startAngle);
					SmartDashboard.putString("Gyro Angle","D "+angleReading);
				}
				myRobot.setLeftRightMotorOutputs(0,0);
	//			Timer.delay(.05);
			}
			attempts++;
			Timer.delay(0.025);
		}
    	return true;
    } 

    /*
         public boolean turnDegree(double angle, double speed, int mode) {
    	double startAngle = gyro.getAngle();
    	double angleReading = 0;//startAngle;
//    	double targetAngle=0;
		SmartDashboard.putString("Gyro Turn To","" + angle);
		speed = Math.abs(speed);
		double correctionSpeed = .75*speed;
    	if(angle > 0) {
//        	targetAngle = startAngle + angle;
//        	if(targetAngle > 360) targetAngle -= 360;
			SmartDashboard.putString("Gyro Angle",""+startAngle);//Reading);
			angleReading = gyro.getAngleRelativeTo(startAngle);
        	while(modeEnabled(mode) &&  angleReading < angle) {
//         	while(modeEnabled(mode) && angleReading < targetAngle ) {
      			myRobot.setLeftRightMotorOutputs(-speed, speed);
                Timer.delay(0.001); // wait for a motor update time
//                angleReading = gyro.getAngle()-startAngle;
    			angleReading = gyro.getAngleRelativeTo(startAngle);
    			SmartDashboard.putString("Gyro Angle","A "+angleReading);
        	}
        	while(modeEnabled(mode) && angleReading > angle) {
//            	while(modeEnabled(mode) && angleReading > targetAngle) {
      			myRobot.setLeftRightMotorOutputs(correctionSpeed, -correctionSpeed);
                Timer.delay(0.001); // wait for a motor update time
//                angleReading = gyro.getAngle()-startAngle;
    			angleReading = gyro.getAngleRelativeTo(startAngle);
    			SmartDashboard.putString("Gyro Angle","B "+angleReading);
        	}
    	} else {
//        	targetAngle = startAngle + angle;
			angleReading = gyro.getAngleRelativeTo(startAngle);
        	while(modeEnabled(mode) && angleReading > angle ) {
      			myRobot.setLeftRightMotorOutputs(speed, -speed);
                Timer.delay(0.001); // wait for a motor update time
//                angleReading = gyro.getAngle();
    			angleReading = gyro.getAngleRelativeTo(startAngle);
    			SmartDashboard.putString("Gyro Angle","C "+angleReading);
        	}
        	
        	while(modeEnabled(mode) && angleReading < angle) {
//            	while(modeEnabled(mode) && angleReading < targetAngle) {
      			myRobot.setLeftRightMotorOutputs(-correctionSpeed, correctionSpeed);
                Timer.delay(0.001); // wait for a motor update time
//                angleReading = gyro.getAngle();
    			angleReading = gyro.getAngleRelativeTo(startAngle);
    			SmartDashboard.putString("Gyro Angle","D "+angleReading);
        	}
    	}
    	return true;
    }    
    
 
     
     */
    
    
    public void driveForTime(double power, long time){
    	long endTime = System.currentTimeMillis()  + time; 
    	if(gyro.sensorEnabled) {
 //   		double realAngle = 0;
    		double gyroAngle;
    		double startAngle = gyro.getAngle();
    		while(System.currentTimeMillis() < endTime && isEnabled() && isAutonomous()) {
//    			realAngle = gyro.getAngle();
    			gyroAngle = gyro.getAngleRelativeTo(startAngle);
    			myRobot.drive(power, -.03*gyroAngle);     		
    			Timer.delay(0.005); // wait for a motor update time
    		}
    	} else {
    		while(System.currentTimeMillis() < endTime && isEnabled() && isAutonomous()) {
    			myRobot.drive(power,0);        		
    			Timer.delay(0.005); // wait for a motor update time
    		}	
    	}
		myRobot.drive(0,0);        		
    }
    
    public boolean driveWithinDistance(double targetDistance,double speed){
    	distanceSensor.smartInitialize();
    	SmartDashboard.putString("statusdistance", "driving within " + targetDistance);
		double distance= distanceSensor.getSmartDistance(false);
		double initialAngle = gyro.getAngle();
		SmartDashboard.putString("start","" + initialAngle);
		double curSpeed;
		double maxSpeed;
		double minSpeed = Math.max(.2,.6*speed);
		maxSpeed = speed;
		int maxLoop = 2;
		int curLoop = 0;
		while(isEnabled() && curLoop < maxLoop && Math.abs(distance - targetDistance)>1.0) {	
//	    	SmartDashboard.putString("Distance", " " + distance);
			while(isEnabled() && distance> targetDistance) {
	//	    	SmartDashboard.putString("Distance", " " + distance);
				curSpeed = minSpeed + (distance - targetDistance)/24;
				if(curSpeed > maxSpeed) curSpeed = maxSpeed;
				myRobot.drive(-curSpeed, -.03*gyro.getAngleRelativeTo(initialAngle));// (gyro.getAngle()-initialAngle));
				SmartDashboard.putString("Gyro Angle","" + gyro.getAngle());
				distance = distanceSensor.getSmartDistance(false);
				SmartDashboard.putString("Distance DWD","*" + distance);
				if(distance == HFDistanceSensor.flagOnErrorValue) distance = targetDistance + 1;
				// Timer.delay(.005);
			}
			myRobot.drive(0,0);
			Timer.delay(.005);

			maxSpeed = .3;
			while(isEnabled() && distance< targetDistance) {
		    	SmartDashboard.putString("Distance", " " + distance);
 				curSpeed = minSpeed + (targetDistance - distance)/24;
				if(curSpeed > maxSpeed) curSpeed = maxSpeed;
				myRobot.drive(curSpeed, -.03*gyro.getAngleRelativeTo(initialAngle));// (gyro.getAngle()-initialAngle));
				//	SmartDashboard.putString("Gyro Angle","" + gyro.getAngle());
				distance = distanceSensor.getSmartDistance(false);
				if(distance == HFDistanceSensor.flagOnErrorValue) distance = targetDistance - 1;
				//    		Timer.delay(.05);
			}
			myRobot.drive(0,0);
			Timer.delay(.025);
			distance= distanceSensor.getSmartDistance(false);
			curLoop++;
		}
	  	SmartDashboard.putString("Gyro Angle","" + gyro.getAngle());
		turnDegree(-gyro.getAngleRelativeTo(initialAngle),.3,AUTO);//  gyro.getAngle()-initialAngle),.3,AUTO);

    	SmartDashboard.putString("statusdistance", "driving within complete");
		distance= distanceSensor.getSmartDistance(false);
    	return true;
    }

}
