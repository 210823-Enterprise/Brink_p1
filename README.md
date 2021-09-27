# Brink

## Project Description
Brink is a custom built Object-relational mapping tool created to simplify the process of connecting to a database without requiring the need for SQL statements or connection management. 

## Technologies Used

* PostgreSQL - version 42.2.12  
* Java - version 8.0  
* Apache commons - version 2.1  

## Features

* User API that allows for ease of use.
* Bypass need for database specific language 
* Annotation based
* Logging of processes

To-do list:
* Allow ORM to create Join Tables based on Annotation in Entities    
* Allow ORM to set table constraints in Annotations 

## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/yassineelbaamrani/Group-6---Project-1.git
  cd Brink_p1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
    <dependency>
        <groupId>com.revature</groupId>
        <artifactId>diyORM</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>

```

Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
url=jdbc:postgresql://team-6-ncc.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/postgres?currentSchema=public
username=postgres
password=postgres
  ```
  
## Usage  
  ### Annotating classes  
  All classes which represent objects in database must be annotated.

   - #### @Id(columnName="test_id")  
      - Indicates that the Annotated field is ID for the column in the table with the name 'test_id'    
   - #### @Column(columnName="column_name", columnType="[`insert sql data type`]") 
      - Indicates that the Annotated field is a column in the table with the name 'column_name' of a specified data type
   - #### @Entity(table_name = "name of the table")
      -Indicates that the annotated class contains data to be persisted to a database.
    
  ### User API  
  - #### `public static DIYORM getInstance()`  
     - Returns the singleton instance of the class. It is the starting point to calling any of the below methods.
  - #### `public boolean createTableToDb(MetaModel m)`  
     - Creates table in database   
  - #### `public boolean deleteTableFromDb(MetaModel m)`  
     - Deletes table from database
  - #### `public boolean removeObjFromDb(Object obj)`  
     - Removes the given object from the database.   
  - #### `public boolean saveObjectToDb(Object obj)`  
     - Saves an object to the database.
  - #### `public boolean updateObjectFromDb(Object obj)`  
     - Updates values of object in database 
  - #### `public Object getObjectFromDb(Object obj)`  
     - Retrieves an object from the database 
  - #### `public Object getAllObjectsFromDb(Object obj)`  
     - Retrieves all objects from the database 



  - #### `public boolean commitChanges()`  
     - begin database commit.  
  - #### `public boolean rollbackChanges()`  
     - Rollback to previous commit.  
  - #### `public boolean setSavepoint(final String name)`  
     - Set a savepoint with the given name.  
  - #### `public boolean releaseSavepoint(final String name)`  
     - Release the savepoint with the given name.  
  - #### `public boolean enableAutoCommit()`  
     - Enable auto commits on the database.
  - #### `public boolean disableAutoCommit()`  
     - Disables auto commits on the database. 
  - #### `public boolean returnToSavepoint(Connection conn, String name)`  
     - rollback to inputted savepoint    

  - #### `public void putObjectInCache(Object obj)`  
     - Adds object to the cache.  
  - #### `public boolean cacheContains(Object obj)`  
     - Returns boolean based on if object is present within cache.  
  - #### `public boolean removeObjectFromCache(Object obj)`  
     - Removes object from cache.  
  - #### `public boolean updateObjectInCache(Object toReplace, Object toAdd)`  
     - Changes value of object in cache.  
