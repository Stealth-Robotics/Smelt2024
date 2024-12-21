package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.ExtenderDefaultCommand;
import org.firstinspires.ftc.teamcode.commands.FollowerCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCombinedCmd;
import org.firstinspires.ftc.teamcode.commands.IntakeSuckCommand;
import org.firstinspires.ftc.teamcode.commands.LifterDefaultCommand;
import org.firstinspires.ftc.teamcode.commands.OutputCombinedCmd;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

// Example of using the Stealth library for TeleOp driving
// this example also uses pedroPathing to drive the robot
@TeleOp(name = "A Stealth TeleOp", group = "example")
public class TeleStealth extends StealthOpMode {

    // This is the starting position of the robot in inches. Blue home facing red bucket
    // is 0, 0, 0 + 1/2 the robot width and Length
    private static final Pose startPose = new Pose(0, 0, 0);

    private GamepadEx driver;
    private GamepadEx operator;

    private FollowerSubsystem fss;

    @Override
    public void whileWaitingToStart(){
        // Enabling this would mean the robot is live even before play is pushed
        //CommandScheduler.getInstance().run();
        // block to
    }

    @Override
    public void initialize(){
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        // The Subsystem sets up the hardware and the command runs the code on the hardware
        fss = new FollowerSubsystem(hardwareMap);
        LifterSubsystem lifter = new LifterSubsystem(hardwareMap);
        ExtenderSubsystem extender = new ExtenderSubsystem(hardwareMap);
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap);
        ClipsSubsystem clips = new ClipsSubsystem(hardwareMap);
        OutputLiftSubsystem output = new OutputLiftSubsystem(hardwareMap,telemetry);
        IntakeElbowSubsystem intakeElbow = new IntakeElbowSubsystem(hardwareMap);
        OutputRotationSubsystem rotate = new OutputRotationSubsystem(hardwareMap);
        IntakeWristSubsystem wrist = new IntakeWristSubsystem(hardwareMap);
        // schedules periodic method
        register(fss, lifter, extender, intake, intakeElbow, wrist);
        IntakeSuckCommand intakeCmd = new IntakeSuckCommand(
                intake,
                () -> (operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)),
                telemetry);
        intake.setDefaultCommand(intakeCmd);

        // this is setting for telemetry to be sent to the driver station
        fss.getFollower().setStartingPose(startPose);
        // registers for the periodic to be called (telemetry)
        // Follower controls robot motion
        FollowerCommand cmd = new FollowerCommand(
                fss,
                telemetry,
                () -> -driver.getLeftY(),
                () -> driver.getLeftX(),
                () -> (driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)),
                () -> driver.getGamepadButton(GamepadKeys.Button.A).get());
        fss.setDefaultCommand(cmd);

        LifterDefaultCommand liftCmd = new LifterDefaultCommand(
                lifter,
                ()-> -operator.getRightY(),
                ()-> driver.gamepad.touchpad);
                //()-> driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).get());

        lifter.setDefaultCommand(liftCmd);
        ExtenderDefaultCommand extenderCmd = new ExtenderDefaultCommand(
                extender,
                ()-> operator.getLeftY());

        extender.setDefaultCommand(extenderCmd);
        OutputCombinedCmd outputCombinedCmd = new OutputCombinedCmd(
                rotate,
                output,
                lifter,
                clips,
                ()-> operator.getGamepadButton(GamepadKeys.Button.A).get(),
                ()-> operator.getGamepadButton(GamepadKeys.Button.B).get(),
                ()-> operator.getGamepadButton(GamepadKeys.Button.X).get(),
                ()-> operator.getGamepadButton(GamepadKeys.Button.Y).get());

        output.setDefaultCommand(outputCombinedCmd);

        IntakeCombinedCmd intakeCombinedCmd = new IntakeCombinedCmd(
                intakeElbow,
                wrist,
                extender,
                intake,
                rotate,
                lifter,
                clips,
                output,
                ()-> operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).get(),
                ()-> operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).get());
        intakeElbow.setDefaultCommand(intakeCombinedCmd);

        // This zeros the encoder regardless of position move the arms all the way in.
        operator.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(extender::resetEncoder);
        operator.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(lifter::resetEncoder);

        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(clips.setOpenCmd());
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(clips.setCloseCmd());
        operator.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(lifter.startSetPositionCommand(1));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(lifter.startSetPositionCommand(.001));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(wrist.setStartPositionCmd());
        operator.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(clips.toggleCmd());

    }


}
