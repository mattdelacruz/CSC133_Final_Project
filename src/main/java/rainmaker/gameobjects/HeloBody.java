package rainmaker.gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HeloBody extends GameObject {
        private static final int BLADE_BODY_WIDTH = 45;
        private static final int BLADE_BODY_HEIGHT = 10;
        private static final int PILOT_SEAT_RADIUS = 20;
        private static final int PILOT_SEAT_START_ANGLE = 135;
        private static final int PILOT_SEAT_LENGTH = 270;
        private static final int PILOT_SEAT_ANGLE = 70;
        private static final ArcType PILOT_SEAT_TYPE = ArcType.OPEN;
        private static final Color HELO_BODY_FILL = Color.BLACK;
        private static final Color HELO_BODY_STROKE = Color.RED;
        private static final int SKID_HEIGHT = 50;
        private static final int SKID_WIDTH = 5;
        private static final int TAIL_LENGTH = 50;
        private static final int TAIL_WIDTH = 3;
        private static final int ROTOR_SIZE = 10;
        private static final double SKID_ATTACHMENT_HEIGHT = 2;

        private Rectangle bladeBody;
        private Arc pilotSeat;
        private Rectangle leftSkid;
        private Rectangle rightSkid;
        private Group skids;
        private Rectangle leftSkidAttachTop;
        private Rectangle leftSkidAttachBottom;
        private Rectangle rightSkidAttachTop;
        private Rectangle rightSkidAttachBottom;
        private Group skidAttachments;
        private Line tailBase;
        private Group tail;
        private Rectangle rotorBlock;
        private Rectangle rotorBlade;
        private Rectangle rotorAttach;
        private Rectangle rotorBody;
        private Group rotor;

        private Point2D pos;

        HeloBody(Point2D p) {
                pos = p;
                createBladeBody();
                createPilotSeat();
                createSkids();
                createTail();
                createRotor();
                getChildren().addAll(bladeBody, pilotSeat, skids, tail, rotor);
        }

        public Point2D getPivotPoint() {
                return new Point2D(bladeBody.getX() + (bladeBody.getWidth() / 2),
                                bladeBody.getY() + (bladeBody.getHeight() / 2));

        }

        private void createBladeBody() {
                bladeBody = new Rectangle(pos.getX() - BLADE_BODY_WIDTH / 2,
                                pos.getY() - BLADE_BODY_HEIGHT / 2,
                                BLADE_BODY_WIDTH, BLADE_BODY_HEIGHT);
                bladeBody.setFill(HELO_BODY_FILL);
                bladeBody.setStroke(HELO_BODY_STROKE);
        }

        private void createPilotSeat() {
                pilotSeat = new Arc();
                pilotSeat.setCenterX(bladeBody.getBoundsInLocal().getCenterX());
                pilotSeat.setCenterY(bladeBody.getBoundsInLocal().getCenterY() +
                                PILOT_SEAT_RADIUS);
                pilotSeat.setRadiusX(PILOT_SEAT_RADIUS);
                pilotSeat.setRadiusY(PILOT_SEAT_RADIUS);
                pilotSeat.setStartAngle(PILOT_SEAT_START_ANGLE);
                pilotSeat.setLength(PILOT_SEAT_LENGTH);
                pilotSeat.setType(PILOT_SEAT_TYPE);
                pilotSeat.setFill(HELO_BODY_FILL);
                pilotSeat.setStroke(HELO_BODY_STROKE);
        }

        private void createSkids() {
                // 10, 15 is ad hoc
                skids = new Group();
                double skidY = (pilotSeat.getCenterY() +
                                pilotSeat.getRadiusY()) - SKID_HEIGHT;
                double skidXOnCurve = (pilotSeat.getRadiusX() *
                                Math.sin(Math.toRadians(PILOT_SEAT_ANGLE)));

                leftSkid = heliPartGenerator(new Point2D(pilotSeat.getCenterX() -
                                skidXOnCurve - 15, skidY),
                                SKID_WIDTH,
                                SKID_HEIGHT);
                rightSkid = heliPartGenerator(new Point2D(pilotSeat.getCenterX() +
                                skidXOnCurve + 10, skidY),
                                SKID_WIDTH,
                                SKID_HEIGHT);
                createSkidAttachments();
                skids.getChildren().addAll(leftSkid, rightSkid, skidAttachments);
        }

        private void createSkidAttachments() {
                skidAttachments = new Group();
                double bottomSkidY = bladeBody.getY() +
                                (bladeBody.getHeight() / 2) -
                                SKID_ATTACHMENT_HEIGHT / 2;
                double topSkidX = (pilotSeat.getRadiusX() *
                                Math.sin(Math.toRadians(PILOT_SEAT_ANGLE)));
                double topSkidY = pilotSeat.getCenterY() + (pilotSeat.getRadiusY() *
                                Math.cos(Math.toRadians(PILOT_SEAT_ANGLE)));
                double leftSkidWidth = leftSkid.getX() + leftSkid.getWidth();
                double bladeBodyEndX = bladeBody.getX() + bladeBody.getWidth();
                double rightTopSkidPosX = pilotSeat.getCenterX() + topSkidX;
                double leftTopSkidAttachPosX = pilotSeat.getCenterX() - topSkidX;

                leftSkidAttachTop = heliPartGenerator(
                                new Point2D(leftTopSkidAttachPosX - (leftTopSkidAttachPosX -
                                                (leftSkidWidth)), topSkidY),
                                leftTopSkidAttachPosX - (leftSkidWidth),
                                SKID_ATTACHMENT_HEIGHT);

                leftSkidAttachBottom = heliPartGenerator(new Point2D(
                                leftSkidWidth, bottomSkidY),
                                bladeBody.getX() - (leftSkidWidth),
                                SKID_ATTACHMENT_HEIGHT);

                rightSkidAttachTop = heliPartGenerator(
                                new Point2D(rightTopSkidPosX, topSkidY),
                                rightSkid.getX() - (rightTopSkidPosX),
                                SKID_ATTACHMENT_HEIGHT);

                rightSkidAttachBottom = heliPartGenerator(new Point2D(
                                bladeBodyEndX, bottomSkidY),
                                rightSkid.getX() - bladeBodyEndX,
                                SKID_ATTACHMENT_HEIGHT);

                skidAttachments.getChildren().addAll(
                                rightSkidAttachTop, leftSkidAttachTop,
                                leftSkidAttachBottom, rightSkidAttachBottom);
        }

        private void createTail() {
                Group zigZag = new Group();
                tail = new Group();
                double zigZagXStart = (bladeBody.getX() + bladeBody.getWidth() / 2);
                double zigZagLength = bladeBody.getY() - (TAIL_LENGTH);
                double zigZagHalfLength = bladeBody.getY() - (TAIL_LENGTH / 2);

                tailBase = heliLineGenerator(
                                new Point2D((bladeBody.getX() +
                                                bladeBody.getWidth() / 2), bladeBody.getY()),
                                new Point2D(bladeBody.getX() + bladeBody.getWidth() / 2,
                                                bladeBody.getY() - TAIL_LENGTH));
                tailBase.setStrokeWidth(TAIL_WIDTH);
                tailBase.setStroke(HELO_BODY_FILL);

                // 7 is ad hoc

                Line zigZagLeftTop = heliLineGenerator(
                                new Point2D(zigZagXStart - ROTOR_SIZE,
                                                bladeBody.getY()),
                                new Point2D(zigZagXStart + 7,
                                                zigZagHalfLength));

                Line zigZagRightTop = heliLineGenerator(
                                new Point2D(zigZagXStart + ROTOR_SIZE,
                                                bladeBody.getY()),
                                new Point2D(zigZagXStart - 7,
                                                zigZagHalfLength));

                Line zigZagLeftBottom = heliLineGenerator(
                                new Point2D(zigZagXStart - 7,
                                                zigZagRightTop.getEndY()),
                                new Point2D(zigZagXStart + ROTOR_SIZE / 2,
                                                zigZagLength));

                Line zigZagRightBottom = heliLineGenerator(
                                new Point2D(zigZagXStart + 7,
                                                zigZagRightTop.getEndY()),
                                new Point2D(zigZagXStart - ROTOR_SIZE / 2,
                                                zigZagLength));

                Line leftTailBase = heliLineGenerator(
                                new Point2D(zigZagXStart - ROTOR_SIZE,
                                                bladeBody.getY()),
                                new Point2D(zigZagXStart - ROTOR_SIZE / 2,
                                                zigZagLength));

                Line rightTailBase = heliLineGenerator(
                                new Point2D(zigZagXStart + ROTOR_SIZE,
                                                bladeBody.getY()),
                                new Point2D(zigZagXStart + ROTOR_SIZE / 2,
                                                zigZagLength));

                zigZag.getChildren().addAll(zigZagLeftTop, zigZagRightTop,
                                zigZagLeftBottom, zigZagRightBottom,
                                leftTailBase, rightTailBase);
                tail.getChildren().addAll(zigZag, tailBase);
        }

        private void createRotor() {

                rotor = new Group();

                rotorBlock = heliPartGenerator(new Point2D(
                                (bladeBody.getX() + bladeBody.getWidth() / 2) - ROTOR_SIZE / 2,
                                tailBase.getEndY() - ROTOR_SIZE),
                                ROTOR_SIZE, ROTOR_SIZE);

                double rotorBlockEndX = rotorBlock.getX() + ROTOR_SIZE;

                rotorBody = heliPartGenerator(new Point2D(
                                (rotorBlock.getX() - ROTOR_SIZE), rotorBlock.getY()),
                                ROTOR_SIZE, ROTOR_SIZE - 2);

                rotorBlade = heliPartGenerator(
                                new Point2D((rotorBlockEndX + (ROTOR_SIZE / 2)),
                                                rotorBlock.getY() - (ROTOR_SIZE / 2)),
                                ROTOR_SIZE / 3,
                                ROTOR_SIZE * 2);
                rotorAttach = heliPartGenerator(
                                new Point2D(rotorBlockEndX,
                                                rotorBlock.getY() + (ROTOR_SIZE / 2)),
                                rotorBlade.getX() - (rotorBlockEndX), ROTOR_SIZE / 5);

                rotor.getChildren().addAll(rotorBlock, rotorBody,
                                rotorBlade, rotorAttach);
        }

        private Line heliLineGenerator(Point2D start, Point2D end) {
                Line l = new Line(start.getX(), start.getY(), end.getX(), end.getY());
                l.setStroke(HELO_BODY_STROKE);
                return l;
        }

        private Rectangle heliPartGenerator(Point2D pos, double width,
                        double height) {
                Rectangle s = new Rectangle(pos.getX(), pos.getY(), width, height);
                s.setFill(HELO_BODY_FILL);
                s.setStroke(HELO_BODY_STROKE);
                return s;
        }
}