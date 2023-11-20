package org.springframework.samples.petclinic;

import org.springframework.samples.petclinic.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Imports testdata into the database on application startup
 *
 * @author Nils Hartmann
 */
public class FakeDataSqlCreator {


    private CycleIterator<Integer> vets;
    private CycleIterator<Integer> petTypes;
    private CycleIterator<Integer> owners;
    private CycleIterator<Integer> pets;

    public static void main(String[] args) throws Exception {
        new FakeDataSqlCreator().importAll();
    }

    private void importAll() throws Exception {
        vets = new CycleIterator<Integer>(List.of(7,8,9,10));
        petTypes = new CycleIterator<Integer>(List.of(1,2,3,4,5,6));
        owners = new CycleIterator<Integer>(importOwners());
        pets = new CycleIterator<Integer>(importPets());
        importVisits();
    }

    private List<Integer> importPets() throws Exception {
        return readCsv("pets.csv", (index, parts) -> {
            var id = index + 13;

            var sql = System.out.printf("""
INSERT INTO pets VALUES (%s, '%s', '%s', %s, %s);
                """,
                id, parts[1], asLocalDate(parts[0]), petTypes.next(), owners.next()
                );

            return id;
        });
    }
//
    private void importVisits() throws Exception {
        pets.reset();
        vets.reset();
        AtomicInteger ix = new AtomicInteger();
        readCsv("visits.csv", (index, parts) -> {
            var id = index + 4;
            Visit visit = new Visit();

            System.out.printf("""
INSERT INTO visits (id, pet_id, vet_id, visit_date, description) VALUES (%s, %s, %s, '%s', '%s');
                """,
                id, pets.next(), vets.next(), asLocalDate(parts[0]), parts[1]
                );
            return visit;
        });
    }

    private List<Integer> importOwners() throws Exception {

        return readCsv("owners.csv", (lineNo, parts) -> {
            var ix = lineNo + 10;

            System.out.printf("""
INSERT INTO owners VALUES (%s, '%s', '%s', '%s', '%s', '%s');
                """, lineNo+10,parts[0], parts[1], parts[2], parts[3], parts[4] );
            return ix;
        });
    }

    private static LocalDate asLocalDate(String date) throws Exception {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, format);
    }

    static class CycleIterator<E> {
        private int index = -1;
        private final List<E> elements;

        public CycleIterator(List<E> elements) {
            this.elements = elements;
        }

        void reset() {
            index = -1;
        }

        public E next() {
            index++;
            if (index >= elements.size()) {
                index = 0;
            }
            return elements.get(index);
        }
    }

    private <E> List<E> readCsv(String name, CsvLineConsumer<E> consumer) throws Exception {
        final List<E> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/testdata/" + name)))) {
            String line = null;
            int index = 0;
            while ((line = br.readLine()) != null) {
                if (index == 0) {
                    // ignore first line (header)
                    index++;
                    continue;
                }
                String[] parts = line.trim().split("\\,");
                E e = consumer.consume(index, parts);
                result.add(e);
                index++;
            }
        }
        return result;
    }

    @FunctionalInterface
    interface CsvLineConsumer<E> {
        E consume(int lineNo, String[] parts) throws Exception;
    }
}
