package org.weatherill.roadwatch.roadevents;

import java.util.Date;

import org.weatherill.roadwatch.location.Location;

/**
 * A RoadEvent is a data class which holds details regarding a specific
 * event that effects a road in the United Kingdom
 * 
 * @author william
 *
 */
public class RoadEvent
{
	/**
	 * The type of event. There are 4 broad categories
	 * of road event
	 * 
	 * @author william
	 */
	public enum Category
	{
		ACCIDENT("Accident"),
		
		ROADWORKS("Roadworks"),
		
		NO_DELAY("No Delay"),
		
		UNKNOWN("Unknown");
		
		/**
		 * Human readable name
		 */
		private String value = null;
		
		private Category(String value)
		{
			this.value = value;
		}
		
		/**
		 * Translates from human readable name to the {@link Category}
		 * that it represents
		 * 
		 * @param value - the lookup
		 * @return Category - associated with the name. If no association
		 * exists then the exceptions below are throw.
		 * @throws IllegalArgumentException - if Category has no constant with the specified value
		 * @throws NullPointerException - if value is null
		 */
		public static Category getCategory(String value)
		{
			value = value.replace(" ", "_");
			value = value.toUpperCase();
			
			return valueOf(value);
		}
		
		/**
		 * @return String - the human readable name
		 */
		public String getValue()
		{
			return value;
		}
	}
	
	/**
	 * Where this event has taken place or is taking place
	 */
	private Location location = null;
	
	/**
	 * The general type of event eg Accident
	 */
	private Category category = null;
	
	/**
	 * a meaningful description of the event
	 */
	private String description = null;
	
	/**
	 * effectively when this event was first reported
	 */
	private Date publishedDate = null;
	
	/**
	 * a short title describing the main aspects of the event to a human reader
	 */
	private String title = null;
	
	/**
	 * which road is effected by this event
	 */
	private String road = null;
	
	/**
	 * where in the United Kingdom this event occurred, e.g. North East
	 */
	private String region = null;
	
	/**
	 * which county of the United Kingdom this event occurred in
	 */
	private String county = null;

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
	public RoadEvent(Location location, Category category, String description, Date publishedDate, String title,
			String road, String region, String county)
	{
		super();
		this.location = location;
		this.category = category;
		this.description = description;
		this.publishedDate = publishedDate;
		this.title = title;
		this.road = road;
		this.region = region;
		this.county = county;
	}

	/**
	 * @return the location
	 */
	public Location getLocation()
	{
		return location;
	}

	/**
	 * @return the category
	 */
	public Category getCategory()
	{
		return category;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the publishedDate
	 */
	public Date getPublishedDate()
	{
		return publishedDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return the road
	 */
	public String getRoad()
	{
		return road;
	}

	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @return the county
	 */
	public String getCounty()
	{
		return county;
	}

	
}
