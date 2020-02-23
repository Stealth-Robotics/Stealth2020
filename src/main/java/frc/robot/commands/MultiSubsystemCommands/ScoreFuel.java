
package frc.robot.commands.MultiSubsystemCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BeltsCommands.ResetBelts;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
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

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ScoreFuel(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight) 
    {
        this.driveBase = driveBase;
        this.shooter = shooter;
        this.limelight = limelight;
        this.belts = belts;

        addRequirements(shooter, driveBase, belts);
    }

    @Override
    public void initialize() 
    {
        //limelight.SetLedMode(3);

        addCommands(
            new ParallelCommandGroup(
                new AlignWithTarget(driveBase, limelight), 
                new AimHood(shooter, limelight), 
                new RunCommand(() -> belts.runAllBelts(), belts).withInterrupt(new BooleanSupplier()
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
            new ResetBelts(belts).withTimeout(3)
        );

        //limelight.SetLedMode(0);
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
        // TODO : Find Out When To End Command
        return false;
    }
}
