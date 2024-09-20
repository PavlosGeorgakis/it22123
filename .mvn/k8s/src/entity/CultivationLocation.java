import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "cultivation_locations")
@Getter
@Setter
@ToString
public class CultivationLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String address;

    @Column
    private String area;

    @Column
    private String zipCode;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "cultivationLocations")
    private List<Cooperative> cooperatives;

    public CultivationLocation() {
    }

    public CultivationLocation(String address, String area, String zipCode) {
        this.address = address;
        this.area = area;
        this.zipCode = zipCode;
    }

    @PreRemove
    private void preRemove() {
        for (Cooperative cooperative : cooperatives) {
            cooperative.removeCultivationLocation(this);
        }
    }
}

