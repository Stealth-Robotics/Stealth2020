
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase
{

    private final Talon winch;
    private final SpeedControllerGroup claw;

    /**
     * Creates a new Climber.
     */
    public Climber() 
    {
        winch = new Talon(RobotMap.winch);
        claw = new SpeedControllerGroup(new Talon(RobotMap.claw1), new Talon(RobotMap.claw2));
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    public void liftClaw()
    {
        claw.set(1);
    }

    public void climb()
    {
        winch.set(1);
    }

    public void stopClimb()
    {
        winch.set(0);
    }
}
