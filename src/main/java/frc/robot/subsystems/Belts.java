package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Belts extends SubsystemBase
{
    private final WPI_TalonSRX belt1;
    private final WPI_TalonSRX belt2;

    private final DigitalInput beamBreak1;
    private final DigitalInput beamBreak2;
    private final DigitalInput beamBreak3;

    
    public Belts()
    {
        beamBreak1 = new DigitalInput(RobotMap.BeamBreak1);
        beamBreak2 = new DigitalInput(RobotMap.BeamBreak2);
        beamBreak3 = new DigitalInput(RobotMap.BeamBreak3);

        belt1 = new WPI_TalonSRX(RobotMap.Belt1);
        belt2 = new WPI_TalonSRX(RobotMap.Belt2);
    }

    @Override
    public void periodic()
    {
        System.out.println(beamBreak1.get());
    }

    public void runBelt1()
    {
        belt1.set(0.75);
    }

    public void runBelt2()
    {
        belt2.set(0.75);
    }

    public void runAllBelts()
    {
        belt1.set(0.75);
        belt2.set(0.75);
    }

    public void stopBelt1()
    {
        belt1.set(0);
    }

    public void stopBelt2()
    {
        belt2.set(0);
    }

    public void stopAllBelts()
    {
        belt2.set(0);
        belt1.set(0);
    }

    public void reverseBelt1()
    {
        belt1.set(-0.75);
    }

    public void reverseBelt2()
    {
        belt2.set(-0.75);
    }

    public void reverseAllBelts()
    {
        belt1.set(-0.75);
        belt2.set(-0.75);
    }

    /**
     * Gets the state of the beam break at the front of the belts
     * 
     * @return If the beam is broken
     */
    public boolean getBeamBreak1()
    {
        return !beamBreak1.get();
        // return false;
    }

    /**
     * Gets the state of the beam break at the bottom of second belt
     * 
     * @return If the beam is broken
     */
    public boolean getBeamBreak2()
    {
        return !beamBreak2.get();
        // return false;
    }

    /**
     * Gets the state of the beam break right before the shooter
     * 
     * @return If the beam is broken
     */
    public boolean getBeamBreak3()
    {
        return !beamBreak3.get();
        // return false;
    }
}