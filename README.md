# Spring Boot Test 

## Build

To build use command

```shell script
mvn clean package
```

after that check `target/` folder, this folder should contain `jar` file

## Endpoints
### POST /register 
 
This endpoint accepts json object with two string properties: email and password. On successful create it will return status 201.

Request body:
```json
{
	"email" : "test@test.tt",
	"password" : "pass"
}
``` 
 
### GET /api/file 
 
This endpoint will return json object with two properties: owned and shared. Both of those will be an array of objects that are representing files owned by authenticated user and files that are shared with him. 
```json
{
    "owned": [
        {
            "id": "457fca81-7fda-4f26-946e-068e8de2c986"
        }
    ],
    "shared": []
}
```
### GET /api/file/{id} 
 
This endpoint will be used to download file associated with given ID in uri. This endpoint returns any kind of data formats as is files were uploaded  
```
bytes here
```
### POST /api/file 
 
This endpoint will be used to upload the file and on success it will return file ID that can be used with GET endpoint to download file.

Request: expect `multipart/form-data` with field `file` that contains data to be uploaded

Response on success:
```json
{
    "id": "ccc6d5ce-17bf-4fa3-aa81-e2c7963fb6d0"
}
```

### POST /api/share 
 
This endpoint will accept json object with two string properties: email and file ID. It will be used by file owner to share access to the file with other users. Only the file owner can give the access to his file.

Request body:
```json
{
	"fileId" : "457fca81-7fda-4f26-946e-068e8de2c986",
	"email" : "test2@test.tt"
}
``` 

Response: 200OK