package org.firstinspires.ftc.teamcode.Autos;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Paths.BucketScorePath;
import org.firstinspires.ftc.teamcode.commands.OutputCombinedCmd;
import org.firstinspires.ftc.teamcode.commands.presets.OutputBucketScorePreset;
import org.firstinspires.ftc.teamcode.commands.presets.OutputUnDumpPreset;
import org.firstinspires.ftc.teamcode.common.StealthAutoMode;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;


@Autonomous(name = "AutoBucket Score", group = "Blue")
public class AutoBucketScore extends StealthAutoMode {


    private BucketScorePath path;

    /**
     * Override this to setup your hardware, commands, button bindings, etc.
     */
    @Override
    public void initialize() {

        super.initialize();
        telemetry.addLine("Example path of using the follower");
        path = new BucketScorePath();
        commandGroup.addCommands(initPath(), runPath());
        intakeElbowSubsystem.setUpPose();
        intakeWristSubsystem.setStartPosition();


    }

    @Override
    public Command getAutoCommand() {
        return commandGroup;
    }

    protected Command initPath() {
        return new InstantCommand(()->
        {
            Follower follower = followerSubsystem.getFollower();

            follower.setStartingPose(path.getStartPose());
            follower.update();
        });

    }

    private Command runPath() {
        Follower follower = followerSubsystem.getFollower();

        return new SequentialCommandGroup(
                lifterSubsystem.startSetPositionCommand(.99),
                new InstantCommand(() ->follower.followPath(path.getNextSegment())),

                lifterSubsystem.endSetPositionCommand(2000),
                new OutputBucketScorePreset(outputRotationSubsystem, outputLiftSubsystem),
                new WaitCommand(2000),
                new OutputUnDumpPreset(outputRotationSubsystem, outputLiftSubsystem),
                new WaitCommand(200),
                lifterSubsystem.startSetPositionCommand(.0001)

        );
    }

    private InstantCommand setBothArms(double pose) {
        return new InstantCommand(() ->
        {
            getExtender().setPosition(pose);
            getLifter().setPosition(pose);
        });
    }

}
