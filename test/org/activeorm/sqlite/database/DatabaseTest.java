package org.activeorm.sqlite.database;

import org.activeorm.database.Database;
import org.activeorm.utility.Resource;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Francis on 30/04/16.
 * Project Jactive-Record.
 */
public class DatabaseTest {

    @Test
    public void loadSQLiteDatabase() {
        final Database database = Database.fromYaml("./sqllite-config.yml");
        assertNotNull(database);
    }

    @Test
    public void testSQLiteRawQuery() throws SQLException {
        final Database database = Database.fromYaml("./sqllite-config.yml");
        database.execute("CREATE TABLE users(user_id INTEGER PRIMARY KEY, username TEXT, password TEXT)", null);
        assertEquals(1, database.execute(database.sql.insert("users", new String[]{"username", "password"}), new Object[]{"Jackson", "super secret password"}));
        final Resource resource = database.query(database.sql.select("users", null, new String[]{"username"}, new String[]{"="}, null, null, false), new Object[]{"Jackson"});
        assertNotNull(resource);
        resource.release();
        database.execute("DROP TABLE users", null);
        database.disconnect();
    }
}
