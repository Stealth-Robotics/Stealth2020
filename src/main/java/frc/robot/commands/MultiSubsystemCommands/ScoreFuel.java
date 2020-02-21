
package frc.robot.commands.MultiSubsystemCommands;

import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * An example command that uses an example subsystem.
 */
public class ScoreFuel extends SequentialCommandGroup 
{
    DriveBase driveBase;
    Shooter shooter;

    Limelight limelight;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ScoreFuel(DriveBase driveBase, Shooter shooter, Limelight limelight) 
    {
        this.driveBase = driveBase;
        this.shooter = shooter;
        this.limelight = limelight;

        addRequirements(shooter, driveBase, limelight);
    }

    @Override
    public void initialize() 
    {
        //limelight.SetLedMode(3);

        addCommands(
            new ParallelCommandGroup(
                new AlignWithTarget(driveBase, limelight), 
                new AimHood(shooter), 
                new InstantCommand(() -> shooter.enable())),
            new FireShooter(shooter),
            new RunCommand(() -> shooter.reverseBelt(), shooter).withInterrupt(new BooleanSupplier()
            {
                @Override
                public boolean getAsBoolean()
                {
                    return shooter.getBeamBreak2();
                }
            }));

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
        return false;
    }
}
