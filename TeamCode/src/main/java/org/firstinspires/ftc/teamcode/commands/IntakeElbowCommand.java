package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputSubsystem;

//leftintakedump pos: 0.036
//leftintakepickup pos: 0.816
// leftintake up pos: 0.49
// rightintake dump pos: 0.982
//rightintakepickup pos: 0.21
//rightintake middle pos: 0.5

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
