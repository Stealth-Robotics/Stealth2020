
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.BeltsCommands.BeltsDefault;
import frc.robot.commands.IntakeCommands.IntakeFuel;
import frc.robot.commands.MultiSubsystemCommands.ScoreFuel;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final DriveBase driveBase;
    private final Shooter shooter;
    private final Intake intake;
    private final Climber climber;
    //private final PanelControl panelControl;
    private final Belts belts;
    private final Limelight limelight;

    private Joystick driveJoystick;
    private Joystick mechJoystick;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer()
    {
        driveBase = new DriveBase();
        shooter = new Shooter();
        intake = new Intake();
        climber = new Climber();
        belts = new Belts();
        //panelControl = new PanelControl();

        limelight = new Limelight();

        driveJoystick = new Joystick(0);
        mechJoystick = new Joystick(1);

        driveJoystick.setYChannel(1);
        driveJoystick.setXChannel(4);

        // Configure the button bindings
        configureButtonBindings();

        driveBase.setDefaultCommand(new RunCommand(
                () -> driveBase.arcadeDrive(driveJoystick.getRawAxis(1) * (driveJoystick.getRawButton(4) ? 1 : 0.6), driveJoystick.getRawAxis(4)), driveBase));

        belts.setDefaultCommand(new BeltsDefault(belts));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        new JoystickButton(driveJoystick, 1).whenPressed(() -> driveBase.reverseDrive());
        new JoystickButton(driveJoystick, 2).whileHeld(new ScoreFuel(driveBase, shooter, belts, limelight));
        new JoystickButton(driveJoystick, 3).whenPressed(new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)));

        new JoystickButton(mechJoystick, 1).whenHeld(new IntakeFuel(intake));

        // new JoystickButton(mechJoystick, 2).whenHeld(new StartEndCommand(
        // () -> this.intake.run(),
        // () -> this.intake.stopIntake()
        // ));

        new JoystickButton(mechJoystick, 2).whenHeld(new AimHood(shooter, limelight));

        new JoystickButton(mechJoystick, 3).whenHeld(new StartEndCommand(
            () -> climber.runClimb(0.6, 0),
            () -> climber.runClimb(0, 0)
        ));

        new JoystickButton(mechJoystick, 4).whenHeld(new StartEndCommand(
            () -> climber.runClimb(-0.4, 0),
            () -> climber.runClimb(0, 0)
        ));

        new JoystickButton(mechJoystick, 5).whenHeld(new StartEndCommand(
            () -> climber.runClimb(0, -0.5),
            () -> climber.runClimb(0, 0)
        ));

        new JoystickButton(mechJoystick, 6).whenHeld(new StartEndCommand(
            () -> climber.runClimb(0, 0.5),
            () -> climber.runClimb(0, 0)
        ));

        new JoystickButton(mechJoystick, 7).whenHeld(new FireShooter(shooter, belts));

        StartEndCommand runBelts = new StartEndCommand(
            () -> belts.runAllBelts(),
            () -> belts.stopAllBelts()
        );
        runBelts.addRequirements(belts);
        new JoystickButton(mechJoystick, 8).whenHeld(runBelts);
    }
  
  
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() 
    {
        // TODO : Add Choosing Functionality
        String trajectoryJSON = "PathWeaver/Output/output/BlueCenter_SimpleShoot.wpilib.json";

        Path trajectoryPath;
        Trajectory trajectory = null;

        try
        {
            trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        }
    
        catch (IOException ex)
        {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }

        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            driveBase::getPose,
            new RamseteController(Constants.AutoConstants.kRamseteB, Constants.AutoConstants.kRamseteZeta),
            new SimpleMotorFeedforward(Constants.DriveConstants.ksVolts,
                                       Constants.DriveConstants.kvVoltSecondsPerMeter,
                                       Constants.DriveConstants.kaVoltSecondsSquaredPerMeter),
            Constants.DriveConstants.kDriveKinematics,
            driveBase::getWheelSpeeds,
            new PIDController(Constants.DriveConstants.kPDriveVel, 0, 0),
            new PIDController(Constants.DriveConstants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            driveBase::tankDriveVolts,
            driveBase
        );

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> driveBase.tankDriveVolts(0, 0));
    }

    public void TurnOffLimelight()
    {
        limelight.SetLedMode(1);
    }

    public void TurnOnLimelight()
    {
        limelight.SetLedMode(3);
    }
}
