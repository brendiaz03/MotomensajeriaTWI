function eliminarViaje(boton) {
    const viajeDiv = boton.closest(".viaje");
    viajeDiv.parentNode.removeChild(viajeDiv);
}