package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;

import java.util.function.DoubleSupplier;

public class IntakeSuckCommand extends CommandBase {
    private final IntakeSubsystem intake;
    private final DoubleSupplier axisPower;
    private static final double axisDeadZone = .05;
    private boolean manualControl = false;
    private final Telemetry tel;

    public IntakeSuckCommand(IntakeSubsystem intake, DoubleSupplier intakeSuckHold, Telemetry telemetry){
        this.intake = intake;
        this.axisPower = intakeSuckHold;
        this.tel = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        addRequirements(intake);
    }


    @Override
    public void execute() {
        double power = axisPower.getAsDouble();
        if(power > axisDeadZone || power < -axisDeadZone) {
            tel.addData("Op Trigger:", power);
            intake.setPower(power);
            manualControl = true;
        }
        else if (manualControl)
        {
            manualControl = false;
            intake.setPower(0);
        }
    }

}

