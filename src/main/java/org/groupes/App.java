package org.groupes;

import org.groupes.Model.DAO.ISujetDAO;
import org.groupes.Model.Entity.Sujet;
import org.groupes.Model.DAO.SujetDAO;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws SQLException {
        ISujetDAO sujetDAO = new SujetDAO();
        Sujet s = new Sujet("ce c'est un test ");
        sujetDAO.addSujet(s);
    }
}
