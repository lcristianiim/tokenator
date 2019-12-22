[![tokenator](https://i.imgur.com/KmN97w1.png)](#)

# tokenator
A java based server that is implementing the OAuth2 specification. 

## :gear: Installation
### Setting environment variables
Setup the following environments variables (replace the values with you're own):
```shell script
DB_URL='jdbc:mysql://localhost:3306/user_db?useSSL=false&useUnicode=true&characterEncoding=UTF-8'
DB_USERNAME='root'
DB_PASSWORD='123'
TOKEN_SIGNATURE=123
CLIENT_NAME=app
CLIENT_SECRET=secret
CLIENT_SCOPES='read,write'
AUTHORIZED_GRANT_TYPE='refresh_token,password'
ACCESS_TOKEN_VALIDITY=86400
REFRESH_TOKEN_VALIDITY=604800
HIBERNATE_DLL_AUTO=create
```
### Starting the database within a docker container
```shell script 
# if you run it for the first time
docker-compose up -d
# 
```
```shell script
# if you starting it not for the first time
docker-compose start
# 
```
## :clipboard: Run
Method 1
```shell script
# Create the executable jar (build/libs/tokenator-0.0.1-SNAPSHOT.jar)
./gradlew bootJar
cd build/libs
# Run the jar build/libs/tokenator-0.0.1-SNAPSHOT.jar
java -jar tokenator-0.0.1-SNAPSHOT.jar  
``` 

Method 2
```shell script
./gradlew bootRun
```

## Requests examples
### Get access & refresh token for a certain user (username: client@email.com; password: client123456789)
```shell script
curl -X POST \
  'http://localhost:8080/oauth/token?grant_type=password&username=client%40email.com&password=client123456789' \
  -H 'authorization: Basic YXBwOnNlY3JldA==' \
  -H 'cache-control: no-cache'
```

Response will be in this format:
``` json
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiY2xpZW50QGVtYWlsLmNvbSIsImRpc3BsYXlOYW1lIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU3NzA5NzY2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9HVUVTVCJdLCJqdGkiOiJmMzJiYWU0Ny0yNGRiLTQxZWYtOGZkNy01NTRhODdkYWJjNDUiLCJjbGllbnRfaWQiOiJhcHAifQ.mfpXHv9HgWTmWvH_tb21Hu-6zAtTnLmxyM648juRpnY","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiY2xpZW50QGVtYWlsLmNvbSIsImRpc3BsYXlOYW1lIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImYzMmJhZTQ3LTI0ZGItNDFlZi04ZmQ3LTU1NGE4N2RhYmM0NSIsImV4cCI6MTU3NzYxNjA2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9HVUVTVCJdLCJqdGkiOiI5ZDAyZWM3YS04ZjFjLTQxMDgtYjgxNS1iNGZhOTExYjEyODUiLCJjbGllbnRfaWQiOiJhcHAifQ.qJDfHdC2X1w_kvYXgBADFKOuNu1HZF8suhuhaYQAaOM","expires_in":86399,"scope":"read write","displayName":"client","jti":"f32bae47-24db-41ef-8fd7-554a87dabc45"} 
``` 
### Verify if token is valid

```shell script
curl -X GET \
  'http://localhost:8080/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiY2xpZW50QGVtYWlsLmNvbSIsImRpc3BsYXlOYW1lIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU3NzA5NzY2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9HVUVTVCJdLCJqdGkiOiJmMzJiYWU0Ny0yNGRiLTQxZWYtOGZkNy01NTRhODdkYWJjNDUiLCJjbGllbnRfaWQiOiJhcHAifQ.mfpXHv9HgWTmWvH_tb21Hu-6zAtTnLmxyM648juRpnY' \
  -H 'authorization: Basic YXBwOnNlY3JldA==' \
  -H 'cache-control: no-cache'
```

Response will be in this format:
``` json
{
    "aud": [
        "resource"
    ],
    "user_name": "client@email.com",
    "displayName": "client",
    "scope": [
        "read",
        "write"
    ],
    "exp": 1577097663,
    "authorities": [
        "ROLE_GUEST"
    ],
    "jti": "f32bae47-24db-41ef-8fd7-554a87dabc45",
    "client_id": "app"
}
```
### Request a new access token using the refresh token
```shell script
curl -X POST \
  'http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiY2xpZW50QGVtYWlsLmNvbSIsImRpc3BsYXlOYW1lIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImYzMmJhZTQ3LTI0ZGItNDFlZi04ZmQ3LTU1NGE4N2RhYmM0NSIsImV4cCI6MTU3NzYxNjA2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9HVUVTVCJdLCJqdGkiOiI5ZDAyZWM3YS04ZjFjLTQxMDgtYjgxNS1iNGZhOTExYjEyODUiLCJjbGllbnRfaWQiOiJhcHAifQ.qJDfHdC2X1w_kvYXgBADFKOuNu1HZF8suhuhaYQAaOM&client_id=app&client_secret=secret' \
  -H 'authorization: Basic YXBwOnNlY3JldA==' \
  -H 'cache-control: no-cache'
```
Response will be in this format:
```json
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiY2xpZW50QGVtYWlsLmNvbSIsImRpc3BsYXlOYW1lIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU3NzA5ODI2NCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9HVUVTVCJdLCJqdGkiOiIwYTBmYzA5Yi1hOGNlLTRhZTEtYjFhOC05YzBjNzBlMThjOGIiLCJjbGllbnRfaWQiOiJhcHAifQ.Ty4k02Nkt4rfH5yeqXXZlT4SlW3vGQClxcJAdeoNRts","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiY2xpZW50QGVtYWlsLmNvbSIsImRpc3BsYXlOYW1lIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjBhMGZjMDliLWE4Y2UtNGFlMS1iMWE4LTljMGM3MGUxOGM4YiIsImV4cCI6MTU3NzYxNjA2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9HVUVTVCJdLCJqdGkiOiI5ZDAyZWM3YS04ZjFjLTQxMDgtYjgxNS1iNGZhOTExYjEyODUiLCJjbGllbnRfaWQiOiJhcHAifQ.UCZs5Ri_cDHpI6RJl3xjweeSGMBlY1qDSO-cYJ7yVg8","expires_in":86399,"scope":"read write","displayName":"client","jti":"0a0fc09b-a8ce-4ae1-b1a8-9c0c70e18c8b"}
```

### Request notes
Tho following: 
```
Basic YXBwOnNlY3JldA=='
``` 
represents the values for CLIENT_NAME:SECRET defined as environments variables.
In this case it would be app:secret transformed in base64. This is where the `YXBwOnNlY3JldA=='` comes from.

## Note
Two dummy users are created by default in the Main class for testing purposes only.


## :scroll: License
Apache License, Version 2.0 © Cristian Szabo

