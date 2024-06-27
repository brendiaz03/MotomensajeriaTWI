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
});