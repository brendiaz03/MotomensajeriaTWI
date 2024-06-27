
document.addEventListener("DOMContentLoaded", () => {
    const item = document.querySelector('.contenedor-filtro');
    const pregunta = item.querySelector('.pregunta_acordeon');

    pregunta.addEventListener('click', () => {
        item.classList.toggle('active');
    });
});