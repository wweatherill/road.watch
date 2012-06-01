package org.weatherill.roadwatch.roadevents;

import java.util.HashSet;
import java.util.Set;

import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.location.Location;

/**
 * The sole responsibility of this service is to find any {@link RoadEvent}
 * instances that effect a given {@link Journey}
 * <p>
 * This service is stateless and can be called repeatedly with different
 * {@link Journey} instances
 * 
 * @author william
 *
 */
public class RoadEventService
{
	/**
	 * The finder looks up {@link RoadEvent} instances by {@link Location}
	 */
	private IRoadEventFinder roadEventFinder = null;
	
	/**
	 * Create a brand new service instance
	 * 
	 * @param roadEventFinder - The finder looks up {@link RoadEvent} 
	 * instances by {@link Location}. It is the caller's responsibility 
	 * to ensure that the finder is not null
	 */
	public RoadEventService(IRoadEventFinder roadEventFinder)
	{
		this.roadEventFinder = roadEventFinder;
	}

	/**
	 * Get all known {@link RoadEvent} instances that are relevant to the
	 * given Journey where "relevant" means that the journey's steps which
	 * are {@link Location} instances can be found in one or more RoadEvents
	 * 
	 * @param journey - the journey whose steps the caller is interested in. Should
	 * not be null
	 * @return Set<RoadEvent> - the road events relevant to this Journey or an empty
	 * set if no relevant events could be found.
	 * @throws RoadEventFinderException - problem looking up RoadEvents
	 */
	public Set<RoadEvent> getRoadEvents(Journey journey) throws RoadEventFinderException
	{
		Set<RoadEvent> roadEvents = new HashSet<RoadEvent>();

		if(journey != null)
		{
			IRoadEventFinder roadEventFinder = getRoadEventFinder();
			for(Location step : journey.getSteps())
			{
				roadEvents.addAll(roadEventFinder.getRoadEvents(step));
			}
		}
		
		return roadEvents;
	}
	
	/**
	 * @return IRoadEventFinder - the finder - {@link RoadEventService#roadEventFinder}
	 */
	private IRoadEventFinder getRoadEventFinder()
	{
		return roadEventFinder;
	}
}
