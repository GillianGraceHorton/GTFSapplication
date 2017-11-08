var map;
var markers;
var routes;

function initMap() {
        var midWest = {lat: 40.105124, lng: -91.142859};
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 5,
          center: midWest,
          styles: [
             {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
             {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
             {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
             {
               featureType: 'administrative.locality',
               elementType: 'labels.text.fill',
               stylers: [{color: '#d59563'}]
             },
             {
               featureType: 'poi',
               elementType: 'labels.text.fill',
               stylers: [{color: '#d59563'}]
             },
             {
               featureType: 'poi.park',
               elementType: 'geometry',
               stylers: [{color: '#263c3f'}]
             },
             {
               featureType: 'poi.park',
               elementType: 'labels.text.fill',
               stylers: [{color: '#6b9a76'}]
             },
             {
               featureType: 'road',
               elementType: 'geometry',
               stylers: [{color: '#38414e'}]
             },
             {
               featureType: 'road',
               elementType: 'geometry.stroke',
               stylers: [{color: '#212a37'}]
             },
             {
               featureType: 'road',
               elementType: 'labels.text.fill',
               stylers: [{color: '#9ca5b3'}]
             },
             {
               featureType: 'road.highway',
               elementType: 'geometry',
               stylers: [{color: '#746855'}]
             },
             {
               featureType: 'road.highway',
               elementType: 'geometry.stroke',
               stylers: [{color: '#1f2835'}]
             },
             {
               featureType: 'road.highway',
               elementType: 'labels.text.fill',
               stylers: [{color: '#f3d19c'}]
             },
             {
               featureType: 'transit',
               elementType: 'geometry',
               stylers: [{color: '#2f3948'}]
             },
             {
               featureType: 'transit.station',
               elementType: 'labels.text.fill',
               stylers: [{color: '#d59563'}]
             },
             {
               featureType: 'water',
               elementType: 'geometry',
               stylers: [{color: '#17263c'}]
             },
             {
               featureType: 'water',
               elementType: 'labels.text.fill',
               stylers: [{color: '#515c6d'}]
             },
             {
               featureType: 'water',
               elementType: 'labels.text.stroke',
               stylers: [{color: '#17263c'}]
             }
           ]
        });
        markers = [];
        routes = [];
}

function addStopMarker(latitude, longitude){
        var marker = new google.maps.Marker({
           position: {lat: latitude, lng:  longitude},
        });
        markers.push(marker);
}

function drawRoute(color){
        makeConnection(color);
        markers = [];
}

function generateWaypoints(markersChunk){
        waypts = [];
        for(index = 0; index < (markersChunk.length - 2); index++){
            waypts.push({
                location: markersChunk[index].getPosition(),
                stopover: true
            });
        }
        return waypts;
}

function makeConnection(color){
        var i,j,temparray,chunk = 25;
        for (i=0,j=markers.length; i<j; i+=chunk) {
            chunk = markers.slice(i,i+chunk);

            var directionsService = new google.maps.DirectionsService;
            directionsDisplay = new google.maps.DirectionsRenderer({
                polylineOptions: {
                  strokeColor: color
                }
              });
            directionsDisplay.setMap(map);
            waypts = generateWaypoints(chunk);
            directionsService.route({
                origin: chunk[0].getPosition(),
                destination: chunk[chunk.length - 1].getPosition(),
                waypoints: waypts,
                optimizeWaypoints: false,
                travelMode: 'DRIVING'
            }, function(response, status) {
                if (status === 'OK') {
                  directionsDisplay.setDirections(response);
                  routes.push(directionsDisplay);
                }else {
                  var infowindow = new google.maps.InfoWindow({
                                          content: 'route print failed: ' + status
                  });
                  infowindow.open(map, chunk[0]);
                }
            });
        }
}

function removeRoutes(){
        for(index = 0; index < routes.length; index++){
            routes[index].setMap(null);
        }
        routes = [];
}

function pausecomp(millis)
{
    var date = new Date();
    var curDate = null;
    do { curDate = new Date(); }
    while(curDate-date < millis);
}