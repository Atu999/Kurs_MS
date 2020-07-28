# Wersja 1. Rest API

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
