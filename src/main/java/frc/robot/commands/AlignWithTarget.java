
package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AlignWithTarget extends CommandBase 
{
    private final DriveBase driveBase;
    private final PIDController controller;

    NetworkTable limelight;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AlignWithTarget(DriveBase driveBase) 
    {
        this.driveBase = driveBase;
        
        controller = new PIDController(Constants.basekP, Constants.basekI, Constants.basekD);

        addRequirements(driveBase);

        limelight = NetworkTableInstance.getDefault().getTable("limelight");
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {        
        limelight.getEntry("ledMode").setNumber(3);
        limelight.getEntry("camMode").setNumber(0);

        controller.setSetpoint(updateLimelightTracking());
        controller.setTolerance(5);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        driveBase.arcadeDrive(0, controller.calculate(driveBase.getHeading()));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return controller.atSetpoint();
    }

    public double updateLimelightTracking()
    {
        double tv = limelight.getEntry("tv").getDouble(0);

        if (tv < 1.0)
        {
            return 0;
        }

        return limelight.getEntry("tx").getDouble(0);
    }
}
