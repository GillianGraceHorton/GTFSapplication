var map;
var markers;

function initMap() {
        var midWest = {lat: 40.105124, lng: -91.142859};
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 5,
          center: midWest
        });
        markers = [];
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

function generateWaypoints(){
        waypts = [];
        for(index = 0; index < (markers.length - 2); index++){
            waypts.push({
                location: markers[index].getPosition(),
                stopover: true
            });
        }
        return waypts;
}

function makeConnection(color){
        var directionsService = new google.maps.DirectionsService;
        directionsDisplay = new google.maps.DirectionsRenderer({
            polylineOptions: {
              strokeColor: color
            }
          });
        directionsDisplay.setMap(map);
        waypts = generateWaypoints();
        directionsService.route({
            origin: markers[0].getPosition(),
            destination: markers[markers.length - 1].getPosition(),
            waypoints: waypts,
            optimizeWaypoints: false,
            travelMode: 'DRIVING'
        }, function(response, status) {
            if (status === 'OK') {
              directionsDisplay.setDirections(response);
              //var route = response.routes[0];
            }else {
              window.alert('Directions request failed due to ' + status);
            }
        });
}