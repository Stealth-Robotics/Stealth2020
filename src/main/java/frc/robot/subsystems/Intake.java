
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    private final Talon intake;
    private final SpeedControllerGroup belts;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
        intake = new Talon(RobotMap.intake);
        belts = new SpeedControllerGroup(new Talon(RobotMap.belt1), new Talon(RobotMap.belt2), new Talon(RobotMap.belt3));
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

    public void runBelts()
    {
        belts.set(1);
    }
}
