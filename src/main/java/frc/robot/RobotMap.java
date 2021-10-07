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

    //DriveBase
    public static final int LeftDriveMotor1 = 26;
    public static final int LeftDriveMotor2 = 25;
    public static final int RightDriveMotor1 = 22;
    public static final int RightDriveMotor2 = 24;

    public static final int LeftEncoder = 51;
    public static final int RightEncoder = 50;

    public static final int GyroPort = 35;

    //Shooter
    public static final int Shooter1 = 28;
    public static final int Shooter2 = 21;
    public static final int Hood = 31;
    public static final int DistanceSensor = 7;

    //Climber
    public static final int Winch = 29;
    public static final int Climber1 = 14;
    public static final int Climber2 = 14;

    public static final int LeftLimitSwitch = 8;
    public static final int RightLimitSwitch = 9;

    //Intake
    public static final int Intake = 35;
    public static final int IntakeHelper = 32;
    public static final int Belt1 =  33;
    public static final int Belt2 = 34;

    public static final int BeamBreak1 = 7; // intake beam break
    public static final int BeamBreak2 = 1;
    public static final int BeamBreak3 = 2;

    //ColorWheel
    public static final int ColorWheelMotor = 14;

    //Pneumatics
    public static final int PCM = 20;
    public static final int ColorWheelPCMChannel = 0;
    public static final int IntakeDeployPCMChannel = 1;

    
}
