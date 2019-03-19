var maxCaracteres = 100;
document.getElementById("enviar").addEventListener("click",validarPedido);
function compararCadena(nombre, comparacion){
	if (nombre == comparacion)
		return true;
	return false;
}
function compararLongitud(nombre, longitud){
	if (nombre>longitud)
		return true;
	return false;
}
function validarEntero(entero){
	if (entero == parseInt(entero, 10))
		return true;
	else {
		alert(entero + " no es un Entero")
		return false;
	}
}
function validarNombre(nombre){    
    if (compararCadena(nombre,"")){
		alert("El campo Nombre no puede estar vacío");
		return false;
  } else {      
		if (compararLongitud(nombre["length"],maxCaracteres)){
            alert("El campo Nombre no puede superar los 100 caracteres: "+nombre["length"]);
            return false;
        } else
        return true;
    }
}
function validarPedido(){
	var pedido = {
		/* Faltaría obtener el idPedido */
		nombre: document.getElementById("nombre").value,
		monto: document.getElementById("monto").value,
		descuento: document.getElementById("descuento").value
	};
    var xhr = new XMLHttpRequest();
 
	if (validarNombre(pedido.nombre))
		if (validarEntero(pedido.monto) && validarEntero(pedido.descuento)){			
		    xhr.open("POST", "/pedidos/guardar");
		    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		    xhr.send(pedido);
		}
 
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            document.getElementById("respuesta").innerHTML = xhr.responseText;
        }
    }
}