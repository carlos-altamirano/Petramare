function Main() {
    
    this.getQueryString = function (name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    };
    
    this.obtieneAjax = function(url, tipo, datos, asinc) {
        return $.ajax({
            url: url,
            type: tipo,
            data: datos,
            async: asinc
        }).responseText;
    };
    
    this.creaModal = function(selector, clase, titulo, contenido) {
        var clases = ['success', 'alert', 'secondary'];
        
        for (var i = 0; i < clases.length; i++) {
            $('#'+selector+'Boton').removeClass(clases[i]);
            $('#'+selector+'Titulo').removeClass(clases[i]);
        }
        $('#'+selector+'Boton').addClass(clase);
        
        $('#'+selector+'Titulo').text(titulo);
        if (clase === 'warning') {
            $('#'+selector+'Titulo').css('color', '#ffae00');
        }
        if (clase === 'alert') {
            $('#'+selector+'Titulo').css('color', 'red');
        }
        if (clase === 'success') {
            $('#'+selector+'Titulo').css('color', 'green');
        }
        if (this.contenido === null) {
            $('#'+selector+'Contenido').html('');
            $('#'+selector+'Contenido').fadeOut('fast');
        } else {
            $('#'+selector+'Contenido').html(contenido);
            $('#'+selector+'Contenido').fadeIn('fast');
        }
        
        $('#'+selector).foundation('open');
    };
    
};