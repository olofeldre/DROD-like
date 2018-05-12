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

    public static Direction getDirection(double angle) {
        Direction direction = Direction.LEFT;
        switch((int)angle) {
            case 0: direction = Direction.RIGHT; break;
            case 90: direction = Direction.UP; break;
            case 180: direction = Direction.LEFT; break;
            case 270: direction = Direction.DOWN; break;
            case 45: direction = Direction.UPRIGHT; break;
            case 135: direction = Direction.UPLEFT; break;
            case 225: direction = Direction.DOWNLEFT; break;
            case 315: direction = Direction.DOWNRIGHT; break;
        }

        return direction;
    }

}
