package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class TurnToAngle extends CommandBase
{
    DriveBase driveBase;
    double angle;

    PIDController controller;


    public TurnToAngle(double angle, DriveBase driveBase)
    {
        controller = new PIDController(Constants.AutoConstants.turnkP, Constants.AutoConstants.turnkI, Constants.AutoConstants.turnkD);
        controller.setTolerance(5);
        this.driveBase = driveBase;
        this.angle = angle;

        addRequirements(driveBase);
    }

    @Override
    public void initialize() 
    {
        controller.setSetpoint(angle);
    }

    @Override
    public void execute()
    {
        // System.out.println(driveBase.getHeading());
        driveBase.arcadeDrive(0, -controller.calculate(driveBase.getHeading()));
    }

    @Override
    public boolean isFinished()
    {
        return controller.atSetpoint();
    }
}