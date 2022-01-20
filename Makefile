.PHONY: up
up:
	@docker-compose up -d

.PHONY: down
down:
	@docker-compose down

.PHONY: build
build:
	@docker exec -it java-app-demo-java ./gradlew build --warning-mode all

.PHONY: test
test:
	@docker exec -it java-app-demo-java ./gradlew test --warning-mode all

.PHONY: run
run:
	@docker exec -it java-app-demo-java ./gradlew bootrun
