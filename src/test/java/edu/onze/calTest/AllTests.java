package edu.onze.calTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EventComponentTest.class, ICalendarObjectTest.class, TestUserInterface.class })
public class AllTests {

}
