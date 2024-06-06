// document.addEventListener('DOMContentLoaded', () => {
//     const pasos = document.querySelectorAll('.paso');
//     const steps = document.querySelectorAll('.step');
//     let currentStep = 0;
//
//     pasos.forEach((paso, index) => {
//         paso.addEventListener('click', () => {
//             updateStep(index);
//         });
//     });
//
//     document.getElementById('viajeForm').addEventListener('submit', (event) => {
//         event.preventDefault();
//         updateStep(2); // Paso 3 después de enviar el formulario
//     });
//
//     function updateStep(step) {
//         steps[currentStep].classList.remove('active');
//         pasos[currentStep].classList.remove('active');
//         currentStep = step;
//         steps[currentStep].classList.add('active');
//         pasos[currentStep].classList.add('active');
//
//         if (currentStep === 2) {
//             document.getElementById('detalleSalida').textContent = document.getElementById('direccionSalida').value;
//             document.getElementById('detalleLlegada').textContent = document.getElementById('direccionLlegada').value;
//         }
//     }
//
//     // Inicia en el primer paso
//     updateStep(0);
// });

import React, { useState } from 'react';

// Componente Paso, que es solo visual
const Paso = ({ pasoActual, numeroPaso, children }) => {
    const clase = pasoActual === numeroPaso ? 'activo' : 'inactivo';
    return <div className={`paso ${clase}`}>{children}</div>;
};

const App = () => {
    const [pasoActual, setPasoActual] = useState(1);
    const [isEditViaje, setIsEditViaje] = useState(false);

    const handleCrearPaquete = () => {
        setPasoActual(2);
    };

    const handleEditarPaquete = () => {
        setIsEditViaje(true);
        setPasoActual(3);
    };

    const handleEditarViaje = () => {
        setIsEditViaje(true);
        setPasoActual(3);
    };

    const handleSiguientePaso = () => {
        if (pasoActual < 3) {
            setPasoActual(pasoActual + 1);
        }
    };

    const handlePasoAnterior = () => {
        if (pasoActual > 1) {
            setPasoActual(pasoActual - 1);
        }
    };

    return (
        <div>
            <div className="navegacion-pasos">
                <Paso pasoActual={pasoActual} numeroPaso={1}>
                    Paso 1
                </Paso>
                <Paso pasoActual={pasoActual} numeroPaso={2}>
                    Paso 2
                </Paso>
                <Paso pasoActual={pasoActual} numeroPaso={3}>
                    Paso 3
                </Paso>
            </div>

            <div className="contenido">
                {pasoActual === 1 && (
                    <div>
                        <h2>Paso 1: Información del paquete</h2>
                        <button onClick={handleCrearPaquete}>Crear Paquete</button>
                        <button onClick={handleEditarViaje}>Editar Viaje</button>
                    </div>
                )}

                {pasoActual === 2 && (
                    <div>
                        <h2>Paso 2: Detalles del paquete</h2>
                        <button onClick={handleSiguientePaso}>Siguiente</button>
                        <button onClick={handleEditarPaquete}>Editar Paquete</button>
                    </div>
                )}

                {pasoActual === 3 && (
                    <div>
                        <h2>Paso 3: Confirmación</h2>
                        <button onClick={handlePasoAnterior}>Paso Anterior</button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default App;


