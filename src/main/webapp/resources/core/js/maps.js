window.onload = function() {
    getDriverLocation();
};
const mapStyles = [
    {
        "featureType": "all",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "on"
            }
        ]
    },
    {
        "featureType": "all",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "saturation": 36
            },
            {
                "color": "#000000"
            },
            {
                "lightness": 40
            }
        ]
    },
    {
        "featureType": "all",
        "elementType": "labels.text.stroke",
        "stylers": [
            {
                "visibility": "on"
            },
            {
                "color": "#000000"
            },
            {
                "lightness": 16
            }
        ]
    },
    {
        "featureType": "all",
        "elementType": "labels.icon",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "administrative",
        "elementType": "geometry.fill",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 20
            }
        ]
    },
    {
        "featureType": "administrative",
        "elementType": "geometry.stroke",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 17
            },
            {
                "weight": 1.2
            }
        ]
    },
    {
        "featureType": "administrative.country",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "color": "#838383"
            }
        ]
    },
    {
        "featureType": "administrative.locality",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "color": "#c4c4c4"
            }
        ]
    },
    {
        "featureType": "administrative.neighborhood",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "color": "#aaaaaa"
            }
        ]
    },
    {
        "featureType": "landscape",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 20
            }
        ]
    },
    {
        "featureType": "poi",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 21
            },
            {
                "visibility": "on"
            }
        ]
    },
    {
        "featureType": "poi.business",
        "elementType": "geometry",
        "stylers": [
            {
                "visibility": "on"
            }
        ]
    },
    {
        "featureType": "road.highway",
        "elementType": "geometry.fill",
        "stylers": [
            {
                "color": "#6e6e6e"
            },
            {
                "lightness": "0"
            }
        ]
    },
    {
        "featureType": "road.highway",
        "elementType": "geometry.stroke",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "road.highway",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "color": "#ffffff"
            }
        ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 18
            }
        ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "geometry.fill",
        "stylers": [
            {
                "color": "#575757"
            }
        ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "color": "#ffffff"
            }
        ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "labels.text.stroke",
        "stylers": [
            {
                "color": "#2c2c2c"
            }
        ]
    },
    {
        "featureType": "road.local",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 16
            }
        ]
    },
    {
        "featureType": "road.local",
        "elementType": "labels.text.fill",
        "stylers": [
            {
                "color": "#999999"
            }
        ]
    },
    {
        "featureType": "transit",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 19
            }
        ]
    },
    {
        "featureType": "water",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 17
            }
        ]
    }
];
function getDriverLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showDriverPosition, showError, {});
    } else {
        document.getElementById("driverLocation").innerText = "La geolocalización no es soportada por este navegador.";
    }
}

function showDriverPosition(position) {
    let latitudActual = position.coords.latitude;
    let longitudActual = position.coords.longitude;

    iniciarMapa(latitudActual, longitudActual);
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

    // Verifica que todas las coordenadas sean números válidos
    if (isNaN(latitudActual) || isNaN(longitudActual) || isNaN(latitudSalida) || isNaN(longitudSalida) || isNaN(latitudLlegada) || isNaN(longitudLlegada)) {
        console.error("Una o más coordenadas no son números válidos.");
        return;
    }

    const map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: latitudActual, lng: longitudActual},
        zoom: 12,
        styles: mapStyles, // Aplica los estilos aquí
    });

    const markerConductor = new google.maps.Marker({
        position: {lat: latitudActual, lng: longitudActual},
        map: map,
        title: 'Ubicación actual'
    });

    const markerSalida = new google.maps.Marker({
        position: {lat: latitudSalida, lng: longitudSalida},
        map: map,
        title: 'Salida del Paquete'
    });

    const markerDestino = new google.maps.Marker({
        position: {lat: latitudLlegada, lng: longitudLlegada},
        map: map,
        title: 'Destino del paquete'
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
                    strokeColor: '#ef6292',
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