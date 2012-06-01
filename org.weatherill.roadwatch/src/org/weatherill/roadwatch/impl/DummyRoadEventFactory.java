package org.weatherill.roadwatch.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.weatherill.roadwatch.IRoadWatch;
import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.roadevents.RoadEvent;
import org.weatherill.roadwatch.roadevents.RoadEvent.Category;

public class DummyRoadEventFactory
{
	/**
	 * Format that date's will be formatted into e.g. "Mon, 21 May 2012 11:54:18 BST"
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("E, dd MMM yyyy kk:mm:ss z");
	
	static RoadEvent getDummyRoadEvent(Location location)
	{
		RoadEvent roadEvent = null;
		
		try
		{
			return IRoadWatch.API.getRoadEvent(location, 
											   Category.ROADWORKS, 
											   "This is a dummy road event. It exists to demonstate some of the ideas behind RoadWatch", 
											   DATE_FORMAT.parse("Mon, 21 May 2012 13:54:33 BST"), 
											   "A Dummy Road Event", 
											   "The M something", 
											   "The North", 
											   "Yorkshire");
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roadEvent;
	}
	
}
