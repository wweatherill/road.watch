package org.weatherill.roadwatch.roadevents.highwaysagency;

import java.io.IOException;

public class XMLRoadEventSourceInstances
{
	public static XMLRoadEventSourceInstances INSTANCE = new XMLRoadEventSourceInstances();
	
	/**
	 * This source will <b>always</b> throw an {@link IOException} 
	 * when a call to {@link XMLRoadEventSource#openStream()} is made
	 * 
	 * @return XMLRoadEventSource - as described
	 */
	public XMLRoadEventSource getXMLRoadEventSource001()
	{
		return new XMLRoadEventSourceMock();
	}
	
	/**
	 * This source will wrap an invalid XML file
	 * 
	 * @return XMLRoadEventSource - as described
	 */
	public XMLRoadEventSource getXMLRoadEventSource002()
	{
		return new XMLRoadEventSource(XMLRoadEventSourceInstances.class.getResource("invalid.xml"));
	}
	
	/**
	 * This source will wrap a valid XML file
	 * 
	 * @return XMLRoadEventSource - as described
	 */
	public XMLRoadEventSource getXMLRoadEventSource003()
	{
		return new XMLRoadEventSource(XMLRoadEventSourceInstances.class.getResource("valid.xml"));
	}	
}
