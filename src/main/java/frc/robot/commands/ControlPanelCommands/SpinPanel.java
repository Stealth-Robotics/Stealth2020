package frc.robot.commands.ControlPanelCommands;

import frc.robot.subsystems.PanelControl;
import frc.util.StopWatch;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command spins the control panel 3 times
 */
public class SpinPanel extends CommandBase 
{
    StopWatch timer;
    int greenCounter;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final PanelControl panelControl;

    /**
     * Creates a new SpinPanel.
     *
     * @param subsystem The subsystem used by this command.
     */
    public SpinPanel(PanelControl panelControl) 
    {
        this.panelControl = panelControl;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(panelControl);
    }
    
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        //timer = new StopWatch(3000);
        //panelControl.setWheelSpeed(1.0);
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        if (panelControl.getColor().equals("G"))
        {
           
               greenCounter ++;
               System.out.println("We have found the green on the control panel. The green");
             
            
        }
        while(greenCounter <= 6)
        {
            panelControl.setWheelSpeed(0.5);
            System.out.println("We are spinning the control panel");

        }
        if(greenCounter > 6)
        {
            panelControl.setWheelSpeed(0.0);
            System.out.printf("We have seen green 6 times , the color wheel has stopped spiining");
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
      //  panelControl.setWheelSpeed(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return timer.isExpired();
    }
}
