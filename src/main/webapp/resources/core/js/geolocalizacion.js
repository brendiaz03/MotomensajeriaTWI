function iniciarMapaDetalle(latitudActual, longitudActual){

    const contenedorDeLosDatos = document.getElementById("datosMapDetalle");

    const latitudSalida = parseFloat(contenedorDeLosDatos.dataset.latitudSalida);
    const longitudSalida = parseFloat(contenedorDeLosDatos.dataset.longitudSalida);
    const latitudLlegada = parseFloat(contenedorDeLosDatos.dataset.latitudLlegada);
    const longitudLlegada = parseFloat(contenedorDeLosDatos.dataset.longitudLlegada);

    console.log(latitudActual)
    console.log(longitudActual)

    //let latitudActual = -34.818787;
    //let longitudActual =  -58.646844;

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
            const directionsRendererRuta1 = new google.maps.DirectionsRenderer({
                map: map,
                directions: responseRuta1
            });

            directionsRendererRuta1.setMap(map);
        }
    });

    directionsServiceRuta2.route(directionsRequestRuta2, function (responseRuta2, statusRuta2) {
        if (statusRuta2 === 'OK') {
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
        }
    });
}