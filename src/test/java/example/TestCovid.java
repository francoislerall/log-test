package example;

import org.junit.Test;
import org.junit.Assert;


public class TestCovid {
    @Test
    public void testSetCases() {
        Integer testCase = 7;
        Covid covidManager = new Covid();
        covidManager.setCases(testCase);
        Assert.assertEquals(testCase, covidManager.getCases());
    }
}
