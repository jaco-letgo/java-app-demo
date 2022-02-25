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

.PHONY: lint
lint:
	@docker exec -it java-app-demo-java ./gradlew ktlintCheck

.PHONY: fix
fix:
	@docker exec -it java-app-demo-java ./gradlew ktlintFormat

.PHONY: mutations
mutations:
	@docker exec -it java-app-demo-java ./gradlew pitest
