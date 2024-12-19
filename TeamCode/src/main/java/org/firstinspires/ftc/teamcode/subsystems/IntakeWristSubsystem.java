package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.IntakeElbowCommand;
import org.stealthrobotics.library.StealthSubsystem;

public class IntakeWristSubsystem extends StealthSubsystem{
    private static final String INTAKE_WRIST_NAME = "intakewrist";
    private static final double START_POSITION = 0.0;
    private final Servo intakeWrist;

    private boolean isRetracted = false;
    public IntakeWristSubsystem (HardwareMap hardwareMap, Telemetry telemetry){
        intakeWrist = hardwareMap.get(Servo.class, INTAKE_WRIST_NAME);
    }

    public double getPosition(){
        return intakeWrist.getPosition();
    }

    public void setStartPosition(){
        intakeWrist.setPosition(START_POSITION);
    }

    public Command retractCommand(){
        return this.runOnce(()-> intakeWrist.setPosition(START_POSITION));
    }
}
