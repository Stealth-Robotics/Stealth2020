package frc.robot.commands.AutoCommands.AutoPaths;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoCommands.DriveForInches;
import frc.robot.commands.AutoCommands.TurnToAngle;
import frc.robot.commands.BeltsCommands.ReverseBelt;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.MultiSubsystemCommands.ScoreFuel;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class SixBallAuto extends SequentialCommandGroup {

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on
   * 
   */
  public SixBallAuto(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, Intake intake,
      DistanceSensor distanceSensor) {

    addCommands
    (
        // Drive forward the specified distance
        //3 ball auto
     
        new AlignWithTarget(driveBase, limelight),
        new AimHood(shooter, distanceSensor),
        // new ReverseBelt(belts, 300),
        new RunCommand(() -> shooter.setShooterSpeedDirect(0.8)).withTimeout(0),
        // new WaitCommand(0.5),
        new FireShooter(shooter, belts).withTimeout(5),
        new DriveForInches(360, driveBase)
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