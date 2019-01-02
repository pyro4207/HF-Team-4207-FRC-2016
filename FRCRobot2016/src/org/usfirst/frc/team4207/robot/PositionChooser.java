package org.usfirst.frc.team4207.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class PositionChooser extends SendableChooser {
	public static final int POSITION_NOBOWLING = -1;
	// note the numbers do not actually matter as long as they are unique
	//DEFENSES
	public static final int POSITION_1_FARLEFT = 00;
	public static final int POSITION_2_MIDLEFT = 01;
	public static final int POSITION_3_MIDDLE = 10;
	public static final int POSITION_4_MIDRIGHT = 11;
	public static final int POSITION_5_FARRIGHT = 12;
	

	public PositionChooser() {
        addDefault("No Bowling", POSITION_NOBOWLING);
        addObject("1 - Far Left Low Bar", POSITION_1_FARLEFT);
        addObject("2 - Mid Left Pos", POSITION_2_MIDLEFT);
        addObject("3 - Middle Pos", POSITION_3_MIDDLE);
        addObject("4 - Mid Right Pos", POSITION_4_MIDRIGHT);
        addObject("5 - Far Right", POSITION_5_FARRIGHT);
	}

}
