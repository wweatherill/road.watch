<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" isELIgnored="false" %>
<portlet:defineObjects />

<script type="text/javascript">

	var roadWatch;
	var exampleJourneys;
	var forceRoadEvent;

	function initialize() 
	{
		roadWatch = new RoadWatch
		({
			mapArea: document.getElementById("map_canvas"),
			roadEventsServiceUrl: '<portlet:resourceURL id="get.road.events"/>',
			roadMarkerImage: "${renderRequest.contextPath}/images/road.event_46x25.png"			
		});
		
		exampleJourneys = new ExampleJourneys(${exampleJourneys});
		
		document.getElementById("routeStart").value = roadWatch.getInitialCoords();
	}
	
	function showRoadEvents()
	{
		var start = document.getElementById("routeStart").value;
		var end = document.getElementById("routeEnd").value;
		var forceRoadEvent = document.getElementById("forceRoadEvent").value
	
		roadWatch.showRoadEvents(start, end, forceRoadEvent);
		
		return false;
	}
	
	function setExampleJourney()
	{
		var exampleJourney = exampleJourneys.getNextExampleJourney();
	
		document.getElementById("routeStart").value = exampleJourney.start;
		document.getElementById("routeEnd").value = exampleJourney.end;					
	}
	
	function toggle(showHideDivId, imageDivId) 
	{
        var ele = document.getElementById(showHideDivId);
        var imageDiv = document.getElementById(imageDivId);
        if(ele.style.display == "block") 
        {
			ele.style.display = "none";	
			imageDiv.innerHTML = '<img style="vertical-align:middle" src="${renderRequest.contextPath}/images/maximize_16x16.png" alt="Maximize content">'; 		
        }
        else 
        {
			ele.style.display = "block";           
			imageDiv.innerHTML = '<img style="vertical-align:middle" src="${renderRequest.contextPath}/images/minimize_16x16.png" alt="Minimize content">';
        }
	}	
	
    window.onload = initialize;	
	
</script>

<div style="height:500px;width:805px"> 
	<div style="height:500px;width:350px;float:left">
		<img src="${renderRequest.contextPath}/images/about_24x24.png" alt="about image" align=absmiddle>
		<span style="font-size:12px;font-weight:bold">About RoadWatch</span>
		<div style="padding:1px; border-style: solid; border-width: 1px;border-color: #7C7F7D">
	    	<span style="font-style:italic">What is RoadWatch?</span>
			<p>
				RoadWatch provides information about road events which may effect a particular journey.
				The user provides the start and end points and RoadWatch uses Google's mapping
				services to calculate and plot a route. RoadWatch then overlays the journey with 
				relevant road events allowing the user to see if their journey is subject to delays.
			</p>
			<span style="font-style:italic">Using RoadWatch</span>
			<p>
				RoadWatch uses latitude and longitude coordinates to specify its start and end points.
				You need to enter them in the following way: <i>"53.801576, -1.549104"</i> (no quotes).
			</p>
			<p>
				By pressing the "Example" button the start and end point fields will be populated with
				coordinates to give you an idea of the required format. Press the "Get Road Events" button 
				to see them on the adjacent map.
			</p>
			<p>
				If you select the "Force Road Event" checkbox then any Journey that is submitted to RoadWatch
				will come back with a dummy RoadEvent. This feature is intended to highlight how a RoadEvent
				is displayed in the UI without you having to provide a Journey for which there are known
				RoadEvents. 
			</p>						
		</div>
		<div style="margin-top:5px"> 
			<img src="${renderRequest.contextPath}/images/input_24x24.png" alt="input image" align=absmiddle>
			<span style="font-size:12px;font-weight:bold">Input your Journey</span>
		</div>
		<div style="padding: 1px; border-style: solid; border-width: 1px;border-color: #7C7F7D">
			<label style="width:140px">Start point:</label>
			<input style="width:200px;margin:2px 0 10px 10px" type="text" name="routeStart" id="routeStart"/>
			<br>
			<label style="width:140px">End point:</label>
			<input style="width:200px;margin:2px 0 10px 10px" type="text" name="routeEnd" id="routeEnd"/>
			<br>
			<div class="clear">
				<a class="button" href="#" onclick="this.blur(); setExampleJourney(); return false;"><span>Example</span></a>
				<a class="button" href="#" onclick="this.blur(); showRoadEvents(); return false;"><span>Get Road Events</span></a>
				<label style="display: block">
					<input type="checkbox" style="vertical-align:middle" id="forceRoadEvent"/>
					Force Road Event
				</label>
			</div>	
		</div>
  </div>
  <img src="${renderRequest.contextPath}/images/road.event_24x24.png" alt="road event image" align=absmiddle>
  <span style="font-size:12px;font-weight:bold">Relevant RoadEvents</span>
  <div id="map_canvas" style="height:450px;width:450px;float:right;border-style: solid;border-width: 1px;border-color: #7C7F7D"></div>
</div>