package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class DriveForTicks extends CommandBase
{
    DriveBase driveBase;
    double ticksToDrive = 0;
    double maxSpeed;
    double angle;

    PIDController controller;
    PIDController turnController;

    public DriveForTicks(double ticks, double maxSpeed, DriveBase drivebase)
    {
        controller = new PIDController(Constants.AutoConstants.basekP, Constants.AutoConstants.basekI, Constants.AutoConstants.basekD);
        controller.setTolerance(5);

        turnController = new PIDController(Constants.AutoConstants.turnkP, Constants.AutoConstants.turnkI, Constants.AutoConstants.turnkD);
        controller.setTolerance(5);

        this.driveBase = drivebase;
        ticksToDrive = ticks;
        this.maxSpeed = maxSpeed;
        addRequirements(drivebase);
        angle = drivebase.getHeading();
    }

    public DriveForTicks(double ticks, double maxSpeed, DriveBase drivebase, double angle)
    {
        controller = new PIDController(Constants.AutoConstants.basekP, Constants.AutoConstants.basekI, Constants.AutoConstants.basekD);
        controller.setTolerance(5);

        turnController = new PIDController(Constants.AutoConstants.turnkP, Constants.AutoConstants.turnkI, Constants.AutoConstants.turnkD);
        controller.setTolerance(5);

        this.driveBase = drivebase;
        ticksToDrive = ticks;
        this.maxSpeed = maxSpeed;
        addRequirements(drivebase);
        this.angle = angle;
    }

    @Override
    public void initialize() 
    {
        driveBase.resetEncoders();
        controller.setSetpoint(ticksToDrive);
        turnController.setSetpoint(angle);
    }

    @Override
    public void execute()
    {
        // System.out.println("Setpoint :" + controller.getSetpoint() + " Encoder Distance : " + driveBase.getAverageEncoderDistance());
        double power = -controller.calculate(driveBase.getAverageEncoderDistance());
        double sign = Math.signum(power);
        driveBase.arcadeDrive(Math.abs(power) > maxSpeed ? maxSpeed * sign : power, -turnController.calculate(driveBase.getHeading()));
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