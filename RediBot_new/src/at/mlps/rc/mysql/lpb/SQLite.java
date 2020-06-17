package at.mlps.rc.mysql.lpb;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SQLImpl{
	
	private File db;

    public SQLite(File db){
        this.db = db;
    }

    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+db.getAbsolutePath());

    }

}
