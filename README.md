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
- **Kalender**: Studierende können ihre Ereignisse und Termine in einem Kalender anzeigen lassen.

## Sicherheit

StudyDash legt großen Wert auf Sicherheit. Wir verwenden JWT-Tokens für die Authentifizierung der Nutzer und Verschlüsselung der Passwörter in der Datenbank, um die Daten unserer Benutzer zu schützen.
Vielmehr, bitte beachten Sie, dass aufgrund der Sicherheit die Spring-Datei application.properties nicht im Repository enthalten ist. Sie müssen Ihre eigene Datei erstellen, um die Anwendung auszuführen.

## Installation und Verwendung

Da es sich um ein Maven-Projekt handelt, können Sie es einfach mit dem Befehl `mvn install` bauen und dann mit `mvn spring-boot:run` ausführen.

Bitte beachten Sie, dass Sie eine Datenbank und einen passenden JDBC-Treiber benötigen, um StudyDash auszuführen. Die Konfigurationseinstellungen können in der `application.properties` Datei angepasst werden.

## Lizenz

Die Lizenzen wurden unter LICENSE.md aufgeführt. Bitte beachten Sie, dass dieses Projekt nur für Bildungszwecke erstellt wurde und nicht für kommerzielle Zwecke verwendet werden sollte.