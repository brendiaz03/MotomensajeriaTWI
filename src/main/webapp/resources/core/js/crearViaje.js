function iniciarAutocompletado() {
    const inputSalida = document.getElementById('direccionSalida');
    const inputLlegada = document.getElementById('direccionLlegada');

    const options = {
        types: ['geocode']
    };

    const autocompleteSalida = new google.maps.places.Autocomplete(inputSalida, options);
    const autocompleteLlegada = new google.maps.places.Autocomplete(inputLlegada, options);

    autocompleteSalida.addListener('place_changed', function () {
        const place = autocompleteSalida.getPlace();
        if (!place.geometry) {
            console.error('No se encontró información para la dirección de salida:', place);
            mostrarError('No se encontró información para la dirección de salida');
            return;
        }
        document.getElementById('latitudSalida').value = place.geometry.location.lat();
        document.getElementById('longitudSalida').value = place.geometry.location.lng();
    });

    autocompleteLlegada.addListener('place_changed', function () {
        const place = autocompleteLlegada.getPlace();
        if (!place.geometry) {
            console.error('No se encontró información para la dirección de llegada:', place);
            mostrarError('No se encontró información para la dirección de llegada');
            return;
        }
        document.getElementById('latitudLlegada').value = place.geometry.location.lat();
        document.getElementById('longitudLlegada').value = place.geometry.location.lng();
    });

    function mostrarError(mensaje) {
        const errorMessage = document.getElementById('error-message');
        errorMessage.textContent = mensaje;
        errorMessage.style.display = 'block';
    }
}
