
package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * An example command that uses an example subsystem.
 */
public class ScoreFuel extends SequentialCommandGroup 
{

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ScoreFuel(Shooter shooter, DriveBase driveBase) 
    {
        addCommands(new ParallelCommandGroup(new AlignWithTarget(driveBase), new AimHood(shooter)), new FireShooter(shooter));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
