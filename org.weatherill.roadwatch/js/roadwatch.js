//--------------------------------------------------------------------
//
// RoadWatch - handles communication with RoadWatch portlet. 
// Is responsible for submitting requests and rendering response data
// 
// This type is meant to be publically accessible except for functions
// whose names begin with an underscore ("_"). These should be viewed
// as private API and subject or change or removal without notice
//--------------------------------------------------------------------
function RoadWatch(arguments) 
{
	for(arg in arguments)
	{		
		this[arg] = arguments[arg];
	}
	
	this.location = new Location();
	var mapOptions = 
	{
		zoom: 14,
		center: this.location.initialCoords,
		mapTypeId: google.maps.MapTypeId.ROADMAP,
		mapTypeControl: false
	};
	
	this.map = new google.maps.Map(this.mapArea, mapOptions);
	
	this.directionsService = new google.maps.DirectionsService();
	
	this.directionsDisplay = new google.maps.DirectionsRenderer();
	this.directionsDisplay.setMap(this.map);	
	this.directionsDisplay.setPanel(null);
	
	this.initalLocationMarker = new google.maps.Marker
	({
		position: this.location.initialCoords, 
		map: this.map, 
		title: "Start point"
	}); 
		
	this.roadEventDisplay = new RoadEventDisplay
	({
		map: this.map, 
		markerImage: this.roadMarkerImage
	}); 	
}

RoadWatch.prototype.getInitialCoords = function()
{
	return this.location.getFormattedInitialCoords();
}

RoadWatch.prototype.showRoadEvents = function(start, end, forceRoadEvent)
{
	// remove our initial marker from the map
	this.initalLocationMarker.setMap(null);
	
	var request = 
	{
		origin:start,
		destination:end,
		travelMode: google.maps.DirectionsTravelMode.DRIVING
	};
	
	var roadWatch = this;
	this.directionsService.route(request, function(response, status)
	{		
		if (status == google.maps.DirectionsStatus.OK) 
		{
			roadWatch._handleDirections(response, forceRoadEvent);
		} 
		else 
		{
			alert("There was an error in your request. Response status: \n\n"+status);
		}		
	});
};

RoadWatch.prototype._handleDirections = function(directionsResponse, forceRoadEvent) 
{		
	this.directionsDisplay.setDirections(directionsResponse);	
				
	// fire off an async request to the controller 
	// to get road events for the given route
	var directionRoute = directionsResponse.routes[0];
					
	// this journey will always have just 1 leg
	// due to the way the request to the
	// DirectionsService was formed
	var directionLeg = directionRoute.legs[0];	

	this._getRoadEvents(directionLeg.steps, forceRoadEvent);
}

RoadWatch.prototype._getRoadEvents = function(journeySteps, forceRoadEvent) 
{
	var roadEventDisplay = this.roadEventDisplay;
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() 
	{			
		if(xhr.readyState == 4) 
		{
			if(window.JSON && window.JSON.parse) 
			{
				var roadEvents = window.JSON.parse(xhr.responseText);			
				roadEventDisplay.addAllRoadEvents(roadEvents);
			}			
		}
	};
		
	// encode legs into string
	var coordinates = "";	
	for (var l = 0; l < journeySteps.length; l++)
	{		
		var journeyStep = journeySteps[l];
	
		coordinates += journeyStep.start_location.toString();
		coordinates += journeyStep.end_location.toString();
	}
		
	var params = "coordinates=" + coordinates;
	params += "&forceRoadEvent=" + forceRoadEvent;
		
	xhr.open("POST", this.roadEventsServiceUrl, true);
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");  
	xhr.setRequestHeader("Content-length", params.length);
	xhr.setRequestHeader("Connection", "close");		
	xhr.send(params);			
};

//--------------------------------------------------------------------
//
// ExampleJourneys - handles serving up example journeys to client code
// It effectively acts as an looping iterator. There are a finite number of
// example Journey's available. This type makes sure that subsequent requests
// for an example journey return a different journey to the previous request
// (assuming that there are at least 2 example journey's in the pool)
// 
// This type is meant to be publically accessible except for functions
// whose names begin with an underscore ("_"). These should be viewed
// as private API and subject or change or removal without notice
//--------------------------------------------------------------------
function ExampleJourneys(exampleJourneys)
{
	this.currentExample = -1;
	this.exampleJourneys = exampleJourneys;
	
	var location = new Location();

	// remove brackets
	for(key in this.exampleJourneys)
	{
		var exampleJourney = this.exampleJourneys[key];
	
		var formattedStart = location.formatLocation(exampleJourney.start);
		exampleJourney.start = formattedStart;
		
		var formattedEnd = location.formatLocation(exampleJourney.end);
		exampleJourney.end = formattedEnd;
	}
}

ExampleJourneys.prototype.getNextExampleJourney = function()
{
	// when we get to the end of the examples loop around
	// back to the beginning
	if(this.currentExample < this.exampleJourneys.length - 1)
	{
		this.currentExample++;
	}
	else
	{
		this.currentExample = 0;
	}

	var next = this.exampleJourneys[this.currentExample];
	
	return next;
};

//--------------------------------------------------------------------
//
// RoadEventDisplay - handles Google Map marker creation. Making sure
// that information about a given road event is layed down on the map
//  
// This type is not meant to be used outside of this library. 
//--------------------------------------------------------------------
function RoadEventDisplay(arguments)
{
	for(arg in arguments)
	{		
		this[arg] = arguments[arg];
	}
	
	// tracks current markers on the map
	this.currentMarkers = [];
}

RoadEventDisplay.prototype.addAllRoadEvents = function(roadEvents)
{
	// clear existing road event markers
	this._clearMarkers();

 	for (var r = 0; r < roadEvents.length; r++)
 	{
 		this.addRoadEvent(roadEvents[r]);
 	}
};

RoadEventDisplay.prototype._clearMarkers = function() 
{
	for(m in this.currentMarkers)
	{
		this.currentMarkers[m].setMap(null);
	}
}

RoadEventDisplay.prototype.addRoadEvent = function(roadEvent)
{
	var roadEventMarker = new google.maps.Marker(
 	{
 		position: new google.maps.LatLng(roadEvent.latitude, roadEvent.longitude), 
 		map: this.map, 
 		title: roadEvent.category,
 		icon: this.markerImage
 	});	
	
	// track this marker
	this.currentMarkers.push(roadEventMarker);
	
	var roadEventInfoWindow = new google.maps.InfoWindow(
 	{
 		content: this._toHtml(roadEvent)
 	});
 			
 	google.maps.event.addListener(roadEventMarker, 'click', function()
 	{
		roadEventInfoWindow.open(this.map, roadEventMarker);
 	});
}

RoadEventDisplay.prototype._toHtml = function(roadEvent)
{
	var infoHtml =  "<div class=\"bubble\">";
 	infoHtml += "<h1>" + roadEvent.title + "</h1>";
 	infoHtml += "<p><strong>Category:</strong> " + roadEvent.category + "<br />";
 	infoHtml += "<p><strong>Road:</strong> " + roadEvent.road + "<br />";
 	infoHtml += "<p><strong>Published date:</strong> " + roadEvent.publishedDate + "<br />"; 			 			
 	infoHtml += "<p>" + roadEvent.description + "</p>";		
 	infoHtml += "</div>";
 	
 	return infoHtml;
};

//--------------------------------------------------------------------
//
// Location - sets up an initial set of coordinates based on geolocation
// If the user's browser does not support the geolocation api
// (http://dev.w3.org/geo/api/spec-source.html) then a default location
// is generated. This type also provides some UI formatting functionality
// for the display of coordinates.
// 
// This type is not meant to be used outside of this library. 
//--------------------------------------------------------------------
function Location()
{
	this.defaultCoords = new google.maps.LatLng(53.801576,-1.549104);
	this.initialCoords = this.defaultCoords;

	// does the geolocation object exist?             
	if (navigator && navigator.geolocation)
	{  
		var location = this;
  		navigator.geolocation.getCurrentPosition
  		(
  			function(position)
  			{
  				location.initialCoords = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
  			}
  		);
    } 	  
}

Location.prototype.getFormattedInitialCoords = function()
{
	return this.formatLocation(this.initialCoords);
}

Location.prototype.formatLocation = function(location)
{
	var formattedLocation = location.replace("(", "");
	formattedLocation = formattedLocation.replace(")", "");
	
	return formattedLocation;
};