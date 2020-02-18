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
    public static final int kLeftMotor1Port = 26;
    public static final int kLeftMotor2Port = 25;
    public static final int kRightMotor1Port = 22;
    public static final int kRightMotor2Port = 24;

    public static final int kLeftEncoderPort = 51;
    public static final int kRightEncoderPort = 50;

    public static final int kGyroPort = 30;

    public static final int shooter1 = 28;
    public static final int shooter2 = 1;
    public static final int hood = 31;

    public static final int winch = 29;
    public static final int climber1 = 30;
    public static final int climber2 = 36;

    public static final int leftLimitSwitch = 8;
    public static final int rightLimitSwitch = 9;

    public static final int intake = 35;
    public static final int intakeHelper = 32;
    public static final int belt1 =  33;
    public static final int belt2 = 34;
    // does not exist public static final int belt3 = 13;

    public static final int panelWheelMotor = 14;

    public static final int PCM = 20;
    public static final int colorWheelPistonChannel = 1;
    public static final int intakeDeployPistons = 2;

    public static final int beamBreak1 = 0;
    public static final int beamBreak2 = 1;
    public static final int beamBreak3 = 2;
}
