package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;

import java.util.function.DoubleSupplier;

public class ExtenderDefaultCommand extends CommandBase {

    private final ExtenderSubsystem extender;
    private final MultipleTelemetry telemetryA;
    private final DoubleSupplier extendAxis;
    private boolean manualControl = false;
    private static final double axisDeadZone = 0.15;

    public ExtenderDefaultCommand(ExtenderSubsystem extender, Telemetry telemetry, DoubleSupplier extendAxis) {
        addRequirements(extender);
        this.extender = extender;
        this.telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        this.extendAxis = extendAxis;
    }

    @Override
    public void execute() {

        double power = extendAxis.getAsDouble();

        if(power > axisDeadZone || power < -axisDeadZone) {
            extender.stopRunTo();
            extender.getMotors().setRunMode(Motor.RunMode.RawPower);
            manualControl = true;
            extender.setPower(power); //Manual control
            telemetryA.addData("Manual Extend", power);
        }else if (manualControl) {

            extender.setPower(0);
            manualControl = false;
        }
    }

}
