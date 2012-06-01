package org.weatherill.roadwatch.roadevents.highwaysagency;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Abstraction over the actual XML data holding road event information
 * This class is intended to hide away the details of retrieving the XML
 * data
 * 
 * @author william
 *
 */
public class XMLRoadEventSource
{
	/**
	 * The location of the XML road event data
	 */
	private URL location = null;

	/**
	 * Create a brand new source
	 * 
	 * @param location - the location of the XML road event data.
	 * This should not be null.
	 */
	public XMLRoadEventSource(URL location)
	{
		this.location = location;
	}
	
	/**
	 * Open an {@link InputStream} to the XML road event data
	 * @return InputStream - as described
	 * @throws IOException - problem opening the stream
	 */
	InputStream openStream() throws IOException
	{
		return location.openStream();
	}
}
