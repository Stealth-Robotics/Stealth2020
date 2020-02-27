/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.EncoderAutoCommands;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class IntakeUp extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake intake;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public IntakeUp(Intake intake) 
    {
        this.intake = intake;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        
        intake.stopIntake();
        intake.toggle();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        intake.toggle();
        intake.stopIntake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
