plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.json:json:20231013")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8" // Устанавливаем кодировку для компиляции
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "OllamaClient" // Указываем главный класс
    }
}