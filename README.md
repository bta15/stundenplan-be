# Stundenplan-Backend [ in Arbeit ]

Hier entsteht eine stark vereinfachte Stundenplan-API, umgesetzt mit Kotlin und Ktor.

#### Schule

##### Eingaben:
- Name der Schule
- Schulform: Grundschule, Oberschule
- Klassen: 1-6 oder 7-12 je nach Schulform (konkrete Klassen z.B. 1a, 5b)

##### Ausgabe:
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

- [ ] Fächer:
  - verfügbar für Klassen: 1-12
- [ ] eine Beispiel-Schule mit allen nötigen Einträgen

#### Speicherung der Daten:

- eine H2 In-Memory DB

#### TODOs

- [ ] Liste von Klassen bei Schule in der DB speichern (siehe TODOs im Code)
- [x] InMemory-DB
- [ ] weitere Endpoints
- [ ] Lehrer:
  - [ ] Fächer (die sie unterrichten könnten)
  - [ ] Klassen (die sie unterrichten könnten): 1-12
  - [ ] Klassenleiter für: Klasse 1-12 (Auswahl an bereits vorhandenen Klassen)
- [ ] minimale Logik für Stundenplan + Endpoints
- [ ] CRUD für alles, was nötig ist
