package com.huijiewei.agile.core.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * @author huijiewei
 */

@Repository
@RequiredArgsConstructor
public class TreeClosureJpaRepositoryImpl<T extends AbstractJpaTreeClosureEntity> implements TreeClosureJpaRepository<T> {
    private final EntityManager entityManager;

    @Override
    public void truncateClosures(T entity) {
        String tableName = entity.getClosureTableName();

        this.entityManager
                .createNativeQuery(String.format("TRUNCATE TABLE %s", tableName))
                .executeUpdate();
    }

    @Override
    public void insertClosures(T entity) {
        String tableName = entity.getClosureTableName();

        this.entityManager
                .createNativeQuery(String.format("INSERT INTO %s (ancestor, descendant, distance) " +
                        "SELECT ancestor AS ancestor, :id AS descendant, distance + 1 AS distance " +
                        "FROM %s " +
                        "WHERE descendant = :parentId " +
                        "UNION ALL " +
                        "SELECT :id AS ancestor, :id AS descendant, 0 AS distance", tableName, tableName))
                .setParameter("id", entity.getId())
                .setParameter("parentId", entity.getParentId())
                .executeUpdate();
    }

    @Override
    public void updateClosures(T entity) {
        String tableName = entity.getClosureTableName();

        this.entityManager
                .createNativeQuery(String.format("DELETE C FROM %s AS C " +
                        "INNER JOIN %s AS D ON C.descendant = D.descendant " +
                        "LEFT JOIN %s AS A ON A.ancestor = D.ancestor AND A.descendant = C.ancestor " +
                        "WHERE D.ancestor = :id AND A.ancestor IS NULL", tableName, tableName, tableName))
                .setParameter("id", entity.getId())
                .executeUpdate();

        this.entityManager
                .createNativeQuery(String.format("INSERT INTO %s (ancestor, descendant, distance) " +
                        "SELECT A.ancestor, D.descendant, A.distance + D.distance + 1 " +
                        "FROM %s AS A " +
                        "CROSS JOIN %s AS D " +
                        "WHERE D.ancestor = :id AND A.descendant = :parentId", tableName, tableName, tableName))
                .setParameter("id", entity.getId())
                .setParameter("parentId", entity.getParentId())
                .executeUpdate();
    }

    @Override
    public void deleteWithClosures(T entity) {
        String tableName = entity.getTableName();
        String closureTableName = entity.getClosureTableName();

        this.entityManager
                .createNativeQuery(String.format("DELETE D, DC " +
                        "FROM %s C " +
                        "INNER JOIN %s D ON C.descendant = D.id " +
                        "INNER JOIN %s DC ON DC.ancestor = D.id OR DC.descendant = D.id " +
                        "WHERE C.ancestor = :id", closureTableName, tableName, closureTableName))
                .setParameter("id", entity.getId())
                .executeUpdate();
    }

    @Override
    public List<T> findAncestors(T entity) {
        return this.buildAncestorsQuery(entity).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public TypedQuery<T> buildAncestorsQuery(T entity) {
        String tableName = entity.getTableName();
        String closureTableName = entity.getClosureTableName();

        return (TypedQuery<T>) this.entityManager
                .createNativeQuery(String.format("SELECT E.* " +
                        "FROM %s AS E " +
                        "INNER JOIN %s AS A ON E.id = A.ancestor " +
                        "WHERE A.descendant = :id " +
                        "ORDER BY E.id ASC", tableName, closureTableName), entity.getClass())
                .setParameter("id", entity.getId());
    }

    @Override
    public List<T> findDescendants(T entity) {
        return this.buildDescendantsQuery(entity).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public TypedQuery<T> buildDescendantsQuery(T entity) {
        String tableName = entity.getTableName();
        String closureTableName = entity.getClosureTableName();

        return (TypedQuery<T>) this.entityManager
                .createNativeQuery(String.format("SELECT E.* " +
                        "FROM %s AS E " +
                        "INNER JOIN %s AS D ON E.id = D.descendant " +
                        "WHERE D.ancestor = :id " +
                        "ORDER BY E.id ASC", tableName, closureTableName), entity.getClass())
                .setParameter("id", entity.getId());
    }

    @Override
    public List<T> findAncestors(String where, Map<String, Object> values, String sort, T entity) {
        return this.buildAncestorsQuery(where, values, sort, entity).getResultList();
    }

    @SuppressWarnings("unchecked")
    public TypedQuery<T> buildAncestorsQuery(String where, Map<String, Object> values, String sort, T entity) {
        String tableName = entity.getTableName();
        String closureTableName = entity.getClosureTableName();

        var query = (TypedQuery<T>) this.entityManager
                .createNativeQuery(String.format("SELECT DISTINCT E.* " +
                        "FROM %s AS E " +
                        "INNER JOIN %s AS A ON E.id = A.ancestor " +
                        "INNER JOIN %s AS S ON A.descendant = S.id " +
                        "WHERE %s " +
                        "ORDER BY %s", tableName, closureTableName, tableName, where, sort), entity.getClass());

        for (var value : values.entrySet()) {
            query.setParameter(value.getKey(), value.getValue());
        }

        return query;
    }
}