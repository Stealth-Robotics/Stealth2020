package frc.robot.commands.AutoCommands;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class DriveForInches extends CommandBase
{
    DriveBase driveBase;
    double ticksToDrive = 0;

    PIDController controller;
    PIDController turnController;


    public DriveForInches(double inches, DriveBase drivebase)
    {
        controller = new PIDController(Constants.AutoConstants.basekP, Constants.AutoConstants.basekI, Constants.AutoConstants.basekD);
        controller.setTolerance(5);

        turnController = new PIDController(Constants.AutoConstants.turnkP, Constants.AutoConstants.turnkI, Constants.AutoConstants.turnkD);
        controller.setTolerance(5);

        this.driveBase = drivebase;
        ticksToDrive = inches * Constants.AutoConstants.ticksPerInch;
        addRequirements(drivebase);
    }

    @Override
    public void initialize() 
    {
        driveBase.resetEncoders();
        driveBase.zeroHeading();
        controller.setSetpoint(ticksToDrive);
        turnController.setSetpoint(0);
    }

    @Override
    public void execute()
    {
        System.out.println("Setpoint :" + controller.getSetpoint() + "Encoder Distance : " + driveBase.getAverageEncoderDistance());
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