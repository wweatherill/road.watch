package org.weatherill.roadwatch.roadevents.io;

import java.util.List;

/**
 * Serialises a collection of Strings which are assumed to be valid JSON
 * objects into a JSON array String
 * 
 * @author william
 *
 */
class JSONArray
{
	/**
	 * Create a JSON array string where each element is
	 * one of the elements in the List param
	 * 
	 * @param elements - the elements of the array
	 * @return String - a JSON array holding the elements
	 * passed into this method
	 */
	static String toArray(List<String> elements)
	{
		StringBuffer json = new StringBuffer();
		
		json.append("[");
		
		if(elements != null && elements.isEmpty() == false)
		{
			for(int e = 0; e < elements.size() - 1; e++)
			{
				json.append(elements.get(e));
				json.append(",");
			}
			
			// last entry
			json.append(elements.get(elements.size() - 1));
		}
		json.append("]");
		
		return json.toString();
	}
}
