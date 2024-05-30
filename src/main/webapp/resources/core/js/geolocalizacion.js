function getDriverLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showDriverPosition, showError, {
        });
    } else {
        document.getElementById("driverLocation").innerText = "La geolocalización no es soportada por este navegador.";
    }
}

function showDriverPosition(position) {
    const latitudActual = position.coords.latitude;
    const longitudActual = position.coords.longitude;


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

window.onload = function() {
    getDriverLocation();
};