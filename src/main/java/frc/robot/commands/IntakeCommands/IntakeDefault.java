/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.IntakeCommands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * An example command that uses an example subsystem.
 */
public class IntakeDefault extends CommandBase 
{
    private final Intake intake;
    private final Shooter shooter;

    /**
     * Creates a new ExampleCommand.
     *
     * @param intake The subsystem used by this command.
     */
    public IntakeDefault(Intake intake, Shooter shooter) 
    {
        this.intake = intake;
        this.shooter = shooter;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        if (shooter.getBeamBreak2())
        {
            new RunCommand(() -> intake.runBelt(), intake)
                    .withInterrupt(new BooleanSupplier()
                    {
                        @Override
                        public boolean getAsBoolean()
                        {
                            return !shooter.getBeamBreak2();
                        }
                    })
                    .andThen(new WaitCommand(5))
                    .andThen(() -> intake.reverseBelt(), intake)
                        .withInterrupt(new BooleanSupplier()
                        {
                            public boolean getAsBoolean()
                            {
                                return intake.getBeamBreak1();
                            }
                        }).schedule();
        }
        if (intake.getBeamBreak1())
        {
            intake.runBelt();
        }
        else
        {
            intake.stopBelt();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
