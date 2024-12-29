package org.firstinspires.ftc.teamcode.commands.presets;

import static org.firstinspires.ftc.teamcode.common.StealthAutoMode.*;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;


public class SpecimenGrabPreset extends SequentialCommandGroup {
    protected static final long ROTATE_DELAY = 500;
    protected static final double LIFTER_SCORE_POSITION = .51;

    public SpecimenGrabPreset() {

        addCommands(
                clipsSs.setCloseCmd(),
                new WaitCommand(300),
                lifterSs.startSetPositionCommand(LIFTER_SCORE_POSITION),
                new WaitCommand(50),
                outputLiftSs.setClipScoreCmd(),
                outputRotateSs.setScorePoseCmd()
        );

    }
}
