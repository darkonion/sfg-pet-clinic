package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;
    final Long ownerId = 1L;
    final String lastName = "Recon";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PerTypeServiceMap(), new PetServiceMap());
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(lastName);
        ownerServiceMap.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> all = ownerServiceMap.findAll();

        assertEquals(1, all.size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);

        assertEquals(0, ownerServiceMap.findAll().size());

    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerId));

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void saveExistingId() {
        long id = 2L;
        Owner owner2 = new Owner();
        owner2.setId(id);

        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(id, owner2.getId());
    }

    @Test
    void saveNoId() {
        Owner owner3 = new Owner();

        Owner savedOwner = ownerServiceMap.save(owner3);

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());

        assertEquals(2, owner3.getId());
    }

    @Test
    void findByLastName() {

        Owner recon = ownerServiceMap.findByLastName(lastName);

        assertNotNull(recon);

        assertEquals(ownerId, recon.getId());
    }

    @Test
    void findByLastNameNotFound() {

        Owner recon = ownerServiceMap.findByLastName("foo");

        assertNull(recon);

    }
}