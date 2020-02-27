
package frc.robot.commands.MultiSubsystemCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.BeltsCommands.ResetBelts;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
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

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ScoreFuel(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, DistanceSensor distanceSensor) 
    {
        this.driveBase = driveBase;
        this.shooter = shooter;
        this.limelight = limelight;
        this.distanceSensor = distanceSensor;
        this.belts = belts;

        addRequirements(shooter, driveBase, belts);
    }

    @Override
    public void initialize() 
    {
        addCommands(
            new SequentialCommandGroup(
                new AlignWithTarget(driveBase, limelight), 
                new AimHood(shooter, distanceSensor), 
                new RunCommand(() -> belts.runAllBelts(0.6, 0.8), belts).withInterrupt(new BooleanSupplier()
                    {
                        @Override
                        public boolean getAsBoolean()
                        {
                            return belts.getBeamBreak3();
                        }
                    }
                ).withTimeout(3)
            ),
            new FireShooter(shooter, belts).withTimeout(10),
            new ResetBelts(belts).withTimeout(3),
            new InstantCommand(() -> shooter.setHoodPos(Constants.maxAngle))
        );
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        shooter.disable();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
