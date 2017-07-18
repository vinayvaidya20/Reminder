# ReminderService README 

This is a simple reminder restful service. It uses an in-memory database to store the data and ready to be consumed by a client. Folowing are the operations available via this service-
1. Add new reminder
2. Update an existing reminder
3. Delete a reminder
4. Retrive reminder based on name,due date and status

## Table of Contents

1. [Implementation](#implementation)
1. [Dependencies](#dependencies)
1. [Installation](#installation)
1. [API](#api)


### Implementation

The service is standalone SpringBoot application. The code consists of two separate services, one for the crud operation (reminder-crud) and other of the business layer (reminder-service).
The crud service is called from the reminder-services via rest api calls.
The implementation uses concepts of -
1. Spring Data JPA
2. Spring Boot REST API
3. Spring Boot 1.5.4.RELEASE
4. Spring Boot Actuators
4. HATEOAS

In the interest of time, the Junit via Mockito/Powermock has been done for few files and code analyzer tool sonarLite has been run on the src files.

### Dependencies

The application has following dependencies -
1. JDK 1.8
2. Maven 3.x
3. RestClient Tool such as Advanced restClient/Postman/SOAP-UI

### Installation

1. Clone the services crud-reminder and reminder-service.
2. Make sure you are using JDK 1.8 and Maven 3.x.
3. You can build the projects by running mvn clean install.
4. Once successfully built you can run the services one by one of these two methods.

```
java -jar {jar path of crud-reminder-0.0.1-SNAPSHOT.jar}
java -jar {jar path of reminder-service-0.0.1-SNAPSHOT.jar}
	
or 
	
From Eclipse
Right Click on the reminder-crud project -> Run As -> Java Application or Spring Boot App(STS)
Right Click on the reminder-service project -> Run As -> Java Application or Spring Boot App(STS)
```
5. The default port of the reminder-service is 8080 and configurable in the application.properties.
6. The default port of the reminder-crud is 8082 and configurable in the application.properties.
7. The endpoint of the remider-crud is configured in the /src/main/resources/reminder.properties in reminder-service.

### API

### Get information about system health, info, etc.

```
reminder-crud
http://localhost:8081/health
http://localhost:8081/info

reminder-service
http://localhost:8083/health
http://localhost:8083/info
```

#### Create a reminder resource
```
**POST**

http://localhost:8080/reminders/
Accept: application/json
Content-Type: application/json

SAMPLE VALID POST REQUEST
{
"name": "Vinay",
"description": "Vinay Test 123",
"status": "DONE",
"dueDate": "07/17/2017"
}

CREATED SUCCESS RESPONSE 
{
"type": "Created",
"code": "201",
"message": "Reminder addition successful.",
"links": [
  {
"link": "http://localhost:8080/reminders/Vinay",
"rel": "self"
}
],
}

SAMPLE INVALID REQUEST WITH PAST DATE(MM/dd/yyyy)
{
"name": "Vinay",
"description": "Vinay test 1234",
"status": "NOTDONE",
"dueDate": "05/08/2017"
}

INVALID DATA ERROR RESPONSE
{
"type": "Bad Request",
"code": "400",
"message": "Please enter valid date.",
"links": [],
}

SAMPLE INVALID REQUEST WITH DB SERVICE DOWN
{
"name": "Vinay",
"description": "Vinay test 1234",
"status": "NOTDONE",
"dueDate": "08/08/2017"
}

INTERNAL ERROR RESPONSE
{
"type": "Failed",
"code": "500",
"message": "Reminder could not be added.",
"links": [],
}
```

#### Retrieve a list of reminder

```
**GET**

http://localhost:8080/reminders/Vinay
http://localhost:8080/reminders/Vinay?status=NOTDONE
http://localhost:8080/reminders/Vinay?dueDate=07/17/2017
http://localhost:8080/reminders/Vinay?dueDate=07/17/2017&status=DONE

SAMPLE GET VALID RESPONSE
{
   "type": "Success",
   "code": "200",
   "message": "Following are your Reminders.",
   "reminderList":    [
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "NOTDONE",
         "dueDateString": "07/20/2017",
         "id": 2
      },
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "DONE",
         "dueDateString": "07/17/2017",
         "id": 7
      },
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "DONE",
         "dueDateString": "07/17/2017",
         "id": 8
      },
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "DONE",
         "dueDateString": "07/17/2017",
         "id": 9
      },
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "DONE",
         "dueDateString": "07/18/2017",
         "id": 10
      },
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "NOTDONE",
         "dueDateString": "07/19/2017",
         "id": 11
      },
            {
         "name": "Vinay",
         "description": "Vinay Test 123",
         "status": "NOTDONE",
         "dueDateString": "07/20/2017",
         "id": 21
      }
   ]
}

SAMPLE GET REQUEST INVALID NAME
http://localhost:8080/reminders/XYZ

RESPONSE FOR INVALID NAME
{
   "type": "Success",
   "code": "200",
   "message": "You don't have any Reminders."
}
```


#### Update a reminder resource
```
**PUT**

http://localhost:8080/reminders/
Accept: application/json
Content-Type: application/json

PUT VALID REQUEST
{
"description": "Vinay Test Updated",
"id": 1
}

PUT VALID RESPONSE
{
   "type": "Success",
   "code": "200",
   "message": "Reminder updated successfully.",
   "links": [   {
      "link": "http://localhost:8080/reminders/Vinay",
      "rel": "self"
   }]
}

PUT INVALID ID REQUEST
{
"description": "Vinay Test Updated",
"id": 678
}

PUT INVALID ID RESPONSE
{
   "type": "Failed",
   "code": "400",
   "message": "No records found",
   "links": []
}
```

#### Delete a reminder resource by id

```
**DELETE**

DELETE VALID REQUEST
http://localhost:8080/reminders/1

DELETE VALID RESPONSE
{
   "type": "Success",
   "code": "200",
   "message": "Reminder deleted successfully.",
   "links": []
}

DELETE INVALID ID REQUEST
http://localhost:8080/reminders/100

DELETE INVALID ID RESPONSE
{
   "type": "Failed",
   "code": "400",
   "message": "No records found",
   "links": []
}
```

**[Back to top](#table-of-contents)**



