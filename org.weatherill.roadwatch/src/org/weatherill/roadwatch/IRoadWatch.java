package org.weatherill.roadwatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.weatherill.roadwatch.impl.RoadWatch;
import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.roadevents.RoadEvent;
import org.weatherill.roadwatch.roadevents.RoadEvent.Category;
import org.weatherill.roadwatch.roadevents.RoadEventFinderException;

/**
 * The main entry point into all publicly accessible RoadWatch functionality.
 * 
 * @author william
 *
 */
public interface IRoadWatch
{
	/**
	 * A singleton instance of the default {@link IRoadWatch} interface
	 */
	public static final IRoadWatch API = RoadWatch.INSTANCE;
	
	/**
	 * For the given journey return all {@link RoadEvent} instances that
	 * are located along one or more of the journey's steps 
	 * 
	 * @param journey - the journey we are interested in analysing for 
	 * road events
	 * @param forceRoadEvent - true if a "fake" road event will be generated and related to
	 * an indeterminate {@link Location} in the given Journey.
	 * @return Set<RoadEvent> - as described. If there are no events for
	 * this journey then an empty Set will be returned
	 * @throws RoadEventFinderException - problem looking up RoadEvents
	 */
	public Set<RoadEvent> getRoadEvents(Journey journey, Boolean forceRoadEvent) throws RoadEventFinderException;
	
	/**
	 * Create a brand new Journey instance from the given steps
	 * <p>
	 * Note that behaviour is not guaranteed if the steps are null
	 * and also no validation is performed on the steps in terms
	 * of them forming a continuous journey
	 * 
	 * @param steps - a Set of {@link Location} instances that
	 * make up the journey
	 * @return Journey instance
	 */
	public Journey getJourney(List<Location> steps);
	
	/**
	 * Create a brand new {@link Location} instance based on the passed 
	 * latitude and longitude
	 * <p>
	 * Note that behaviour is not guaranteed if either or both of the
	 * parameters are null. Also no checking is performed of the validity 
	 * of the params in terms of being well formed coordinates
	 * 
	 * @param latitude - the latitude to set in the new Location
	 * @param longitude - the longitude to set in the new Location
	 * @return Location - as described above
	 */
	public Location getLocation(BigDecimal latitude, BigDecimal longitude);
	
	/**
	 * Create a brand new {@link RoadEvent} instance
	 * <p>
	 * Note that behaviour is not guaranteed if any of the
	 * parameters are null.
	 * 
	 * @param location - where the road event is to be found
	 * @param category - the type of event e.g. Accident
	 * @param description - a meaningful description of the event
	 * @param publishedDate - effectively when this event was first 
	 * 						  reported
	 * @param title - a short title describing the main aspects of 
	 * 				  the event to a human reader
	 * @param road - which road is effected by this event
	 * @param region - where in the United Kingdom this event occurred, 
	 * 				   e.g. North East
	 * @param county - which county of the United Kingdom this event 
	 * 				   occurred in
	 * @return RoadEvent - as described
	 */
	public RoadEvent getRoadEvent(Location location,
								  Category category,
								  String description,
								  Date publishedDate,
								  String title,
								  String road,
								  String region,
								  String county);
	
	public List<Journey> getExampleJourneys();
	
	
}
