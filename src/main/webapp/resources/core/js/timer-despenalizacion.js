document.addEventListener('DOMContentLoaded', function () {
    const penalizacionElemento = document.querySelector('.isPenalizado');
    const horaPenalizacion = penalizacionElemento.getAttribute('data-penalizacion');
    const penalizacionDuracion = 60 * 1000; // 60 segundos en milisegundos para pruebas
    const penalizacionFin = new Date(new Date(horaPenalizacion).getTime() + penalizacionDuracion);

    function actualizarContador() {
        const ahora = new Date();
        const tiempoRestante = penalizacionFin - ahora;

        if (tiempoRestante <= 0) {
            document.getElementById('contador').textContent = '0 horas';
            document.getElementById('formDespenalizar').submit();
        } else {
            const minutos = Math.floor((tiempoRestante % (1000 * 60 * 60)) / (1000 * 60));
            const segundos = Math.floor((tiempoRestante % (1000 * 60)) / 1000);

            document.getElementById('contador').textContent = `${minutos} minutos y ${segundos} segundos`;
        }
    }

    setInterval(actualizarContador, 1000);
});