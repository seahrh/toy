package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockAngle {
	private static final Logger log = LoggerFactory.getLogger(ClockAngle.class);

	private ClockAngle() {
		// Not meant to be instantiated
	}

	/**
	 * Given a time, calculate the angle between the hour and minute hands.
	 * 
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static int angle(int hour, int minute) {
		if (hour < 0) {
			log.error("hour must not be less than zero");
			throw new IllegalArgumentException();
		}
		if (minute < 0) {
			log.error("minute must not be less than zero");
			throw new IllegalArgumentException();
		}
		// Formula gives a floating point result,
		// then cast the value to int
		int hangle = (int) (hour % 12 / 12D * 360);
		int mangle = (int) (minute % 60 / 60D * 360);
		int ret = Math.abs(hangle - mangle);
		log.info("hour [{}] minute [{}] hangle [{}] mangle [{}] ret [{}]",
				hour, minute, hangle, mangle, ret);
		return ret;
	}

}
