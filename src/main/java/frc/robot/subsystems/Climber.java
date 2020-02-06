
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase
{

    private final WPI_TalonSRX winch;
    //private final SpeedControllerGroup claw;

    /**
     * Creates a new Climber.
     */

    public Climber() 
    {
        winch = new WPI_TalonSRX(RobotMap.winch);
        //claw = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.claw1), new WPI_TalonSRX(RobotMap.claw2));
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    /*public void liftClaw()
    {
        claw.set(1);
    }*/

    public void climb()
    {
        winch.set(1);
    }

    public void stopClimb()
    {
        winch.set(0);
    }
}
