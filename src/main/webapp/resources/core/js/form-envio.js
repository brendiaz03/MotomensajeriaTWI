document.addEventListener('DOMContentLoaded', () => {
    const pasos = document.querySelectorAll('.paso');
    const steps = document.querySelectorAll('.step');
    let currentStep = 0;

    pasos.forEach((paso, index) => {
        paso.addEventListener('click', () => {
            updateStep(index);
        });
    });

    document.getElementById('formPaquete').addEventListener('submit', (event) => {
        event.preventDefault();
        const peso = document.getElementById('peso').value;
        const dimension = document.getElementById('dimension').value;
        const esFragil = document.querySelector('input[name="esFragil"]:checked').value;

        if (peso && dimension && esFragil) {
            updateStep(1);
        } else {
            document.getElementById('errorPaquete').style.display = 'block';
            document.getElementById('errorPaquete').textContent = 'Todos los campos son obligatorios.';
        }
    });

    document.getElementById('formViaje').addEventListener('submit', (event) => {
        event.preventDefault();
        const direccionSalida = document.getElementById('direccionSalida').value;
        const direccionLlegada = document.getElementById('direccionLlegada').value;

        if (direccionSalida && direccionLlegada) {
            document.getElementById('detalleSalida').textContent = direccionSalida;
            document.getElementById('detalleLlegada').textContent = direccionLlegada;
            updateStep(2);
        }
    });

    function updateStep(step) {
        steps.forEach((stepElement, index) => {
            if (index === step) {
                stepElement.style.display = 'block';
                pasos[index].classList.add('active');
            } else {
                stepElement.style.display = 'none';
                pasos[index].classList.remove('active');
            }
        });
        currentStep = step;
    }

    // Set the initial step to be visible and active
    steps[0].style.display = 'block';
    pasos[0].classList.add('active');
});
