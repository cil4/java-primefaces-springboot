package proyecto.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.cuentas.modelo.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
}
