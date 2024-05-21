function getDriverLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showDriverPosition, showError);
    } else {
        document.getElementById("driverLocation").innerText = "La geolocalización no es soportada por este navegador.";
    }
}

// Función para mostrar la posición del conductor
async function showDriverPosition(position) {
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    // Recorrer todos los elementos con la clase 'viaje' para calcular la distancia
    document.querySelectorAll('.contenedor-viaje').forEach(function(viajeElement) {
        const latitudSalida = parseFloat(viajeElement.dataset.latitudSalida);
        const longitudSalida = parseFloat(viajeElement.dataset.longitudSalida);
        const latitudLlegada = parseFloat(viajeElement.dataset.latitudLlegada);
        const longitudLlegada = parseFloat(viajeElement.dataset.longitudLlegada);

        if (!isNaN(latitudSalida) && !isNaN(longitudSalida) && !isNaN(latitudLlegada) && !isNaN(longitudLlegada)) {
            // Calcular la distancia desde tu ubicación actual hasta la longitud y latitud de salida del viaje
            const distanceFromCurrentPosition = calculateDistance(latitude, longitude, latitudSalida, longitudSalida);
            // Calcular la distancia desde la latitud/longitud de salida hasta la latitud/longitud de llegada del viaje
            const distanceFromStartToEnd = calculateDistance(latitudSalida, longitudSalida, latitudLlegada, longitudLlegada);

            // Convertir las distancias de kilómetros a metros
            const distanceFromCurrentPositionInMeters = distanceFromCurrentPosition * 1000;
            const distanceFromStartToEndInMeters = distanceFromStartToEnd * 1000;

            viajeElement.querySelector('.distanceFromCurrentPosition').innerText = ` ${distanceFromCurrentPositionInMeters.toFixed(2)} metros`;
            viajeElement.querySelector('.distanceFromStartToEnd').innerText = ` ${distanceFromStartToEndInMeters.toFixed(2)} metros`;
        } else {
            console.error("No se encontraron datos válidos de latitud y longitud de salida y llegada.");
        }
    });
}



// Función para manejar errores
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

// Función para calcular la distancia usando la fórmula de Haversine
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

// Obtener la ubicación del conductor al cargar la página
window.onload = function() {
    getDriverLocation();
};
