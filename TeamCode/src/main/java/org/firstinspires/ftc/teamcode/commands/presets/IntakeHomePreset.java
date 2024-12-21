package org.firstinspires.ftc.teamcode.commands.presets;

import static org.firstinspires.ftc.teamcode.common.StealthAutoMode.*;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

public class IntakeHomePreset extends ParallelCommandGroup {
    public IntakeHomePreset(){
        addCommands(
                intakeWristSs.setStartPositionCmd(),
                intakeElbowSs.setUpCmd()
        );
    }
}
