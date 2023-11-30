package proyecto.cuentas.servicio;

import proyecto.cuentas.modelo.Cuenta;

import java.util.List;

public interface ICuentaServicio {


    public List<Cuenta> listarCuentas();

    public Cuenta buscarCuentaPorId(Integer idCuenta);

    public void guardarCuenta(Cuenta cuenta);

    public void borrarCuenta(Cuenta cuenta);
}
