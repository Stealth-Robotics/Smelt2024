package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;

public class ClipsCommand extends CommandBase {

    private final ClipsSubsystem clips;


    public ClipsCommand(ClipsSubsystem clips, Telemetry telemetry){
        this.clips = clips;
        addRequirements(clips);
    }

    public Command setOpen() {
        return new InstantCommand(clips::setOpen);
    }

    public Command setClose() {
        return new InstantCommand(clips::setClose);
    }

}

