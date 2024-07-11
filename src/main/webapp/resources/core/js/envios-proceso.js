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

document.addEventListener("DOMContentLoaded", function() {
    const confirmPopup = document.getElementById("confirm-popup");
    const openPopupButtons = document.querySelectorAll(".open-confirm-popup");
    const closePopupButtons = document.querySelectorAll("#close-popup");
    const popupIdViaje = document.getElementById("popup-idViaje");

    openPopupButtons.forEach(button => {
        button.addEventListener("click", function() {
            const idViaje = this.getAttribute("data-id");
            popupIdViaje.value = idViaje;
            confirmPopup.classList.add("show");
        });
    });

    closePopupButtons.forEach(button => {
        button.addEventListener("click", function() {
            confirmPopup.classList.remove("show");
        });
    });
});
