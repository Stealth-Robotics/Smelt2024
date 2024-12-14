package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;

public class ClipsCommand extends CommandBase {
    private enum ClipsState {
        STOPPED,
        OPENING,
        CLOSING
    }
    private final ClipsSubsystem clips;
    private ClipsState state = ClipsState.STOPPED;

    public ClipsCommand(ClipsSubsystem clips, Telemetry telemetry){
        this.clips = clips;
        addRequirements(clips);
    }
    @Override
    public void execute(){

    }
    public void setOpen(){clips.setPower(0.6);}
    public void setClose(){clips.setPower(0);}

    public void toggleOpen(){
        switch (state) {

            default:
                state = ClipsState.OPENING;
                setOpen();
        }

    }
    public void toggleClose(){
        switch (state) {

            default:
                state = ClipsState.CLOSING;
                setClose();
        }

    }
}

