
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PanelControl;

public class TogglePanelWheelPos extends CommandBase
{
    private final PanelControl panelControl;

    public TogglePanelWheelPos(PanelControl subsystem)
    {
        panelControl = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        panelControl.togglePosition();
    }

    @Override
    public boolean isFinished() 
    {
        return true;
    }
}