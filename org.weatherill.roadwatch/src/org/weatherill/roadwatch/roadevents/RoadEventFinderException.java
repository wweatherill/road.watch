package org.weatherill.roadwatch.roadevents;

import org.weatherill.roadwatch.location.Location;

@SuppressWarnings("serial")
public class RoadEventFinderException extends Exception
{

	public RoadEventFinderException(Throwable cause, Location location)
	{
		super(String.format("Problem finding road events for location: %s", location), cause);
	}

}
