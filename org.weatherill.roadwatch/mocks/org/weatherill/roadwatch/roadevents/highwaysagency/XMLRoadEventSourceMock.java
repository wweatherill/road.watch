package org.weatherill.roadwatch.roadevents.highwaysagency;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is only intended to be used for testing. It will <b>always</b>
 * throw an {@link IOException} when a call to {@link XMLRoadEventSource#openStream()}
 * is made
 * 
 * @author william
 *
 */
public class XMLRoadEventSourceMock extends XMLRoadEventSource
{

	public XMLRoadEventSourceMock()
	{
		super(null);
	}

	/* (non-Javadoc)
	 * @see org.weatherill.roadwatch.roadevents.highwaysagency.XMLRoadEventSource#openStream()
	 */
	@Override
	InputStream openStream() throws IOException
	{
		throw new IOException("thrown for testing purposes");
	}
	
	

}
