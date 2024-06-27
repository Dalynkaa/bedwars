dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.0.3")
    shadow("org.mariadb.jdbc:mariadb-java-client:3.0.3")
}
tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}
