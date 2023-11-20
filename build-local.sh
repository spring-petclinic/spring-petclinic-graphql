#! /bin/bash

cd petclinic-graphiql && rm -rf ./dist && pnpm build && pnpm copy-to-backend && cd ..

./mvnw -DskipTests -pl backend spring-boot:build-image -Dspring-boot.build-image.imageName=spring-petclinic/petclinic-graphql-backend:0.0.1

cd frontend && rm -rf ./dist && pnpm build && docker build . --tag spring-petclinic/petclinic-graphql-frontend:0.0.1 && cd ..

echo "Docker images have been built"
echo "You can run the docker-compose file with"
echo "docker-compose -f docker-compose-petclinic.yml up -d"

