package HF2016Utility;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class HFPiston {
	// Variables
	Solenoid solenoidSideIn;
	Solenoid solenoidSideOut;

	int dashboardLED;
//	String displayName;
	// Constructors
	public HFPiston(int sideIn, int sideOut) {
		this.solenoidSideIn = new Solenoid(sideIn);
		this.solenoidSideOut = new Solenoid(sideOut);
		dashboardLED = -1;
	}	
	public HFPiston(int sideIn, int sideOut,int dashboardLED) {
		this.solenoidSideIn = new Solenoid(sideIn);
		this.solenoidSideOut = new Solenoid(sideOut);
		this.dashboardLED = dashboardLED;
	}	
	// Methods
	public void pistonIn() {
		solenoidSideIn.set(true);
		solenoidSideOut.set(false);
		if(dashboardLED > -1) SmartDashboard.putString("DB/String " + dashboardLED, "Piston IN-");
	}
	
	public void pistonOut() {
		solenoidSideIn.set(false);
		solenoidSideOut.set(true);
		if(dashboardLED > -1) SmartDashboard.putString("DB/String " + dashboardLED, "Piston OUT-----");
	}
	

}
