package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.IntakeElbowCommand;
import org.stealthrobotics.library.StealthSubsystem;

public class IntakeElbowSubsystem extends StealthSubsystem {
    private static final String INTAKE_ELBOW_LEFT_NAME = "leftintakeelbow";
    private static final String INTAKE_ELBOW_RIGHT_NAME = "rightintakeelbow";
    private final Servo intakeElbowLeft;
    private final Servo intakeElbowRight;
    public IntakeElbowSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        intakeElbowLeft = hardwareMap.get(Servo.class, INTAKE_ELBOW_LEFT_NAME);
        intakeElbowRight = hardwareMap.get(Servo.class, INTAKE_ELBOW_RIGHT_NAME);
    }
}
