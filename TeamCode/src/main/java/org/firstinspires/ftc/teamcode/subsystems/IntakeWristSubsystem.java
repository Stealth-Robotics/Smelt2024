package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.IntakeElbowCommand;
import org.stealthrobotics.library.StealthSubsystem;

public class IntakeWristSubsystem extends StealthSubsystem{
    private static final String INTAKE_WRIST_NAME = "intakewrist";
    private final Servo intakeWrist;

    public IntakeWristSubsystem (HardwareMap hardwareMap, Telemetry telemetry){
        intakeWrist = hardwareMap.get(Servo.class, INTAKE_WRIST_NAME);
    }

    public Command startSetPosition(double position) {
        return this.runOnce(()->intakeWrist.setPosition(position));
    }


    public double getPosition(){
        return intakeWrist.getPosition();
    }
}
