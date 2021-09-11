package frc.robot.commands.ControlPanelCommands;

import frc.robot.subsystems.PanelControl;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleWheel extends CommandBase
{
    private final PanelControl panelControl;

    public ToggleWheel(PanelControl panelControl)
    {
        this.panelControl = panelControl;

    }

    public void initialize()
    {
        panelControl.togglePosition();
    }
}