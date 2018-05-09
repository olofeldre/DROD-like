import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class AngleTest {
    @Test
    public void getAngleShouldReturnZeroWhenRight() {
        assertThat((int) Angle.getAngle(Direction.RIGHT), equalTo(0));
    }

    @Test
    public void getAngleShouldReturn90WhenUp() {
        assertThat((int) Angle.getAngle(Direction.UP), equalTo(90));
    }

    @Test
    public void getAngleShouldReturn180WhenLeft() {
        assertThat((int) Angle.getAngle(Direction.LEFT), equalTo(180));
    }

    @Test
    public void getAngleShouldReturn270WhenDown() {
        assertThat((int) Angle.getAngle(Direction.DOWN), equalTo(270));
    }

    @Test
    public void getAngleShouldReturn45WhenUpRight() {
        assertThat((int) Angle.getAngle(Direction.UPRIGHT), equalTo(45));
    }

    @Test
    public void getAngleShouldReturn135WhenUpLeft() {
        assertThat((int) Angle.getAngle(Direction.UPLEFT), equalTo(135));
    }

    @Test
    public void getAngleShouldReturn225WhenDownLeft() {
        assertThat((int) Angle.getAngle(Direction.DOWNLEFT), equalTo(225));
    }

    @Test
    public void getAngleShouldReturn315WhenDownRight() {
        assertThat((int) Angle.getAngle(Direction.DOWNRIGHT), equalTo(315));
    }
}