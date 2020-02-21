/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class DriveBase extends SubsystemBase 
{
	// The motors on the left side of the drive.
	private final SpeedControllerGroup m_leftMotors = new SpeedControllerGroup(new CANSparkMax(RobotMap.LeftDriveMotor1, MotorType.kBrushless),
			new CANSparkMax(RobotMap.LeftDriveMotor2, MotorType.kBrushless));

	// The motors on the right side of the drive.
	private final SpeedControllerGroup m_rightMotors = new SpeedControllerGroup(new CANSparkMax(RobotMap.RightDriveMotor1, MotorType.kBrushless),
			new CANSparkMax(RobotMap.RightDriveMotor2, MotorType.kBrushless));

	// The robot's drive
	private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

	// Left Side Drive Encoder
	private final CANCoder m_leftEncoder = new CANCoder(RobotMap.LeftEncoder);

	// Right Side Drive Encoder
	private final CANCoder m_rightEncoder = new CANCoder(RobotMap.RightEncoder);

	// The gyro sensor
	private final PigeonIMU m_gyro = new PigeonIMU(new TalonSRX(RobotMap.GyroPort));

	// Odometry class for tracking robot pose
	private final DifferentialDriveOdometry m_odometry;

	private float driveSensitivity;

	//PowerDistributionPanel PDP;
	
	/**
	 * Creates a new DriveSubsystem.
	 */
    public DriveBase() 
    {
		//this.PDP = PDP;

		// Sets the distance per pulse for the encoders

		//m_leftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
		//m_rightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);

		resetEncoders();
		m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

		driveSensitivity = 0.6f;
	}

	@Override
    public void periodic()
    {
		//VoltageCheck();

		// Update the odometry in the periodic block
		m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition() * (DriveConstants.kLeftEncoderReversed ? -1.0 : 1.0),
				m_rightEncoder.getPosition() * (DriveConstants.kRightEncoderReversed ? -1.0 : 1.0));

		System.out.println("LEFT CANCoder: " + m_leftEncoder.getPosition() + " RIGHT CANCoder: " + m_rightEncoder.getPosition() + " Angle: " + m_gyro.getFusedHeading());
	}

	/**
	 * Returns the currently-estimated pose of the robot.
	 *
	 * @return The pose.
	 */
    public Pose2d getPose() 
    {
		return m_odometry.getPoseMeters();
	}

	/**
	 * Returns the current wheel speeds of the robot.
	 *
	 * @return The current wheel speeds.
	 */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() 
    {
		return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity(), m_rightEncoder.getVelocity());
	}

	/**
	 * Resets the odometry to the specified pose.
	 *
	 * @param pose The pose to which to set the odometry.
	 */
    public void resetOdometry(Pose2d pose) 
    {
		resetEncoders();
		m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
	}

	/**
	 * Drives the robot using arcade controls.
	 *
	 * @param fwd the commanded forward movement
	 * @param rot the commanded rotation
	 */
    public void arcadeDrive(double fwd, double rot) 
    {
		m_drive.arcadeDrive(fwd * driveSensitivity, rot * Math.abs(driveSensitivity));
	}

	public void reverseDrive()
	{
		driveSensitivity = -driveSensitivity;
	}

	/**
	 * Controls the left and right sides of the drive directly with voltages.
	 *
	 * @param leftVolts  the commanded left output
	 * @param rightVolts the commanded right output
	 */
    public void tankDriveVolts(double leftVolts, double rightVolts) 
    {
		m_leftMotors.setVoltage(leftVolts);
		m_rightMotors.setVoltage(-rightVolts);
	}

	/**
	 * Resets the drive encoders to currently read a position of 0.
	 */
    public void resetEncoders() 
    {
		m_leftEncoder.setPosition(0.00);
		m_rightEncoder.setPosition(0.00);
	}

	/**
	 * Gets the average distance of the two encoders.
	 *
	 * @return the average of the two encoder readings
	 */
    public double getAverageEncoderDistance() 
    {
		return (m_leftEncoder.getPosition() * (DriveConstants.kLeftEncoderReversed ? -1.0 : 1.0) + m_rightEncoder.getPosition() * (DriveConstants.kRightEncoderReversed ? -1.0 : 1.0)) / 2.0;
	}

	/**
	 * Gets the left drive encoder.
	 *
	 * @return the left drive encoder
	 */
    public CANCoder getLeftEncoder() 
    {
		return m_leftEncoder;
	}

	/**
	 * Gets the right drive encoder.
	 *
	 * @return the right drive encoder
	 */
    public CANCoder getRightEncoder() 
    {
		return m_rightEncoder;
	}

	/**
	 * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
	 *
	 * @param maxOutput the maximum output to which the drive will be constrained
	 */
    public void setMaxOutput(double maxOutput) 
    {
		m_drive.setMaxOutput(maxOutput);
	}

	/**
	 * Zeroes the heading of the robot.
	 */
    public void zeroHeading() 
    {
		m_gyro.setCompassAngle(0.0, Constants.DriveConstants.kTimeoutMs);
	}

	/**
	 * Returns the heading of the robot.
	 *
	 * @return the robot's heading in degrees, from 180 to 180
	 */
    public double getHeading() 
    {
		return m_gyro.getAbsoluteCompassHeading();
	}

	/**
	 * Returns the turn rate of the robot.
	 *
	 * @return The turn rate of the robot, in degrees per second
	 */
    public double getTurnRate() 
    {
		double[] mXYZDegreePerSecond = new double[3];
		m_gyro.getRawGyro(mXYZDegreePerSecond);

   		return mXYZDegreePerSecond[2] * (Constants.DriveConstants.kGyroReversed ? -1.0 : 1.0);
	}

	/* void VoltageCheck()
	{
		if(PDP.getCurrent(RobotMap.kLeftMotor1PDPChannel) > Constants.NEOVoltageLimit
		|| PDP.getCurrent(RobotMap.kLeftMotor2PDPChannel) > Constants.NEOVoltageLimit)
        {
            m_leftMotors.setVoltage(0);
		}
		
		if(PDP.getCurrent(RobotMap.kRightMotor1PDPChannel) > Constants.NEOVoltageLimit
		|| PDP.getCurrent(RobotMap.kRightMotor2PDPChannel) > Constants.NEOVoltageLimit)
		{
			m_rightMotors.setVoltage(0);
		}
	}*/
}
