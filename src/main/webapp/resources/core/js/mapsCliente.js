window.onload = function() {
    iniciarMapaCliente();
};

function iniciarMapaCliente(){
    const contenedorDeLosDatos = document.getElementById("datosMapDetalleEnvios");

    const latitudSalida = parseFloat(contenedorDeLosDatos.dataset.latitudSalida);
    const longitudSalida = parseFloat(contenedorDeLosDatos.dataset.longitudSalida);
    const latitudLlegada = parseFloat(contenedorDeLosDatos.dataset.latitudLlegada);
    const longitudLlegada = parseFloat(contenedorDeLosDatos.dataset.longitudLlegada);

    const map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: latitudSalida, lng: longitudSalida},
        zoom: 12,
        mapId: "DEMO_MAP_ID"
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

    const travelMode = google.maps.TravelMode.DRIVING;

    const directionsRequestRuta1 = {
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
                contenedorDeLosDatos.innerHTML += "<p>Distancia del envio: " + distanceConductorPaquete + "</p>";
            }
            if (!contenedorDeLosDatos.innerHTML.includes(travelTimeConductorPaquete)) {
                contenedorDeLosDatos.innerHTML += "<p>Duracion del envio: " + travelTimeConductorPaquete + "</p>";
            } else {
                console.error('Solicitud de direcciones fallida debido a: ' + statusRuta1);
            }
        }
    });
}