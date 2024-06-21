document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('conductorForm');

    form.addEventListener('submit', function(event) {
        // Reiniciar mensajes de error
        var errors = document.querySelectorAll('.error');
        errors.forEach(function(error) {
            error.textContent = '';
        });

        var valid = true;

        // Validar nombre
        var nombre = document.getElementById('nombre').value;
        if (!/^[a-zA-Z\u00C0-\u017F\s]+$/.test(nombre)) {
            document.getElementById('nombreError').textContent = 'El nombre no puede contener números ni caracteres especiales.';
            valid = false;
        }

        // Validar apellido
        var apellido = document.getElementById('apellido').value;
        if (!/^[a-zA-Z\u00C0-\u017F\s]+$/.test(apellido)) {
            document.getElementById('apellidoError').textContent = 'El apellido no puede contener números ni caracteres especiales.';
            valid = false;
        }

        // Validar domicilio
        var domicilio = document.getElementById('domicilio').value;
        if (!domicilio) {
            document.getElementById('domicilioError').textContent = 'El domicilio es obligatorio.';
            valid = false;
        }

        // Validar número de teléfono
        var nroTelefono = document.getElementById('nroTelefono').value;
        if (!/^\d+$/.test(nroTelefono)) {
            document.getElementById('nroTelefonoError').textContent = 'El número de teléfono solo puede contener números.';
            valid = false;
        }

        // Validar número de DNI
        var numeroDeDni = document.getElementById('numeroDeDni').value;
        if (!/^\d+$/.test(numeroDeDni) || numeroDeDni.length < 7 || numeroDeDni.length > 8) {
            document.getElementById('numeroDeDniError').textContent = 'Ingrese un número de DNI válido (7 u 8 dígitos).';
            valid = false;
        }

        // Validar nombre de usuario
        var nombreUsuario = document.getElementById('nombreUsuario').value;
        if (!nombreUsuario) {
            document.getElementById('nombreUsuarioError').textContent = 'El nombre de usuario es obligatorio.';
            valid = false;
        }

        // Validar email
        var email = document.getElementById('email').value;
        if (!email) {
            document.getElementById('emailError').textContent = 'El email es obligatorio.';
            valid = false;
        }

        // Validar contraseña y confirmación de contraseña
        var password = document.getElementById('password').value;
        var confirmarPassword = document.getElementById('confirmarPassword').value;
        if (!password) {
            document.getElementById('passwordError').textContent = 'La contraseña es obligatoria.';
            valid = false;
        }
        if (password !== confirmarPassword) {
            document.getElementById('confirmarPasswordError').textContent = 'La contraseña y la confirmación de la contraseña no coinciden.';
            valid = false;
        }

        // Validar tipo de usuario
        var tipoUsuario = document.getElementById('tipoUsuario').value;
        if (!tipoUsuario) {
            document.getElementById('tipoUsuarioError').textContent = 'Seleccione un tipo de usuario.';
            valid = false;
        }

        if (!valid) {
            event.preventDefault();
        }
    });

    var botonCancelarEdit = document.getElementById('boton_cancelar_edit');
    if (botonCancelarEdit) {
        botonCancelarEdit.addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = botonCancelarEdit.getAttribute('href');
        });
    }
    var botonCancelarIngresar = document.getElementById('boton_cancelar_ingresar');
    if (botonCancelarIngresar) {
        botonCancelarIngresar.addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = botonCancelarIngresar.getAttribute('href');
        });
    }
});
