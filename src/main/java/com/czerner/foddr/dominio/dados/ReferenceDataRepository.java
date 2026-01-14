package com.czerner.foddr.dominio.dados;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReferenceDataRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReferenceDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findNationName(Integer nationId) {
        return findName("nations", "nation_id", nationId);
    }

    public String findLeagueName(Integer leagueId) {
        return findName("leagues", "league_id", leagueId);
    }

    public String findClubName(Integer clubId) {
        return findName("clubs", "club_id", clubId);
    }

    private String findName(String table, String idColumn, Integer id) {
        if (id == null) {
            return null;
        }
        try {
            return jdbcTemplate.queryForObject(
                    String.format("select name from %s where %s = ?", table, idColumn),
                    String.class,
                    id
            );
        } catch (DataAccessException ex) {
            return null;
        }
    }
}
