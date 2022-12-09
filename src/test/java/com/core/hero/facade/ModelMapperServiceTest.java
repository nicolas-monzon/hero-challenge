package com.core.hero.facade;

import com.core.hero.errors.http.UnprocessableEntityException;
import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperServiceTest {

    private ModelMapperService modelMapperService;

    @BeforeEach
    public void before() {
        this.modelMapperService = new ModelMapperService();
        this.modelMapperService.initialize();
    }

    @Test
    @DisplayName("Should be config model mapper")
    public void testInitialize() {
        final var modelMapperService2 = new ModelMapperService();
        assertThrows(NullPointerException.class, () -> modelMapperService2.map(new A(), B.class));
        modelMapperService2.initialize();
        assertDoesNotThrow(() -> modelMapperService2.map(new A("a", "b"), C.class));
    }

    @Test
    @DisplayName("Should be map entity")
    public void testMap() {
        final var a = new A("a", "b");
        final var b = new B();
        this.modelMapperService.map(a, b);
        assertEquals(a.getX(), b.getX());

        final var c = new C();
        this.modelMapperService.map(a, c);
        assertEquals(a.getX(), c.getX());
        assertEquals(a.getY(), c.getY());

        final var d = new D();
        this.modelMapperService.map(a, d);
        assertEquals(a.getX(), d.getX());
        assertEquals(a.getY(), d.getY());
        assertNull(d.getZ());
    }

    @Test
    @DisplayName("Should be map entity with error")
    public void testMapWithError() {
        final var a = new A("a", "b");
        final var f = new F();
        assertThrows(UnprocessableEntityException.class, () -> modelMapperService.map(a, f));
    }

    @Test
    @DisplayName("Should be map entity with class")
    public void testMapWithClass() {
        final var a = new A("a", "b");
        final var b = modelMapperService.map(a, B.class);
        assertEquals(a.getX(), b.getX());

        final var c = modelMapperService.map(a, C.class);
        assertEquals(a.getX(), c.getX());
        assertEquals(a.getY(), c.getY());

        final var d = modelMapperService.map(a, D.class);
        assertEquals(a.getX(), d.getX());
        assertEquals(a.getY(), d.getY());
        assertNull(d.getZ());
    }

    @Test
    @DisplayName("Should be map entity with class, with error")
    public void testMapWithClassWithError() {
        final var a = new A("a", "b");
        assertThrows(UnprocessableEntityException.class, () -> modelMapperService.map(a, E.class));
    }

    @Test
    @DisplayName("Should be map all entities with class")
    public void testMapAll() {
        final var a = new A("a", "b");
        final var a2 = new A("a2", "b2");
        final var a3 = new A("a3", "b3");

        final var list = List.of(a, a2, a3);

        final var actual = modelMapperService.mapAll(list, B.class);
        assertEquals(3, actual.size());
        assertEquals(a.getX(), actual.get(0).getX());
        assertEquals(a2.getX(), actual.get(1).getX());
        assertEquals(a3.getX(), actual.get(2).getX());

        final var actual2 = modelMapperService.mapAll(list, C.class);
        assertEquals(a.getX(), actual2.get(0).getX());
        assertEquals(a.getY(), actual2.get(0).getY());
        assertEquals(a2.getX(), actual2.get(1).getX());
        assertEquals(a2.getY(), actual2.get(1).getY());
        assertEquals(a3.getX(), actual2.get(2).getX());
        assertEquals(a3.getY(), actual2.get(2).getY());

        final var actual3 = modelMapperService.mapAll(list, D.class);
        assertEquals(a.getX(), actual3.get(0).getX());
        assertEquals(a.getY(), actual3.get(0).getY());
        assertNull(actual3.get(0).getZ());
        assertEquals(a2.getX(), actual3.get(1).getX());
        assertEquals(a2.getY(), actual3.get(1).getY());
        assertNull(actual3.get(1).getZ());
        assertEquals(a3.getX(), actual3.get(2).getX());
        assertEquals(a3.getY(), actual3.get(2).getY());
        assertNull(actual3.get(2).getZ());
    }

    @Test
    @DisplayName("Should be map all entities with class, with error")
    public void testMapAllWithError() {
        final var a = new A("a", "b");
        final var a2 = new A("a2", "b2");
        final var a3 = new A("a3", "b3");

        final var list = List.of(a, a2, a3);
        assertThrows(UnprocessableEntityException.class, () -> modelMapperService.mapAll(list, E.class));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class A {
        private String x;
        private String y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class B {
        private String x;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class C {
        private String x;
        private String y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class D {
        private String x;
        private String y;
        private String z;
    }

    static class E {
        public E() {
            throw new RuntimeException();
        }
    }

    @Setter
    @NoArgsConstructor
    static class F {
        @SuppressWarnings("unused")
        private String x;

        @SuppressWarnings("unused")
        public String getX() {
            throw new RuntimeException();
        }
    }

}
