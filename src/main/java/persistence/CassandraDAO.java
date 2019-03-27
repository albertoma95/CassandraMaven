/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import com.datastax.driver.core.querybuilder.Select;
import config.CassandraConnector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Empleado;
import model.Historial;
import model.Incidencia;
import model.Ranking;
import utilities.MyUtilities;

/**
 *
 * @author avalldeperas
 */
public class CassandraDAO implements DaoImpl {

    private CassandraConnector cassandraConnector;
    private static CassandraDAO cassandraDAO;

    private static final String NOMBRE_DATABASE = "stucom_incidencias";
    private static final String NOMBRE_TABLA_EMPLEADO = "empleado";
    private static final String NOMBRE_TABLA_INCIDENCIA = "incidencia";
    private String NUSUARIO_COL = "nusuario", APELLIDO_COL = "apellido",
            EDAD_COL = "edad", NOMBRE_COL = "nombre", PASSWORD_COL = "password";

    private String ID_INC_COL = "id", ORIGEN_COL = "origen", DESTINO_COL = "destino", FECHA_COL = "fecha",
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
        Insert insert = QueryBuilder.insertInto(NOMBRE_DATABASE, NOMBRE_TABLA_EMPLEADO)
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
    public boolean loginEmpleado(String nusuario, String password) {
        Session session = cassandraConnector.getSession();
        Statement s = QueryBuilder.select().all()
                .from(NOMBRE_TABLA_EMPLEADO)
                .where(eq(NUSUARIO_COL, nusuario))
                .and(eq(PASSWORD_COL, password));

        Select selectQuery = QueryBuilder.select().all().from(NOMBRE_DATABASE, NOMBRE_TABLA_EMPLEADO);
        selectQuery.allowFiltering();
        Select.Where selectWhere = selectQuery.where();
        Clause clause = QueryBuilder.eq(NUSUARIO_COL, nusuario);
        Clause clause2 = QueryBuilder.eq(PASSWORD_COL, password);
        selectWhere.and(clause).and(clause2);
        ResultSet results = session.execute(selectQuery);
        return results.one() != null;
    }

    @Override
    public void removeEmpleado(Empleado e) {
        Session session = cassandraConnector.getSession();
        Delete.Where delete = QueryBuilder.delete()
                .from(NOMBRE_DATABASE, NOMBRE_TABLA_EMPLEADO)
                .where(eq(NUSUARIO_COL, e.getNusuario()));
        System.out.println(delete);
        ResultSet result = session.execute(delete);
    }

    @Override
    public Incidencia getIncidenciaById(int id) {
        Session session = cassandraConnector.getSession();
        Select selectQuery = QueryBuilder.select().all().from(NOMBRE_DATABASE, NOMBRE_TABLA_INCIDENCIA);
        Select.Where selectWhere = selectQuery.where();
        Clause clause = QueryBuilder.eq(ID_INC_COL, id);
        selectWhere.and(clause).limit(1);
        ResultSet results = session.execute(selectQuery);
        Row row = results.one();
        if (row == null) {
            return null;
        }

        Incidencia incidencia = new Incidencia();
        incidencia.setId(row.getInt(ID_INC_COL));
        incidencia.setOrigen(new Empleado(row.getString(ORIGEN_COL)));
        incidencia.setEstado(row.getInt(ESTADO_COL));
        incidencia.setDestino(new Empleado(row.getString(DESTINO_COL)));
        incidencia.setDescripcion(row.getString(DESTINO_COL));
        incidencia.setFecha(MyUtilities.stringToDate(row.getString(DESCRIPCION_COL)));
        incidencia.setUrgente(row.getBool(URGENTE_COL));
        return incidencia;
    }

    @Override
    public List<Incidencia> selectAllIncidencias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Empleado> selectAllEmpleado() {
        List<Empleado> empleados = new ArrayList<>();
        Session session = cassandraConnector.getSession();
        Select selectQuery = QueryBuilder.select().all().from(NOMBRE_DATABASE, NOMBRE_TABLA_EMPLEADO);
        ResultSet results = session.execute(selectQuery);
        List<Row> rows = results.all();
        for (Row row : rows) {
            Empleado empleado = new Empleado();
            empleado.setNusuario(row.getString(NUSUARIO_COL));
            empleado.setNombre(row.getString(NOMBRE_COL));
            empleado.setApellido(row.getString(APELLIDO_COL));
            empleado.setPassword(row.getString(PASSWORD_COL));
            empleado.setEdad(row.getInt(EDAD_COL));
            empleados.add(empleado);
        }
        return empleados;
    }

    @Override
    public void insertOrUpdateIncidencia(Incidencia i) {
        Session session = cassandraConnector.getSession();
        LocalDate fecha = LocalDate.fromDaysSinceEpoch((int) i.getFecha().getTime());
        Insert insert = QueryBuilder.insertInto(NOMBRE_DATABASE, NOMBRE_TABLA_INCIDENCIA)
                .value(ID_INC_COL, i.getId())
                .value(ORIGEN_COL, i.getOrigen().getNusuario())
                .value(DESTINO_COL, i.getDestino().getNusuario())
                .value(FECHA_COL, fecha)
                .value(ESTADO_COL, i.getEstado())
                .value(DESCRIPCION_COL, i.getDescripcion())
                .value(URGENTE_COL, i.isUrgente());
        System.out.println(insert.toString());
        ResultSet result = session.execute(insert.toString());
    }

    @Override
    public void removeIncidencia(Incidencia i) {
        Session session = cassandraConnector.getSession();
        Delete.Where delete = QueryBuilder.delete()
                .from(NOMBRE_DATABASE, NOMBRE_TABLA_INCIDENCIA)
                .where(eq(ID_INC_COL, i.getId()));
        System.out.println(delete);
        ResultSet result = session.execute(delete);
    }

    @Override
    public List<Incidencia> getIncidenciaByDestino(Empleado e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Incidencia> getIncidenciaByOrigen(Empleado e) {
        List<Incidencia> incidencias = new ArrayList<>();
        Session session = cassandraConnector.getSession();
        Select selectQuery = QueryBuilder.select().all().from(NOMBRE_DATABASE, NOMBRE_TABLA_INCIDENCIA);
        selectQuery.allowFiltering();
        Select.Where selectWhere = selectQuery.where();
        Clause clause = QueryBuilder.eq(ORIGEN_COL, e.getNusuario());
        selectWhere.and(clause);
        ResultSet results = session.execute(selectQuery);
        List<Row> rows = results.all();
        for (Row row : rows) {
            Incidencia incidencia = new Incidencia();
            incidencia.setDescripcion(row.getString(DESCRIPCION_COL));
            incidencia.setOrigen(e);
            incidencia.setDestino(getEmpleadoByNusuario(row.getString(DESTINO_COL)));
            incidencia.setEstado(row.getInt(ESTADO_COL));
            incidencia.setUrgente(row.getBool(URGENTE_COL));
            Date fecha = new Date(row.getDate(FECHA_COL).getMillisSinceEpoch());
            incidencia.setFecha(fecha);
            incidencias.add(incidencia);
        }
        return incidencias;
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
        Select selectQuery = QueryBuilder.select().all().from(NOMBRE_DATABASE, NOMBRE_TABLA_EMPLEADO);
        Select.Where selectWhere = selectQuery.where();
        Clause clause = QueryBuilder.eq(NUSUARIO_COL, nUsuario);
        selectWhere.and(clause).limit(1);
        ResultSet results = session.execute(selectQuery);
        Row row = results.one();
        if (row == null) {
            return null;
        }

        Empleado empleado = new Empleado();
        empleado.setNusuario(row.getString(NUSUARIO_COL));
        empleado.setNombre(row.getString(NOMBRE_COL));
        empleado.setApellido(row.getString(APELLIDO_COL));
        empleado.setPassword(row.getString(PASSWORD_COL));
        empleado.setEdad(row.getInt(EDAD_COL));
        return empleado;
    }

}
