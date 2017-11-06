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

function createCluster(){
        // Add a marker clusterer to manage the markers.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
        }


