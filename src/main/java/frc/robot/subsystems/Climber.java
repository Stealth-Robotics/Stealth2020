
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
import frc.util.StopWatch;
// import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.DigitalInput;
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
    private final DigitalInput leftLimitSwitch;  
    private final DigitalInput rightLimitSwitch;
    private final SpeedControllerGroup climbElevators;

    //PowerDistributionPanel PDP;

    /**
     * Creates a new Climber.
     */
    public Climber() 
    {    
        //this.PDP = PDP;

        leftLimitSwitch = new DigitalInput(RobotMap.LeftLimitSwitch);
        rightLimitSwitch  = new DigitalInput(RobotMap.LightLimitSwitch);
        winch = new CANSparkMax(RobotMap.Winch, MotorType.kBrushless);

        WPI_TalonSRX leftClimber = new WPI_TalonSRX(RobotMap.Climber1);
        WPI_TalonSRX rightClimber = new WPI_TalonSRX(RobotMap.Climber2);

        rightClimber.setInverted(true);

        climbElevators = new SpeedControllerGroup(leftClimber,  rightClimber);
        climbElevators.setInverted(true);
    }

    // TODO : Make Into Commands
    /**
     * Climb Motion Upward
     * 
     * @param elevatorSpeed The speed to run elevator
     */
    public void UpClimb(double elevatorSpeed)
    {   
        climbElevators.set(0.3);

        while (!leftLimitSwitch.get() || !rightLimitSwitch.get())
        {
            
        }

        climbElevators.set(0);
    }

    /**
     * Climb Motion Downward
     * 
     * @param elevatorSpeed The speed to run elevator
     */
    public void DownClimb(double elevatorSpeed, double winchSpeed)
    {   
        StopWatch stopWatch = new StopWatch(3000);
        climbElevators.set(elevatorSpeed);

        while (!stopWatch.isExpired())
        {
            
        }

        climbElevators.set(0);

        winch.set(winchSpeed);
    }
}
