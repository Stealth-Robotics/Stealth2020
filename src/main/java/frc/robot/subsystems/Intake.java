
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    private final WPI_TalonSRX intake;

    private final WPI_TalonSRX belt;

    private final Solenoid deployPistons;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
        intake = new WPI_TalonSRX(RobotMap.intake);
        belt = new WPI_TalonSRX(RobotMap.belt3);
        deployPistons = new Solenoid(RobotMap.PCM, RobotMap.intakeDeployPistons);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    public void run()
    {
        intake.set(1);
    }

    public void stopIntake()
    {
        intake.set(0);
    }

    public void runBelt()
    {
        belt.set(1);
    }

    public void stopBelt()
    {
        belt.set(0);
    }

    public void toggle()
    {
        deployPistons.set(!deployPistons.get());
    }
}
