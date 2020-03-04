
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class PanelControl extends SubsystemBase 
{
    private final WPI_TalonSRX wheelMotor;
    private final ColorSensorV3 colorSensor;
    private final Solenoid colorWheelPiston;

    /**
     * Creates a new ColorWheel.
     */
    public PanelControl() 
    {
        wheelMotor = new WPI_TalonSRX(RobotMap.ColorWheelMotor);
        colorSensor = new ColorSensorV3(Port.kOnboard);
        colorWheelPiston = new Solenoid(RobotMap.PCM, RobotMap.ColorWheelPCMChannel);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
        // double prox = Math.pow(colorSensor.getProximity(), 1.25);
        // System.out.println((int)colorSensor.getRed() / prox + " " + (int)colorSensor.getBlue() / prox + " " + (int)colorSensor.getGreen() / prox + " " + colorSensor.getProximity());
        // System.out.println(getColor());
    }

    public void setWheelSpeed(double speed)
    {
        wheelMotor.set(speed);
    }

    /**
     * Gets the current color of the control panel
     * 
     * @return The color of control panel
     */
    public String getColor()
    {
        double red = colorSensor.getRed() / Math.pow(colorSensor.getProximity(), 1.25);
        double blue = colorSensor.getBlue() / Math.pow(colorSensor.getProximity(), 1.25);
        double green = colorSensor.getGreen() / Math.pow(colorSensor.getProximity(), 1.25);

        if (red > 6.5)
        {
            if (green > 15.0)
            {
                return "G";
            }
            else
            {
                return "B";
            }
        }
        else
        {
            if (blue > 9.5)
            {
                return "R";
            }
            else
            {
                return "Y";
            }
        }
    }

    public void togglePosition()
    {
        colorWheelPiston.set(!colorWheelPiston.get());
    }
}
