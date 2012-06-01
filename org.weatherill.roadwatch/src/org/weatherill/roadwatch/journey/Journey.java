package org.weatherill.roadwatch.journey;

import java.util.List;

import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.roadevents.RoadEvent;



/**
 * From within RoadWatch the purpose of a Journey is to
 * collect together a series of steps ({@link Location})
 * for which a user is interested in finding road events 
 * {@link RoadEvent} that overlap the journey
 * <p>
 * Conceptually a journey is a collection of steps that 
 * together map a route from an origin to a destination.
 * However this is not strictly a requirement for a RoadWatch
 * journey therefore no validation of this concept is 
 * performed. If it is important that your Journey fit the
 * above definition then it is up to the caller to ensure
 * the validity of their Journey
 * 
 * @author william
 *
 */
public class Journey
{
	/**
	 * The default journey name. This will be used when
	 * the {@link Journey#name} member variable is not
	 * set explicitly
	 */
	private static final String DEFAULT_NAME = "a journey";
	
	/**
	 * The <i>optional</i> name of the journey. If not set
	 * explicity then {@link Journey#DEFAULT_NAME} will be used
	 */
	private String name = DEFAULT_NAME;
	
	/**
	 * The steps ({@link Location}) that make up the journey
	 * Typically the caller is interested in learning if any
	 * road events ({@link RoadEvent}) can be found for one
	 * or more of the steps
	 */
	private List<Location> steps = null;

	/**
	 * Create a brand new {@link Journey}
	 * 
	 * @param steps - this journey's steps
	 * No validation is performed on the steps
	 * for example if null is passed then the 
	 * steps for this journey will be null
	 */
	public Journey(List<Location> steps)
	{
		this.steps = steps;
	}

	/**
	 * @return String  - get this Journey's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - the journey's name. Note if null
	 * if passed then the name will be null. The 
	 * {@link Journey#DEFAULT_NAME} will not be returned
	 * by a call to {@link Journey#getName()}
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return List<Location> - this journey's steps
	 */
	public List<Location> getSteps()
	{
		return steps;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((steps == null) ? 0 : steps.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Journey other = (Journey) obj;
		if (name == null)
		{
			if (other.name != null) { return false; }
		}
		else if (!name.equals(other.name)) { return false; }
		if (steps == null)
		{
			if (other.steps != null) { return false; }
		}
		else if (!steps.equals(other.steps)) { return false; }
		return true;
	}
	
	
}
