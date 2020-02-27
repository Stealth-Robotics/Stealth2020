package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class TurnToAngle extends CommandBase
{
    DriveBase driveBase;
    double angle = 0;

    PIDController controller;


    public TurnToAngle(double angle, DriveBase drivebase)
    {
        controller = new PIDController(Constants.AutoConstants.baseTurnkP, Constants.AutoConstants.baseTurnkI, Constants.AutoConstants.baseTurnkD);
        this.driveBase = drivebase;
        this.angle = angle;
    }

    @Override
    public void initialize() 
    {
        controller.setSetpoint(angle);
    }

    @Override
    public void execute()
    {
        driveBase.arcadeDrive(0, controller.calculate(driveBase.getHeading()));
    }

    @Override
    public boolean isFinished()
    {
        return controller.atSetpoint();
    }
}