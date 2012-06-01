/**
 * 
 */
package org.weatherill.roadwatch.roadevents;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.weatherill.roadwatch.location.LocationInstances;
import org.weatherill.roadwatch.roadevents.RoadEvent.Category;

/**
 * A pool of RoadEvent instances that can be used in testing
 * Their state's are known and documented
 * 
 * @author william
 *
 */
public class RoadEventInstances
{
	public static final RoadEventInstances INSTANCES = new RoadEventInstances();
	
	/**
	 * Format that date's will be formatted into e.g. "Mon, 21 May 2012 11:54:18 BST"
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("E, dd MMM yyyy kk:mm:ss z");
	
	/**
	 * location - {@link LocationInstances#getLocation002()}
	 * category - {@link Category#ROADWORKS}
	 * description - "description of road event 01"
	 * publishedDate - "Mon, 21 May 2012 13:54:33 BST"
	 * title - "road event 01"
	 * road - "M1"
	 * region - "North"
	 * county - "West Yorkshire"
	 * 
	 * @return RoadEvent - as described
	 */
	public RoadEvent getRoadEvent001()
	{
		RoadEvent roadEvent;
		
		try
		{
			roadEvent = new RoadEvent(LocationInstances.INSTANCE.getLocation002(), 
												Category.ROADWORKS, 
												"description of road event 01", 
												DATE_FORMAT.parse("Mon, 21 May 2012 13:54:33 BST"), 
												"road event 01",
												"M1", 
												"North", 
												"West Yorkshire");
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Problem parsing date string into instance of java.util.Date.", e);
		}
		
		return roadEvent;
	}
}
