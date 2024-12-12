package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.utils.MovementUtil;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * Base class for autonomous OpModes in Stealth Robotics framework.
 * Provides common functionality for initialization, telemetry, and subsystem management.
 * **Usage:**
 * 1. Add your robot specific hardware subsystems
 * 2. Extend this class to create your specific autonomous OpMode.
 * 3. Override the `getAutoCommand()` method to implement your autonomous logic.
 * 4. Utilize the provided subsystems and helper methods for robot control.
 */
public abstract class StealthAutoMode extends StealthOpMode {

    // Telemetry instances for driver station and dashboard
    protected Telemetry telemetryA;

    // Subsystems for robot control
    protected FollowerSubsystem followerSubsystem; // For path following
    protected LifterSubsystem lss; // For controlling the lifter
    protected ExtenderSubsystem ess; // For controlling the extender

    // Stores a set of commands to be executed on run.
    protected final SequentialCommandGroup commandGroup = new SequentialCommandGroup();

    // Last known position of the robot
    protected Pose lastPose;

    // Average position of the robot over the last 500ms
    protected Pose avgPose;

    // How often to calculate the average position
    private static final long AVERAGE_WAIT_TIME = 100;

    private long timer = 0;

    /**
     * Override this to setup your hardware, commands, button bindings, etc.
     * You can extend this by calling `super.initialize()` in your override.
     */
    @Override
    public void initialize() {
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        followerSubsystem = new FollowerSubsystem(hardwareMap, telemetry);
        lss = new LifterSubsystem(hardwareMap, telemetry);
        ess = new ExtenderSubsystem(hardwareMap, telemetry);
        // registers the subsystems for running the periodic override when the match starts
        register(followerSubsystem, lss, ess);
    }

    /**
     * This runs between initialization and start. This code is pulling the limelight AprilTags
     * pipeline to attempt to get field position to update the follower and Alliance data.
     * There is a bit of telemetry spew here that could be commented out.
     * You can extend or override this in your Auto.
     */
    @Override
    public void whileWaitingToStart() {
        telemetryA.addData("Alliance", Alliance.get().toString());
    }

    /**
     * Gets the LifterSubsystem instance.
     * @return LifterSubsystem instance
     */
    public LifterSubsystem getLifter() {
        return lss;
    }

    /**
     * Gets the ExtenderSubsystem instance.
     * @return ExtenderSubsystem instance
     */
    public ExtenderSubsystem getExtender() {
        return ess;
    }

    /**
     * Gets the FollowerSubsystem instance.
     * @return FollowerSubsystem instance
     */
    public FollowerSubsystem getFollower() {
        return followerSubsystem;
    }


}
