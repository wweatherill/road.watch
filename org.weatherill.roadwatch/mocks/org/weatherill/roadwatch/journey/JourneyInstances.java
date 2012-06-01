/**
 * 
 */
package org.weatherill.roadwatch.journey;

import java.util.ArrayList;
import java.util.List;

import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.location.LocationInstances;

/**
 * A pool of Journey instances that can be used in testing
 * Their state's are known and documented
 * 
 * @author william
 *
 */
public class JourneyInstances
{
	public static final JourneyInstances INSTANCE = new JourneyInstances();
	
	/**
	 * steps - zero
	 * 
	 * @return Journey - as described
	 */
	public Journey getJourney001()
	{
		List<Location> zeroSteps = new ArrayList<Location>();
		return new Journey(zeroSteps);
	}
	
	/**
	 * steps - one: {@link LocationInstances#getLocation001()}
	 * 
	 * @return Journey - as described
	 */
	public Journey getJourney002()
	{
		List<Location> oneStep = new ArrayList<Location>();
		oneStep.add(LocationInstances.INSTANCE.getLocation001());
		
		return new Journey(oneStep);
	}
	
	/**
	 * steps - one: {@link LocationInstances#getLocation002()}
	 * 
	 * @return Journey - as described
	 */
	public Journey getJourney003()
	{
		List<Location> oneStep = new ArrayList<Location>();
		oneStep.add(LocationInstances.INSTANCE.getLocation002());
		
		return new Journey(oneStep);
	}	
}
