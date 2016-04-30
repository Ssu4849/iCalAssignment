package edu.onze.calTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import edu.onze.cal.TestCalendar;

public class TestGreatCircleDistanceCalculation {

	private static final double DELTA = 1e-3;
	
	@Test
	public void test_Distance() {
		double dist = TestCalendar.distance(21.3000, 21.2901, -157.8190, -157.8482);
		dist = new BigDecimal(dist).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
		assertEquals(dist, 3.219, DELTA);
	}
}
