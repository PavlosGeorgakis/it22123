import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "cooperatives")
@Getter
@Setter
@ToString
public class Cooperative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String vat;

    @Column
    private String status = "processing";

    @Column
    private String notes = "";

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User employee;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="cooperative_farmers",
            joinColumns = @JoinColumn(name="cooperative_id"),
            inverseJoinColumns = @JoinColumn(name="farmer_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"farmer_id", "cooperative_id"})}
    )
    private List<User> farmers;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="cooperative_products",
            joinColumns = @JoinColumn(name="cooperative_id"),
            inverseJoinColumns = @JoinColumn(name="product_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"product_id", "cooperative_id"})}
    )
    private List<Product> products;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="cooperative_locations",
            joinColumns = @JoinColumn(name="cooperative_id"),
            inverseJoinColumns = @JoinColumn(name="location_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"location_id", "cooperative_id"})}
    )
    private List<CultivationLocation> cultivationLocations;

    // Υπολογιστικές μέθοδοι παραμένουν οι ίδιες όπως στο αρχικό σου παράδειγμα

    @PreRemove
    private void preRemove() {
        if (employee != null) {
            employee.removeApplication(this);
        }
        for (User farmer : farmers) {
            farmer.removeCooperative(this);
        }
        for (Product product : products) {
            product.removeCooperative(this);
        }
        for (CultivationLocation cultivationLocation : cultivationLocations) {
            cultivationLocation.removeCooperative(this);
        }
    }

    public String check() {
        if (status.equalsIgnoreCase("approved")) {
            return "Application already approved.";
        } else if (status.equalsIgnoreCase("rejected")) {
            return "Application already rejected.";
        }
        if (!(farmers.isEmpty() || products.isEmpty() || cultivationLocations.isEmpty()) && isVatValid()) {
            return "Application is valid.";
        } else {
            return getString();
        }
    }

    private String getString() {
        StringBuilder checkResult = new StringBuilder();
        if (farmers.isEmpty()) {
            checkResult.append("Less than 1 member,");
        }
        if (products.isEmpty()) {
            checkResult.append("Less than 1 product,");
        }
        if (cultivationLocations.isEmpty()) {
            checkResult.append("Less than 1 cultivation location,");
        }
        if (!isVatValid()) {
            checkResult.append("Invalid VAT number");
        }
        return checkResult.toString();
    }

    private boolean isVatValid() {
        if (vat.length() == 9) {
            try {
                Integer.parseInt(vat);
            } catch (NumberFormatException error) {
                return false;
            }
            int sum = 0;
            for (int i = 0, product; i < 8; i++) {
                product = Character.getNumericValue(vat.charAt(i));
                product *= (int) Math.pow(2, 8 - i);
                sum += product;
            }
            return (sum % 11) % 10 == Character.getNumericValue(vat.charAt(8)) && !vat.equals("000000000");
        } else {
            return false;
        }
    }
}

