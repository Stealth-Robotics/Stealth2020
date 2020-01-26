package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase 
{
    private final SpeedControllerGroup shooter;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {
        shooter = new SpeedControllerGroup(new Talon(RobotMap.shooter1), new Talon(RobotMap.shooter2));
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    public void run(double speed)
    {
        shooter.set(speed);
    }
}
