dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("dev.triumphteam:triumph-gui:3.1.5")
    compileOnly("me.clip:placeholderapi:2.11.2")
    implementation("net.wesjd:anvilgui:1.6.3-SNAPSHOT")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") {
        exclude(group = "*", module = "FastAsyncWorldEdit-Core")
    }
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.3")
    implementation("me.catcoder:bukkit-sidebar:6.2.3-SNAPSHOT")
    implementation("redis.clients:jedis:5.0.1")
    implementation("org.mineskin:java-client:1.2.4-SNAPSHOT")
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }
    compileOnly("net.kyori:adventure-text-serializer-json:4.14.0")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    compileOnly("me.dalynkaa.bedwars.api:bedwarsmusic-api:1.4-SNAPSHOT")
    implementation("commons-io:commons-io:2.15.1")
    implementation(enforcedPlatform("com.intellectualsites.bom:bom-newest:1.43"))
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


