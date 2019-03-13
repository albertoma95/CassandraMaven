/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import com.datastax.driver.core.querybuilder.Select;
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
    private static final String NOMBRE_TABLA_INCIDENCIA = "incidencia";
    private String NUSUARIO_COL = "nusuario", APELLIDO_COL = "apellido",
            EDAD_COL = "edad", NOMBRE_COL = "nombre", PASSWORD_COL = "password";

    private String ID_COL = "id", ORIGEN_COL = "origen", DESTINO_COL = "destino", FECHA_COL = "fecha",
            ESTADO_COL = "estado", DESCRIPCION_COL = "descripcion", URGENTE_COL = "urgente";

    public CassandraDAO() {
        cassandraConnector = CassandraConnector.getInstance();
    }

    public static CassandraDAO getInstance() {
        if (cassandraDAO == null) {
            cassandraDAO = new CassandraDAO();
        }
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
        Session session = cassandraConnector.getSession();
        Delete.Where delete = QueryBuilder.delete()
                .from(NOMBRE_DATABASE, NOMBRE_TABLA)
                .where(eq(NUSUARIO_COL, e.getNusuario()));
        System.out.println(delete);
        ResultSet result = session.execute(delete);
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
    public void insertOrUpdateIncidencia(Incidencia i) {
        Session session = cassandraConnector.getSession();
        Insert insert = QueryBuilder.insertInto(NOMBRE_DATABASE, NOMBRE_TABLA_INCIDENCIA)
                .value(ID_COL, i.getId())
                .value(ORIGEN_COL, i.getOrigen().getNusuario())
                .value(DESTINO_COL, i.getDestino().getNusuario())
                .value(FECHA_COL, i.getFecha())
                .value(ESTADO_COL, i.getEstado())
                .value(DESCRIPCION_COL, i.getDescripcion())
                .value(URGENTE_COL, i.isUrgente());
        System.out.println(insert.toString());
        ResultSet result = session.execute(insert.toString());
    }
    
    @Override
    public void removeIncidencia(Incidencia i){
        Session session = cassandraConnector.getSession();
        Delete.Where delete = QueryBuilder.delete()
                .from(NOMBRE_DATABASE, NOMBRE_TABLA_INCIDENCIA)
                .where(eq(ID_COL, i.getId()));
        System.out.println(delete);
        ResultSet result = session.execute(delete);
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

    @Override
    public Empleado getEmpleadoByNusuario(String nUsuario) {
        Session session = cassandraConnector.getSession();
        Select selectQuery = QueryBuilder.select().all().from(NOMBRE_DATABASE, NOMBRE_TABLA);
        Select.Where selectWhere = selectQuery.where();
        Clause clause = QueryBuilder.eq(NUSUARIO_COL, nUsuario);
        selectWhere.and(clause).limit(1);
        ResultSet results = session.execute(selectQuery);
        Row row = results.one();
        if(row == null) return null;
        
        Empleado empleado = new Empleado();
        empleado.setNusuario(row.getString(NUSUARIO_COL));
        empleado.setNombre(row.getString(NOMBRE_COL));
        empleado.setApellido(row.getString(APELLIDO_COL));
        empleado.setPassword(row.getString(PASSWORD_COL));
        empleado.setEdad(row.getInt(EDAD_COL));
        return empleado;
    }

}
