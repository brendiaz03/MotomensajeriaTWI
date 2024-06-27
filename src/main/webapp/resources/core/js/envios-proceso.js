function confirmarEliminacion(event) {
    event.preventDefault(); // Evitar el envío automático del formulario

    if (confirm("¿Está seguro que desea cancelar este envío?")) {
        // Si el usuario confirma, encontrar y enviar el formulario
        const form = event.target.closest('form');
        if (form) {
            form.submit();
        } else {
            console.error("No se pudo encontrar el formulario");
        }
    } else {
        // Si el usuario cancela, no hacer nada
        return false;
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const items = document.querySelectorAll('.tarjeta-viaje-proceso');

    items.forEach(item => {
        const toggleButton = item.querySelector('.toggle-info');
        const extraInfo = item.querySelector('.info-extra-envio');

        toggleButton.addEventListener('click', () => {
            if (extraInfo.classList.contains('active')) {
                extraInfo.classList.remove('active');
                toggleButton.classList.remove('active');
            } else {
                extraInfo.classList.add('active');
                toggleButton.classList.add('active');
            }
        });
    });

    // Agregar el evento onclick a los botones de eliminación
    const deleteButtons = document.querySelectorAll('.icon-button');
    deleteButtons.forEach(button => {
        button.addEventListener('click', confirmarEliminacion);
    });
});
