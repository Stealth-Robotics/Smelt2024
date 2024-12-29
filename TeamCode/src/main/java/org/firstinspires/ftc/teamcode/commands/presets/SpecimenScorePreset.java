package org.firstinspires.ftc.teamcode.commands.presets;

import static org.firstinspires.ftc.teamcode.common.StealthAutoMode.*;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class SpecimenScorePreset extends SequentialCommandGroup {
    protected static final double LIFTER_SCORE_POSITION = .51;

    public SpecimenScorePreset(OutputRotationSubsystem outputRotate)
    {
        addCommands(
                outputRotate.setScorePoseCmd(),
                lifterSs.startSetPositionCommand(LIFTER_SCORE_POSITION * .49),
                new WaitCommand(600),
                clipsSs.setOpenCmd()
        );
    }
}
