package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.function.Consumer;

@Service
@Validated
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public Owner addOwner(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String telephone, @NotEmpty String address, @NotEmpty String city) {
        final Owner owner = new Owner();
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);

        ownerRepository.save(owner);

        return owner;
    }



    @Transactional
    public Owner updateOwner(@NotEmpty int ownerId, String firstName, String lastName, String telephone, String address, String city) {
        Owner owner = ownerRepository.findById(ownerId);

        setIfGiven(address, owner::setAddress);
        setIfGiven(firstName, owner::setFirstName);
        setIfGiven(lastName, owner::setLastName);
        setIfGiven(telephone, owner::setTelephone);
        setIfGiven(address, owner::setAddress);
        setIfGiven(city, owner::setCity);

        ownerRepository.save(owner);

        return owner;
    }

    private void setIfGiven(String value, Consumer<String> s) {
        if (value != null) {
            s.accept(value);
        }
    }
}
