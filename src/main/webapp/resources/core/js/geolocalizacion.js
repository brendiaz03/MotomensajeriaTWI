function getDriverLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showDriverPosition, showError);
    } else {
        document.getElementById("driverLocation").innerText = "La geolocalización no es soportada por este navegador.";
    }
}


async function showDriverPosition(position) {
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    document.getElementById("driverLocation").innerText = `Ubicacion del conductor: Latitud: ${latitude}, Longitud: ${longitude}`;


    document.querySelectorAll('.contenedor-viaje').forEach(function(viajeElement) {
        const latitudSalida = parseFloat(viajeElement.dataset.latitudSalida);
        const longitudSalida = parseFloat(viajeElement.dataset.longitudSalida);
        const latitudLlegada = parseFloat(viajeElement.dataset.latitudLlegada);
        const longitudLlegada = parseFloat(viajeElement.dataset.longitudLlegada);

        if (!isNaN(latitudSalida) && !isNaN(longitudSalida) && !isNaN(latitudLlegada) && !isNaN(longitudLlegada)) {
            const distanceFromCurrentPosition = calculateDistance(latitude, longitude, latitudSalida, longitudSalida); //HASTA EL LUGAR
            const distanceFromStartToEnd = calculateDistance(latitudSalida, longitudSalida, latitudLlegada, longitudLlegada); //DESDE SALIDA HASTA LLEGADA

            const distanceFromCurrentPositionInMeters = distanceFromCurrentPosition * 1000;
            const distanceFromStartToEndInMeters = distanceFromStartToEnd * 1000;

            viajeElement.querySelector('.distanceFromCurrentPosition').innerText = ` ${distanceFromCurrentPositionInMeters.toFixed(2)} metros`;
            viajeElement.querySelector('.distanceFromStartToEnd').innerText = ` ${distanceFromStartToEndInMeters.toFixed(2)} metros`;
        } else {
            console.error("No se encontraron datos válidos de latitud y longitud de salida y llegada.");
        }
    });
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

// FÓRMULA DE HAVERSINE
function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371; // Radio de la Tierra en km
    const dLat = degreesToRadians(lat2 - lat1);
    const dLon = degreesToRadians(lon2 - lon1);
    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(degreesToRadians(lat1)) * Math.cos(degreesToRadians(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c; // Distancia en km
    return distance;
}

function degreesToRadians(degrees) {
    return degrees * (Math.PI / 180);
}

window.onload = function() {
    getDriverLocation();
};
