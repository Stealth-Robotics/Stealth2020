
package frc.robot.commands.ShooterCommands;

import com.ctre.phoenix.time.StopWatch;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

/**
 * An example command that uses an example subsystem.
 */
public class FireShooter extends CommandBase 
{
    private final Shooter shooter;
    private final Belts belts;
    private final Intake intake;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public FireShooter(Shooter shooter, Belts belts, Intake intake) 
    {
        this.shooter = shooter;
        this.belts = belts;
        this.intake = intake;

        addRequirements(shooter, belts);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        //shooter.enable();
        intake.runHelperWheel(0.5);
        belts.runAllBelts(0.75, 0.95);
        //shooter.setShooterSpeedDirect(0.85);
    
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        shooter.disable();
        belts.stopAllBelts();
        shooter.setShooterSpeedDirect(0);
        belts.resetBallCount();
        intake.stopHelperWheel();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
