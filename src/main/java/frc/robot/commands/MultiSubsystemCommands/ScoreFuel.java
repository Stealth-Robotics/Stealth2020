
package frc.robot.commands.MultiSubsystemCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/**
 * An example command that uses an example subsystem.
 */
public class ScoreFuel extends SequentialCommandGroup 
{
    DriveBase driveBase;
    Shooter shooter;
    Belts belts;

    Limelight limelight;
    DistanceSensor distanceSensor;
    Intake intake;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ScoreFuel(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, DistanceSensor distanceSensor,Intake intake) 
    {
        this.driveBase = driveBase;
        this.shooter = shooter;
        this.limelight = limelight;
        this.distanceSensor = distanceSensor;
        this.belts = belts;
        this.intake = intake;
        

        addRequirements(shooter, driveBase, belts);

        addCommands(
            new AlignWithTarget(driveBase, limelight, distanceSensor ,0 ),
            new AimHood(shooter, distanceSensor, false),
            new FireShooter(shooter, belts, intake)
        );
    }

    @Override
    public void initialize() 
    {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        shooter.disable();
        shooter.setHoodPos(Constants.maxAngle);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
