/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;
import frc.robot.RobotMap;

public class DriveBase extends SubsystemBase 
{
	// The motors on the left side of the drive.
	private final SpeedControllerGroup m_leftMotors = new SpeedControllerGroup(new PWMSparkMax(RobotMap.kLeftMotor1Port),
			new PWMSparkMax(RobotMap.kLeftMotor2Port));

	// The motors on the right side of the drive.
	private final SpeedControllerGroup m_rightMotors = new SpeedControllerGroup(new PWMSparkMax(RobotMap.kRightMotor1Port),
			new PWMSparkMax(RobotMap.kRightMotor2Port));

	// The robot's drive
	private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

	// The left-side drive encoder
	private final Encoder m_leftEncoder = new Encoder(RobotMap.kLeftEncoderPorts[0], RobotMap.kLeftEncoderPorts[1],
			DriveConstants.kLeftEncoderReversed);

	// The right-side drive encoder
	private final Encoder m_rightEncoder = new Encoder(RobotMap.kRightEncoderPorts[0], RobotMap.kRightEncoderPorts[1],
			DriveConstants.kRightEncoderReversed);

	// The gyro sensor
	private final Gyro m_gyro = new ADXRS450_Gyro();

	// Odometry class for tracking robot pose
	private final DifferentialDriveOdometry m_odometry;

	/**
	 * Creates a new DriveSubsystem.
	 */
    public DriveBase() 
    {
		// Sets the distance per pulse for the encoders
		m_leftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
		m_rightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);

		resetEncoders();
		m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
	}

	@Override
    public void periodic()
    {
		// Update the odometry in the periodic block
		m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getDistance(),
				m_rightEncoder.getDistance());
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
		return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
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
		m_drive.arcadeDrive(fwd, rot);
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
		m_leftEncoder.reset();
		m_rightEncoder.reset();
	}

	/**
	 * Gets the average distance of the two encoders.
	 *
	 * @return the average of the two encoder readings
	 */
    public double getAverageEncoderDistance() 
    {
		return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
	}

	/**
	 * Gets the left drive encoder.
	 *
	 * @return the left drive encoder
	 */
    public Encoder getLeftEncoder() 
    {
		return m_leftEncoder;
	}

	/**
	 * Gets the right drive encoder.
	 *
	 * @return the right drive encoder
	 */
    public Encoder getRightEncoder() 
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
		m_gyro.reset();
	}

	/**
	 * Returns the heading of the robot.
	 *
	 * @return the robot's heading in degrees, from 180 to 180
	 */
    public double getHeading() 
    {
		return Math.IEEEremainder(m_gyro.getAngle(), 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
	}

	/**
	 * Returns the turn rate of the robot.
	 *
	 * @return The turn rate of the robot, in degrees per second
	 */
    public double getTurnRate() 
    {
		return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
	}
}
