# vrtlr

- start_dev.sh im Root startet zwei docker run commands. 
- einer zum kompilieren einer zum starten. (Code etc wird dabei vom host gemountet)

- das Jar wird ausgepackt und ein bisschen rumkopiert. 
- dann läuft das ding mit vrtlr-impl/src/main/resources im classpath

- Da liegen die ordner templates und public.
- Templates sind thymeleaf templates um das HTML zu rendern
- Public ist quasi das docroot (für JS Dateien und bilder, also public/foo.png wird dann http://127.0.0.1:8080/foo.png)
