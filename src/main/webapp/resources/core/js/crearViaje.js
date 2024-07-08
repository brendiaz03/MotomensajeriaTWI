document.addEventListener('DOMContentLoaded', function() {
    iniciarAutocompletado();

    var form = document.getElementById('viajeForm');
    var botonCrear = document.getElementById('boton_crear_viaje');

    botonCrear.addEventListener('click', function(event) {
        if (!validarFormulario()) {
            event.preventDefault(); // Evita que se envíe el formulario si hay errores
        }
    });

    form.addEventListener('submit', function(event) {
        if (!validarFormulario()) {
            event.preventDefault(); // Evita que se envíe el formulario si hay errores
        }
    });

    function validarFormulario() {
        // Reiniciar mensajes de error
        var errors = document.querySelectorAll('.error');
        errors.forEach(function(error) {
            error.textContent = ''; // Reinicia el texto del mensaje de error
        });

        var valid = true;

        // Validar que ambos campos de dirección tengan información
        var direccionSalida = document.getElementById('direccionSalida').value.trim();
        var direccionLlegada = document.getElementById('direccionLlegada').value.trim();

        if (!direccionSalida) {
            mostrarError('direccionSalidaError', 'Ingrese una dirección de salida válida.');
            valid = false;
        }

        if (!direccionLlegada) {
            mostrarError('direccionLlegadaError', 'Ingrese una dirección de llegada válida.');
            valid = false;
        }

        // Validar que ambas direcciones hayan sido seleccionadas del autocompletado
        var latitudSalida = document.getElementById('latitudSalida').value;
        var longitudSalida = document.getElementById('longitudSalida').value;
        var latitudLlegada = document.getElementById('latitudLlegada').value;
        var longitudLlegada = document.getElementById('longitudLlegada').value;

        if (!latitudSalida || !longitudSalida) {
            mostrarError('direccionSalidaError', 'Debe seleccionar una dirección de salida válida del autocompletado.');
            valid = false;
        }

        if (!latitudLlegada || !longitudLlegada) {
            mostrarError('direccionLlegadaError', 'Debe seleccionar una dirección de llegada válida del autocompletado.');
            valid = false;
        }

        return valid;
    }

    function mostrarError(id, mensaje) {
        const errorMessage = document.getElementById(id);
        errorMessage.textContent = mensaje;
        errorMessage.style.display = 'block';
    }

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
                mostrarError('direccionSalidaError', 'Por favor, selecciona una dirección válida del autocompletado.');
                return;
            }
            document.getElementById('latitudSalida').value = place.geometry.location.lat();
            document.getElementById('longitudSalida').value = place.geometry.location.lng();
        });

        autocompleteLlegada.addListener('place_changed', function () {
            const place = autocompleteLlegada.getPlace();
            if (!place.geometry) {
                mostrarError('direccionLlegadaError', 'Por favor, selecciona una dirección válida del autocompletado.');
                return;
            }
            document.getElementById('latitudLlegada').value = place.geometry.location.lat();
            document.getElementById('longitudLlegada').value = place.geometry.location.lng();
        });
    }
});
