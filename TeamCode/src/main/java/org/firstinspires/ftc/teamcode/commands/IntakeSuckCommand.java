package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeSuckCommand extends CommandBase {
    private final IntakeSubsystem intake;

    public IntakeSuckCommand(IntakeSubsystem intake, Telemetry telemetry){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setIntake();
    }
}

