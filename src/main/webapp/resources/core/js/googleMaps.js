function iniciarMap(latitudActual, longitudActual) {
    var contenedorDeLosDatos = document.getElementById("datosMap");

    var latitudSalida = parseFloat(contenedorDeLosDatos.dataset.latitudSalida);
    var longitudSalida = parseFloat(contenedorDeLosDatos.dataset.longitudSalida);
    var latitudLlegada = parseFloat(contenedorDeLosDatos.dataset.latitudLlegada);
    var longitudLlegada = parseFloat(contenedorDeLosDatos.dataset.longitudLlegada);
    var distanceConductorPaquete;
    var distancePaqueteDestino;

    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        mapId: "DEMO_MAP_ID",
    });

    // Create markers for each location
    var markerConductor = new google.maps.Marker({
        position: {lat: latitudActual, lng: longitudActual},
        map: map,
        label: 'Su ubicacion actual',
    });
    var markerSalida = new google.maps.Marker({
        position: {lat: latitudSalida, lng: longitudSalida},
        map: map,
        label: 'Paquete'
    });

    var markerDestino = new google.maps.Marker({
        position: {lat: latitudLlegada, lng: longitudLlegada},
        map: map,
        label: 'Destino del paquete'
    });

    // Create directions services for each route
    var directionsServiceRuta1 = new google.maps.DirectionsService();
    var directionsServiceRuta2 = new google.maps.DirectionsService();

    // Define travel mode (change "driving" to "bicycling", "walking", etc.)
    var travelMode = google.maps.TravelMode.DRIVING;

    // Create directions requests for each route
    var directionsRequestRuta1 = {
        origin: new google.maps.LatLng(latitudActual, longitudActual),
        destination: new google.maps.LatLng(latitudSalida, longitudSalida),
        travelMode: travelMode
    };

    var directionsRequestRuta2 = {
        origin: new google.maps.LatLng(latitudSalida, longitudSalida),
        destination: new google.maps.LatLng(latitudLlegada, longitudLlegada),
        travelMode: travelMode
    };

    // Route the directions and display them on the map
    directionsServiceRuta1.route(directionsRequestRuta1, function (responseRuta1, statusRuta1) {
        if (statusRuta1 === 'OK') {
            distanceConductorPaquete = responseRuta1.routes[0].legs[0].distance.text;
            var travelTimeConductorPaquete = responseRuta1.routes[0].legs[0].duration.text;
            var directionsRendererRuta1 = new google.maps.DirectionsRenderer({
                map: map,
                directions: responseRuta1
            });
            directionsRendererRuta1.setMap(map); // Display route 1 on map
            contenedorDeLosDatos.innerHTML += "<p>Distancia entre Conductor y Paquete: " + distanceConductorPaquete + "</p>";
            contenedorDeLosDatos.innerHTML += "<p>Tiempo estimado de viaje entre Conductor y Paquete: " + travelTimeConductorPaquete + "</p>";
        } else {
            console.error('Directions request failed due to: ' + statusRuta1);
        }
    });

    directionsServiceRuta2.route(directionsRequestRuta2, function (responseRuta2, statusRuta2) {
        if (statusRuta2 === 'OK') {
            distancePaqueteDestino = responseRuta2.routes[0].legs[0].distance.text;
            var travelTimePaqueteDestino = responseRuta2.routes[0].legs[0].duration.text;
            var directionsRendererRuta2 = new google.maps.DirectionsRenderer({
                map: map,
                directions: responseRuta2
            });
            // Optionally set a different polyline style for route 2
            directionsRendererRuta2.setOptions({
                polylineOptions: {
                    strokeColor: '#ff0000', // Green color for route 2
                    strokeWeight: 5
                }
            });
            directionsRendererRuta2.setMap(map); // Display route 2 on map
            contenedorDeLosDatos.innerHTML += "<p>Distancia entre Paquete y Destino: " + distancePaqueteDestino + "</p>";
            contenedorDeLosDatos.innerHTML += "<p>Tiempo estimado de viaje entre Paquete y Destino: " + travelTimePaqueteDestino + "</p>";
        } else {
            console.error('Directions request failed due to: ' + statusRuta2);
        }
    });
}