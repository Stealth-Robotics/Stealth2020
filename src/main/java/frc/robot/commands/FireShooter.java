
package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class FireShooter extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter shooter;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public FireShooter(Shooter shooter) 
    {
        this.shooter = shooter;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        if (shooter.shooterAtSpeed())
        {
            shooter.runBelt();
        }
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