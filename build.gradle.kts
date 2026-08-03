plugins {
    id("java")
}

group = "com.crimsonlogic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")

    implementation("org.mybatis:mybatis:3.5.19")
}

tasks.test {
    useJUnitPlatform()
}