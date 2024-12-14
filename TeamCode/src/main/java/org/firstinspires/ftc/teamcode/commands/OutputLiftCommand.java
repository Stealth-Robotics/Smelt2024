package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
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
    private final OutputSubsystem outputlift;
    public OutputState state = OutputState.CLIP;

    public OutputLiftCommand(OutputSubsystem outputSubsystem, Telemetry telemetry) {
        this.outputlift = outputSubsystem;
        addRequirements(outputlift);
    }

    @Override
    public void execute() {

    }
    public void setClip(){outputlift.setPosition(0.5);}

    public void setPose(double pose) {
        outputlift.setPosition(pose);
    }

    public void testPositionUp() {
        double curPose = .5;
        for(double i = curPose; i < 1; i += .005)
        {
            outputlift.setPosition(i);
        }
    }
    public void testPositionDown() {
        double curPose = .5;
        for(double i = curPose; i < 1; i -= .005)
        {
            outputlift.setPosition(i);
        }
    }
}
