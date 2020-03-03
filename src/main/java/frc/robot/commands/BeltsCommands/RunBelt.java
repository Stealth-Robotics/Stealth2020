/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BeltsCommands;

import frc.robot.subsystems.Belts;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class RunBelt extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Belts belts;
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public RunBelt(Belts belts) 
    {
        this.belts = belts;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(belts);
    }

    @Override
    public void initialize() {
        
        belts.runAllBelts(0.6, 0.8);
    }

    @Override
    public void execute()
    {
        
    }

    @Override
    public void end(boolean interrupted) {
        belts.runAllBelts(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
