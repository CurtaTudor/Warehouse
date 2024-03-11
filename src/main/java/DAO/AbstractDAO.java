package DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;

/**
 * Reprezinta clasa generica ce contine metodele pentru adaugare,actualizare,stergere si selectie din baza de date.
 * @param <T> Tipul generic, acesta poate fi: Client,Product sau Order
 * @author Curta Tudor
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Are rolul de a creea query-ul sql pentru a vizualiza o tabela.
     * @return String-ul rezultant
     */
    private String createSelectAllQuery()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT ");
        sb.append("* ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * Are rolul de a creea query-ul sql pentru a vizualiza un item in functie de un id
     * @return String-ul rezultant
     */
    private String createSelectIDQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Are rolul de a creea query-ul sql pentru inserarea unui element in tabela.
     * @return String-ul rezultant
     */
    private String createInsertStatement()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" VALUES (");
        for(Field field:type.getDeclaredFields()){
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }
    /**
     * Are rolul de a creea query-ul sql pentru stergerea unui element in tabela.
     * @return String-ul rezultant
     */
    private String createDeleteStatement(String field)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Are rolul de a creea query-ul sql pentru actualizarea unui element in tabela.
     * @return String-ul rezultant
     */
    private String createUpdateStatement()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(Field field:type.getDeclaredFields()){
            field.setAccessible(true);
            String fieldName=field.getName();
            sb.append(fieldName + "=?,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" WHERE id=?");
        return sb.toString();
    }

    /**
     * Returneaza o lista de elemente ce se afla in baza de date cu un anumit id
     * @param id id-ul elementului dupa care se face cautarea
     * @return Lista de elemente ce au id-ul dat ca parametru
     */
    public List<T> findID(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectIDQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;

    }

    /**
     * Returneaza o lista de elemente ce se afla in baza de date
     * @return Lista de elemente ce se afla in baza de date
     */
    public List<T> selectAll()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query=createSelectAllQuery();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creeaza o tabela cu elemente dintr-o lista primita ca parametru
     * @param list elementele cu care este populata tabela
     * @return Tabela populata cu elemente din lista
     * @throws IllegalAccessException
     */
    public JTable createTable(List<T> list) throws IllegalAccessException {
        JTable table=new JTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        List<String> column = new ArrayList<String>();
        for(Field field : type.getDeclaredFields()){
            column.add(field.getName());
        }
        String[] columnArray=column.toArray(new String[0]);
        model.setColumnIdentifiers(columnArray);

        for(T t:list){
            Object[] o = new Object[columnArray.length];
            int i=0;
            for(Field field:type.getDeclaredFields()){
                field.setAccessible(true);
                o[i]=field.get(t);
                i++;
            }
            model.addRow(o);
        }
        return table;
    }

    /**
     * Insereaza in baza de date un element primit ca si parametru
     * @param t parametrul ce va fi inserat in baza de date
     */
    public void insert(T t)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String insertStatement=createInsertStatement();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertStatement);
            int index=1;
            for(Field field:t.getClass().getDeclaredFields()){
                field.setAccessible(true);
                String type= String.valueOf(field.getType());
                if(type.equals("int")){
                    int intNumber=(int)field.get(t);
                    statement.setInt(index, intNumber);
                }else{
                    if(type.equals("float")){
                        float floatNumber=(float)field.get(t);
                        statement.setFloat(index,floatNumber);
                    }else {
                        String str = (String) field.get(t);
                        statement.setString(index, str);
                    }
                }
                index++;
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sterge din baza de date un element cu un id dat ca si parametru
     * @param id id-ul elementului ce va fi sters din baza de date
     */
    public void delete(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteStatement("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Actualizeaza un element din baza de date cu un id primit ca si parametru
     * @param t elementul cu care se va face actualizarea
     * @param id id-ul elementului ce fa fi actualizat
     */
    public void update(T t,int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String updateStatement=createUpdateStatement();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(updateStatement);
            int index=1;
            for(Field field:t.getClass().getDeclaredFields()){
                field.setAccessible(true);
                String type= String.valueOf(field.getType());
                if(type.equals("int")){
                    int intNumber=(int)field.get(t);
                    statement.setInt(index, intNumber);
                }else{
                    if(type.equals("float")){
                        float floatNumber=(float)field.get(t);
                        statement.setFloat(index,floatNumber);
                    }else {
                        String str = (String) field.get(t);
                        statement.setString(index, str);
                    }
                }
                index++;
            }
            statement.setInt(index,id);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Are rolul de a converti din ResultSet intr-o lista de obiecte
     * @param resultSet ResultSet-ul ce este returnat dupa comenzi sql
     * @return Lista de obiecte convertita
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }



}
