/**
 * 
 */
package org.weatherill.roadwatch.location;

import java.math.BigDecimal;

import junit.framework.TestCase;

/**
 * Tests for {@link Location}
 * 
 * @author william
 *
 */
public class LocationTest extends TestCase
{	
	/** Made up of lat - 1 and long - 10 */
	private Location test01 = null;
	
	/** The expected output from {@link LocationTest#test01#toString()}*/
	private String test01String = null;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		test01 = LocationInstances.INSTANCE.getLocation001();
		test01String = "(1, 10)";
	}
	
	/**
	 * Test method for {@link Location#Location(BigDecimal, BigDecimal)}
	 */
	public void testLocation()
	{
		// null longitude
		try
		{
			new Location(BigDecimal.ONE, null);
			fail("Since the longitude passed to Location's constructor was null it should have thrown an IllegalArgumentException.");
		}
		catch(IllegalArgumentException iae)
		{
			// we're expecting an exception
		}
		
		// null latitude
		try
		{
			new Location(null, BigDecimal.ONE);
			fail("Since the latitude passed to Location's constructor was null it should have thrown an IllegalArgumentException.");
		}
		catch(IllegalArgumentException iae)
		{
			// we're expecting an exception
		}
		
		// null latitude & longitude
		try
		{
			new Location(null, null);
			fail("Since both the latitude and longitude passed to Location's constructor were null it should have thrown an IllegalArgumentException.");
		}
		catch(IllegalArgumentException iae)
		{
			// we're expecting an exception
		}
	}

	/**
	 * Test method for {@link org.weatherill.roadwatch.location.Location#toString()}.
	 */
	public void testToString()
	{
		assertEquals("Make sure that the toString method of test01 returns as expected", test01String, test01.toString());
	}

}
