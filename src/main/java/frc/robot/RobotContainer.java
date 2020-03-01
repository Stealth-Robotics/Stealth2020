
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoCommands.AutoPaths.ThreeBallAuto;
import frc.robot.commands.BeltsCommands.BeltsDefault;
import frc.robot.commands.BeltsCommands.ReverseBelt;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.IntakeCommands.IntakeFuel;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DistanceSensor;
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
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveBase driveBase;
    private final Shooter shooter;
    private final Intake intake;
    private final Climber climber;
    private final Belts belts;
    // private final PanelControl panelControl;

    private final Limelight limelight;
    private final DistanceSensor distanceSensor;

    private final ThreeBallAuto autoCommand;

    private Joystick driveJoystick;
    private Joystick mechJoystick;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        driveBase = new DriveBase();
        shooter = new Shooter();
        intake = new Intake();
        climber = new Climber();
        belts = new Belts();
        // panelControl = new PanelControl();

        limelight = new Limelight();
        distanceSensor = new DistanceSensor();

        driveJoystick = new Joystick(0);
        mechJoystick = new Joystick(1);

        driveJoystick.setYChannel(1);
        driveJoystick.setXChannel(4);

        // Configure the button bindings
        configureButtonBindings();

        driveBase.setDefaultCommand(new RunCommand(
                () -> driveBase.arcadeDrive(driveJoystick.getRawAxis(1), driveJoystick.getRawAxis(2)), driveBase));

        belts.setDefaultCommand(new BeltsDefault(belts));

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
        camera.setResolution(320, 240);
        camera.setFPS(25);

        autoCommand = new ThreeBallAuto(driveBase, shooter, belts, limelight, intake, distanceSensor);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        new JoystickButton(driveJoystick, 2).whenPressed(() -> driveBase.reverseDrive());
        // new JoystickButton(driveJoystick, 2).whenHeld(new ScoreFuel(driveBase,
        // shooter, belts, limelight, distanceSensor));
        new JoystickButton(driveJoystick, 1).whenHeld(
            new SequentialCommandGroup(
                new InstantCommand(() -> limelight.SetLedMode(3)),
                new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)),
                new ReverseBelt(belts, 300),
                new WaitCommand(0.2),
                new AlignWithTarget(driveBase, limelight, distanceSensor),
                new AimHood(shooter, distanceSensor, false),
                new InstantCommand(() -> shooter.setShooterSpeedDirect(0.825)),
                new WaitCommand(0.3), 
                new FireShooter(shooter, belts)
            )
        );

        new JoystickButton(driveJoystick, 3).whenHeld(
            new SequentialCommandGroup(
                new InstantCommand(() -> limelight.SetLedMode(3)),
                new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)),
                new ReverseBelt(belts, 300),
                new WaitCommand(0.2),
                new AlignWithTarget(driveBase, limelight, distanceSensor),
                new AimHood(shooter, distanceSensor, true),
                new InstantCommand(() -> shooter.setShooterSpeedDirect(0.825)),
                new WaitCommand(0.3), 
                new FireShooter(shooter, belts)
            )
        );

        new JoystickButton(driveJoystick, 4).whenHeld(
            new SequentialCommandGroup(
                new InstantCommand(() -> limelight.SetLedMode(3)),
                new ParallelDeadlineGroup(new AlignWithTarget(driveBase, limelight, distanceSensor),
                    new AimHood(shooter, distanceSensor, true).perpetually()),
                new InstantCommand(() -> shooter.setShooterSpeedDirect(0.825)),
                new ReverseBelt(belts, 300).withTimeout(0.2),
                new WaitCommand(0.1), 
                new FireShooter(shooter, belts)
            )
        );

        new JoystickButton(driveJoystick, 1).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));
        new JoystickButton(driveJoystick, 3).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));
        new JoystickButton(driveJoystick, 4).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));

        new JoystickButton(mechJoystick, 9).whenHeld(new IntakeFuel(intake));

        new JoystickButton(mechJoystick, 8).whenHeld(new StartEndCommand(
            ///() -> climber.runClimb(0.85, 0.6, 0),
            () -> belts.reverseAllBelts(),
            () -> belts.stopAllBelts())
            .alongWith(new StartEndCommand(
                        () -> intake.reverse(),
                        () -> intake.stopIntake()
                    )));

        // new JoystickButton(mechJoystick, 8).whenHeld(new StartEndCommand(
        //     ///() -> climber.runClimb(0.85, 0.6, 0),
        //     () -> intake.reverse(),
        //     () -> intake.stopIntake()
        // ));

        new JoystickButton(mechJoystick, 5).whenHeld(new StartEndCommand(
            ///() -> climber.runClimb(0.85, 0.6, 0),
            () -> climber.runClimb(0.6, 0.6, 0),
            () -> climber.runClimb(0, 0, 0)
        ));

        new JoystickButton(mechJoystick, 6).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(-0.7, -0.7, 0),	
            () -> climber.runClimb(0, 0, 0)	
        ));	

        new JoystickButton(mechJoystick, 2).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0, 0, 0.75),	
            () -> climber.runClimb(0, 0, 0)	
        )/* .beforeStarting(() -> intake.setDeployment(true)) */);

        new JoystickButton(mechJoystick, 3).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0, 0, -0.5),	
            () -> climber.runClimb(0, 0, 0)	
        ));
    }
  
  
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() 
    {
        return autoCommand;
    }
}
