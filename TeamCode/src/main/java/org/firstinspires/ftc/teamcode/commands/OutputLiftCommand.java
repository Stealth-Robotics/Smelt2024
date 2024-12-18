package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.OutputSubsystem;

public class OutputLiftCommand extends CommandBase {


    private final OutputSubsystem outputLift;


    public OutputLiftCommand(OutputSubsystem outputSubsystem, Telemetry telemetry) {
        this.outputLift = outputSubsystem;
        addRequirements(outputLift);
    }

    @Override
    public void execute() {
        // delete this class if we don't find a specific need for a command
    }

}
