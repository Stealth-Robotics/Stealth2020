package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class DriveForTicks extends CommandBase
{
    DriveBase driveBase;
    double ticksToDrive = 0;

    PIDController controller;
    PIDController turnController;

    public DriveForTicks(double ticks, DriveBase drivebase)
    {
        controller = new PIDController(Constants.AutoConstants.basekP, Constants.AutoConstants.basekI, Constants.AutoConstants.basekD);
        controller.setTolerance(5);

        turnController = new PIDController(Constants.AutoConstants.turnkP, Constants.AutoConstants.turnkI, Constants.AutoConstants.turnkD);
        controller.setTolerance(5);

        this.driveBase = drivebase;
        ticksToDrive = ticks;
        addRequirements(drivebase);
    }

    @Override
    public void initialize() 
    {
        driveBase.resetEncoders();
        controller.setSetpoint(ticksToDrive);
        turnController.setSetpoint(driveBase.getHeading());
    }

    @Override
    public void execute()
    {
        // System.out.println("Setpoint :" + controller.getSetpoint() + " Encoder Distance : " + driveBase.getAverageEncoderDistance());
        driveBase.arcadeDrive(-controller.calculate(driveBase.getAverageEncoderDistance()), -turnController.calculate(driveBase.getHeading()));
    }

    @Override
    public void end(boolean interrupted)
    {
        driveBase.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished()
    {
        return controller.atSetpoint();
    }
}