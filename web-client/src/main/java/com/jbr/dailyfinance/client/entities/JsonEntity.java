package com.jbr.dailyfinance.client.entities;

/**
 *
 * @author jbr
 */
public interface JsonEntity<J extends JsonEntity> {

    J toNewJsonEntity();
    
    Long getId();

    String toJson();

    /*
    public final boolean equals(JsonEntity j) {
        System.out.println("Equals run");
        if (getId() == null)
            return false;
        return getId().equals(j.getId());
    }

    public final String asJson() {
        return new JSONObject(this).toString();
    }
     *
     */
}
