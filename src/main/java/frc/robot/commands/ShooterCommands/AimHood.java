
package frc.robot.commands.ShooterCommands;

import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AimHood extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter shooter;
    private final Limelight limelight;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AimHood(Shooter shooter, Limelight limelight) 
    {
        this.shooter = shooter;
        this.limelight = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter, limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        double dist = limelight.getTargetDistance();
        
        double angle = Math.atan(Constants.fuelInitVelocY / (dist / Constants.fuelAirTime));
        angle = (angle > Constants.maxAngle) ? Constants.maxAngle : (angle < Constants.minAngle) ? Constants.minAngle : angle;
        System.out.println("Dist: " + dist);
        System.out.println("Angle: " + angle * 180 / Math.PI);

        shooter.setHoodPos(angle);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return shooter.hoodAimed();
        // return false;
    }
}
