
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    private final Talon intake;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
        intake = new Talon(RobotMap.intake);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    public void runIntake()
    {
        intake.set(1);
    }

    public void stopIntake()
    {
        intake.set(0);
    }
}
