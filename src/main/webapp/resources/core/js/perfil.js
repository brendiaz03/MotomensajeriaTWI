document.addEventListener('DOMContentLoaded', function () {
    // Obtenemos todas las opciones
    const options = document.querySelectorAll('.option');
    // Obtenemos todas las tarjetas de la derecha
    const cards = document.querySelectorAll('.card[id]');

    // Función para mostrar la tarjeta seleccionada y ocultar las demás
    function showCard(targetId) {
        cards.forEach(card => {
            if (card.id === targetId) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });
    }

    // Añadimos el evento click a cada opción
    options.forEach(option => {
        option.addEventListener('click', function () {
            // Quitamos la clase 'active' de todas las opciones
            options.forEach(opt => opt.classList.remove('active'));
            // Añadimos la clase 'active' solo a la opción seleccionada
            this.classList.add('active');
            // Obtenemos el valor del atributo data-target de la opción seleccionada
            const targetId = this.dataset.target;
            // Mostramos la tarjeta correspondiente
            showCard(targetId);
        });
    });

    // Mostramos la primera tarjeta por defecto
    const defaultOption = document.querySelector('.option');
    if (defaultOption) {
        const defaultTarget = defaultOption.dataset.target;
        showCard(defaultTarget);
    }
    // Obtener el enlace de cerrar cuenta y agregar evento de clic
    var confirmLink = document.querySelector('.confirm-link');
    if (confirmLink) {
        confirmLink.addEventListener('click', function(event) {
            event.preventDefault(); // Prevenir el comportamiento por defecto del enlace

            // Mostrar el popup de confirmación
            openPopup();
        });
    }

    // Función para mostrar el popup de confirmación
    function openPopup() {
        document.getElementById('confirmPopup').style.display = 'flex';
    }

    // Función para cerrar el popup de confirmación
    function closePopup() {
        document.getElementById('confirmPopup').style.display = 'none';
    }

    // Event listener para el enlace de "Cerrar Cuenta"
    document.querySelectorAll('.confirm-link').forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault(); // Prevenir el comportamiento por defecto del enlace
            openPopup(); // Mostrar el popup de confirmación
        });
    });

    // Event listener para el botón "Sí" dentro del popup de confirmación
    document.getElementById('confirmYes').addEventListener('click', function () {
            document.getElementById("form-cerrar-cuenta").submit(); // Enviar el formulario si se confirma
        closePopup(); // Cerrar el popup después de la confirmación
    });

    // Event listener para el botón "No" dentro del popup de confirmación
    document.getElementById('confirmNo').addEventListener('click', function () {
        closePopup(); // Cerrar el popup si se selecciona "No"
    });
});