package com.core.hero.controller;

import com.core.hero.consts.Routes;
import com.core.hero.repositories.HeroRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void shouldCreateAUserSuccessfully() {
        final String URI = Routes.HERO_BASE + Routes.HERO_GET_ALL;

        final String result = expectGetPerform(URI, HttpStatus.OK.value());
        assertEquals("", result);
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

}
