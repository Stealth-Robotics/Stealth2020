package frc.robot.commands.AutoCommands.AutoPaths;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AutoCommands.DriveForInches;
import frc.robot.commands.AutoCommands.TurnToAngle;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class SixBallAuto extends SequentialCommandGroup 
{

  DriveBase driveBase;
  Shooter shooter;
  Belts belts;
  Limelight limelight;
  Intake intake;
  DistanceSensor distanceSensor;

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on
   * 
   */

  public SixBallAuto(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, Intake intake,
      DistanceSensor distanceSensor) 
  {
    this.driveBase = driveBase;
    this.shooter = shooter;
    this.belts = belts;
    this.limelight = limelight;
    this.intake = intake;
    this.distanceSensor = distanceSensor;
  }
  
  @Override
  public void initialize()
  {
    addCommands
    (
      new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
        new AlignWithTarget(driveBase, limelight),
        new AimHood(shooter, distanceSensor, false),
        // new ReverseBelt(belts, 300),
        new RunCommand(() -> shooter.setShooterSpeedDirect(0.85)).withTimeout(0),
        // new WaitCommand(0.5),
        new FireShooter(shooter, belts).withTimeout(5),
        new RunCommand(() -> shooter.setHoodPos(Constants.maxAngle)).withTimeout(0),
        // new DriveForInches(-50000, driveBase)
        new RunCommand(() -> driveBase.resetEncoders()).withTimeout(0),
        new RunCommand(() -> driveBase.arcadeDrive(-0.5, 0), driveBase)
            .withInterrupt(new BooleanSupplier()
            {
              @Override
              public boolean getAsBoolean() 
              {
                return driveBase.getLeftEncoder().getPosition() > 700;
              }
            }),
       
       // make the bot straight
       new TurnToAngle(0, driveBase),
       //Put the intake down and run the intake
       new RunCommand(() -> intake.toggle()),
        new RunCommand(() -> intake.run()),
        //drive backwards to collect intake
        new RunCommand(() -> driveBase.arcadeDrive(-0.5, 0), driveBase)
            .withInterrupt(new BooleanSupplier()
            {
              @Override
              public boolean getAsBoolean() 
              {
                return driveBase.getLeftEncoder().getPosition() > 500;
              }
            }) , 
       //bring  intake up and collect balls
       new RunCommand(() -> intake.stopIntake()),
        new RunCommand(() -> intake.toggle()),
       //drive back to shoot in range
           new RunCommand(() -> driveBase.arcadeDrive(0.5, 0), driveBase)
            .withInterrupt(new BooleanSupplier()
            {
              @Override
              public boolean getAsBoolean() 
              {
                return driveBase.getLeftEncoder().getPosition() > 500;
              }
            }),
        //shoot
        new AlignWithTarget(driveBase, limelight),
        new AimHood(shooter, distanceSensor, false),
        // new ReverseBelt(belts, 300),
        new RunCommand(() -> shooter.setShooterSpeedDirect(0.8)).withTimeout(0),
        // new WaitCommand(0.5),
        new FireShooter(shooter, belts).withTimeout(5),
        //drive backwards a tiny bitjust to make sure we got off the line   
        new DriveForInches(360, driveBase)
     
    );
  }
}