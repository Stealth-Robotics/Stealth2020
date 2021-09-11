/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BeltsCommands;

import frc.robot.subsystems.Belts;
import frc.robot.subsystems.Intake;
import frc.util.StopWatch;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class BeltsDefault extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Belts belts;
    private final Intake intake;
    private StopWatch timer;
    private StopWatch timer1;

    private boolean previousBeamBreak1;
    private boolean delay = false;
    //private boolean previousBeamBreak2;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public BeltsDefault(Belts belts, Intake intake) 
    {
        this.belts = belts;
        this.intake = intake;

        timer = new StopWatch(100);
        timer1 = new StopWatch(500);

        previousBeamBreak1 = false;
        //previousBeamBreak2 = false;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(belts);
    }

    @Override
    public void execute()
    {    

        if (belts.getBelt1() >= 0)
        {
            if(belts.getBeamBreak1() && belts.getBallCount() <= 4 && delay)
            {    
                //set a delay/pause for 500 ms here
                
                belts.runAllBelts(0.8, 0.8);
                timer.reset();
                timer1.reset();
                delay = false;
            }

            if(timer1.isExpired())
            {
                delay = true;
            }
 
            if(belts.getBallCount() > 4 && belts.getBeamBreak1() == true)
            {
                intake.runHelperWheel(0.1);
            }
            
            if(timer.isExpired())
            {
                belts.stopAllBelts();
            }

            if (belts.getBeamBreak1() && !previousBeamBreak1)
            {
                belts.addBall();
            }
        }
        else if (!belts.getBeamBreak1() && previousBeamBreak1)
        {
            belts.removeBall();
        }

        previousBeamBreak1 = belts.getBeamBreak1();
        //previousBeamBreak2 = belts.getBeamBreak2();

        /*
        if (belts.getBeamBreak2() && !belts.getBeamBreak3())
        {
            belts.runBelt2();
        }
        else
        {
            belts.stopBelt2();;
        }
        */
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
