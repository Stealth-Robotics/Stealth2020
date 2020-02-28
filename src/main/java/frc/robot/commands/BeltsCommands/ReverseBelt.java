/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BeltsCommands;

import frc.robot.subsystems.Belts;
import frc.util.StopWatch;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ReverseBelt extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Belts belts;

    private StopWatch timer;
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ReverseBelt(Belts belts, long time) 
    {
        this.belts = belts;

        timer = new StopWatch(time);

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(belts);
    }

    @Override
    public void initialize() {
        
        timer.reset();
    }

    @Override
    public void execute()
    {
        belts.runAllBelts(-0.7, -0.7);
    }

    @Override
    public void end(boolean interrupted) {
        belts.runAllBelts(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return timer.isExpired();
    }
}
