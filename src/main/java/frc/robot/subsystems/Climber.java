package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/**
 * The class to control the climber
 */
public class Climber extends SubsystemBase
{
    private final CANSparkMax winch;
    private final DigitalInput leftLimitSwitch;  
    private final DigitalInput rightLimitSwitch;
    //private final SpeedControllerGroup climbElevators;

    WPI_TalonSRX leftClimber;
    WPI_TalonSRX rightClimber;

    //PowerDistributionPanel PDP;

    /**
     * Creates a new Climber.
     */
    public Climber() 
    {    
        //this.PDP = PDP;

        leftLimitSwitch = new DigitalInput(RobotMap.LeftLimitSwitch);
        rightLimitSwitch  = new DigitalInput(RobotMap.RightLimitSwitch);
        winch = new CANSparkMax(RobotMap.Winch, MotorType.kBrushless);

        leftClimber = new WPI_TalonSRX(RobotMap.Climber1);
        rightClimber = new WPI_TalonSRX(RobotMap.Climber2);

        leftClimber.setInverted(true);

        //climbElevators = new SpeedControllerGroup(leftClimber,  rightClimber);
    }

    @Override
    public void periodic() 
    { 
        if((!leftLimitSwitch.get() || !rightLimitSwitch.get()) && (leftClimber.get() > 0 && rightClimber.get() > 0))
        {
            runClimb(0, 0, 0);
        }
    }

    /**
     * Sets the speed of the climb motors
     * 
     * @param elevatorSpeed The speed to run elevator
     * @param winchSpeed The speed to run winch
     */
    public void runClimb(double leftSpeed, double rightSpeed, double winchSpeed)
    {
        leftClimber.set(leftSpeed);
        rightClimber.set(rightSpeed);
        winch.set(-winchSpeed);
    }
}