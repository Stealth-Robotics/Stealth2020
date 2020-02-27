
package frc.robot.commands.EncoderAutoCommands;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.subsystems.*;
 
 /**
  * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
  * explicitly for pedagogical purposes - actual code should inline a command this simple with {@link
  * edu.wpi.first.wpilibj2.command.RunCommand}.
  */
 public class DriveBackwards extends CommandBase {
   private int mPosition = 0;
   private DriveBase mDriveSubsystem;
   private final CANCoder m_leftEncoder = new CANCoder(RobotMap.LeftEncoder);

   /**
    * Creates a new DefaultDrive.
    *
    * @param subsystem The drive subsystem this command wil run on.
    * @param forward The control input for driving forwards/backwards
    * @param rotation The control input for turning
    */
   public DriveBackwards(int position, DriveBase driveSubsystem) {
     mPosition = position;
     mDriveSubsystem = driveSubsystem;
   }
 
   @Override
   public void initialize() {
   }
 
   @Override
   public void execute() {
       mDriveSubsystem.arcadeDrive(-0.5, -0.5);
      // System.out.println("Run Forward");
     }
 
   @Override
   public boolean isFinished() {
     if( m_leftEncoder.getPosition() > mPosition)
     {
       return true;
     }
     else 
     {
       return false;
     }
       
   }
 
   @Override
   public void end(boolean interrupted) {
     mDriveSubsystem.arcadeDrive(0, 0);
   }
 }
 