package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;

/**
 * Deploys the intake system to pickup a specimen
 */
public class IntakeDeployPreset extends SequentialCommandGroup {
    public IntakeDeployPreset(ExtenderSubsystem extender, IntakeElbowSubsystem intakeElbow, IntakeWristSubsystem intakeWrist, IntakeSubsystem intake){

        addCommands(
                //intakeElbow.setMiddleCmd(),
                intakeWrist.setStartPositionCmd(),
                //new WaitCommand(50),
                //extender.startSetPositionCommand(.98),
                //new WaitCommand(500),
                intake.setIntakeCmd(),
                //extender.endSetPositionCommand(500),
                intakeElbow.setPickupCmd());
    }
}
