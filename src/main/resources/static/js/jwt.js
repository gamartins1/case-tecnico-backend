$(document).ready(function() {
    $("#form-validate-jwt").submit(function (event) {
        event.preventDefault();

        const jwt = $('#jwtField').val();
        if(jwt === "") {
            alert('Digite algum JWT para realizar a validação');
            return;
        }

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/v1/validarJwt",
            data: jwt,
            dataType: 'json',
            cache: false,
            timeout: 50000,
            error: function (e) {
                alert(e);
            },
            success: function (data,status,xhr) {
                if(Boolean(data)) {
                    alert("Resposta da validação recebida: '" + data + "'. O JWT é válido");
                }
                else {
                    alert("Resposta da validação recebida: '" + data + "'. O JWT é inválido");
                }
            }
        });
        $('#jwtField').val("");
    });
});