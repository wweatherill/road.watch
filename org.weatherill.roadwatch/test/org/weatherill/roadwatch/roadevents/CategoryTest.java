package org.weatherill.roadwatch.roadevents;

import junit.framework.TestCase;

import org.weatherill.roadwatch.roadevents.RoadEvent.Category;

/**
 * Tests for {@link Category}
 * 
 * @author william
 *
 */
public class CategoryTest extends TestCase
{
	/** This will stay null */
	private String test01 = null;
	
	/** an unknown string - i.e. does not map to a category  */
	private String test02 = null;
	
	/** a known string - i.e. maps to a category */
	private String test03 = null;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		test01 = null;
		test02 = CategoryTest.class.getName();
		test03 = Category.ROADWORKS.getValue();
	}

	/**
	 * Test method for {@link Category#getCategory(String)}.
	 */
	public void testGetCategory()
	{
		try
		{
			Category.getCategory(test01);
			fail("Since the string lookup was null the method should have thrown a NullPointerException.");
		}
		catch(NullPointerException npe)
		{
			// we're expecting an exception
		}
		
		try
		{
			Category.getCategory(test02);
			fail("Since the string lookup was not known the method should have thrown an IllegalArgumentException.");
		}
		catch(IllegalArgumentException iae)
		{
			// we're expecting an exception
		}
		
		try
		{
			Category.getCategory(test03);
			
		}
		catch(Throwable t)
		{
			fail(String.format("Since the string lookup was known the method should not have thrown anything. - %s", t.getMessage()));
			t.printStackTrace();
		}
	}

}
