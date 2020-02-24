
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
        
        // double angle = Math.atan(Constants.fuelInitVelocY / (dist / Constants.fuelAirTime));
        double angle = calcAngle(dist);
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

    private double calcAngle(double dist)
    {
        double sqrtPort = Math.sqrt(4 * Math.pow(Constants.shooterMaxVeloc, 4) / (Constants.g * Constants.g * dist * dist)
                 + 8 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc * Constants.heightDiff / (Constants.g * dist * dist)
                 - 4);
        double quadResult;
        if (dist > 2.5)
        {
            quadResult = (-2 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc / (Constants.g * dist)
                 + sqrtPort) / 2;
        }
        else
        {
            quadResult = (-2 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc / (Constants.g * dist)
                 - sqrtPort) / 2;
        }

        return Math.atan(1 / quadResult);
    }
}
