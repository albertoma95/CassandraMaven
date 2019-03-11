/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import config.CassandraConnector;
import java.util.List;
import model.Empleado;
import model.Historial;
import model.Incidencia;
import model.Ranking;

/**
 *
 * @author avalldeperas
 */
public class CassandraDAO implements DaoImpl {
    
    private CassandraConnector cassandraConnector;
    private static CassandraDAO cassandraDAO;
    
    private static final String NOMBRE_DATABASE = "stucom_incidencias";
    private static final String NOMBRE_TABLA = "empleado";
    private String NUSUARIO_COL = "nusuario", APELLIDO_COL = "apellido", 
            EDAD_COL = "edad", NOMBRE_COL = "nombre", PASSWORD_COL = "password";

    public CassandraDAO() {
        cassandraConnector = CassandraConnector.getInstance();
    }
    
     public static CassandraDAO getInstance() {
        if(cassandraDAO == null)
            cassandraDAO = new CassandraDAO();
        return cassandraDAO;
    }
    
    @Override
    public void saveOrUpdateEmpleado(Empleado e) {
        Session session = cassandraConnector.getSession();
        
        Insert insert = QueryBuilder.insertInto(NOMBRE_DATABASE, NOMBRE_TABLA)
                                    .value(NUSUARIO_COL, e.getNusuario())
                                    .value(APELLIDO_COL, e.getApellido())
                                    .value(EDAD_COL, e.getEdad())
                                    .value(NOMBRE_COL, e.getNombre())
                                    .value(PASSWORD_COL, e.getPassword());
        System.out.println(insert.toString());
        ResultSet result = session.execute(insert.toString());
        // System.out.println(result.getExecutionInfo().getQueryTrace());
    }

    @Override
    public boolean loginEmpleado(String user, String pass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeEmpleado(Empleado e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Incidencia getIncidenciaById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Incidencia> selectAllIncidencias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertIncidencia(Incidencia i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Incidencia> getIncidenciaByDestino(Empleado e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Incidencia> getIncidenciaByOrigen(Empleado e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarEvento(Historial e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Historial getUltimoInicioSesion(Empleado e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ranking> getRankingEmpleados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
