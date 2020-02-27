/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants 
{
    public static final double targetHeight = 2.49; //meters
    public static final double crosshairHeight = 2.23; //meters | old: 2.3876
    public static final double shooterHeight = 0.6858; //meters
    public static final double heightDiff = targetHeight - shooterHeight; //meters
    public static final double fuelAirTimeSquared = heightDiff * 2 / 9.8; //seconds^2
    public static final double fuelAirTime = 0.608; //seconds
    public static final double fuelInitVelocY = 9.8 * fuelAirTime; //meters / second

    public static final double shooterMaxVeloc = 8.967; //meters / second
    public static final double shooterMaxVelocToTheFourth = shooterMaxVeloc * shooterMaxVeloc * shooterMaxVeloc * shooterMaxVeloc;
    public static final double g = -9.8; //meters / second^2
    
    // Estimating Distance With Limelight
    // Based On : https://docs.limelightvision.io/en/latest/cs_estimating_distance.html
    public static final double cameraHeight = 0.65405; // in meters
    public static final double cameraAngle = 20 * (Math.PI / 180); // in radians
    
    public static final double shooterkP = 0.3; //TODO: Tune Shooter PID
    public static final double shooterkI = 0.0;
    public static final double shooterkD = 0.0;

    public static final double ticksPerRadian = 2850 / (5 * Math.PI / 18);

    public static final double maxAngle = 14 * Math.PI / 36;
    public static final double minAngle = maxAngle - 2 * Math.PI / 9;
    
    public static final double hoodkP = 0.01; 
    public static final double hoodkI = 0.007; //these ones work I think
    public static final double hoodkD = 0.0;

    public static final double limekP = 0.09; //TODO: Tune limelight PID on carpet
    public static final double limekI = 0.0;
    public static final double limekD = 0.01;

    public static final double basekP = 0.05; //TODO: Tune drivebase PID on carpet
    public static final double basekI = 0.0;
    public static final double basekD = 0.0;

    public static final class DriveConstants 
    {
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = true;

        public static final double kTrackwidthMeters = 0.69;
        public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterMeters = 0.15;
        public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

        public static final boolean kGyroReversed = true;
        public static final double kTurnP = .005;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;
        public static final double kTurnToleranceDeg = 5;
        public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
        // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
        // These characterization values MUST be determined either experimentally or theoretically
        // for *your* robot's drive.
        // The Robot Characterization Toolsuite provides a convenient tool for obtaining these
        // values for your robot.
        public static final double ksVolts = 0.22;
        public static final double kvVoltSecondsPerMeter = 1.98;
        public static final double kaVoltSecondsSquaredPerMeter = 0.2;

        // Example value only - as above, this must be tuned for your drive!
        public static final double kPDriveVel = 8.5;

        public static final int kTimeoutMs = 20;
    }

    public static final class AutoConstants 
    {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }
}
