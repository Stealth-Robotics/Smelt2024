package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.commands.ExtenderDefaultCommand;
import org.firstinspires.ftc.teamcode.commands.FollowerCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSuckCommand;
import org.firstinspires.ftc.teamcode.commands.OutputCombinedCommand;
import org.firstinspires.ftc.teamcode.commands.LifterDefaultCommand;
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
    private static final Pose startPose = new Pose(8.5, 48, 0);

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

        // You need to created two classes for each mechanism.
        // The Subsystem sets up the hardware and the command runs the code on the hardware
        fss = new FollowerSubsystem(hardwareMap, telemetry);
        LifterSubsystem lifter = new LifterSubsystem(hardwareMap, telemetry);
        ExtenderSubsystem extender = new ExtenderSubsystem(hardwareMap, telemetry);
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, telemetry);
        ClipsSubsystem clips = new ClipsSubsystem(hardwareMap, telemetry);
        OutputLiftSubsystem output = new OutputLiftSubsystem(hardwareMap,telemetry);
        IntakeElbowSubsystem intakeElbow = new IntakeElbowSubsystem(hardwareMap, telemetry);
        OutputRotationSubsystem rotate = new OutputRotationSubsystem(hardwareMap, telemetry);
        IntakeWristSubsystem wrist = new IntakeWristSubsystem(hardwareMap, telemetry);
        register(fss, lifter, extender, intake);
        IntakeSuckCommand intakeCmd = new IntakeSuckCommand(
                intake,
                () -> (operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)),
                telemetry);
        intake.setDefaultCommand(intakeCmd);
//        ClipsCommand clipsCmd = new ClipsCommand(clips, telemetry);
//        OutputRotateCommand rotateCmd = new OutputRotateCommand(rotate, telemetry);
//        OutputLiftCommand outputLiftCmd = new OutputLiftCommand(output, telemetry);
//        IntakeElbowCommand intakeElbowCmd = new IntakeElbowCommand(intakeElbow, telemetry);
        OutputCombinedCommand outputCombinedCmd = new OutputCombinedCommand(rotate, output, lifter, telemetry);
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
                telemetry,
                ()-> -operator.getRightY(),
                ()-> driver.gamepad.guide);

        lifter.setDefaultCommand(liftCmd);
        ExtenderDefaultCommand extenderCmd = new ExtenderDefaultCommand(
                extender,
                telemetry,
                ()-> operator.getRightX());

        extender.setDefaultCommand(extenderCmd);
//        driver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(cmd::resetImu));
//        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(()->lifter.setPosition(.5)));
//        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(()->lifter.setPosition(.99)));
//        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(()->lifter.setPosition(.001)));
//        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(()->extender.setPosition(.5)));

        operator.getGamepadButton(GamepadKeys.Button.A).whenPressed(outputCombinedCmd.setClipGrab());
        operator.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand((outputCombinedCmd::setIntakeReadyBucket)));
        operator.getGamepadButton(GamepadKeys.Button.X).whenPressed(outputCombinedCmd.setDumpBucket());
        operator.getGamepadButton(GamepadKeys.Button.Y).whenPressed(outputCombinedCmd.setClipScore());

        operator.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand((clips::setOpen)));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand((clips::setClose)));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand((rotate::toggleClips)));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand((rotate::toggleBucket)));
    }


}
