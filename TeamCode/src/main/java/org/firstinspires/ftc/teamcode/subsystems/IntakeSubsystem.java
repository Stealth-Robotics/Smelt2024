package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class IntakeSubsystem extends StealthSubsystem {
    private static final String INTAKE_NAME = "intakesuck";
    private final CRServo intakesuck;

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        intakesuck = hardwareMap.get(CRServo.class, INTAKE_NAME);

    }
    public void setPower(double speed){
        intakesuck.setPower(speed);
    }
    public double getPosition() {
        return intakesuck.getPower();
    }
}
