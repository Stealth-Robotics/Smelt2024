package org.firstinspires.ftc.teamcode.Paths;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants;
import org.stealthrobotics.library.Alliance;

public class SpecimenScorePath extends PathManager{


    private static final Pose DEFAULT_START_POINT_BLUE = new Pose(
            9.000, 66.000, Math.toRadians(0));
    private static final Pose DEFAULT_START_POINT_RED = DEFAULT_START_POINT_BLUE;

    /**
     * Sample path using Bezier curves.
     *
     */
    public SpecimenScorePath() {
        super(DEFAULT_START_POINT_BLUE);

        if (Alliance.isRed()) {
            startPose = DEFAULT_START_POINT_RED;
        }

        this.bluePathChain = buildBluePathChain();
        this.redPathChain = buildRedPathChain();
        // chop up the full path chain into separate path chains
        // so that other operations (like lifter movements) can be done at each segment
        createSegment(0);
        buildSegments();
    }

    /**
     * the blue path chain
     * @return returns the path for when running as blue Alliance
     */
    protected PathChain buildBluePathChain() {
        PathBuilder builder = new PathBuilder();

        return builder
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(9.000, 66.000, Point.CARTESIAN),
                                new Point(36.400, 66.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    /**
     * Builds the red path chain
     * @return Returns the Red PathChain for when on the Red Alliance
     */
    protected PathChain buildRedPathChain() {
        // Can just invert the blue chain to get the red chain
        return buildBluePathChain();
    }
}
