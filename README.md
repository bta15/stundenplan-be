# Stundenplan-Backend

Hier entsteht eine Stundenplan-API, umgesetzt mit Kotlin und Ktor.

#### Eingaben:

- Name der Schule
- Schulform: Grundschule, Oberschule
- Klassen: 1-6 oder 7-12 je nach Schulform (konkrete Klassen z.B. 1a, 5b)
- Lehrer:
  - Fächer (die sie unterrichten könnten)
  - Klassen (die sie unterrichten könnten): 1-12
  - Klassenleiter für: Klasse 1-12 (Auswahl an bereits vorhandenen Klassen)

#### Ausgabe:

Stundenplan, ungefähr so:

```
{
    "schule": {
        "name": "...",
        ...
    }
}
```

#### Initial vorhanden:

- Fächer:
  - verfügbar für Klassen: 1-12
- eine Beispiel-Schule mit allen nötigen Einträgen

#### Speicherung der Daten:

- eine InMemory DB (H2 usw.)
