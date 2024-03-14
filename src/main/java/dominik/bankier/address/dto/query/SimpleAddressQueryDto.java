package dominik.bankier.address.dto.query;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
public class SimpleAddressQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @NotNull
    private String streetName;
    @NotNull
    private String city;
    @NotNull
    private String country;

    public SimpleAddressQueryDto(String streetName, String city, String country) {
        this.streetName = streetName;
        this.city = city;
        this.country = country;
    }
}
