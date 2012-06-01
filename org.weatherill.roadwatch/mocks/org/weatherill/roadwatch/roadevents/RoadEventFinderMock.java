/**
 * 
 */
package org.weatherill.roadwatch.roadevents;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.weatherill.roadwatch.location.Location;

/**
 * A mock {@link IRoadEventFinder} whose behaviour can be
 * tweaked for testing purposes
 * 
 * @author william
 *
 */
public class RoadEventFinderMock implements IRoadEventFinder
{
	/**
	 * This can be changed at runtime to modify what 
	 * the finder will return
	 */
	private Map<Location, Set<RoadEvent>> lookup = null;

	/**
	 * @see org.weatherill.roadwatch.roadevents.IRoadEventFinder#getRoadEvents(org.weatherill.roadwatch.location.Location)
	 */
	@Override
	public Set<RoadEvent> getRoadEvents(Location location)
	{
		Set<RoadEvent> roadEvents = lookup.get(location);
		if(roadEvents == null)
		{
			roadEvents = new HashSet<RoadEvent>();
		}
		
		return roadEvents;
	}

	/**
	 * @param lookup the lookup to set
	 * @see RoadEventFinderMock#lookup
	 */
	public void setLookup(Map<Location, Set<RoadEvent>> lookup)
	{
		this.lookup = lookup;
	}

	
}
