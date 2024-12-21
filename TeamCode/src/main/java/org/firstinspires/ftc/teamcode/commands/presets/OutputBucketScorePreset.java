package org.firstinspires.ftc.teamcode.commands.presets;


import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class OutputBucketScorePreset extends SequentialCommandGroup {
    protected static final long ROTATE_DELAY = 500;
    public OutputBucketScorePreset(OutputRotationSubsystem outputRotate, OutputLiftSubsystem outputLift) {
        addCommands(
                outputLift.setDumpCmd(),
                new WaitCommand(ROTATE_DELAY),
                outputRotate.setBucketCmd()
        );
    }
}
