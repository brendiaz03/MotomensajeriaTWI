document.addEventListener("DOMContentLoaded", () => {
    // Acordeón
    const items = document.querySelectorAll('.item_acordeon');

    items.forEach(item => {
        const pregunta = item.querySelector('.pregunta_acordeon');

        pregunta.addEventListener('click', () => {
            const currentlyActive = document.querySelector('.item_acordeon.active');
            if (currentlyActive && currentlyActive !== item) {
                currentlyActive.classList.remove('active');
            }
            item.classList.toggle('active');
        });
    });

    // Modal
    const modal = document.getElementById('viajeCanceladoModal');
    if (modal) {
        modal.classList.add('show');
    }
});
