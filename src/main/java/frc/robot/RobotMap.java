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

    public static final int kLeftEncoderPort = 1;
    public static final int kRightEncoderPort = 2;

    public static final int kGyroPort = 3;

    public static final int shooter1 = 4;
    public static final int shooter2 = 5;
    public static final int hood = 1;

    public static final int winch = 7;
    public static final int climber1 = 8;
    public static final int climber2 = 9;

    public static final int intake = 10;
    public static final int belt1 =  11;
    public static final int belt2 = 12;
    public static final int belt3 = 13;

    public static final int panelWheelMotor = 14;

    public static final int PCM = 15;
    public static final int colorWheelPistonChannel = 1;
    public static final int intakeDeployPistons = 2;

    public static final int beamBreak1 = 0;
    public static final int beamBreak2 = 1;
    public static final int beamBreak3 = 2;
    public static final int leftLimitSwitch = 3;
    public static final int rightLimitSwitch = 4;

    // Voltage Control
    
    /*
        Voltage Control Docs :
            https://docs.wpilib.org/en/latest/docs/software/can-devices/power-distribution-panel.html
    */

    // PDP Channels 

    public static final int PDPCanID = 0;

    public static final int kLeftMotor1PDPChannel = 1;
    public static final int kLeftMotor2PDPChannel = 2;
    public static final int kRightMotor1PDPChannel = 3;
    public static final int kRightMotor2PDPChannel = 4;

    public static final int shooter1PDPChannel = 5;
    public static final int shooter2PDPChannel = 6;
    public static final int hoodPDPChannel = 7;

    public static final int winchPDPChannel = 8;
    public static final int climber1PDPChannel = 9;
    public static final int climber2PDPChannel = 9;

    public static final int intakePDPChannel = 10;
    public static final int belt1PDPChannel =  11;
    public static final int belt2PDPChannel = 12;
    public static final int belt3PDPChannel = 13;

    public static final int panelWheelMotorPDPChannel = 14;

    public static final int PCMPDPChannel = 15;
}
