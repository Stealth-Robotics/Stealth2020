
package frc.robot.commands.DrivebaseCommands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AlignWithTarget extends CommandBase 
{
    private final DriveBase driveBase;
    private final PIDController controller;

    private final Limelight limelight;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AlignWithTarget(DriveBase driveBase, Limelight limelight) 
    {
        this.driveBase = driveBase;
        this.limelight = limelight;
        
        controller = new PIDController(Constants.limekP, Constants.limekI, Constants.limekD);

        addRequirements(driveBase, limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {       
        limelight.SetLedMode(3);
        //limelight.SetLedMode(3);

        controller.setSetpoint(0);
        controller.setTolerance(0.5);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        driveBase.arcadeDrive(0, -controller.calculate(limelight.GetTargetHorizontalOffset()));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        limelight.SetLedMode(1);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return controller.atSetpoint() || !limelight.HasValidTarget();
    }
}
