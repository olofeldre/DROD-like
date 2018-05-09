public class Angle {
    public static double getAngle(Direction direction) {
        double angle = 0;
        switch(direction) {
            case RIGHT: angle = 0; break;
            case UP: angle = 90; break;
            case LEFT: angle = 180; break;
            case DOWN: angle = 270; break;
            case UPRIGHT: angle = 45; break;
            case UPLEFT: angle = 135; break;
            case DOWNLEFT: angle = 225; break;
            case DOWNRIGHT: angle = 315; break;
        }

        return angle;
    }
}
