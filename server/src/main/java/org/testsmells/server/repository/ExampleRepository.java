package org.testsmells.server.repository;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.testsmells.server.tables.pojos.TestSmells;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExampleRepository {

    private final DSLContext dashboardDsl;

    public ExampleRepository(@Qualifier("dsl-dashboard") DSLContext dashboardDsl) {
        this.dashboardDsl = dashboardDsl;
    }

    public List<TestSmells> getSmellTypes() throws SQLException {
        final String queryString = "SELECT * FROM tsdetect.test_smells";
        try (ResultQuery<Record> query = dashboardDsl.resultQuery(queryString)) {
            return query.fetchInto(TestSmells.class);
        }
    }

}
