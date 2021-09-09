package com.photographer.app.modelsNew;

public class CartItem {
    private long id;
    private long en_id;
    private long guest_id;
    private long pr_id;
    private long counter;

    public CartItem(long id, long en_id, long guest_id, long pr_id, long counter) {
        this.id = id;
        this.en_id = en_id;
        this.guest_id = guest_id;
        this.pr_id = pr_id;
        this.counter = counter;
    }

    public CartItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEn_id() {
        return en_id;
    }

    public void setEn_id(long en_id) {
        this.en_id = en_id;
    }

    public long getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(long guest_id) {
        this.guest_id = guest_id;
    }

    public long getPr_id() {
        return pr_id;
    }

    public void setPr_id(long pr_id) {
        this.pr_id = pr_id;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "\nCartItem{" +
                "id=" + id +
                ", en_id=" + en_id +
                ", guest_id=" + guest_id +
                ", pr_id=" + pr_id +
                ", counter=" + counter +
                '}';
    }
}
