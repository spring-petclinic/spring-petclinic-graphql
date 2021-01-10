package org.springframework.samples.petclinic.vet.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.graphql.VisitConnection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Resolver for PetClinic Vet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class VetQueryResolver implements GraphQLQueryResolver {
    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    public VetQueryResolver(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public List<Vet> vets() {
        return List.copyOf(vetRepository.findAll());
    }

    public Optional<Vet> vet(int vetId) {
        return vetRepository.findById(vetId);
    }

    public List<Specialty> specialties() {
        return List.copyOf(specialtyRepository.findAll());
    }
}
