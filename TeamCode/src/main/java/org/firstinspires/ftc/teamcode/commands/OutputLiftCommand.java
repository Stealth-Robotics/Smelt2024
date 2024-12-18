package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.OutputSubsystem;

public class OutputLiftCommand extends CommandBase {
    //Servo value for original pos - 0.9289
    //Servo value for clip pick up - 0.6661
    //Servo value for maximum - 0.25
    //Servo value for dumping - 0.7389
    public enum OutputState {
        CLIP,
        DOWN,
        BUCKET
    }

    private final OutputSubsystem outputLift;
    public OutputState state = OutputState.CLIP;

    public OutputLiftCommand(OutputSubsystem outputSubsystem, Telemetry telemetry) {
        this.outputLift = outputSubsystem;
        addRequirements(outputLift);
    }

    @Override
    public void execute() {
        // Empty for now
    }

    public void setClip(){outputLift.setPosition(0.6661);}
    public void setDown(){outputLift.setPosition(0.9289);}
    public void setDump(){outputLift.setPosition(0.7389);}
    public void setMax(){outputLift.setPosition(0.25);}

    public void setPose(double pose) {
        outputLift.setPosition(pose);
    }

}
