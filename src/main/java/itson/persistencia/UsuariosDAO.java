package itson.persistencia;

import itson.entidades.Usuario;
import itson.usuariosDTOs.NuevoUsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author juanpheras
 */
public class UsuariosDAO {

    private final ManejadorConexiones manejadorConexiones;

    public UsuariosDAO(ManejadorConexiones manejadorConexiones) {
        this.manejadorConexiones = manejadorConexiones;
    }

    public Usuario registrarUsuario(NuevoUsuarioDTO usuarioDTO) {
        String codigoSQL = """
            INSERT INTO usuarios(NOMBRE,APELLIDOPATERNO, APELLIDOMATERNO, CORREOELECTRONICO, CONTRASEÑA_HASH,
                           FECHA_NACIMIENTO, CIUDAD, CALLE, COLONIA, NUMERO)
                                                  VALUES(?, ?, ? , ?,?,?, ?, ?, ?, ?);
                           """;

        try {
            //establece la conexion con la base de datos
            Connection conexion = manejadorConexiones.crearConexion();
            //Construye un comando SQL
            PreparedStatement comando = conexion.prepareStatement(codigoSQL);
            comando.setString(1, usuarioDTO.getNombre());
            comando.setString(2, usuarioDTO.getApellidoPaterno());
            comando.setString(3, usuarioDTO.getApellidoMaterno());
            comando.setString(4, usuarioDTO.getCorreoElectronico());
            comando.setString(5, usuarioDTO.getContraseniaHash());
            comando.setString(6, usuarioDTO.getFechaNacimiento());
            comando.setString(7, usuarioDTO.getCiudad());
            comando.setString(8, usuarioDTO.getCalle());
            comando.setString(9, usuarioDTO.getColonia());
            comando.setString(10, usuarioDTO.getNumero());

            // Ejecutar la inserción
        int filasAfectadas = comando.executeUpdate();
        
        if (filasAfectadas > 0) {
            // Si se registró correctamente, devolver el usuario
            return new Usuario(usuarioDTO.getNombre(), usuarioDTO.getApellidoPaterno(),
                    usuarioDTO.getApellidoMaterno(), usuarioDTO.getCorreoElectronico(),
                    usuarioDTO.getCiudad(),usuarioDTO.getCalle(), usuarioDTO.getColonia(), usuarioDTO.getNumero());  //

        } else {
            return null;  // Si no se registró, devolver null o manejar el error de otra manera
        }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }

        
    }

}
