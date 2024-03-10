package dominik.bankier.client;


import dominik.bankier.account.query.SimpleAccountQueryDto;
import dominik.bankier.address.query.SimpleAddressQueryDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private ClientStatusList status;
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id")
    private Set<SimpleAddressQueryDto> addresses;
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id")
    private Set<SimpleAccountQueryDto> accountsList;

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = ClientStatusList.ACTIVE;
        this.addresses = new HashSet<>();
        this.accountsList = new HashSet<>();
    }
}
