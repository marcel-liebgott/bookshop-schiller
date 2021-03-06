bookshop-schiller
================

This is a spring project for university. It is a small sales point application for a bookshop.

Continous Integration
---------------------

[![Build Status](https://magnum.travis-ci.com/MarauderXtreme/bookshop-schiller.svg?token=zfEnTimz1GphjpRCVMQw)](https://magnum.travis-ci.com/MarauderXtreme/bookshop-schiller)

Project Description
-------------------

Die Buchhandlung **SCHILLER** benötigt eine Verkaufsanwendung. Hauptsächlich ist eine Verkaufsanwendung für die Bücher zu implementieren. Jedoch hat der Geschäftsführer noch einige eigene Ideen.

Die Anwendung benötigt, neben einer Artikelverwaltung auch eine Benutzerverwaltung. Zu jedem Buch muss mindestens der Autor, Verlag, die ISBN und eine kurze Inhaltsbeschreibung gespeichert werden. Eine Abbildung des Buchbundes anzuzeigen, würde die Attraktivität des Verkaufsprogrammes deutlich steigern. Die Bücher der Buchhandlung SCHILLER sind nach Genre in die Kategorien Fiktion, Sachbuch, Unterhaltung, Ratgeber unterteilt. Eine Möglichkeit zu Erweiterung und nachträglichem Hinzufügen weiterer Genres ist wünschenswert. Der Geschäftsinhaber denkt auch über ein Angebot von CDs und DVDs nach. Die Benutzerverwaltung soll einige wichtige Angaben zum Kunden liefern (Name, Kundennummer, Lieferadresse, etc.).

Als zusätzliches Feature wünscht der Buchhandel **SCHILLER** sich einen Kalender auf der Homepage, welcher die wöchentlichen Lesungen aufführt, die in den Räumen der Buchhandlung stattfinden. Die Bezahlung der gekauften Bücher erfolgt über Rechnungsversand. 

How-To WAR
----------

1. Make sure in your *pom.xml* the packaging is set to "**war**".
2. After that open that directory in the command line. (Where the pom.xml resists)
3. Run >$ mvn clean package
4. now cd into the *target/* directory.
5. Now you can copy the *.war whereever you want.
6. Now you can start the *.war with java -jar /path/to/file/*.war
7. ???
8. Profit!
