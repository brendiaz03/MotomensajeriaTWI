document.addEventListener('DOMContentLoaded', function () {
    const penalizacionElemento = document.querySelector('.isPenalizado');
    const horaPenalizacion = penalizacionElemento.getAttribute('data-penalizacion');
    const penalizacionDuracion = 60 * 1000; // 1 hora en milisegundos
    const penalizacionFin = new Date(new Date(horaPenalizacion).getTime() + penalizacionDuracion);

    function actualizarContador() {
        const ahora = new Date();
        const tiempoRestante = penalizacionFin - ahora;

        if (tiempoRestante <= 0) {
            document.getElementById('contador').textContent = '0 minutos y 0 segundos';
            document.getElementById('formDespenalizar').submit();
        } else {
            const horas = Math.floor(tiempoRestante / (1000 * 60 * 60));
            const minutos = Math.floor((tiempoRestante % (1000 * 60 * 60)) / (1000 * 60));
            const segundos = Math.floor((tiempoRestante % (1000 * 60)) / 1000);

            document.getElementById('contador').textContent = `${horas} horas, ${minutos} minutos y ${segundos} segundos`;
        }
    }

    actualizarContador(); // Llamar inmediatamente para mostrar el contador sin esperar el primer intervalo
    setInterval(actualizarContador, 1000);
});
