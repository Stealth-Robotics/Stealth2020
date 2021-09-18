
package frc.robot.commands.DrivebaseCommands;

import frc.robot.Constants;
import frc.robot.subsystems.DistanceSensor;
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
    private final DistanceSensor distanceSensor;
    private boolean overrideOffset;
    double angle;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AlignWithTarget(DriveBase driveBase, Limelight limelight, DistanceSensor distanceSensor, double angle) 
    {
        this.driveBase = driveBase;
        this.limelight = limelight;
        this.distanceSensor = distanceSensor;
        this.angle = angle;
        
        controller = new PIDController(Constants.limekP, Constants.limekI, Constants.limekD);
        overrideOffset = false;

        addRequirements(driveBase, limelight);
    }

    public AlignWithTarget(DriveBase driveBase, Limelight limelight, DistanceSensor distanceSensor, boolean overrideOffset, double angle) 
    {
        this.driveBase = driveBase;
        this.limelight = limelight;
        this.distanceSensor = distanceSensor;
        this.angle = angle;
        
        controller = new PIDController(Constants.limekP, Constants.limekI, Constants.limekD);
        this.overrideOffset = overrideOffset;

        addRequirements(driveBase, limelight);

        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {       
        if (overrideOffset)
        {
            controller.setSetpoint(0.4);
            angle = 0.4;
        }
        else
        {
            controller.setSetpoint(Math.atan(Constants.cameraOffset / distanceSensor.getDistance()) * 180 / Math.PI);
            angle = Math.atan(Constants.cameraOffset / distanceSensor.getDistance()) * 180 / Math.PI;
        }
        // controller.setSetpoint(0.5);
        controller.setTolerance(0.7);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        driveBase.arcadeDrive(0, -controller.calculate(limelight.getTargetHorizontalOffset()));
        System.out.println(controller.getPositionError());
    }

    @Override
    public void end(boolean interrupted) 
    {
        driveBase.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
  public boolean isFinished() {
    double posError = Math.abs(driveBase.getPoseThetaDegrees() - angle);
    System.out.printf("iF:%f %s\n",posError,driveBase.getPose().toString());
    return posError < Constants.DriveConstants.kTurnToleranceDeg;
//    return getController().atGoal();
  }


}
