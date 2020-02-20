
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
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

        leftLimitSwitch = new DigitalInput(RobotMap.leftLimitSwitch);
        rightLimitSwitch  = new DigitalInput(RobotMap.rightLimitSwitch);
        winch = new CANSparkMax(RobotMap.winch, MotorType.kBrushless);
        WPI_TalonSRX leftClimber = new WPI_TalonSRX(RobotMap.climber1);
        WPI_TalonSRX rightClimber = new WPI_TalonSRX(RobotMap.climber2);

        rightClimber.setInverted(true);

        climbElevators = new SpeedControllerGroup(leftClimber,  rightClimber);
    }

    @Override
   
    public void periodic() 
    {
        if(leftLimitSwitch.get() == false)
        {
           setClimbElevatorSpeed(0);
           setWinchSpeed(1);
        }

        
        if(rightLimitSwitch.get() == false)
        {
            setClimbElevatorSpeed(0);
            setWinchSpeed(1);
    }


    /**
     * Sets the speed of the climb motors
     * 
     * @param elevatorSpeed The speed to run elevator
     * @param winchSpeed The speed to run winch
     */
    public void runClimb(double elevatorSpeed, double winchSpeed)
    {
        climbElevators.set(elevatorSpeed);
        winch.set(winchSpeed);
    }
}
