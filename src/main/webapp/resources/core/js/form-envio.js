document.addEventListener('DOMContentLoaded', function () {
    const steps = document.querySelectorAll('.step');
    let currentStep = 0;

    function showStep(step) {
        steps.forEach((stepElement, index) => {
            stepElement.classList.toggle('active', index === step);
        });
    }

    document.getElementById('paqueteForm').addEventListener('submit', function (e) {
        e.preventDefault();
        // Aquí puedes agregar la lógica para enviar los datos del paquete al servidor
        // Luego de recibir la respuesta exitosa, avanzar al siguiente paso:
        currentStep++;
        showStep(currentStep);
    });

    document.getElementById('viajeForm').addEventListener('submit', function (e) {
        e.preventDefault();
        // Aquí puedes agregar la lógica para enviar los datos del viaje al servidor
        // Luego de recibir la respuesta exitosa, avanzar al siguiente paso:
        currentStep++;
        showStep(currentStep);
    });

    document.getElementById('confirmar').addEventListener('click', function () {
        // Aquí puedes agregar la lógica para confirmar los detalles
        currentStep++;
        showStep(currentStep);
    });

    document.getElementById('editarPaquete').addEventListener('click', function () {
        currentStep = 0;
        showStep(currentStep);
    });

    document.getElementById('editarViaje').addEventListener('click', function () {
        currentStep = 1;
        showStep(currentStep);
    });
});