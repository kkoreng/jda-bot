plugins {
    id("java")
}

group = "com.kkoreng"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:6.7.0")

    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")

    implementation("io.github.cdimascio:dotenv-java:3.2.0")
    implementation("org.yaml:snakeyaml:2.7")
    implementation("io.github.classgraph:classgraph:4.8.196")

    implementation("org.slf4j:slf4j-api:2.0.17")
    runtimeOnly("ch.qos.logback:logback-classic:1.6.5")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}