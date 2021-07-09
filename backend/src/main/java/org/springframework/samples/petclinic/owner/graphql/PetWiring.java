package org.springframework.samples.petclinic.owner.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.samples.petclinic.visit.graphql.VisitConnection;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetWiring implements RuntimeWiringBuilderCustomizer{
    private final VisitRepository visitRepository;

    public PetWiring(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Pet", wiring -> wiring.dataFetcher("visits", env -> {
            Pet pet = env.getSource();
            var visits = visitRepository.findByPetId(pet.getId());
            return new VisitConnection(visits);
        }));
    }
}
