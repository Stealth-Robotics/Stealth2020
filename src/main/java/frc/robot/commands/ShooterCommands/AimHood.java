  
package frc.robot.commands.ShooterCommands;

import frc.robot.Constants;
import frc.robot.subsystems.DistanceSensor;
// import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AimHood extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter shooter;
    private final DistanceSensor distanceSensor;

    private boolean override;
    // private final Limelight limelight;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AimHood(Shooter shooter, DistanceSensor distanceSensor, boolean override)
    {
        this.shooter = shooter;
        this.distanceSensor = distanceSensor;
        this.override = override;
        // this.limelight = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter, distanceSensor);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        
    }

    @Override
    public void execute()
    {
        double dist = distanceSensor.getDistance() / 1000;

        if (override)
        {
            dist = 3.048;
        }
        
        double angle;
        if (dist > 1)
        {
            // double angle = Math.atan(Constants.fuelInitVelocY / (dist / Constants.fuelAirTime));
            // angle = calcAngle(dist) - 3 * Math.PI / 72;
            angle = calcAngle(dist) - 13 * Math.PI / 180;
            // System.out.println("Angle: " + angle * 180 / Math.PI);
            angle = (angle > Constants.maxAngle) ? Constants.maxAngle : (angle < Constants.minAngle) ? Constants.minAngle : angle;
        }
        else
        {
            angle = Constants.maxAngle - 3 * Math.PI / 36;
        }

        // if(limelight.hasValidTarget())
        // {
        //     shooter.setHoodPos(angle);
        // }
        // else
        // {
        //     shooter.setHoodPos(Constants.maxAngle);
        // }

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
        double sqrtPart = Math.sqrt(4 * Constants.shooterMaxVelocToTheFourth / (Constants.g * Constants.g * dist * dist)
                 + 8 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc * Constants.heightDiff / (Constants.g * dist * dist)
                 - 4);
        // System.out.println("SqrtPart: " + sqrtPart);
        double quadResult = (-2 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc / (Constants.g * dist)
                - sqrtPart) / 2;
        // System.out.println("QuadResult: " + quadResult);
        // if (dist/1000 > 2.5)
        // {
        //     quadResult = (-2 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc / (Constants.g * dist)
        //          + sqrtPort) / 2;
        // }1
        //]
        // else
        // {
        //     quadResult = (-2 * Constants.shooterMaxVeloc * Constants.shooterMaxVeloc / (Constants.g * dist)
        //          - sqrtPort) / 2;
        // }

        return Math.atan(quadResult);
    }
}
