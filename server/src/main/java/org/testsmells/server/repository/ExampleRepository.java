package org.testsmells.server.repository;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.testsmells.server.tables.pojos.TestSmells;
import org.testsmells.server.util.DatasourceUtil;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExampleRepository {

    @Autowired
    DSLContext dsl;

    public List<TestSmells> getSmellTypes() throws SQLException {
        final String queryString = "SELECT * FROM tsdetect.test_smells";
        ResultQuery<Record> query = dsl.resultQuery(queryString);
        return query.fetchInto(TestSmells.class);
    }

}
