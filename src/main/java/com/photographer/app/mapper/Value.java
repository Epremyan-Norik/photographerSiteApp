package com.photographer.app.mapper;

public class Value {

    long en_id;

    long att_id;

    String att_value;

    public Value() {
    }

    public long getEntityId() {
        return en_id;
    }

    public void setEntityId(long en_id) {
        this.en_id = en_id;
    }

    public long getAttId() {
        return att_id;
    }

    public void setAttId(long att_id) {
        this.att_id = att_id;
    }

    public String getValue() {
        return att_value;
    }

    public void setValue(String att_value) {
        this.att_value = att_value;
    }

    @Override
    public String toString() {
        return "\nValue{" +
                "entityId=" + en_id +
                ", attId=" + att_id +
                ", value='" + att_value + '\'' +
                '}';
    }
}
