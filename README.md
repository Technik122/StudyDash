# StudyDash

StudyDash ist ein Dashboard für Studierende, das auf Spring Boot und Maven basiert. Es bietet eine Reihe von Funktionen, die speziell auf die Bedürfnisse von Studierenden zugeschnitten sind.
Hier handelt es sich um das **Backend** der Anwendung.

## Projektinformationen

Dieses Projekt wurde im Sommersemester 2024 an der IU Internationale Hochschule in Hamburg für das Modul Fallstudie: Software-Engineering erstellt.

## Funktionen

- **ToDo-Verwaltung**: Studierende können ihre Aufgaben und Verpflichtungen in einer übersichtlichen Liste verwalten.
- **Kursverwaltung**: Studierende können ihre Kurse verwalten und haben einen Überblick über ihre aktuellen Prüfungsleistungen.
- **Notizenverwaltung**: Studierende können Notizen zu ihren Kursen und Aufgaben machen und diese Notizen verwalten.
- **Notenverwaltung**: Studierende können ihre Noten eintragen und den Durchschnitt berechnen. Es gibt auch ein Anreizbenachrichtigungssystem, das Studierende dazu ermutigt, ihre Noten zu verbessern.

## Sicherheit

StudyDash legt großen Wert auf Sicherheit. Wir verwenden JWT-Tokens für die Authentifizierung der Nutzer und Verschlüsselung der Passwörter in der Datenbank, um die Daten unserer Benutzer zu schützen.

## Installation und Verwendung

Da es sich um ein Maven-Projekt handelt, können Sie es einfach mit dem Befehl `mvn install` bauen und dann mit `mvn spring-boot:run` ausführen.

Bitte beachten Sie, dass Sie eine Datenbank und einen passenden JDBC-Treiber benötigen, um StudyDash auszuführen. Die Konfigurationseinstellungen können in der `application.properties` Datei angepasst werden.