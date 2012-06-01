package org.weatherill.roadwatch.portlet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

import org.weatherill.roadwatch.IRoadWatch;
import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.location.Location;

import com.liferay.portal.kernel.util.ParamUtil;

/**
 * A class that encapsulates the coordinates param coming from a client request to the
 * RoadWatch portlet
 * 
 * @author william
 *
 */
public class GetRoadEventsParams
{
	private static final String DEFAULT_JOURNEY_STEPS = "";

	/**
	 * Convert the client's incoming coordinate data into a {@link Journey} instance
	 * that can be manipulated by RoadWatch
	 * 
	 * @param request - the client's request which holds a "coordindates" param
	 * @return Journey - created from the "coordinates" param
	 */
	static Journey getJourney(ResourceRequest request)
	{
		List<Location> journeySteps = new ArrayList<Location>();
		
		String journeyStepsString = ParamUtil.getString(request, "coordinates", DEFAULT_JOURNEY_STEPS);		
		System.out.println("coords: " + journeyStepsString);
		
		String[] allCoordinateStrings = journeyStepsString.split("\\)");
		for(String coordinateString : allCoordinateStrings)
		{
			String[] coords = coordinateString.replace("(", "").split(",");
			Location journeyStep = getLocation(coords[0].trim(), coords[1].trim());
			journeySteps.add(journeyStep);		
		}
		
		return IRoadWatch.API.getJourney(journeySteps);
	}
	
	/**
	 * @param request - the client's request which holds a "forceRoadEvent" param
	 * @return Boolean - true if the user wishes to see a "fake" road event otherwise false
	 */
	static Boolean getForceRoadEvent(ResourceRequest request)
	{
		return ParamUtil.getBoolean(request, "forceRoadEvent");
	}
	
	/**
	 * Helper method to create a {@link Location} instance from the given latitude
	 * and longitude Strings 
	 * 
	 * @param latitude - The latitude coordinate - vertical plain of the globe
	 * @param longitude - The longitude coordinate - horizontal plain of the globe
	 * @return Location - made up of the BigDecimal versions of the latitude and longitude params
	 * @throws NumberFormatException - if either of the latitude or longitude cannot be
	 * parsed into BigDecimal instances
	 */
	private static Location getLocation(String latitude, String longitude)
	{
		Location location = IRoadWatch.API.getLocation(new BigDecimal(latitude.trim()), new BigDecimal(longitude.trim()));
		
		return location;
	}
}
