
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The class to control the climber
 */
public class Climber extends SubsystemBase
{

    private final CANSparkMax winch;
    private final SpeedControllerGroup climbElevators;

    /**
     * Creates a new Climber.
     */

    public Climber() 
    {
        winch = new CANSparkMax(RobotMap.winch, MotorType.kBrushless);
        climbElevators = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.climber1), new WPI_TalonSRX(RobotMap.climber2));
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    /**
     * Sets the speed of the climb elevator
     * 
     * @param speed The speed to run
     */
    public void setClimbElevatorSpeed(double speed)
    {
        climbElevators.set(speed);
    }

    /**
     * Sets the speed of the winch
     * 
     * @param speed The speed to run
     */
    public void setWinchSpeed(double speed)
    {
        winch.set(speed);
    }
}
