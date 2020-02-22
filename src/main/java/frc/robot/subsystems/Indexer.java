package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase
{
    private final WPI_TalonSRX belt1;
    private final WPI_TalonSRX belt2;

    private final DigitalInput beamBreak1;
    private final DigitalInput beamBreak2;

    
    public Indexer()
    {
        beamBreak1 = new DigitalInput(RobotMap.BeamBreak1);
        beamBreak2 = new DigitalInput(RobotMap.BeamBreak2);

        belt1 = new WPI_TalonSRX(RobotMap.Belt1);
        belt2 = new WPI_TalonSRX(RobotMap.Belt2);
    }

    @Override
    public void periodic()
    {
        System.out.println(beamBreak1.get());
    }
}