/*<![CDATA[*/
document.addEventListener('DOMContentLoaded', function() {
    var form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        var password = document.getElementById('password').value;
        var confirmarPassword = document.getElementById('confirmarPassword').value;

        if (password !== confirmarPassword) {
            event.preventDefault();
            alert('La contraseña y la confirmación de la contraseña no coinciden.');
            return;
        }

        var dniInput = document.getElementById('numeroDeDni');
        var dni = parseInt(dniInput.value);

        // Validar DNI
        if (dni < 1000000 || dni > 99999999 || isNaN(dni)) {
            event.preventDefault();
            alert('Ingrese un número de DNI válido.');
            return;
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
/*]]>*/