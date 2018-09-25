package com.github.monosoul.fortuneteller.test.functional;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import com.github.monosoul.fortuneteller.test.functional.model.Horoscope;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HoroscopeTellControllerFT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate client;

    @Test
    void test() {
        val actual = client.getForEntity("http://localhost:" + port + "/horoscope/tell/aquarius", Horoscope.class);

        assertThat(actual.getStatusCode()).isEqualByComparingTo(OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getMessage()).isNotBlank();

        log.info("Received response: {}", actual.getBody());
    }

    //@Test
    void testWithAspect() {
        val actual = client.getForEntity("http://localhost:" + port + "/horoscope/tell/aquarius", Horoscope.class);

        assertThat(actual.getStatusCode()).isEqualByComparingTo(OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getMessage()).isEqualTo(THE_TRUTH);

        log.info("Received response: {}", actual.getBody());
    }
}