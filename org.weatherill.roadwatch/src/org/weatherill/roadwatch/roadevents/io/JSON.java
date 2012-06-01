package org.weatherill.roadwatch.roadevents.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.roadevents.RoadEvent;

/**
 * A utility class responsible for serialising {@link RoadEvent} instances
 * into JSON format String
 * 
 * @author william
 *
 */
public class JSON
{
	/**
	 * A template file for {@link RoadEvent} instances which defines skeletal JSON text whose values will be subsituted
	 * at runtime
	 */
	private String roadEventTemplate = null;
	
	/**
	 * A template file for {@link Journey} instances which defines skeletal JSON text whose values will be subsituted
	 * at runtime
	 */
	private String journeyTemplate = null;
	
	/**
	 * Singleton instance
	 */
	public static final JSON INSTANCE = new JSON();
	
	/**
	 * Create a brand new instance - note private construction
	 * use the singleton instance instead {@link JSON#INSTANCE}
	 */
	private JSON()
	{
		roadEventTemplate = getJsonTempate("roadEventTemplate.json");
		journeyTemplate = getJsonTempate("journeyTemplate.json");
	}
	
	/**
	 * Convert all the  road events into valid JSON
	 * This method will return a JSON array even if there 
	 * is only one roadEvent. If no or null road events are
	 * passed this method will return an empty JSON array.
	 * 
	 * @param roadEvents - the objects to serialise
	 * @return String - a valid JSON array whose elements are
	 * serialised JSON road events
	 */
	public String toJson(RoadEvent ... roadEvents)
	{
		String json = null;
		
		List<String> jsonRoadEvents = new ArrayList<String>();
		
		if(roadEvents != null)
		{
			for(RoadEvent roadEvent : roadEvents)
			{
				json = roadEventTemplate;
				
				json = setValue(json, "latitude", String.format("%s", roadEvent.getLocation().getLatitude()));
				json = setValue(json, "longitude", String.format("%s", roadEvent.getLocation().getLongitude().toString()));
				json = setValue(json, "publishedDate", roadEvent.getPublishedDate().toString());
				json = setValue(json, "title", roadEvent.getTitle());
				json = setValue(json, "category", roadEvent.getCategory().getValue());
				json = setValue(json, "description", roadEvent.getDescription());
				json = setValue(json, "road", roadEvent.getRoad());
			
				jsonRoadEvents.add(json);
			}
		}

		json = JSONArray.toArray(jsonRoadEvents);

		return json;
	}
	
	/**
	 * Convert all the journey into valid JSON
	 * This method will return a JSON array even if there 
	 * is only one journey. If no or null journeys are
	 * passed this method will return an empty JSON array.
	 * <p>
	 * Each journey must have at least 2 steps. The
	 * first step will be taken as the journey's start
	 * and the last step, its end. Intermediate steps
	 * will <b>not</b> be serialised. If a journey has less 
	 * than 2 steps it will <b>not</b> be serialised.
	 * 
	 * @param journey - the {@link Journey} to serialise
	 * @return String - a valid JSON string or an empty string
	 * (see above)
	 */
	public String toJson(Journey ... journeys)
	{	
		String json = null;
		
		List<String> jsonRoadEvents = new ArrayList<String>();
		
		if(journeys != null)
		{
			for(Journey journey : journeys)
			{
				List<Location> steps = journey.getSteps();
				
				if(steps.size() >= 2)
				{
					json = journeyTemplate;
					
					json = setValue(json, "start", steps.get(0).toString());
					json = setValue(json, "end", steps.get(steps.size() - 1).toString());
					
					jsonRoadEvents.add(json);
				}			
			}
		}

		json = JSONArray.toArray(jsonRoadEvents);

		return json;
	}	
	
	/**
	 * Load the JSON template file
	 * 
	 * @param templateFileName - the JSON template file name
	 * @return String - the file's contents
	 */
	private String getJsonTempate(String templateFileName)
	{
		StringBuilder jsonTemplate = new StringBuilder();

		Scanner scanner = new Scanner(JSON.class.getResourceAsStream(templateFileName));

		try
		{
			while (scanner.hasNextLine())
			{
				String nextLine = scanner.nextLine().replaceAll("\t+", "");
				jsonTemplate.append(nextLine);
			}
		}
		finally
		{
			scanner.close();
		}
		
		String jsonTemplateString = jsonTemplate.toString();
		
		return jsonTemplateString;
	}
	
	/**
	 * Substitute the key in the template for the given value
	 * 
	 * @param template - the template string
	 * @param key - the key to be replaced in the template 
	 * only the first instance of the key is replaced
	 * @param value - the value to replace the key with
	 * @return String - a newly updated version of the template holding
	 * the new value
	 */
	private String setValue(String template, String key, String value)
	{
		return template.replaceFirst("\\$" + key, value);
	}
}
