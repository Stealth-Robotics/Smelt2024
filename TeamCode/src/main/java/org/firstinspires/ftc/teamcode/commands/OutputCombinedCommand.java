package org.firstinspires.ftc.teamcode.commands;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

// all positions are 0
public class OutputCombinedCommand extends ParallelRaceGroup {
        private final OutputRotationSubsystem outputRotate;
        private final OutputLiftSubsystem outputLift;

        public OutputCombinedCommand(OutputRotationSubsystem outputRotationSubsystem, OutputLiftSubsystem outputLiftSubsystem, Telemetry telemetry) {
            this.outputRotate = outputRotationSubsystem;
            this.outputLift = outputLiftSubsystem;

        }

        public void  setClipGrab() {
            outputRotate.setClips();
            outputLift.setClip();
        }
        public void setDumpBucket() {
            outputRotate.setBucket();
            outputLift.setDump();
        }
        public void setClipScore() {
            outputRotate.setClips();
            outputLift.setClip();
        }
        public void setIntakeReadyBucket() {
            outputRotate.setIntakeReady();
            outputLift.setDown();
        }




}

