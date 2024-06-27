$(document).ready(function () {
    // Manejar clic en el botón "Pagar"
    $("#pagar").click(function () {
        // Llamar a la función para generar la preferencia de pago
        generarPreferencia(precioTotal);
    });
});

function generarPreferencia(precioTotal) {
    console.log("Iniciando solicitud AJAX...");

    $.ajax({
        method: "POST",
        url: "/preferencia",
        data: { precioTotal: precioTotal }
    }).done(function(response) {
        console.log("Respuesta del servidor:", response);

        const mp = new MercadoPago('APP_USR-3efd2cac-65f7-475c-8fb6-3beb5bdda103', {
            locale: 'es-AR'
        });

        console.log("MercadoPago inicializado");

        mp.bricks();

        mp.bricks().create("wallet", "wallet_container", {
            initialization: {
                preferenceId: response,
                redirectMode: "blank"
            },
        }).catch(function(error) {
            console.log("Error al crear la wallet:", error);
        });
    }).fail(function(xhr, status, error) {
        console.log("Error en la solicitud AJAX:", error);
        console.log("Estado:", status);
        console.log("Respuesta del servidor:", xhr.responseText);
    });
}

function irAlHome(){
    window.location.href="/home-cliente";
}
