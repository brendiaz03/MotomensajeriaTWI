function getDriverLocation() {
    if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showDriverPosition, showError);
} else {
    document.getElementById("driverLocation").innerText = "La geolocalización no es soportada por este navegador.";
}
}

    // Función para mostrar la posición del conductor
    async function showDriverPosition(position) {
        // Aquí se recibe el objeto position
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        document.getElementById("driverLocation").innerText = `Ubicación del conductor: Latitud: ${latitude}, Longitud: ${longitude}`;

        // Obtener la ubicación del paquete desde la base de datos
        const packageLocation = await getPackageLocation();
        const packageLatitude = packageLocation.latitude;
        const packageLongitude = packageLocation.longitude;
        document.getElementById("packageLocation").innerText = `Ubicación del paquete: Latitud: ${packageLatitude}, Longitud: ${packageLongitude}`;

        // Calcular la distancia entre el conductor y el paquete
        const distance = calculateDistance(latitude, longitude, packageLatitude, packageLongitude);
        document.getElementById("distance").innerText = `Distancia entre conductor y paquete: ${distance.toFixed(2)} km`;
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

    // Función para obtener la ubicación del paquete desde la base de datos
    async function getPackageLocation() {
        try {
        const response = await fetch('https://tuservidor.com/api/paquete'); // Cambia esta URL a tu endpoint real
        const data = await response.json();
        return {
        latitude: data.latitude,
        longitude: data.longitude
    };
    } catch (error) {
        console.error('Error al obtener la ubicación del paquete:', error);
    }
    }

    // Obtener la ubicación del conductor al cargar la página
    window.onload = function() {
        getDriverLocation();
    };