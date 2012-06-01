package org.weatherill.roadwatch.location;

import java.math.BigDecimal;

/**
 * A representation of a point as specified by the geographic
 * coordinate system.
 * <p>
 * 2 coordinates - latitude and longitude combine to define a single
 * point
 * 
 * @author william
 *
 */
public class Location
{
	/**
	 * The latitude coordinate - vertical plain of the globe
	 */
	private BigDecimal latitude = null;
	
	/**
	 * The longitude coordinate - horizontal plain of the globe
	 */
	private BigDecimal longitude = null;
	
	/**
	 * Create a brand new Location instance
	 * <p>
	 * It is up to the caller to ensure that the latitude and
	 * longitude values are not null and are also valid coordinates
	 * for the geographic coordinate system
	 * 
	 * @param latitude - north/south coordinate
	 * @param longitude - east/west coordinate
	 * @throws IllegalArgumentException - if one or both of the params are null
	 */
	public Location(BigDecimal latitude, BigDecimal longitude)
	{
		if(latitude != null && longitude != null)
		{
			this.latitude = latitude;
			this.longitude = longitude;
		}
		else
		{
			String message = String.format("One or both of the latitude (passed: %s) or longitude values (passed: %s) is null", 
										   latitude, 
										   longitude);
			
			throw new IllegalArgumentException(message); 
		}
	}

	/**
	 * @return BigDecimal - the latitude
	 */
	public BigDecimal getLatitude()
	{
		return latitude;
	}

	/**
	 * @return BigDecimal - the longitude
	 */
	public BigDecimal getLongitude()
	{
		return longitude;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Location other = (Location) obj;
		if (latitude == null)
		{
			if (other.latitude != null) { return false; }
		}
		else if (!latitude.equals(other.latitude)) { return false; }
		if (longitude == null)
		{
			if (other.longitude != null) { return false; }
		}
		else if (!longitude.equals(other.longitude)) { return false; }
		return true;
	}

	/**
	 * @return - String. If the latitude was 25.44 and the longitude
	 * was 32.36 then a call to toString would return "(25.44, 32.36)"
	 */
	@Override
	public String toString()
	{
		return String.format("(%s, %s)", latitude.toString(), longitude.toString());
	}
	
	
}
