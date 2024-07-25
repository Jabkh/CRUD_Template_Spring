# Utiliser une image de base Maven pour la construction
FROM maven:3.8.5-openjdk-17-slim AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier POM et les fichiers source
COPY pom.xml .
COPY src ./src

# Télécharger les dépendances et construire l'application
RUN mvn clean package -DskipTests

# Utiliser une image de base JDK pour l'exécution
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier jar construit à partir de l'étape précédente
COPY --from=build /app/target/CRUD_Template-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel l'application sera accessible
EXPOSE 8080

# Commande pour exécuter le fichier jar
ENTRYPOINT ["java", "-jar", "app.jar"]
