
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoCommands.DriveForTicks;
import frc.robot.commands.AutoCommands.TurnToAngle;
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
public class RobotContainer 
{
    // The robot's subsystems and commands are defined here...
    private final DriveBase driveBase;
    private final Shooter shooter;
    private final Intake intake;
    private final Climber climber;
    private final Belts belts;
    // private final PanelControl panelControl;

    private final Limelight limelight;
    private final DistanceSensor distanceSensor;

    // private final Command autoCommand;

    private Joystick driveJoystick;
    private Joystick mechJoystick;

    SendableChooser<Command> m_chooser;

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

        //shooter.setDefaultCommand(new RunCommand(() -> shooter.setShooterSpeedDirect(0)));                                                                                                                    

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
        camera.setResolution(320, 240);
        camera.setFPS(25);

        // autoCommand = new SixBallAuto(driveBase, shooter, belts, limelight, intake, distanceSensor);
        SequentialCommandGroup sixBallAuto = new SequentialCommandGroup(
            new InstantCommand(() -> driveBase.zeroHeading()),
            new WaitCommand(0.25),
            new TurnToAngle(15, driveBase),
            new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
            new AlignWithTarget(driveBase, limelight, distanceSensor), new AimHood(shooter, distanceSensor, false),
            // new RunCommand(() -> shooter.setShooterSpeedDirect(0.85)).withTimeout(2),
            new InstantCommand(() -> shooter.enable()),
            new InstantCommand(() -> shooter.setShooterSpeed(4925)),
            new FireShooter(shooter, belts).withTimeout(2.5),
            new RunCommand(() -> shooter.setHoodPos(Constants.maxAngle)).withTimeout(0),
            // new InstantCommand(() -> shooter.disable()),
            
            new TurnToAngle(0, driveBase),
            new TurnToAngle(0, driveBase),
            new WaitCommand(1),
            new InstantCommand(() -> intake.setDeployment(true)),
            new WaitCommand(1),

            // new ParallelDeadlineGroup(
            //     new DriveForTicks(1700, 0.9, driveBase, 0),
            //     new IntakeFuel(intake),
            //     new BeltsDefault(belts)),
            // new ParallelDeadlineGroup(
            //     new DriveForTicks(2000, 0.55, driveBase, 0),
            //     new IntakeFuel(intake),
            //     new BeltsDefault(belts)),
            // // new DriveForTicks(1700, driveBase),
            // new ParallelDeadlineGroup(
            //     new DriveForTicks(-2500, 1.0, driveBase, 0),
            //     new IntakeFuel(intake),
            //     new BeltsDefault(belts)),

            new ParallelDeadlineGroup(
                new SequentialCommandGroup(new DriveForTicks(1700, 0.85, driveBase, 0),
                    new DriveForTicks(2000, 0.55, driveBase, 0),
                    new DriveForTicks(-2500, 1.0, driveBase, 0)),
                new IntakeFuel(intake),
                new BeltsDefault(belts)),

            new TurnToAngle(15, driveBase),
            new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
            new AlignWithTarget(driveBase, limelight, distanceSensor), new AimHood(shooter, distanceSensor, false),
            new InstantCommand(() -> shooter.enable()),
            new InstantCommand(() -> shooter.setShooterSpeed(4925)),
            // new RunCommand(() -> shooter.setShooterSpeedDirect(0.84)).withTimeout(3),
            new FireShooter(shooter, belts).withTimeout(3),
            new RunCommand(() -> shooter.setHoodPos(Constants.maxAngle)).withTimeout(0));
            // new InstantCommand(() -> shooter.disable()));

        m_chooser = new SendableChooser<>();
        m_chooser.setDefaultOption("Three Ball", new ThreeBallAuto(driveBase, shooter, belts, limelight, intake, distanceSensor));
        m_chooser.addOption("Six Ball", sixBallAuto);

        SmartDashboard.putData("Auto mode", m_chooser);
        // System.out.println("Auto Set");
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() 
    {
        new JoystickButton(driveJoystick, 2).whenPressed(() -> driveBase.reverseDrive());
        // new JoystickButton(driveJoystick, 2).whenHeld(new ScoreFuel(driveBase,
        // shooter, belts, limelight, distanceSensor));

        //Shooting Commands
        // new JoystickButton(driveJoystick, 1).whenHeld(
        //     new SequentialCommandGroup(
        //         new InstantCommand(() -> limelight.SetLedMode(3)),
        //         new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)),
        //         new ReverseBelt(belts, 300),
        //         new WaitCommand(0.2),
        //         new AlignWithTarget(driveBase, limelight, distanceSensor),
        //         new AimHood(shooter, distanceSensor, false),
        //         new InstantCommand(() -> shooter.setShooterSpeedDirect(0.85)),
        //         new WaitCommand(0.3), 
        //         new FireShooter(shooter, belts)
        //     )
        // );

        new JoystickButton(driveJoystick, 3).whenHeld(
            new SequentialCommandGroup(
                new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)),
                new ReverseBelt(belts, 300),
                new WaitCommand(0.2),
                new ParallelDeadlineGroup(
                    new AlignWithTarget(driveBase, limelight, distanceSensor).withTimeout(3),
                    new AimHood(shooter, distanceSensor, true)),
                new InstantCommand(() -> shooter.enable()),
                // new InstantCommand(() -> shooter.setShooterSpeedDirect(0.84)),
                new InstantCommand(() -> shooter.setShooterSpeed(4925)),
                new WaitCommand(0.3), 
                new FireShooter(shooter, belts)
            )
        ).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));//.whenReleased(() -> shooter.disable());

        new JoystickButton(driveJoystick, 4).whenHeld(
            new SequentialCommandGroup(
                new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)),
                new ReverseBelt(belts, 300),
                new WaitCommand(0.2),
                new ParallelDeadlineGroup(
                    new AlignWithTarget(driveBase, limelight, distanceSensor, true).withTimeout(3),
                    new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle))),
                new InstantCommand(() -> shooter.enable()),
                // new InstantCommand(() -> shooter.setShooterSpeed(0.94)),
                new InstantCommand(() -> shooter.setShooterSpeed(5512)),
                new WaitCommand(0.3), 
                new FireShooter(shooter, belts)
            )
        ).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));//.whenReleased(() -> shooter.disable());

        new JoystickButton(driveJoystick, 1).whenHeld(
            new SequentialCommandGroup(
                new InstantCommand(() -> shooter.setHoodPos(Constants.minAngle)),
                new ReverseBelt(belts, 300),
                new WaitCommand(0.15),
                new ParallelDeadlineGroup(
                    new AlignWithTarget(driveBase, limelight, distanceSensor).withTimeout(3),
                    new AimHood(shooter, distanceSensor, false)),
                new InstantCommand(() -> shooter.enable()),
                // new InstantCommand(() -> shooter.setShooterSpeedDirect(0.84)),
                new RunCommand(() -> shooter.setShooterSpeed(4500)).withInterrupt(new BooleanSupplier()
                {
                    @Override
                    public boolean getAsBoolean()
                    {
                        return shooter.shooterAtSpeed();
                    }
                }),
                new WaitCommand(0.5), 
                new FireShooter(shooter, belts)
            )
        ).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle)).whenReleased(() -> shooter.disable());

        new JoystickButton(driveJoystick, 5)
            .whenPressed(new InstantCommand(() -> shooter.setShooterSpeed(4984)))
            .whenHeld(new StartEndCommand(() -> shooter.enable(), () -> shooter.disable()));

        new JoystickButton(driveJoystick, 6)
            .whenPressed(new TurnToAngle(0, driveBase));

        // new JoystickButton(driveJoystick, 1).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));
        // new JoystickButton(driveJoystick, 3).whenReleased(() -> shooter.setHoodPos(Constants.maxAngle));

        //Intake
        new JoystickButton(mechJoystick, 9).whenHeld(new IntakeFuel(intake));

        //Climbing

        new JoystickButton(mechJoystick, 1).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0.5, 0, 0),	
            () -> climber.runClimb(0, 0, 0))
            .withTimeout(1));	
        new JoystickButton(mechJoystick, 4).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(-0.5, 0, 0),	
            () -> climber.runClimb(0, 0, 0))
            .withTimeout(1));	
        new JoystickButton(mechJoystick, 7).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0, 0.5, 0),	
            () -> climber.runClimb(0, 0, 0))
            .withTimeout(1));	
        new JoystickButton(mechJoystick, 10).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0, -0.5, 0),	
            () -> climber.runClimb(0, 0, 0))
            .withTimeout(1));	
        // new JoystickButton(mechJoystick, 1).whenHeld(new StartEndCommand(	
        //     () -> climber.runClimb(0, 0, -0.5),	
        //     () -> climber.runClimb(0, 0, 0))
        //     .withTimeout(1));	
            //I am the expert of code
            //Two households both alike in dignity
            //In fair verona where we lay our scene
            //from acent grudge break to new muteny
            //where civil bloobs make civik hand unclean
            //from fourth the fatle loins of these two foes
            //a pair of star crossed their llife (Does LLoyd llike to llive his llife)
            //miss advetures piteous overthows
            //doth with their death bury their parents strife
            //Two houses of this bomb ass play
            //This is for you JIM homie

        new JoystickButton(mechJoystick, 5).whenHeld(new StartEndCommand(
            () -> climber.runClimb(0.6, 0.6, 0),
            () -> climber.runClimb(0, 0, 0)));

        new JoystickButton(mechJoystick, 6).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(-0.7, -0.7, 0),	
            () -> climber.runClimb(0, 0, 0)));	

        new JoystickButton(mechJoystick, 2).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0, 0, 0.75),	
            () -> climber.runClimb(0, 0, 0)));

        new JoystickButton(mechJoystick, 3).whenHeld(new StartEndCommand(	
            () -> climber.runClimb(0, 0, -0.5),	
            () -> climber.runClimb(0, 0, 0)));

        //System Overrides
        // new JoystickButton(mechJoystick, 7).whenHeld(new StartEndCommand(
        //     () -> shooter.setShooterSpeedDirect(1.0),
        //     () -> shooter.setShooterSpeedDirect(0)));

        // new JoystickButton(mechJoystick, 10).whenHeld(new RunBelt(belts));

        // new JoystickButton(mechJoystick, 11).whenHeld(new StartEndCommand(
        //     () -> shooter.setHoodPos(Constants.minAngle),
        //     () -> shooter.setHoodPos(Constants.maxAngle)
        // ));

        new JoystickButton(mechJoystick, 8).whenHeld(new StartEndCommand(
            () -> belts.reverseAllBelts(),
            () -> belts.stopAllBelts())
            .alongWith(new StartEndCommand(
                        () -> intake.reverse(),
                        () -> intake.stopIntake()))
            .alongWith(new StartEndCommand(
                        () -> shooter.setShooterSpeedDirect(-0.2),
                        () -> shooter.setShooterSpeedDirect(0))));
        
    }
  
  
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() 
    {
        return m_chooser.getSelected();
    }

    public void turnOffLimelight()
    {
        limelight.SetLedMode(0);
    }

    public void turnOnLimelight()
    {
        limelight.SetLedMode(3);
    }
}
