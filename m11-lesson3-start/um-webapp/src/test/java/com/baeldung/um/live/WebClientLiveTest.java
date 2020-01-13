package com.baeldung.um.live;

import com.baeldung.um.persistence.model.Privilege;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { LiveTestConfig.class })
public class WebClientLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(WebClientLiveTest.class);

    @Autowired
    private WebClient webClient;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenRetrievingAllPrivileges_thenOk() throws InterruptedException {
        Flux<Privilege> response = webClient.get().uri("/privileges").accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve().bodyToFlux(Privilege.class);
        response.subscribe((privilege -> logger.info(privilege.toString())));

        Thread.sleep(35000);
    }

    @Test
    public void whenRetrievingAllPrivilegesWithWebTestClient_thenOk() throws InterruptedException {
       webTestClient.get().uri("/privileges/1").accept(MediaType.APPLICATION_JSON)
               .exchange().expectStatus().is2xxSuccessful().expectBody(Privilege.class).isEqualTo(getThePrivilege());
    }

    // method to create the assoicated privilege to test it
    private Privilege getThePrivilege() {
        return null;
    }

    @Test
    public void whenCreatingNewPrivilege_thenOk() throws InterruptedException {
        Mono<HttpStatus> statusResult = webClient.post().uri("/privileges").syncBody(createNewPrivilege())
                .exchange().map(response -> response.statusCode());

        statusResult.subscribe(status -> logger.info(status.toString()));
        Thread.sleep(2000);
    }

    private Privilege createNewPrivilege() {
        return new Privilege(RandomStringUtils.randomAlphabetic(5));
    }

}
