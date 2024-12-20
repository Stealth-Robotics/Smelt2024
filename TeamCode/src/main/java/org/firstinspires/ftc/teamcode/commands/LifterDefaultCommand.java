package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class LifterDefaultCommand extends CommandBase {

    private final LifterSubsystem lifter;

    private final DoubleSupplier axis;
    private final BooleanSupplier liftBot;
    private boolean manualControl = false;
    private static final double axisDeadZone = 0.08;
    private static final double CLIMB_POWER = -1;

    public LifterDefaultCommand(
            LifterSubsystem lifter,
            DoubleSupplier axis,
            BooleanSupplier liftBot) {
        addRequirements(lifter);
        this.lifter = lifter;
        this.axis = axis;
        this.liftBot = liftBot;
    }

    @Override
    public void execute() {
        double power = axis.getAsDouble();
        if (liftBot.getAsBoolean()) {
            manualControl = false;
            lifter.setPower(CLIMB_POWER);
            telemetry.addData("liftBot", CLIMB_POWER);
        }
        else if(power > axisDeadZone || power < -axisDeadZone) {
            telemetry.addData("Power", power);
            lifter.stopRunTo();
            lifter.getMotors().setRunMode(Motor.RunMode.RawPower);
            manualControl = true;
            lifter.setPower(power);

        }else if (manualControl) {
            // holds the lift here
            lifter.setPower(LifterSubsystem.HOLD_POWER);
            manualControl = false;
        }
    }

}
