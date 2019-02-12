group = "com.github.monosoul"
version = "1.0"

val springBootVersion: String by extra
val byteBuddyVersion by extra { "1.9.2" }
val junitVersion by extra { "5.3.2" }

buildscript {
    val springBootVersion by extra { "2.1.2.RELEASE" }

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

plugins {
    java
}

apply {
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}

dependencies {
    val lombokDependency = "org.projectlombok:lombok:1.18.4"

    annotationProcessor(lombokDependency)
    compileOnly(lombokDependency)
    compile("org.springframework.boot:spring-boot-starter-aop")
    compile("org.springframework.boot:spring-boot-starter-web")

    testAnnotationProcessor(lombokDependency)
    testCompileOnly(lombokDependency)
    testCompile("org.apache.commons:commons-lang3:3.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testCompile("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntime("org.junit.vintage:junit-vintage-engine:$junitVersion")
    testCompile("org.assertj:assertj-core:3.11.1")
    testCompile("org.mockito:mockito-core:2.23.0") {
        exclude("net.bytebuddy")
    }
    testCompile("net.bytebuddy:byte-buddy:$byteBuddyVersion")
    testCompile("net.bytebuddy:byte-buddy-agent:$byteBuddyVersion")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit")
    }
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

repositories {
    mavenCentral()
}