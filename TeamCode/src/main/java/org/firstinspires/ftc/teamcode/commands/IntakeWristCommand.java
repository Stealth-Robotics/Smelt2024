package org.firstinspires.ftc.teamcode.commands;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;

// all positions are 0
public class IntakeWristCommand extends CommandBase {
        private final IntakeWristSubsystem intakeWrist;

        public IntakeWristCommand(IntakeWristSubsystem intakeWristSubsystem, Telemetry telemetry) {
            this.intakeWrist = intakeWristSubsystem;
            addRequirements(intakeWrist);
        }
    @Override
    public void execute() {

    }


}

