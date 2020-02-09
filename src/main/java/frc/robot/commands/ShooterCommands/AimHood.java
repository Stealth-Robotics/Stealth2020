
package frc.robot.commands.ShooterCommands;

import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AimHood extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter shooter;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AimHood(Shooter subsystem) 
    {
        shooter = subsystem;
        
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        // NetworkTableInstance.getDefault().getEntry("LiveWindow/Ungrouped/dist").setDouble(1);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        double dist = NetworkTableInstance.getDefault().getEntry("LiveWindow/Ungrouped/dist").getDouble(-1); //TODO Figure out how this is actually going to be done
        double angle = Math.atan(Constants.fuelInitVelocY / (dist / Constants.fuelAirTime));
        angle = (angle > Math.PI / 4) ? Math.PI / 4 : (angle < Math.PI / 6) ? Math.PI / 6 : angle;
        System.out.println("Dist: " + dist);
        System.out.println("Angle: " + angle * 180 / Math.PI);
        shooter.setHoodPos(angle);
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
        return shooter.hoodAimed();
        // return false;
    }
}
