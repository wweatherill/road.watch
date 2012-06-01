package org.weatherill.roadwatch.roadevents.highwaysagency;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.weatherill.roadwatch.IRoadWatch;
import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.roadevents.IRoadEventFinder;
import org.weatherill.roadwatch.roadevents.RoadEvent;
import org.weatherill.roadwatch.roadevents.RoadEvent.Category;
import org.weatherill.roadwatch.roadevents.RoadEventFinderException;

/**
 * An implementation of {@link IRoadEventFinder} that has been designed to interpret road event
 * data in the "Traffic Feed" RSS format published and defined by the United Kingdom's Highways 
 * agency
 *  
 * @author william
 *
 */
public class XMLRoadEventFinder implements IRoadEventFinder
{
	/**
	 * Format that date's will be formatted into e.g. "Mon, 21 May 2012 11:54:18 BST"
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("E, dd MMM yyyy kk:mm:ss z");
	
	/**
	 * XPath query string template - used to retrieve XML nodes whose latitude and longitude child
	 * elements correspond to given values supplied at runtime
	 */
	private static final MessageFormat X_PATH_QUERY = new MessageFormat("/rss/channel/item[latitude=''{0}'' and longitude=''{1}'']");

	/**
	 * The source of the road event data
	 */
	private XMLRoadEventSource source = null;
	
	/**
	 * Create a brand new finder
	 * 
	 * @param source - The source of the road event data. Should not be null
	 */
	public XMLRoadEventFinder(XMLRoadEventSource source)
	{
		if(source != null)
		{
			this.source = source;
		}
		else
		{
			throw new IllegalArgumentException("XMLRoadEventSource cannot be null.");
		}
	}

	/**
	 * @see org.weatherill.roadwatch.roadevents.IRoadEventSource#getRoadEvents()
	 */
	@Override
	public Set<RoadEvent> getRoadEvents(Location location) throws RoadEventFinderException
	{
		Set<RoadEvent> roadEvents = new HashSet<RoadEvent>();
		
		try
		{
			InputStream trafficData = source.openStream();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(trafficData);
			XPathFactory xPathfactory = XPathFactory.newInstance();

			XPath xPath = xPathfactory.newXPath();
			String xPathExpression = X_PATH_QUERY.format(new Object[] { location.getLatitude().toPlainString(), location.getLongitude().toPlainString() });
			NodeList items = (NodeList) xPath.compile(xPathExpression).evaluate(doc, XPathConstants.NODESET);;

			// convert Nodes to RoadEvent instances
			for(int r = 0; r < items.getLength(); r++)
			{
				Element item = (Element) items.item(r);
				roadEvents.add(getRoadEvent(item));
			}
			
			trafficData.close();

		}
		catch (Exception e)
		{
			throw new RoadEventFinderException(e, location);
		}

		return roadEvents;
	}
	
	/**
	 * Effectively a converter. Converts the Element into a more strongly typed
	 * {@link RoadEvent} instance
	 * 
	 * @param item - the road event XML data
	 * @return RoadEvent - composed of data from the item param
	 * @throws ParseException - problem parsing the XML data
	 */
	private RoadEvent getRoadEvent(Element item) throws ParseException
	{
		Category category = Category.getCategory(getValue(item, "category"));
		String description = getValue(item, "description");
		Date publishedDate = DATE_FORMAT.parse(getValue(item, "pubDate"));
		String title = getValue(item, "title");
		String road = getValue(item, "road");
		String region = getValue(item, "region");
		String county = getValue(item, "county");
		String latitude = getValue(item, "latitude");
		String longitude = getValue(item, "longitude");
		Location location = IRoadWatch.API.getLocation(new BigDecimal(latitude), new BigDecimal(longitude));
		
		return IRoadWatch.API.getRoadEvent(location, category, description, publishedDate, title, road, region, county);
	}
	
	/**
	 * Helper method to make it easier to retrieve a String value from a child 
	 * element of the given Element where the child's name corresponds to the 
	 * childElementName param.
	 * <p>
	 * Note that if there are multiple child elements with the same name the
	 * value of the first is returned. It is the caller's responsibility to 
	 * ensure that there is a child element with the given childElementName. 
	 * Failure to do so will result in a {@link NullPointerException}
	 * 
	 * @param item - the parent element
	 * @param childElementName - the name of the child element whose value we
	 * wish to return
	 * @return String - the child element's value.
	 */
	private String getValue(Element item, String childElementName)
	{
		Element childElement = (Element) item.getElementsByTagName(childElementName).item(0);
		return childElement.getTextContent().replaceAll("[\r\n\t]+", "");
	}
}
