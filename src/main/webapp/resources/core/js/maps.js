window.onload = function() {
    getDriverLocation();
};

function getDriverLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showDriverPosition, showError, {
        });
    } else {
        document.getElementById("driverLocation").innerText = "La geolocalización no es soportada por este navegador.";
    }
}

function showDriverPosition(position) {
    let latitudActual = position.coords.latitude;
    let longitudActual = position.coords.longitude;

    iniciarMapa(latitudActual, longitudActual)
}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            document.getElementById("driverLocation").innerText = "El usuario denegó la solicitud de geolocalización.";
            break;
        case error.POSITION_UNAVAILABLE:
            document.getElementById("driverLocation").innerText = "La información de la ubicación no está disponible.";
            break;
        case error.TIMEOUT:
            document.getElementById("driverLocation").innerText = "La solicitud de ubicación ha expirado.";
            break;
        case error.UNKNOWN_ERROR:
            document.getElementById("driverLocation").innerText = "Se produjo un error desconocido.";
            break;
    }
}

function iniciarMapa(latitudActual, longitudActual){
    const contenedorDeLosDatos = document.getElementById("datosMap");

    const latitudSalida = parseFloat(contenedorDeLosDatos.dataset.latitudSalida);
    const longitudSalida = parseFloat(contenedorDeLosDatos.dataset.longitudSalida);
    const latitudLlegada = parseFloat(contenedorDeLosDatos.dataset.latitudLlegada);
    const longitudLlegada = parseFloat(contenedorDeLosDatos.dataset.longitudLlegada);

    const map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: latitudActual, lng: longitudActual},
        zoom: 12,
        mapId: "DEMO_MAP_ID"
    });

    const markerConductor = new google.maps.Marker({
        position: {lat: latitudActual, lng: longitudActual},
        map: map,
        label: 'Ubicación actual',
    });

    const markerSalida = new google.maps.Marker({
        position: {lat: latitudSalida, lng: longitudSalida},
        map: map,
        label: 'Salida del Paquete'
    });

    const markerDestino = new google.maps.Marker({
        position: {lat: latitudLlegada, lng: longitudLlegada},
        map: map,
        label: 'Destino del paquete'
    });

    const directionsServiceRuta1 = new google.maps.DirectionsService();
    const directionsServiceRuta2 = new google.maps.DirectionsService();

    const travelMode = google.maps.TravelMode.DRIVING;

    const directionsRequestRuta1 = {
        origin: new google.maps.LatLng(latitudActual, longitudActual),
        destination: new google.maps.LatLng(latitudSalida, longitudSalida),
        travelMode: travelMode
    };

    const directionsRequestRuta2 = {
        origin: new google.maps.LatLng(latitudSalida, longitudSalida),
        destination: new google.maps.LatLng(latitudLlegada, longitudLlegada),
        travelMode: travelMode
    };

    directionsServiceRuta1.route(directionsRequestRuta1, function (responseRuta1, statusRuta1) {
        if (statusRuta1 === 'OK') {
            const distanceConductorPaquete = responseRuta1.routes[0].legs[0].distance.text;
            const travelTimeConductorPaquete = responseRuta1.routes[0].legs[0].duration.text;
            const directionsRendererRuta1 = new google.maps.DirectionsRenderer({
                map: map,
                directions: responseRuta1
            });

            directionsRendererRuta1.setMap(map);

            if (!contenedorDeLosDatos.innerHTML.includes(distanceConductorPaquete)) {
                contenedorDeLosDatos.innerHTML += "<p>Distancia entre Conductor y Paquete: " + distanceConductorPaquete + "</p>";
            }
            if (!contenedorDeLosDatos.innerHTML.includes(travelTimeConductorPaquete)) {
                contenedorDeLosDatos.innerHTML += "<p>Tiempo estimado de viaje entre Conductor y Paquete: " + travelTimeConductorPaquete + "</p>";
            } else {
                console.error('Solicitud de direcciones fallida debido a: ' + statusRuta1);
            }
        }
    });

    directionsServiceRuta2.route(directionsRequestRuta2, function (responseRuta2, statusRuta2) {
        if (statusRuta2 === 'OK') {
            const distancePaqueteDestino = responseRuta2.routes[0].legs[0].distance.text;
            const travelTimePaqueteDestino = responseRuta2.routes[0].legs[0].duration.text;
            const directionsRendererRuta2 = new google.maps.DirectionsRenderer({
                map: map,
                directions: responseRuta2
            });

            directionsRendererRuta2.setOptions({
                polylineOptions: {
                    strokeColor: '#ff0000',
                    strokeWeight: 5
                }
            });
            directionsRendererRuta2.setMap(map);

            if (!contenedorDeLosDatos.innerHTML.includes(distancePaqueteDestino)) {
                contenedorDeLosDatos.innerHTML += "<p>Distancia entre Paquete y Destino: " + distancePaqueteDestino + "</p>";
            }
            if (!contenedorDeLosDatos.innerHTML.includes(travelTimePaqueteDestino)) {
                contenedorDeLosDatos.innerHTML += "<p>Tiempo estimado de viaje entre Paquete y Destino: " + travelTimePaqueteDestino + "</p>";
            }
        } else {
            console.error('Solicitud de direcciones fallida debido a: ' + statusRuta2);
        }
    });
}