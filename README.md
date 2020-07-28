# Wersja 9. Course Service, po zadaniu 4.2

### Eureka Service 
Usługa dynamiczne rejestrująca instancji wszystkich serwisów (w tym projekcie nie rejestrujemy Eureki samem w sobie).
Podgląd zarejestrowanych usług (konsola eureki):

*http://localhost:8761*


### Gateway Service 
Bramka proxy odbierająca wszystkie żądania przychodzące z interfejsu użytkownika.
Przekierowuje ruch do odpowiednich usług. 

Przekierowanie ruchu do Students Service - Pobranie listy studentów:

*http://localhost:9000/api/students*

Przekierowanie ruchu do Course Service - Pobranie listy kursów:

*http://localhost:9000/api/courses*

### Students Service

**Pobieranie listy studentów** Opcjonalny parametr „status”.
 
 GET *http://localhost:8080/students*

**Pobieranie studenta po Id**  

 GET *http://localhost:8080/students/{id}*

**Dodawanie studenta**         - POST *http://localhost:8080/students*
Przykładowe body:
```
{
   "firstName":"Arnold",
   "lastName":"Boczek",
   "email":"arnold@gmail.com",
   "status": "ACTIVE"
}
```
**Modyfikacja całego zasobu student** - PUT *http://localhost:8080/students/{id}*
Przykładowe body:
```
{
   "firstName":"Arnoldek",
   "lastName":"Boczek",
   "email":"arnold@gmail.com",
   "status": "ACTIVE"
}
```

**Modyfikacja części zasobu student** - PATCH  *http://localhost:8080/students/{id}* 
Przykładowe body:
```
{
   "firstName":"Arnoldek"
}
```

**Pobieranie studentów po liscie emaili** - POST  *http://localhost:8080/students/emails* 
Przykładowe body:
```
[
   "pazdzioch@gmail.com",
   "boczek@gmail.com"
]
```

### Course Service

**Pobieranie listy kursów** Opcjonalny parametr „status”
 
 GET *http://localhost:8087/courses*
 
 **Pobieranie kursu po code (Id)**  
 
 GET *http://localhost:8087/courses/{code}*
 
 **Dodawanie kursu**         - POST *http://localhost:8087/courses*
Przykładowe body:
```
    {
        "code": "Java_2020",
        "name": "Java 8",
        "description": "Nauka Javy 8, poziom średnio-zawansowany",
        "startDate": "2020-10-10T08:00:00.274",
        "endDate": "2020-10-20T17:00:00.274",
        "participantsLimit": 6,
        "participantsNumber": 0,
        "status": "ACTIVE"
    }
```

 **Zapisywanie studenta na kurs**         - POST *http://localhost:8087/courses/{courseCode}/student/{studentId}*

 **Wyświetlanie listy uczestników kursu**      -GET *http://localhost:8087/courses/{code}/members*
