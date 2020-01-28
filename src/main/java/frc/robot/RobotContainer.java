
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PanelControl;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
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
  
    private final ExampleCommand m_autoCommand;
  
    private Joystick driveJoystick;
    private Joystick mechJoystick;
  
    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() 
    {
        driveBase = new DriveBase();
        shooter = new Shooter();
        intake = new Intake();
        climber = new Climber();
        panelControl = new PanelControl();
        
        m_autoCommand = new ExampleCommand(driveBase);

        driveJoystick = new Joystick(0);
        mechJoystick = new Joystick(1);

        // Configure the button bindings
        configureButtonBindings();

        new RunCommand(() -> driveBase
            .arcadeDrive(driveJoystick.getY(GenericHID.Hand.kLeft),
                driveJoystick.getX(GenericHID.Hand.kRight)),
            driveBase);
    }
  
    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() 
    {
        new JoystickButton(mechJoystick, 0).whenPressed(new SpinPanel(panelControl));
        new JoystickButton(mechJoystick, 1).whenPressed(new PosPanel(panelControl));
        new JoystickButton(mechJoystick, 2).whenPressed(new RunCommand(() -> shooter.run(1.0)))
                .whenReleased(new RunCommand(() -> shooter.run(0)));
        new JoystickButton(mechJoystick, 3).whenPressed(new RunCommand(() -> intake.runIntake()))
                .whenReleased(new RunCommand(() -> intake.stopIntake()));
        new JoystickButton(mechJoystick, 4).whenPressed(new RunCommand(() -> climber.climb()))
                .whenReleased(new RunCommand(() -> climber.stopClimb()));
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
