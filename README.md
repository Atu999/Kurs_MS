# Wersja 2 Komunikacja asynchroniczna - łączenie dwóch aplikacji.

Pamiętaj, żeby w pliku application.properties w projekcie publisher i receiver ustawić swoje namiary na RabbitMQ.
Publisher wysyłanie notyfikacji na RabbitMq

POST http://localhost:8085/notification 

{
   "email": "marian@gmial.com",
   "title": "Witaj!",
   "body": "Miło, że jesteś z nami!"
}




Reciver automatycznie pobiera notyfikację dzięki @RabbitListener.
Jeśli chce pobrać notyfikację "manualnie" możesz za komentować @RabbitListener i wywołać endpoint:

GET http://localhost:8090/notification


