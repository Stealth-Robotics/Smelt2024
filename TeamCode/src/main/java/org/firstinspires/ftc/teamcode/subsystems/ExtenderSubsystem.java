package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

/**
 * Example usage of a single DC motor with PIDF control.
 */
@Config
public class ExtenderSubsystem extends StealthSubsystem {
    private static final String LEFT_ARM = "leftarm";
    private static final String RIGHT_ARM = "rightarm";

    // Adjust these values for your arm. These will need to change
    // based on arm weight and total range of the arm
    private static final double KP = 0.007;
    private static final double KI = 0.00;
    private static final double KD = 0.0;
    private static final double KF = 0.00;

    // This should be the maximum encoder extension of the arm(s)
    private static final double MAX_HEIGHT = 2170;

    // Acceptable position error to be considered at target location
    private static final double TOLERANCE = 10.0;
    private static final double MAX_SPEED = 1;
    public static final double HOLD_POWER = 0;
    private Boolean usePidf = false;
    private final MotorEx armRight;
    private final MotorEx armLeft;
    private final MotorGroup motors;

    // PIDF to control arm movement keeps the arm from overshooting etc.
    private final PIDFController pidf = new PIDFController(KP, KI, KD, KF);

    public ExtenderSubsystem(HardwareMap hardwareMap) {


        armRight = new MotorEx(hardwareMap, LEFT_ARM);
        armLeft = new MotorEx(hardwareMap, RIGHT_ARM);
        motors = new MotorGroup(armLeft, armRight);
        pidf.setTolerance(TOLERANCE);

        armRight.setInverted(true);
        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motors.setRunMode(Motor.RunMode.RawPower);

        resetEncoder();

    }
    
    /**
     * Called by the scheduler periodically if the subsystem is registered.
     */
    @Override
    public void periodic() {
        if (usePidf) {
            double power = pidf.calculate(getPosition());
            motors.set(power * MAX_SPEED);

            if (pidf.atSetPoint()) {
                motors.set(0);
                usePidf = false;
            }
        }

        telemetry.addData("Extend Position:", getPosition());
        telemetry.addData("Extender RunTo:", usePidf);
    }

    /**
     * Sets the power of the extend motor
     * @param power power to set
     */
    public void setPower(double power) {
        motors.set(power);
    }

    /**
     * Sets the position of the extend motor in % of max range
     * 1 = full up .5 = half up. should be used with limit switches
     * @param position position in % of max range
     */
    public void setPosition(double position) {
        pidf.setSetPoint(position * MAX_HEIGHT);
        usePidf = true;
    }

    /**
     * Starts the motor moving but does not wait for it to reach the desired position
     * @param position % of the arm range
     * @return A Command that can be run
     */
    public Command startSetPositionCommand(double position) {
        return this.runOnce(()-> setPosition(position));
    }

    /**
     * After calling setPosition this can be called to wait for the position to be reached
     * @param timeout waiting time before giving up on the desired position
     * @return command to wait for the position to be reached
     */
    public Command endSetPositionCommand(long timeout) {
        long endTime = System.currentTimeMillis() + timeout;
        return new WaitUntilCommand(()-> !usePidf || System.currentTimeMillis() >= endTime);
    }

    /**
     * Sets the position of the extend motor in encoder ticks
     * @param position position in encoder ticks
     */
    public Command setPositionCommand(double position) {
        return this.runOnce(()-> setPosition(position))
                .andThen(new WaitUntilCommand(()-> !usePidf));
    }

    /**
     * Sets the position of the extend motor in encoder ticks with a timeout
     * @param position % of the arm range
     * @param timeout number of ms to wait for the position to be reached
     * @return command to wait for the position to be reached
     */
    public Command setPositionCommand(double position, long timeout) {
        long endTime = System.currentTimeMillis() + timeout;
        return this.runOnce(()-> setPosition(position))
                .andThen(new WaitUntilCommand(()-> !usePidf || System.currentTimeMillis() >= endTime));
    }

    /**
     * Stops the run to position
     */
    public void stopRunTo() {
        usePidf = false;
    }

    /**
     * Returns the current position of the extend motor
     * @return current position
     */
    public int getPosition() {
        return armRight.getCurrentPosition();
    }

    /**
     * Resets the encoder of the extend motor
     */
    public void resetEncoder() {
        armRight.resetEncoder();
        armLeft.resetEncoder();
        motors.resetEncoder();
        motors.setRunMode(Motor.RunMode.RawPower);
    }

    /**
     * Returns the extend motor
     * @return extend motor
     */
    public MotorGroup getMotors()
    {
        return motors;
    }
}
