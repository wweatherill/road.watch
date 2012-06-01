package org.weatherill.roadwatch.portlet;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.weatherill.roadwatch.IRoadWatch;
import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.roadevents.RoadEvent;
import org.weatherill.roadwatch.roadevents.RoadEventFinderException;
import org.weatherill.roadwatch.roadevents.io.JSON;

@RequestMapping(value = "VIEW")
@Controller
public class RoadWatchController
{
	private static final String VIEW_NAME = "roadWatch";

	/**
	 * Render method. Returns the initial view which requires client input
	 */
	@RenderMapping()
	public ModelAndView handleRenderRequest(RenderRequest request, RenderResponse response) throws Exception
	{	
		//get some example data so that the user can see RoadWatch in action
		String exampleJourneys = getExampleJourneys();
		
		ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
		modelAndView.addObject("exampleJourneys", exampleJourneys);
		
		return modelAndView;
	}
	
	/**
	 * Returns data back to the client which encapsulates road events ({@link RoadEvent}) which
	 * are relevent to the coordinates (latitude and longitude) held in the "coordinates" param
	 * which is found within the given request para
	 * <p>
	 * Data is returned in JSON format. Specifically a JSON array is returned. Each element of the 
	 * array is of the form where "$xxx" represents a the actual value
	 * <pre>
	 *{
  	 *	"latitude": "$latitude",
  	 *	"longitude": "$longitude",
  	 *	"publishedDate": "$publishedDate",
  	 *	"title": "$title", 
  	 *	"category": "$category", 
  	 *	"description": "$description",
  	 *	"road": "$road"	
  	 *}
	 * </pre>
	 * 
	 * 
	 * @param request - from the client - holds the coordinates
	 * @param response - used for writting return data for consumption by the client
	 */
	@ResourceMapping(value="get.road.events")
	public void getRoadEvents(ResourceRequest request, ResourceResponse response)
	{		
		Journey journey = GetRoadEventsParams.getJourney(request);
		Boolean forceRoadEvent = GetRoadEventsParams.getForceRoadEvent(request);
		
		// create relevant road events to highlight to the user
		try
		{
			Set<RoadEvent> roadEvents = IRoadWatch.API.getRoadEvents(journey, forceRoadEvent);
			RoadEvent[] roadEventsArray = roadEvents.toArray(new RoadEvent[roadEvents.size()]);
			
			String jsonRoadEvents = JSON.INSTANCE.toJson(roadEventsArray);
		
			response.setContentType("text/html");
			try
			{
				// Use JSON to move road events back to client
				response.getWriter().print(jsonRoadEvents);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}		
		}
		catch (RoadEventFinderException e1)
		{
			e1.printStackTrace();
		}			
	}
	
	/**
	 * Returns data back to the client which encapsulates road events ({@link RoadEvent}) which
	 * are relevent to the coordinates (latitude and longitude) held in the "coordinates" param
	 * which is found within the given request para
	 * <p>
	 * Data is returned in JSON format. Specifically a JSON array is returned. Each element of the 
	 * array is of the form where "$xxx" represents a the actual value
	 * <pre>
	 *{
  	 *	"latitude": "$latitude",
  	 *	"longitude": "$longitude",
  	 *	"publishedDate": "$publishedDate",
  	 *	"title": "$title", 
  	 *	"category": "$category", 
  	 *	"description": "$description",
  	 *	"road": "$road"	
  	 *}
	 * </pre>
	 * 
	 * 
	 * @param request - from the client - holds the coordinates
	 * @param response - used for writting return data for consumption by the client
	 */
	private String getExampleJourneys()
	{		
		// create example journey to which will hook up to known road events
		List<Journey> exampleJourneys = IRoadWatch.API.getExampleJourneys();
		Journey[] exampleJourneysArray = exampleJourneys.toArray(new Journey[exampleJourneys.size()]);

		String jsonExampleJourneys = JSON.INSTANCE.toJson(exampleJourneysArray);

		return jsonExampleJourneys;
	}	
}