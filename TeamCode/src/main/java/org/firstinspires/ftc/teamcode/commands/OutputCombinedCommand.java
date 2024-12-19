package org.firstinspires.ftc.teamcode.commands;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

// all positions are 0
public class OutputCombinedCommand {
        private final OutputRotationSubsystem outputRotate;
        private final OutputLiftSubsystem outputLift;

        private final LifterSubsystem lifter;

        private static final long ROTATE_DELAY = 300;
        private static final double LIFTER_SCORE_POSITION = .5;

        public OutputCombinedCommand(
                OutputRotationSubsystem outputRotationSubsystem,
                OutputLiftSubsystem outputLiftSubsystem,
                LifterSubsystem lifterSubsystem,
                Telemetry telemetry) {
            this.outputRotate = outputRotationSubsystem;
            this.outputLift = outputLiftSubsystem;
            this.lifter = lifterSubsystem;
        }

        public Command setClipGrab(){
            // prevent the bucket from rotating before the arms have moved
            if (outputLift.isDownPosition()) {
                return new SequentialCommandGroup(
                        new InstantCommand(outputLift::setClip),
                        new WaitCommand(ROTATE_DELAY),
                        new InstantCommand(outputRotate::setClip));
            }
            return new ParallelRaceGroup(
                    new InstantCommand(outputLift::setClip),
                    new InstantCommand(outputRotate::setClip));

        }

        public Command setDumpBucket(){
            if (outputLift.isDownPosition()) {
                return new SequentialCommandGroup(
                        new InstantCommand(outputLift::setDump),
                        new WaitCommand(ROTATE_DELAY),
                        new InstantCommand(outputRotate::setBucket));

            }

            return new ParallelRaceGroup(
                    new InstantCommand(outputLift::setDump),
                    new InstantCommand(outputRotate::setBucket));
        }

        public Command setClipScore() {
            return new SequentialCommandGroup(
                    lifter.startSetPositionCommand(LIFTER_SCORE_POSITION),
                    new WaitCommand(10),
                    new InstantCommand(outputLift::setClip),
                    new InstantCommand(outputRotate::setClip));
        }

        public void setIntakeReadyBucket() {
            outputRotate.setIntakeReady();
            outputLift.setDown();
        }




}

