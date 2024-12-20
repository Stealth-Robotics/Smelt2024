package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;
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

    // Subsystems for robot control
    protected FollowerSubsystem followerSubsystem; // For path following
    protected LifterSubsystem lifterSubsystem; // For controlling the lifter
    protected ExtenderSubsystem extenderSubsystem; // For controlling the extender
    protected IntakeSubsystem intakeSubsystem; // For controlling the intake
    protected ClipsSubsystem clipsSubsystem; // For controlling the clips

    protected OutputLiftSubsystem outputLiftSubsystem;
    protected IntakeElbowSubsystem intakeElbowSubsystem;
    protected OutputRotationSubsystem outputRotationSubsystem;
    protected IntakeWristSubsystem intakeWristSubsystem;

    // Stores a set of commands to be executed on run.
    protected final SequentialCommandGroup commandGroup = new SequentialCommandGroup();

    // Last known position of the robot
    protected Pose lastPose;

    // Average position of the robot over the last 500ms
    protected Pose avgPose;

    /**
     * Override this to setup your hardware, commands, button bindings, etc.
     * You can extend this by calling `super.initialize()` in your override.
     */
    @Override
    public void initialize() {
        followerSubsystem = new FollowerSubsystem(hardwareMap);
        lifterSubsystem = new LifterSubsystem(hardwareMap);
        extenderSubsystem = new ExtenderSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        clipsSubsystem = new ClipsSubsystem(hardwareMap);

        outputLiftSubsystem = new OutputLiftSubsystem(hardwareMap,telemetry);
        intakeElbowSubsystem = new IntakeElbowSubsystem(hardwareMap);
        outputRotationSubsystem = new OutputRotationSubsystem(hardwareMap);
        intakeWristSubsystem = new IntakeWristSubsystem(hardwareMap);
        // schedules periodic method
        register(followerSubsystem, lifterSubsystem, extenderSubsystem, intakeSubsystem, intakeElbowSubsystem, intakeWristSubsystem);
    }

    /**
     * This runs between initialization and start. This code is pulling the limelight AprilTags
     * pipeline to attempt to get field position to update the follower and Alliance data.
     * There is a bit of telemetry spew here that could be commented out.
     * You can extend or override this in your Auto.
     */
    @Override
    public void whileWaitingToStart() {
        telemetry.addData("Alliance", Alliance.get().toString());
    }

    /**
     * Gets the LifterSubsystem instance.
     * @return LifterSubsystem instance
     */
    public LifterSubsystem getLifter() {
        return lifterSubsystem;
    }

    /**
     * Gets the ExtenderSubsystem instance.
     * @return ExtenderSubsystem instance
     */
    public ExtenderSubsystem getExtender() {
        return extenderSubsystem;
    }

    /**
     * Gets the FollowerSubsystem instance.
     * @return FollowerSubsystem instance
     */
    public FollowerSubsystem getFollower() {
        return followerSubsystem;
    }


}
