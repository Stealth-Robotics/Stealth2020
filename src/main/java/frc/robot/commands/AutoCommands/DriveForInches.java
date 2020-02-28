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


    public DriveForInches(double ticks, DriveBase drivebase)
    {
        controller = new PIDController(Constants.AutoConstants.basekP, Constants.AutoConstants.basekI, Constants.AutoConstants.basekD);
        controller.setTolerance(50);
        this.driveBase = drivebase;
        // ticksToDrive = inches * Constants.AutoConstants.ticksPerInch;   
        ticksToDrive = ticks;
    }

    @Override
    public void initialize() 
    {
        driveBase.resetEncoders();
        controller.setSetpoint(ticksToDrive);
    }

    @Override
    public void execute()
    {
        driveBase.arcadeDrive(controller.calculate(driveBase.getAverageEncoderDistance()), 0);
        
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