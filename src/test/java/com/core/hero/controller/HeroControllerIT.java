package com.core.hero.controller;

import com.core.hero.consts.Routes;
import com.core.hero.controller.payload.HeroEditRequest;
import com.core.hero.controller.payload.HeroResponse;
import com.core.hero.enums.Power;
import com.core.hero.repositories.HeroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class HeroControllerIT {

    protected final HeroRepository heroRepository;
    protected final MockMvc mvc;

    @Test
    @DisplayName("Test - " + Routes.HERO_BASE + Routes.HERO_GET_ALL + " - 200")
    public void shouldGetAllHeroesSuccessfully() {
        final String URI = Routes.HERO_BASE + Routes.HERO_GET_ALL;

        final String result = expectGetPerform(URI, HttpStatus.OK.value());
        assertNotNull(result);
        assertTrue(result.contains("Spiderman"));
        assertTrue(result.contains("2001-08-10"));

        Arrays.stream(Power.values()).forEach(power ->
                assertTrue(result.contains(power.name().toLowerCase(Locale.ROOT)))
        );
    }

    @Test
    @DisplayName("Test - " + Routes.HERO_BASE + Routes.HERO_GET + " - 200")
    public void shouldGetHeroSuccessfully() throws ParseException {
        final String URI = Routes.HERO_BASE + Routes.HERO_GET;

        final String result = expectGetPerform(URI + "?id=1", HttpStatus.OK.value());
        assertNotNull(result);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthdate = format.parse("10/08/2001");
        HeroResponse expected = new HeroResponse(1,
                "Spiderman",
                2000,
                550,
                1000,
                Power.MUTATION,
                birthdate);
        assertEquals(this.toJsonString(expected), result);
    }

    @Test
    @DisplayName("Test - " + Routes.HERO_BASE + Routes.HERO_GET + " - 400")
    public void shouldGetHeroBadRequest() {
        final String URI = Routes.HERO_BASE + Routes.HERO_GET;

        final String result = expectGetPerform(URI + "?id=-1", HttpStatus.BAD_REQUEST.value());
        assertNotNull(result);
        assertTrue(result.contains("ConstraintViolation"));
        assertTrue(result.contains("error"));
    }

    @Test
    @DisplayName("Test - " + Routes.HERO_BASE + Routes.HERO_GET + " - 404")
    public void shouldGetHeroNotFound() {
        final String URI = Routes.HERO_BASE + Routes.HERO_GET;

        final String result = expectGetPerform(URI + "?id=100", HttpStatus.NOT_FOUND.value());
        assertNotNull(result);
        assertTrue(result.contains("NotFound"));
        assertTrue(result.contains("error"));
    }

    @Test
    @DisplayName("Test - " + Routes.HERO_BASE + Routes.HERO_UPDATE + " - 200")
    public void shouldUpdateHero() throws ParseException {
        final String URI = Routes.HERO_BASE + Routes.HERO_UPDATE;

        final String result = expectPutPerform(URI, HttpStatus.OK.value(), new HeroEditRequest(3L,
                "Triclope",
                1250,
                800,
                900,
                Power.TRANSFORMATION.name(),
                new Date()
        ));

        assertNotNull(result);
        assertTrue(result.contains("Triclope"));
        assertTrue(result.toUpperCase(Locale.ROOT).contains(Power.TRANSFORMATION.name().toUpperCase(Locale.ROOT)));

        // revert operation
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthdate = format.parse("12/08/2001");
        expectPutPerform(URI, HttpStatus.OK.value(), new HeroEditRequest(3L,
                "Ciclope",
                1250,
                800,
                900,
                Power.MUTATION.name(),
                birthdate
        ));
    }

    private String expectGetPerform(final String URI,
                                    final int status) {
        MvcResult mvcResult;
        try {
            final ResultMatcher matcher = getMatcher(status);
            if (matcher == null) {
                fail();
            }
            mvcResult = mvc.perform(get(URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(matcher)
                    .andReturn();
        } catch (Exception exception) {
            return null;
        }
        String result;
        try {
            result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return result;
    }

    public String expectPutPerform(final String URI,
                                    final int status,
                                    final Object request) {
        MvcResult mvcResult;
        try {
            final ResultMatcher matcher = getMatcher(status);
            if (matcher == null) {
                fail();
            }
            mvcResult = (request == null) ?
                    mvc.perform(put(URI)
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(matcher)
                            .andReturn()
                    :
                    mvc.perform(put(URI)
                                    .content(this.toJsonString(request))
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(matcher)
                            .andReturn();
        } catch (Exception exception) {
            return null;
        }
        String result;
        try {
            result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return result;
    }

    private ResultMatcher getMatcher(final int status) {
        if (status == HttpStatus.OK.value()) {
            return status().isOk();
        }
        if (status == HttpStatus.BAD_REQUEST.value()) {
            return status().isBadRequest();
        }
        if (status == HttpStatus.NOT_FOUND.value()) {
            return status().isNotFound();
        }
        return null;
    }

    private String toJsonString(final Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "ERROR";
        }
    }
}
