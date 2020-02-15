
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
        climbElevators = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.climber1), new WPI_TalonSRX(RobotMap.climber2));
    }

    @Override
   
    public void periodic() 
    {
        //VoltageCheck();

        //TODO Figure when to stop winch

        if(leftLimitSwitch.get())
        {
           setClimbElevatorSpeed(0);
           setWinchSpeed(1);
        }

        if(rightLimitSwitch.get())
        {
            setClimbElevatorSpeed(0);
            setWinchSpeed(1);
        }
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

    /*public void VoltageCheck()
    {
        if(PDP.getCurrent(RobotMap.climber1PDPChannel) > Constants.RedlineVoltageLimit
        || PDP.getCurrent(RobotMap.climber2PDPChannel) > Constants.RedlineVoltageLimit)
        {
            climbElevators.setVoltage(0);
        }

        if(PDP.getCurrent(RobotMap.winchPDPChannel) > Constants.NEOVoltageLimit)
        {
            winch.setVoltage(0);
        }
    }*/
}
