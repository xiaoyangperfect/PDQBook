package com.wehealth.pdqbook.getway.repertory.db;

/**
 * Created by yangxiao on 12/21/2016.
 */
public class SQLExprBuilder {
    enum OP {
        Equals("="),
        GreaterThan(">"),
        LessThan("<"),
        Like("LIKE"),
        ;

        OP(String opStr) {
            _opStr = opStr;
        }

        @Override
        public String toString() {
            return _opStr;
        }

        private final String _opStr;
    }

    enum BoolOp {
        AND,
        OR,
    }

    public String toString() {
        return _builder.toString();
    }

    public SQLExprBuilder fieldEquals(String fieldName) {
        return fieldOp(fieldName, OP.Equals);
    }

    public SQLExprBuilder fieldLike(String fieldName) {
        return fieldOp(fieldName, OP.Like);
    }

    public SQLExprBuilder fieldGreaterThan(String fieldName) {
        return fieldOp(fieldName, OP.GreaterThan);
    }

    public SQLExprBuilder fieldLessThan(String fieldName) {
        return fieldOp(fieldName, OP.LessThan);
    }

    public SQLExprBuilder and() {
        return boolOp(BoolOp.AND);
    }

    public SQLExprBuilder or() {
        return boolOp(BoolOp.OR);
    }

    public SQLExprBuilder in(String columnName, int valueCount) {
        appendWithSpacePostfix(columnName);
        appendWithSpacePostfix("IN ( ?");
        for (int i = 1; i < valueCount; i++) {
            appendWithSpacePostfix(", ?");
        }
        appendWithSpacePostfix(")");
        return this;
    }

    public SQLExprBuilder multipleFieldEquals(String[] fieldNames) {
        return multipleFieldOp(fieldNames, OP.Equals);
    }

    public SQLExprBuilder field(String fieldName) {
        return appendWithSpacePostfix(fieldName);
    }

    public SQLExprBuilder GreaterThan() {
        return appendWithSpacePostfix(OP.GreaterThan.toString());
    }

    public SQLExprBuilder LessThan() {
        return appendWithSpacePostfix(OP.LessThan.toString());
    }

    public SQLExprBuilder Equals() {
        return appendWithSpacePostfix(OP.Equals.toString());
    }

    public SQLExprBuilder Number(Number num) {
        return appendWithSpacePostfix(num.toString());
    }

    public SQLExprBuilder fieldOp(String fieldName, OP op) {
        _builder.append(fieldName);
        _builder.append(" " + op + " ? ");
        return this;
    }

    public SQLExprBuilder boolOp(BoolOp op) {
        return appendWithSpacePostfix(op.name());
    }

    public SQLExprBuilder multipleFieldOp(String[] fieldNames, OP op) {
        if (fieldNames.length > 0) {
            fieldEquals(fieldNames[0]);
            for (int i = 1; i < fieldNames.length; i++) {
                and();
                fieldOp(fieldNames[i], op);
            }
        }
        return this;
    }

    // NOTE: this function should NOT be used in final commit version, it's here for debugging SQL.
    public SQLExprBuilder append(String sqlStr) {
        _builder.append(sqlStr);
        return this;
    }

    private SQLExprBuilder appendWithSpacePostfix(String str) {
        _builder.append(str);
        _builder.append(" ");
        return this;
    }

    private StringBuilder _builder = new StringBuilder();
}
