package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;

public class IntakeElbowCommand extends CommandBase {
    private final IntakeElbowSubsystem intakeelbow;
    public IntakeElbowCommand (IntakeElbowSubsystem intakeElbowSubsystem, Telemetry telemetry){
        this.intakeelbow = intakeElbowSubsystem;
        addRequirements(intakeelbow);
    }

    @Override
    public void execute() {

    }


}
