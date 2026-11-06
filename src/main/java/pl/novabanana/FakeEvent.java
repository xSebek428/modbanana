package pl.novabanana;

import java.util.List;

/** Fejkowa eventowka: nazwa na przycisku, tytul na srodku ekranu i linie czatu (co ~2 s). */
public record FakeEvent(String name, String title, String subtitle, List<String> lines) {}
