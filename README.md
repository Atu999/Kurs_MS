# Wersja 3. Po rozwiązaniu Zadania 1.

### Students Application
**Pobieranie listy studentów** - GET *http://localhost:8080/students*

**Pobieranie studenta po Id**  - GET *http://localhost:8080/students/{id}*

**Dodawanie studenta**         - POST *http://localhost:8080/students*
Przykładowe body:
```
{
   "firstName":"Arnold",
   "lastName":"Boczek",
   "email":"arnold@gmail.com"
}
```
**Modyfikacja całego zasobu student** - PUT *http://localhost:8080/students/{id}*
Przykładowe body:
```
{
   "firstName":"Arnoldek",
   "lastName":"Boczek",
   "email":"arnold@gmail.com"
}
```

**Modyfikacja części zasobu student** - PATCH  *http://localhost:8080/students/{id}*
```
{
   "firstName":"Arnoldek"
}
```

### Publisher Application

Pamiętaj, żeby w pliku application.properties ustawić swoje namiary na RabbitMQ.

**Wysyłanie na RabbitMQ notyfikacji z wykorzystaniem danych studenta.**
Logika:
Publisher Application za pomocą id studenta otrzymanego w request pobiera studenta z Students Appilication.
Wykorzystując dane pobranego studenta buduje notyfikacje i wysyła na RabbitMQ.

GET *http://localhost:8085/notifications?studentId={id}*


Funkcjonalność nie zawiera obsługi błędów.
Do prawidłowego przeprocesowanie potrzeba, aby Students Application był uruchomiony i istniał student o id podanym w parametrze studentId


### Reciver Application
Pamiętaj, żeby w pliku application.properties ustawić swoje namiary na RabbitMQ.
Pamiętaj, że nazwa kolejki na RabbitMQ musi się zgadzać z nazwą kolejki, która jest w kodzie.

Reciver automatycznie pobiera notyfikację z kolejki dzięki @RabbitListener.
Automatycznie pobrana notyfikacja zostaje wyświetlona w konsoli.

Jeśli chcesz pobrać notyfikację za pomocą REST API, możesz zakomentować @RabbitListener i wywołać endpoint:

GET *http://localhost:8090/notification*


