package dominik.bankier.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum ClientStatusList {
    ACTIVE("ACTIVE"),
    SUSPENDED("SUSPENDED"),
    INACTIVE("INACTIVE");
    private final String status;
}
