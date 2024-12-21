package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class OutputUnDumpPreset extends SequentialCommandGroup {

    public OutputUnDumpPreset(OutputRotationSubsystem outputRotate, OutputLiftSubsystem outputLift) {
        addCommands(
                outputRotate.setIntakeReadyCmd(),
                new WaitCommand(500),
                outputLift.setDownCmd()
        );
    }
}
