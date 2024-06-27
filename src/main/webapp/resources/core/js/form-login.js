document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('loginForm');

    form.addEventListener('submit', function(event) {
        var valid = true;

        // Reiniciar mensajes de error
        document.getElementById('usuarioError').textContent = '';
        document.getElementById('passwordError').textContent = '';

        var usuario = document.getElementById('usuario').value.trim();
        var password = document.getElementById('password').value.trim();

        if (!usuario) {
            mostrarError('usuarioError', 'Es obligatorio ingresar el nombre de usuario.');
            valid = false;
        }

        if (!password) {
            mostrarError('passwordError', 'Es obligatorio ingresar la contraseña.');
            valid = false;
        }

        if (!valid) {
            event.preventDefault(); // Evita que se envíe el formulario si hay errores
        }
    });

    function mostrarError(id, mensaje) {
        var errorElement = document.getElementById(id);
        errorElement.textContent = mensaje;
    }
});