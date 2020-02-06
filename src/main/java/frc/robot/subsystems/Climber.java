
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase
{

    private final SpeedControllerGroup winch;
    private final SpeedControllerGroup climbElevator;

    /**
     * Creates a new Climber.
     */

    public Climber() 
    {
        winch = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.winch1), new WPI_TalonSRX(RobotMap.winch2));
        climbElevator = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.claw1), new WPI_TalonSRX(RobotMap.claw2));
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    public void setClimbElevatorSpeed(double speed)
    {
        climbElevator.set(speed);
    }

    public void setWinchSpeed(double speed)
    {
        winch.set(speed);
    }
}
