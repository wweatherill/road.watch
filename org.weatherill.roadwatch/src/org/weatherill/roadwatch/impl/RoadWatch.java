package org.weatherill.roadwatch.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.weatherill.roadwatch.IRoadWatch;
import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.roadevents.RoadEvent;
import org.weatherill.roadwatch.roadevents.RoadEvent.Category;
import org.weatherill.roadwatch.roadevents.RoadEventFinderException;
import org.weatherill.roadwatch.roadevents.RoadEventService;
import org.weatherill.roadwatch.roadevents.highwaysagency.XMLRoadEventFinder;
import org.weatherill.roadwatch.roadevents.highwaysagency.XMLRoadEventSource;

/**
 * Default implementation of {@link IRoadWatch}
 * 
 * @author william
 */
public class RoadWatch implements IRoadWatch
{
	/** Singleton instance */
	public static final IRoadWatch INSTANCE = new RoadWatch(); 
	
	/** This service is used to retrieve {@link RoadEvent} instances
	 * for a given {@link Journey}*/
	private RoadEventService roadEventService = null;
	
	/** Provides access to road event data and also some example
	 * {@link Journey} instances that are known to be associated
	 * with {@link RoadEvent} instances */
	private RoadEventData roadEventData = null;

	/**
	 * Private construction
	 * @see RoadWatch#INSTANCE
	 */
	private RoadWatch()
	{
		roadEventData = new RoadEventData();
		
		XMLRoadEventSource roadEventSource = new XMLRoadEventSource(roadEventData.getUrl());	
		roadEventService = new RoadEventService(new XMLRoadEventFinder(roadEventSource));
	}

	@Override
	public Set<RoadEvent> getRoadEvents(Journey journey, Boolean forceRoadEvent) throws RoadEventFinderException
	{		
		Set<RoadEvent> roadEvents = roadEventService.getRoadEvents(journey);
		
		// if the user wants a road event but none 
		// match the journey fabricate one
		if(forceRoadEvent && roadEvents.isEmpty())
		{
			// get the middle location of the given journey
			List<Location> steps = journey.getSteps();
			int middleStep = steps.size() / 2;
			
			roadEvents.add(DummyRoadEventFactory.getDummyRoadEvent(steps.get(middleStep)));
		}
		
		return roadEvents;
	}

	@Override
	public Journey getJourney(List<Location> steps)
	{
		return new Journey(steps);
	}

	@Override
	public Location getLocation(BigDecimal latitude, BigDecimal longitude)
	{
		return new Location(latitude, longitude);
	}

	@Override
	public RoadEvent getRoadEvent(Location location, Category category, String description, Date publishedDate,
			String title, String road, String region, String county)
	{
		return new RoadEvent(location, category, description, publishedDate, title, road, region, county);
	}

	@Override
	public List<Journey> getExampleJourneys()
	{
		return roadEventData.getExampleJourneys();
	}
}
