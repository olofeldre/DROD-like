import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Movable {
    protected MovableType type;
    protected int x;
    protected int y;
    protected boolean solid = true;
    protected Direction direction = Direction.RIGHT;

    public Movable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected MovableType getType() {
        return type;
    }

    public boolean move(Direction direction, Map map) {
        Point newPos = getNewPosition(direction);

        if(tryMoveTo(newPos.x, newPos.y, map)) {
            return true;
        }
        return false;
    }

    protected Point getNewPosition(Direction direction) {
        int newX = x;
        int newY = y;

        switch(direction) {
            case LEFT: newX -= 1; break;
            case RIGHT: newX += 1; break;
            case UP: newY += 1; break;
            case DOWN: newY -= 1; break;
            case UPLEFT: newX -= 1; newY += 1; break;
            case UPRIGHT: newX += 1; newY += 1; break;
            case DOWNLEFT: newX -= 1; newY -= 1; break;
            case DOWNRIGHT: newX += 1; newY -= 1; break;
        }

        return new Point(newX, newY);
    }

    private Direction getDirectionFromMovement(int x, int y) {
        Direction direction = Direction.RIGHT;

        if(x == 1) {
            if(y == 1) {
                direction = Direction.UPRIGHT;
            }
            else if(y == -1) {
                direction = Direction.DOWNRIGHT;
            }
            else if(y == 0) {
                direction = Direction.RIGHT;
            }
        }
        else if(x == -1) {
            if(y == 1) {
                direction = Direction.UPLEFT;
            }
            else if(y == -1) {
                direction = Direction.DOWNLEFT;
            }
            else if(y == 0) {
                direction = Direction.LEFT;
            }
        }
        else {
            if(y == 1) {
                direction = Direction.UP;
            }
            else if(y == -1) {
                direction = Direction.DOWN;
            }
        }

        return direction;
    }

	/**
	 * Attempts to move the movable to the specified tile on the map provided.
	 * If successful, the method returns true, otherwise false.
	 * @param x new X of tile to move to.
	 * @param y new Y of tile to move to.
	 * @param map the map of the destination.
	 * @return true if move was successful.
	 */
    public boolean tryMoveTo(int x, int y, Map map) {

        this.direction = getDirectionFromMovement(x - this.x, y - this.y);

        if(tileFree(x, y, map) || !solid) {
            setPosition(x, y, map);
            return true;
        }
        else {
            if(type == MovableType.ROACH) {
                Movable movable = map.getMovable(x, y);
                if(movable != null && movable.type == MovableType.SWORD && !map.getTile(x, y).isWall()) {
                    map.removeMovable(this.x, this.y);
                    Enemy enemy = (Enemy)this;
                    enemy.alive = false;

                    return false;
                }
            }
        }
        return false;
    }

    public void setPosition(int x, int y, Map map) {

        map.removeMovable(this.x, this.y);
        this.x = x;
        this.y = y;

        map.setMovable(x, y, this);
    }

    protected boolean tileFree(int x, int y, Map map) {
        if (map.getTile(x, y).isWall() || map.getMovable(x, y) != null)
        {
            return false;
        }

        return true;
    }

    public abstract void display(Graphics graphics, int x, int y);

}
