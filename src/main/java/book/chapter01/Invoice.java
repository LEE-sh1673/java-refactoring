package book.chapter01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Invoice {

    private final String customer;
    private final List<Performance> performances;

    Invoice() {
        this("UNKNOWN", new ArrayList<>());
    }

    Invoice(final String customer, final List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    String customer() {
        return customer;
    }

    List<Performance> performances() {
        return Collections.unmodifiableList(performances);
    }
}
