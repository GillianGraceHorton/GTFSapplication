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

function drawRoute(){
        for(index = 0; index < (markers.length - 2); index++){
            makeConnection(index)
        }
        markers = [];
}

function makeConnection(index){
        var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;
        directionsDisplay.setMap(map);
        directionsService.route({
            origin: markers[index].getPosition(),
            destination: markers[index + 1].getPosition(),
            travelMode: 'DRIVING'
        }, function(response, status) {
            if (status === 'OK') {
                directionsDisplay.setDirections(response);
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });
}


function createCluster(){
        // Add a marker clusterer to manage the markers.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
        }




