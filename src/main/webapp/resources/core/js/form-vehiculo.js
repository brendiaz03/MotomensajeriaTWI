document.addEventListener('DOMContentLoaded', function() {
    var form = document.querySelector('form[novalidate]');

    form.addEventListener('submit', function(event) {
        // Reiniciar mensajes de error
        var errors = document.querySelectorAll('.error');
        errors.forEach(function(error) {
            error.textContent = ''; // Reinicia el texto del mensaje de error
            error.style.display = 'none'; // Asegura que esté oculto inicialmente
        });

        var valid = true;

        // Validar tipo de vehiculo
        var tipoVehiculo = document.getElementById('tipoVehiculo').value;
        if (!tipoVehiculo) {
            document.getElementById('tipoVehiculoError').textContent = 'Seleccione un tipo de vehículo.';
            document.getElementById('tipoVehiculoError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar modelo
        var modelo = document.getElementById('modelo').value;
        if (!modelo) {
            document.getElementById('modeloError').textContent = 'Seleccione un modelo.';
            document.getElementById('modeloError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar color
        var color = document.getElementById('color').value;
        if (!color) {
            document.getElementById('colorError').textContent = 'Seleccione un color.';
            document.getElementById('colorError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar patente
        var patente = document.getElementById('patente').value.toUpperCase(); // Convertir a mayúsculas
        var patenteRegex = /^[A-Z]{3}\s?\d{3}$|^[A-Z]{2}\s?\d{3}\s?[A-Z]{2}$/;
        if (!patente) {
            document.getElementById('patenteError').textContent = 'La patente es obligatoria.';
            document.getElementById('patenteError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        } else if (!patenteRegex.test(patente)) {
            document.getElementById('patenteError').textContent = 'La patente debe tener el formato "AAA 111" o "AA 111 AA".';
            document.getElementById('patenteError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        } else {
            // Guardar el valor en el campo después de la validación
            document.getElementById('patente').value = patente;
        }

        // Validar peso soportado
        var pesoSoportado = document.getElementById('pesoSoportado').value;
        if (!pesoSoportado || isNaN(pesoSoportado) || parseFloat(pesoSoportado) <= 0) {
            document.getElementById('pesoSoportadoError').textContent = 'Ingrese un peso soportado válido en kilogramos (mayor a 0).';
            document.getElementById('pesoSoportadoError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar dimension disponible
        var dimensionDisponible = document.getElementById('dimensionDisponible').value;
        if (!dimensionDisponible || isNaN(dimensionDisponible) || parseFloat(dimensionDisponible) <= 0) {
            document.getElementById('dimensionDisponibleError').textContent = 'Ingrese una dimensión válida en cm³ (mayor a 0).';
            document.getElementById('dimensionDisponibleError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        if (!valid) {
            event.preventDefault(); // Evita que se envíe el formulario si hay errores
        }
    });

    var botonesCancelar = document.querySelectorAll('.boton_cancelar');
    botonesCancelar.forEach(function(botonCancelar) {
        botonCancelar.addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = botonCancelar.closest('a').getAttribute('href');
        });
    });
});
