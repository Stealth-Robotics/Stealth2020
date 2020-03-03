
package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.Shooter;

/**
 * An example command that uses an example subsystem.
 */
public class FireShooter extends CommandBase 
{
    private final Shooter shooter;
    private final Belts belts;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public FireShooter(Shooter shooter, Belts belts) 
    {
        this.shooter = shooter;
        this.belts = belts;

        addRequirements(shooter, belts);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        shooter.enable();
        belts.runAllBelts(0.8, 1.0);
        //shooter.setShooterSpeedDirect(0.8);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        shooter.disable();
        belts.stopAllBelts();
        shooter.setShooterSpeedDirect(0);

        belts.resetBallCount();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
