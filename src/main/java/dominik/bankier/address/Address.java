package dominik.bankier.address;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses",
        uniqueConstraints = @UniqueConstraint(columnNames={"street_name", "city", "client_id"}))
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @NotNull
    private String streetName;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @EqualsAndHashCode.Exclude
    private long client_id;

    public Address(String streetName, String city, String country, long client_id) {
        this.streetName = streetName;
        this.city = city;
        this.country = country;
        this.client_id = client_id;
    }
}
