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

        // Validar peso
        var peso = document.getElementById('peso').value;
        if (!peso || isNaN(peso) || parseFloat(peso) <= 0) {
            document.getElementById('pesoError').textContent = 'Ingrese un peso válido (mayor a 0).';
            document.getElementById('pesoError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar esFragil
        var esFragilSi = document.getElementById('esFragilSi').checked;
        var esFragilNo = document.getElementById('esFragilNo').checked;
        if (!esFragilSi && !esFragilNo) {
            document.getElementById('esFragilError').textContent = 'Seleccione si el paquete es frágil o no.';
            document.getElementById('esFragilError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar dimension
        var dimension = document.getElementById('dimension').value;
        if (!dimension || isNaN(dimension) || parseFloat(dimension) <= 0) {
            document.getElementById('dimensionError').textContent = 'Ingrese una dimensión válida (mayor a 0).';
            document.getElementById('dimensionError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        // Validar destinatario
        var destinatario = document.getElementById('destinatario').value;
        if (!destinatario) {
            document.getElementById('destinatarioError').textContent = 'Ingrese el nombre del destinatario.';
            document.getElementById('destinatarioError').style.display = 'block'; // Mostrar el mensaje de error
            valid = false;
        }

        if (!valid) {
            event.preventDefault(); // Evita que se envíe el formulario si hay errores
        }
    });

});