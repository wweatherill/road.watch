/**
 * 
 */
package org.weatherill.roadwatch.location;

import java.math.BigDecimal;

/**
 * A pool of Location instances that can be used in testing
 * Their state's are known and documented
 * 
 * @author william
 *
 */
public class LocationInstances
{
	public static final LocationInstances INSTANCE = new LocationInstances();
	
	/**
	 * latitude - 1
	 * longitude - 10
	 * 
	 * @return Location - as described
	 */
	public Location getLocation001()
	{
		return new Location(BigDecimal.ONE, BigDecimal.TEN);
	}
	
	/**
	 * latitude - 10
	 * longitude - 1
	 * 
	 * @return Location - as described
	 */
	public Location getLocation002()
	{
		return new Location(BigDecimal.TEN, BigDecimal.ONE);
	}
	
	/**
	 * latitude - 50.75317
	 * longitude - 5.56732999999997
	 * 
	 * @return Location - as described
	 */
	public Location getLocation003()
	{
		return new Location(new BigDecimal("50.75317"), (new BigDecimal("5.56732999999997")));
	}
}
