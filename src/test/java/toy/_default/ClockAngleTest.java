package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ClockAngleTest {

	@Test(dataProvider = "data")
	public void angleTest(int hour, int minute, int expected, String description) {
		int actual = ClockAngle.angle(hour, minute);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] data() {
		return new Object[][] { { 3, 27, 72, "3:27 pm" },
				{ 0, 0, 0, "00:00 midnight" },
				{ 23, 59, 24, "23:59 hrs" },
				{ 3, 15, 0, "3:15pm" },
				{ 9, 50, 30, "09:50" } };
	}

}
