package org.weatherill.roadwatch.roadevents;

import java.util.Set;

import org.weatherill.roadwatch.location.Location;

/**
 * It is the responsibility of this interface to find any
 * {@link RoadEvent} instance(s) that have a given location
 * 
 * @author william
 *
 */
public interface IRoadEventFinder
{
	/**
	 * For the given location param find any {@link RoadEvent}
	 * instances that have the same (via object or referential
	 * equality) {@link Location}.
	 * <p>
	 * If no matching events can be found then an empty set
	 * should be returned 
	 * 
	 * @param location - the lookup. It is the caller's responsibility
	 * to ensure that this is non-null and that it's properties are
	 * non-null too
	 * @return Set<RoadEvent> - as described above
	 * @throws RoadEventFinderException - problem looking up RoadEvents
	 */
	public Set<RoadEvent> getRoadEvents(Location location) throws RoadEventFinderException;

}