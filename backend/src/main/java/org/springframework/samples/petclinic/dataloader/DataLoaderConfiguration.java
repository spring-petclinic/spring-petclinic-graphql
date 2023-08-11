package org.springframework.samples.petclinic.dataloader;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import org.dataloader.DataLoaderOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.graphql.execution.DefaultBatchLoaderRegistry;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetService;
import reactor.core.publisher.Mono;

@Configuration
@AllArgsConstructor
public class DataLoaderConfiguration {

    private final PetService petService;

    @Bean
    public BatchLoaderRegistry batchLoaderRegistry() {
        final var registry = new DefaultBatchLoaderRegistry();

        registry.<Integer, Pet>forName("petDataLoader")
            .withOptions(getDataLoaderOptions())
            .registerMappedBatchLoader((petIds, env) -> Mono.fromSupplier(mapify(petIds,
                                                                                 petService::getPetsById,
                                                                                  Pet::getId)));


        return registry;
    }

    public static <ID, T> Supplier<Map<ID, T>> mapify(final Set<ID> ids,
                                                      final Function<Set<ID>, List<T>> retriever,
                                                      final Function<T, ID> idSupplier) {
        return () -> retriever.apply(ids)
            .stream()
            .filter(Objects::nonNull)
            .collect(toMap(idSupplier, Function.identity()));
    }


    private DataLoaderOptions getDataLoaderOptions() {
        final var options = new DataLoaderOptions();
        options.setBatchingEnabled(true);
        options.setCachingEnabled(true);
        options.setCachingExceptionsEnabled(true);
        options.setMaxBatchSize(500);
        return options;
    }


    //CHECKSTYLE:ON
}
