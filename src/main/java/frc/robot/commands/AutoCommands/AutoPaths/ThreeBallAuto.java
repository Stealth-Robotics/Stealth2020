package frc.robot.commands.AutoCommands.AutoPaths;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ThreeBallAuto extends SequentialCommandGroup {

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on
   * 
   */
  public ThreeBallAuto(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, Intake intake,
      DistanceSensor distanceSensor) {

    addCommands
    (
        // Drive forward the specified distance
        //3 ball auto
     
        new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
        new AlignWithTarget(driveBase, limelight),
        new AimHood(shooter, distanceSensor, limelight),
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
                return driveBase.getLeftEncoder().getPosition() > 500;
              }
            })
       /*
       new TurnToAngle(0, driveBase)
       new RunCommand(() -> intake.toggle()),
        new RunCommand(() -> intake.run()),
       new DriveForInches(1000, driveBase),
       new RunCommand(() -> intake.stop()),
        new RunCommand(() -> intake.toggle()),
          new DriveForInches(-1000, driveBase),
        //shoot
        new AlignWithTarget(driveBase, limelight),
        new AimHood(shooter, distanceSensor),
        // new ReverseBelt(belts, 300),
        new RunCommand(() -> shooter.setShooterSpeedDirect(0.8)).withTimeout(0),
        // new WaitCommand(0.5),
        new FireShooter(shooter, belts).withTimeout(5),
        new DriveForInches(360, driveBase)
      */
      
    );
  }
}