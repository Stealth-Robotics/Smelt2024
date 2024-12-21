package org.firstinspires.ftc.teamcode.Autos;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Paths.BucketScorePath;
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
        intakeElbowSs.setUpPose();
        intakeWristSs.setStartPosition();


    }

    @Override
    public Command getAutoCommand() {
        return commandGroup;
    }

    protected Command initPath() {
        return new InstantCommand(()->
        {
            Follower follower = followerSs.getFollower();

            follower.setStartingPose(path.getStartPose());
            follower.update();
        });

    }

    private Command runPath() {
        Follower follower = followerSs.getFollower();

        return new SequentialCommandGroup(
                lifterSs.startSetPositionCommand(.99),
                new InstantCommand(() ->follower.followPath(path.getNextSegment())),

                lifterSs.endSetPositionCommand(2000),
                new OutputBucketScorePreset(outputRotateSs, outputLiftSs),
                new WaitCommand(2000),
                new OutputUnDumpPreset(outputRotateSs, outputLiftSs),
                new WaitCommand(200),
                lifterSs.startSetPositionCommand(.0001)

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
