package org.springframework.samples.petclinic.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetWiring implements RuntimeWiringConfigurer {
    private final VisitRepository visitRepository;

    public PetWiring(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Pet", wiring -> wiring.dataFetcher("visits", env -> {
            Pet pet = env.getSource();
            var visits = visitRepository.findByPetId(pet.getId());
            return new VisitConnection(visits);
        }));
    }
}
