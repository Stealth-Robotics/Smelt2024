package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class OutputRotateCommand extends CommandBase {
    private enum rotationstate {
        BUCKET,
        INTAKEREADY,
        CLIPS
    }
    private final OutputRotationSubsystem outputrotate;
    private rotationstate state = rotationstate.INTAKEREADY;
    public OutputRotateCommand(OutputRotationSubsystem outputrotate, Telemetry telemetry){
        this.outputrotate = outputrotate;
        addRequirements(outputrotate);
    }
    @Override
    public void execute(){

    }
    public void setIntakeReady(){outputrotate.setPosition(0);}
    public void setClips(){outputrotate.setPosition(0);}
    public void setBucket(){outputrotate.setPosition(0);}

    public void toggleClips() {
        switch (state) {
            case INTAKEREADY:
                state = rotationstate.CLIPS;
                setClips();
                break;

            default:
                state = rotationstate.INTAKEREADY;
                setIntakeReady();
        }
    }
    public void toggleBucket(){
        switch(state){
            case INTAKEREADY:
                state = rotationstate.BUCKET;
                setBucket();
                break;

            default:
                state = rotationstate.INTAKEREADY;
                setIntakeReady();



        }

    }

}
