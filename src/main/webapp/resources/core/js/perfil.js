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

    document.getElementById("form-cerrar-cuenta").addEventListener("submit", function(event) {
        event.preventDefault(); // Evita que el formulario se envíe automáticamente

        if (confirm("¿Estás seguro de que deseas cerrar tu cuenta?")) {
            // Si el usuario confirma, redirige utilizando la URL generada por Thymeleaf
            window.location.href = this.getAttribute("action");
        }
    });

});