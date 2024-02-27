package dominik.bankier.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
 interface AddressRepository extends JpaRepository<Address,Long> {
 @Query("SELECT CASE WHEN COUNT(a) > 0 " +
         "THEN true ELSE false END FROM Address a " +
         "WHERE a.client_id = :client_id AND a.streetName = :streetName")
 boolean existsAddressByClient_idAndStreetName(long client_id, String streetName);
}
