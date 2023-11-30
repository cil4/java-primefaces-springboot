package proyecto.cuentas.controlador;


import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proyecto.cuentas.modelo.Cuenta;
import proyecto.cuentas.servicio.CuentaServicio;

import java.util.List;

@Component
@Data
@ViewScoped
public class IndexControlador {

@Autowired
    CuentaServicio cuentaServicio;

private List<Cuenta> cuentas;

private Cuenta cuentaSeleccionada;

private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

@PostConstruct
    public void init(){
    cargarDatos();
}

public void cargarDatos(){
    this.cuentas=cuentaServicio.listarCuentas();
    cuentas.forEach((cuenta)->logger.info(cuenta.toString()));
}

public void agregarCuenta(){
    logger.info("Se crea objeto cuentaSeleccionada para el caso de Agregar");
    this.cuentaSeleccionada= new Cuenta();

}

public void guardarCuenta(){
    logger.info("Cuenta a pagar " + this.cuentaSeleccionada);
    //Agregar
    if(this.cuentaSeleccionada.getIdCuenta()==null){
        this.cuentaServicio.guardarCuenta(this.cuentaSeleccionada);
        this.cuentas.add(this.cuentaSeleccionada);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cuenta agregada"));

    }else{ //Editar
        this.cuentaServicio.guardarCuenta(this.cuentaSeleccionada);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cuenta actualizada"));

    }
    //Ocultar la ventana
    PrimeFaces.current().executeScript("PF('ventanaModalCuenta').hide()");
    //Actualizar la tabla
    PrimeFaces.current().ajax().update("forma-cuentas:mensajes",
            "forma-cuentas:cuentas-tabla");
    //Reset
    this.cuentaSeleccionada=null;
}
public void eliminarCuenta(){
    logger.info("Cuenta a eliminar " + this.cuentaSeleccionada);

    this.cuentaServicio.borrarCuenta(this.cuentaSeleccionada);
    //Eliminar el registro de la lista de cuentas
    this.cuentas.remove(this.cuentaSeleccionada);
    //Se hace un reset del objeto seleccionado de la tabla
    this.cuentaSeleccionada=null;
    FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Cuenta eliminada"));

    PrimeFaces.current().ajax().update("forma-cuentas:mensajes",
            "forma-cuentas:cuentas-tabla");
}
}
