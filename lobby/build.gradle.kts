dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("dev.triumphteam:triumph-gui:3.1.5")
    compileOnly("me.clip:placeholderapi:2.11.2")
    implementation("net.wesjd:anvilgui:1.6.3-SNAPSHOT")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.3")
    implementation("redis.clients:jedis:5.0.1")
    compileOnly("me.lucko:helper:5.6.14")
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.0.3")
    shadow("org.mariadb.jdbc:mariadb-java-client:3.0.3")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
    runServer {
        minecraftVersion("1.20.1")
    }
}


