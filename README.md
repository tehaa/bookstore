## Bookstore Api system 
this system to help you to manage your bookstore by providing apis which help you to :

1-add new bookstore

2-update book

3-delete book

4-search for books by title and author name (note that author name here is optional)


At first you need to   

## create bookstore schema:

using mysql to create new schema with name bookstore or run `CREATE SCHEMA `bookstore` using mysql .

## import dump to schema:
you can import bookstore.sql dump in bookstore schema using MYSQL Workbench or import using command :
 `mysqldump -p --user=[username] bookstore < [path of dump file]`.
 
## Run the application

you can use this command to run this application

java -jar target/book-store-0.0.1-SNAPSHOT.jar > bookstore.log &

## use apis 

1- import bookStoreCollection.postman_collection collection to your postman application to can use this apis

2-get token from auth api using this credentials
 
username : test

password: 12345

3- start using the apis

 
 