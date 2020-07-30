# Zabezpieczenie aplikacji za pomocą OAuth2

### Auth Service 
Usługa odpowiedzialna za wydawanie i weryfikację tokenu.

**Wydawanie tokenu:**
 POST *http://localhost:8888/oauth/token*

Aby otrzymać token klient musi być zaufany, czyli przedstawić się serwerowi 
autoryzacyjnemu za pomocą akceptowalnych przez niego client_id i password.
Wykonuję się to za pomocą Authorisation Basic Auth:
```
Username = kurs
Password = kurs1
```
W body request należy przekazać dane o tym, z jakiego grant type ma korzystać
Oauth2 przy wydawaniu tokenu oraz dla kogo ma być wydany token. Czyli jeśli chcemy 
dostać token dla username=admin, należy w body w sekcji x-www-form-urlencoded podać:
```
grant_type = password
username = admin
password = admin1
```

 **Weryfikacja tokenu:** POST *http://localhost:8888/oauth/check_token*

Aplikacja jest zaimplementowana w taki sposób, że token może zweryfikować tylko zaufany
klient. Aby zweryfikować token należy podać w request Authorisation Basic Auth:
```
Username = kurs
Password = kurs1
```
Następnie przekazać wartość tokenu w body request w opcji 
form-date:
```
token = {token}
```
lub można przekazać w parametrze HTTP o kluczu token.

### Resource Service 
Usługa posiadająca zasoby, do których chcę się dostać klient.
Aplikacja posiada 3 endpointy do których wywołania potrzebne są różne uprawnienai. 

 **Publiczny:**
GET *http://localhost:7777/public*

 **Dla użytkownika z rolą USER:**
GET *http://localhost:7777/user*

Aby autoryzacja przebiegłą prawidłowo należy podać w nagłówku 
```
Authorisation: Bearer {token_usera}
```
Do przekazania wartości tokenu można wykorzystać zakładkę Authorisation i type Bearer token w Postman.
  
  **Dla użytkownika z rolą ADMIN:**
GET *http://localhost:7777/admin*
Aby autoryzacja przebiegłą prawidłowo należy podać w nagłówku 
```
Authorisation: Bearer {token_admina}
```
Do przekazania wartości tokenu można wykorzystać zakładkę Authorisation i type Bearer token w Postman.
  
