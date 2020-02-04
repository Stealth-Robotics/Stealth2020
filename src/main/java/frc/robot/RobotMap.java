/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{  
    public static final int kLeftMotor1Port = 0;
    public static final int kLeftMotor2Port = 6;
    public static final int kRightMotor1Port = 2;
    public static final int kRightMotor2Port = 3;

    public static final int[] kLeftEncoderPorts = new int[]{0, 1};
    public static final int[] kRightEncoderPorts = new int[]{2, 3};

    public static final int shooter1 = 4;
    public static final int shooter2 = 5;
    public static final int hood = 1;
    public static final int[] shooterEncoderPorts = new int[]{4, 5};
    public static final int[] hoodEncoderPorts = new int[]{6, 7};

    public static final int winch = 7;
    public static final int claw1 = 8;
    public static final int claw2 = 9;

    public static final int intake = 10;
    public static final int belt1 =  11;
    public static final int belt2 = 12;
    public static final int belt3 = 13;

    public static final int panelWheelMotor = 14;

    public static final int PCM = 15;
    public static final int colorWheelPistonChannel = 1;
    public static final int intakeDeployPistons = 2;
}
