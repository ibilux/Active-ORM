package org.activeorm.postgresql.query;

import org.activeorm.User;
import org.activeorm.database.Database;
import org.activeorm.query.Query;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Francis on 28/04/16.
 * Project Jactive-Record.
 */
public class QueryTest {

    @Test
    public void testPostgreQuery() {
        final Database database = Database.fromYaml("./postgre-config.yml");

        database.execute("CREATE TABLE users(user_id serial PRIMARY KEY, username VARCHAR(50), password VARCHAR(50))", null);

        final User tom = new User();
        tom.name = "Tom";
        tom.save();

        assertEquals("Tom", Query.build(User.class).where("username").equalTo("Tom").first().name);
        assertEquals(null, Query.build(User.class).where("username").equalTo("Chimp").first());

        tom.destroy();

        database.execute("DROP TABLE users", null);

        database.disconnect();
    }

}
