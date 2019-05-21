package notky.ua.parts.models;

import javax.persistence.*;

@Entity
@Table(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private int id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "necessary", length = 1, nullable = false)
    private boolean necessary;
    @Column(name = "quantity", length = 11, nullable = false)
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
