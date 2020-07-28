# Wersja 4. Po refactor aplikacji students. Przed zadaniem 2

### Eureka Service 
Usługa dynamiczne rejestrująca instancji wszystkich serwisów (w tym projekcie nie rejestrujemy Eureki samem w sobie).
Podgląd zarejestrowanych usług (konsola eureki):

*http://localhost:8761*


### Gateway Service 
Bramka proxy odbierająca wszystkie żądania przychodzące z interfejsu użytkownika.
Przekierowuje ruch do odpowiednich usług. Pobranie listy studentów za pośrednictwem gateway:

*http://localhost:9000/api/students*

### Students Service

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

