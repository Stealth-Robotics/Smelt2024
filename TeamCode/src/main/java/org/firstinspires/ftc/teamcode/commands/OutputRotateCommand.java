package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class OutputRotateCommand extends CommandBase {
    private final OutputRotationSubsystem outputrotate;

    public OutputRotateCommand(OutputRotationSubsystem outputrotate, Telemetry telemetry){
        this.outputrotate = outputrotate;
        addRequirements(outputrotate);
    }
    @Override
    public void execute(){
        // delete this class if we don't find a specific need for a command
    }

}
