package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class DriveForInches extends CommandBase
{
    DriveBase driveBase;
    double ticksToDrive = 0;

    PIDController controller;


    public DriveForInches(double inches, DriveBase drivebase)
    {
        controller = new PIDController(Constants.AutoConstants.basekP, Constants.AutoConstants.basekI, Constants.AutoConstants.basekD);
        this.driveBase = drivebase;
        ticksToDrive = inches * Constants.AutoConstants.ticksPerInch;   
    }

    @Override
    public void initialize() 
    {
        controller.setSetpoint(ticksToDrive);
    }

    @Override
    public void execute()
    {
        driveBase.arcadeDrive(controller.calculate(driveBase.getAverageEncoderDistance()), 0);
    }

    @Override
    public boolean isFinished()
    {
        return controller.atSetpoint();
    }
}