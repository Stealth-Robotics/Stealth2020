package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;;

public class DeployIntake extends CommandBase
{
    private final Intake intake;

    public DeployIntake(Intake subsystem)
    {
        intake = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        intake.toggleIntake();
    }

    @Override
    public boolean isFinished() 
    {
        return true;
    }
}