package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.MathFunctions;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;

import java.util.function.DoubleSupplier;

public class ExtenderDefaultCommand extends CommandBase {

    private final ExtenderSubsystem extender;

    private final DoubleSupplier extendAxis;
    private boolean manualControl = false;
    private static final double axisDeadZone = 0.3;

    public ExtenderDefaultCommand(ExtenderSubsystem extender, DoubleSupplier extendAxis) {
        addRequirements(extender);
        this.extender = extender;
        this.extendAxis = extendAxis;
    }

    @Override
    public void execute() {

        double power = extendAxis.getAsDouble();
        power = MathFunctions.clamp(power, -1, 1);
        if(power > axisDeadZone || power < -axisDeadZone) {

            extender.stopRunTo();
            extender.getMotors().setRunMode(Motor.RunMode.RawPower);
            manualControl = true;
            extender.setPower(power);

        }else if (manualControl) {
            // holds the lift here
            extender.setPower(ExtenderSubsystem.HOLD_POWER);
            manualControl = false;
        }
    }

}
