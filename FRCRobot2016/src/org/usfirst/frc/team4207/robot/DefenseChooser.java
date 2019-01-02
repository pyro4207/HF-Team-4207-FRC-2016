package org.usfirst.frc.team4207.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DefenseChooser extends SendableChooser {
	public static final int DEFENSE_DONOTHING = -1;
	// note the numbers do not actually matter as long as they are unique
	//DEFENSES
	public static final int DEFENSE_PORTCULLIS = 00;
	public static final int DEFENSE_CHEVAL = 01;
	public static final int DEFENSE_ROUGHTERRAIN = 10;
	public static final int DEFENSE_RAMPART = 11;
	public static final int DEFENSE_DRAWBRIDGE = 20;
	public static final int DEFENSE_SALLYPORT = 21;
	public static final int DEFENSE_MOAT = 30;
	public static final int DEFENSE_ROCKWALL = 41;
	public static final int DEFENSE_LOWBAR = 50;

	public DefenseChooser() {
        addDefault("Do Nothing", DEFENSE_DONOTHING);
        addObject("Cheval de Frise - teeter totter", DEFENSE_CHEVAL);
        addObject("Drawbridge", DEFENSE_DRAWBRIDGE);
        addObject("Rough Terrain", DEFENSE_ROUGHTERRAIN);
        addObject("Low Bar", DEFENSE_LOWBAR);
        addObject("Rampart", DEFENSE_RAMPART);
        addObject("Sally Port - side door", DEFENSE_SALLYPORT);
        addObject("Portcullis gate", DEFENSE_PORTCULLIS);
        addObject("Rock Wall", DEFENSE_ROCKWALL);
        addObject("Moat", DEFENSE_MOAT);
	}

}
