package com.songpo.searched.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(value = JdbcType.TINYINT, includeNullJdbcType = true)
public class DefaultEnumTypeHandler extends BaseTypeHandler<BaseEnum> {

    private Class<BaseEnum> type;

    public DefaultEnumTypeHandler() {
    }

    public DefaultEnumTypeHandler(Class<BaseEnum> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    private BaseEnum convert(int status) {
        BaseEnum[] array = type.getEnumConstants();
        for (BaseEnum em : array) {
            if (em.getValue() == status) {
                return em;
            }
        }
        return null;
    }
}