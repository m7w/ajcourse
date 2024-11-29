package com.github.m7w.ajcourse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class CustomArrayListTest {
    private CustomArrayList<Integer> list;

    @BeforeEach
    public void initList() {
        list = new CustomArrayList<>(2);
        list.add(5);
        list.add(9);
    }

    @Test
    public void testAddGetSize() {
        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(9, list.get(1));
    }

    @Test
    public void testAddAtIndex() {
        list.add(1, 7);
        assertEquals(3, list.size());
        assertEquals(5, list.get(0));
        assertEquals(7, list.get(1));
        assertEquals(9, list.get(2));
    }

    @Test
    public void testAddAtIndexException() {
        IndexOutOfBoundsException e =
                assertThrows(IndexOutOfBoundsException.class, () -> list.add(3, 7));
        assertEquals("index 3 should be > 0 and <= 2", e.getMessage());
    }

    @Test
    public void testAddAss() {
        list.addAll(List.of(10, 11, 12));
        assertEquals(5, list.size());
        assertEquals(10, list.get(2));
        assertEquals(11, list.get(3));
        assertEquals(12, list.get(4));
    }

    @Test
    public void testClearIsEmpty() {
        list.clear();
        assertEquals(true, list.isEmpty());
    }

    @Test
    public void testIndexOf() {
        list.addAll(List.of(10, 11, 12));
        assertEquals(3, list.indexOf(11));
        assertEquals(-1, list.indexOf(100));
    }

    @Test
    void testRemove() {
        list.add(7);
        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(7, list.get(1));
        list.remove(1);
        assertEquals(1, list.size());
        assertEquals(5, list.get(0));
        assertEquals(null, list.get(1));
    }

    @Test
    void testRemoveObject() {
        list.add(7);
        list.remove((Object) 9);
        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(7, list.get(1));
        list.remove((Object) 7);
        assertEquals(1, list.size());
        assertEquals(5, list.get(0));
        assertEquals(null, list.get(1));
    }

    @Test
    void testToArray() {
        Object[] array = list.toArray();
        assertEquals(5, (Integer) array[0]);
        assertEquals(9, (Integer) array[1]);
    }

    @Test
    void testSort() {
        Random rand = new Random();
        list.addAll(rand.ints(100).boxed().toList());
        list.sort(Integer::compare);

        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue(list.get(i) <= list.get(i + 1));
        }
    }
}
