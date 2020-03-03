package frc.robot.commands.AutoCommands.AutoPaths;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AutoCommands.DriveForInches;
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
  DistanceSensor distanceSensor) 
  {

    addCommands
    (
      new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
      new AlignWithTarget(driveBase, limelight, distanceSensor), new AimHood(shooter, distanceSensor, false),
      new RunCommand(() -> shooter.setShooterSpeedDirect(0.85)).withTimeout(3),
      new FireShooter(shooter, belts).withTimeout(5),
      new RunCommand(() -> shooter.setHoodPos(Constants.maxAngle)).withTimeout(0),
      new RunCommand(() -> driveBase.resetEncoders()).withTimeout(0), new DriveForInches(12, driveBase)
    );
  }
}