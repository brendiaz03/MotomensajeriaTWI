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

document.addEventListener('DOMContentLoaded', function () {
    const openPopupButtons = document.querySelectorAll('.open-confirm-popup');
    const confirmPopup = document.getElementById('confirm-popup');
    const closePopupButton = document.getElementById('close-popup');
    const popupForm = document.getElementById('confirm-form');
    const popupIdInput = document.getElementById('popup-idViaje');

    openPopupButtons.forEach(button => {
        button.addEventListener('click', function () {
            const viajeId = this.getAttribute('data-id');
            popupIdInput.value = viajeId;
            confirmPopup.classList.add('show');
        });
    });

    closePopupButton.addEventListener('click', function () {
        confirmPopup.classList.remove('show');
    });
});
