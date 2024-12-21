package org.firstinspires.ftc.teamcode.Autos;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Paths.ParkHomePath;
import org.firstinspires.ftc.teamcode.commands.presets.IntakeHomePreset;
import org.firstinspires.ftc.teamcode.common.StealthAutoMode;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

/**
 * Example of pushing a blue block to the observation station.
 * if the word blue is used in name or group then Alliance is blue.
 * This can later be updated by the limelight if configured so
 */

@Autonomous(name = "Park Home", group = "Blue")
public class AutoParkHome extends StealthAutoMode {
    private ParkHomePath path;

    /**
     * Called aft er the Init button is pushed add any specific
     * code that sets up the robot here
     */
    @Override
    public void initialize() {
        super.initialize();
        path = new ParkHomePath();
        commandGroup.addCommands(new IntakeHomePreset(), initPath(), runPath());
    }

    /**
     * Called by the op mode to get the list of command to run during play
     * @return Command(s) to run
     */
    @Override
    public Command getAutoCommand() {
        return commandGroup;
    }

    /**
     * Put code here that you want to do first right after pressing play
     * @return a Command to be run
     */
    protected Command initPath() {
        return new InstantCommand(() ->
        {
            Follower follower = followerSs.getFollower();
            follower.setStartingPose(path.getStartPose());
            follower.update();
        });

    }

    /**
     * This is were you can put the majority of your code to more the bot
     * and operate different components. The sequential command group will
     * get be called by the scheduler one at a time until all are done.
     * @return a Command to be run
     */
    private Command runPath() {
       assert (path.getSegmentCount() == 2);
        return
                new SequentialCommandGroup(
                        followerSs.followPathCommand(path.getNextSegment(), 5000, false)
                );
    }
}
