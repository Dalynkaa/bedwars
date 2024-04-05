dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    implementation("redis.clients:jedis:5.0.1")
    implementation("io.lumine:Mythic-Dist:5.3.5")
}
tasks {
    runVelocity {
        velocityVersion("3.2.0-SNAPSHOT")
    }
}
