package dominik.bankier.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientStatusList {
    ACTIVE("ACTIVE"),
    SUSPENDED("SUSPENDED"),
    INACTIVE("INACTIVE");
    private final String status;
}
