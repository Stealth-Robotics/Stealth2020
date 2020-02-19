
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ControlPanelCommands.PosPanel;
import frc.robot.commands.ControlPanelCommands.SpinPanel;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.IntakeCommands.IntakeDefault;
import frc.robot.commands.MultiSubsystemCommands.ScoreFuel;
// import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.ShooterDefault;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.PanelControl;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer 
{
    // The robot's subsystems and commands are defined here...
    private final DriveBase driveBase;
    private final Shooter shooter;
    private final Intake intake;
    private final Climber climber;
    private final PanelControl panelControl;
    private final Limelight limelight;
  
    private final ScoreFuel m_autoCommand;
  
    private Joystick driveJoystick;
    private Joystick mechJoystick;
  
    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() 
    {
        //PDP = new PowerDistributionPanel(RobotMap.PDPCanID);

        driveBase = new DriveBase();
        shooter = new Shooter();
        intake = new Intake();
        climber = new Climber();
        panelControl = new PanelControl();
        limelight = new Limelight();
        
        m_autoCommand = new ScoreFuel(driveBase, shooter, limelight);

        driveJoystick = new Joystick(0);
        mechJoystick = new Joystick(1);

        driveJoystick.setYChannel(1);
        driveJoystick.setXChannel(4);

        // Configure the button bindings
        configureButtonBindings();

        driveBase.setDefaultCommand(
                new RunCommand(() -> driveBase.arcadeDrive(driveJoystick.getY(GenericHID.Hand.kLeft),
                    driveJoystick.getRawAxis(4)),
                driveBase));

        intake.setDefaultCommand(new IntakeDefault(intake, shooter));

        shooter.setDefaultCommand(new ShooterDefault(shooter));
    }
  
    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() 
    {
        new JoystickButton(mechJoystick, 2).whenPressed(new ConditionalCommand(new PosPanel(panelControl), new SpinPanel(panelControl), new BooleanSupplier()
        {
			@Override
            public boolean getAsBoolean() 
            {
				return DriverStation.getInstance().getGameSpecificMessage().equals("");
			}
        }));

        // new JoystickButton(mechJoystick, 2).whenPressed(new PosPanel(panelControl));

        new JoystickButton(mechJoystick, 1).whenHeld(new StartEndCommand(
            () -> this.intake.run(),
            () -> this.intake.stopIntake()));

        new JoystickButton(mechJoystick, 3).whenPressed(new ScoreFuel(driveBase, shooter, limelight));

        new JoystickButton(driveJoystick, 1).whenPressed(() -> driveBase.reverseDrive());

        new JoystickButton(driveJoystick, 2).whenPressed(new AlignWithTarget(driveBase, limelight));  
        new JoystickButton(mechJoystick , 4 /*neds to be changed to the buttons on the driver station*/).whenPressed(() -> Climber.runClimb());
    }
  
  
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() 
    {
        // An ExampleCommand will run in autonomous
        return m_autoCommand;
    }
}
