package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ClipsCommand;
import org.firstinspires.ftc.teamcode.commands.ExtenderDefaultCommand;
import org.firstinspires.ftc.teamcode.commands.FollowerCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeElbowCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSuckCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeWristCommand;
import org.firstinspires.ftc.teamcode.commands.LifterDefaultCommand;
import org.firstinspires.ftc.teamcode.commands.OutputLiftCommand;
import org.firstinspires.ftc.teamcode.commands.OutputRotateCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputSubsystem;
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
    //private GamepadEx operator;

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
        //operator = new GamepadEx(gamepad2);

        // You need to created two classes for each mechanism.
        // The Subsystem sets up the hardware and the command runs the code on the hardware
        fss = new FollowerSubsystem(hardwareMap, telemetry);
        LifterSubsystem lifter = new LifterSubsystem(hardwareMap, telemetry);
        ExtenderSubsystem extender = new ExtenderSubsystem(hardwareMap, telemetry);
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, telemetry);
       ClipsSubsystem clips = new ClipsSubsystem(hardwareMap, telemetry);
        OutputSubsystem output = new OutputSubsystem(hardwareMap,telemetry);
        IntakeElbowSubsystem intakeElbow = new IntakeElbowSubsystem(hardwareMap, telemetry);
        OutputRotationSubsystem rotate = new OutputRotationSubsystem(hardwareMap, telemetry);
        IntakeWristSubsystem wrist = new IntakeWristSubsystem(hardwareMap, telemetry);
        register(fss, lifter, extender, intake);
        IntakeSuckCommand intakecmd = new IntakeSuckCommand(intake, telemetry);
        ClipsCommand clipscmd = new ClipsCommand(clips, telemetry);
        OutputRotateCommand rotatecmd = new OutputRotateCommand(rotate, telemetry);
        OutputLiftCommand outputLiftcmd = new OutputLiftCommand(output, telemetry);
        IntakeElbowCommand intakeelbowcmd = new IntakeElbowCommand(intakeElbow, telemetry);
        IntakeWristCommand intakewristcmd = new IntakeWristCommand(wrist, telemetry);
        // this is setting for telemetry to be sent to the driver station
        fss.getFollower().setStartingPose(startPose);
        // registers for the periodic to be called (telemetry)
        // Follower controls robot motion
        FollowerCommand cmd = new FollowerCommand(
                fss,
                telemetry,
                () -> -driver.getLeftY(),
                () -> driver.getLeftX(),
                () -> (driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)));
        fss.setDefaultCommand(cmd);
        driver.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(cmd::toggleSlowMode);
        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(cmd::toggleRobotCentric));
        driver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(cmd::resetImu));

        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand((intake::toggleStateForward)));
        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new InstantCommand((intake::toggleStateBackwards)));

        operator.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand((output::setClip)));
        operator.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand((output::setDown)));
        operator.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand((output::setDump)));
        operator.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand((output::setMax)));

        operator.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand((clips::setOpen)));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand((clips::setClose)));

        operator.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand((rotate::toggleClips)));
        operator.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand((rotate::toggleBucket)));


        LifterDefaultCommand liftCmd = new LifterDefaultCommand(
                lifter,
                telemetry,
                ()-> driver.getRightY());

        lifter.setDefaultCommand(liftCmd);
        ExtenderDefaultCommand extenderCmd = new ExtenderDefaultCommand(
                extender,
                telemetry,
                ()-> driver.getRightX());

        extender.setDefaultCommand(extenderCmd);

        // register for the periodic to be called

        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(()->lifter.setPosition(.5)));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(()->lifter.setPosition(.99)));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(()->lifter.setPosition(.001)));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(()->extender.setPosition(.5)));

    }

}
