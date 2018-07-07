# Choicm Database
A module which provides a abstraction layer over the Firebase Realtime Database.

## Configuration
This module provides **only** a wrapper around the Firebase Realtime Database.
The module expect that Firebase was already successfully setup inside a Application.

## Dependencies
The database uses the (`auth`)[Authentication project] internally so it 
can access the User.
So make sure that you access some methods only if the User is logged in.

## Get an instance
To get an instance just call `DatabaseProvider#getInstance()`.
This will return a default implementation to handle the communication between
Firebase Realtime Database and the "client".

## Architecture
Like I mentioned above the "entrypoint" is the `Database` interface.
This should be the only API point throw the Realtime Database.

The `model` package provides some "default" models which has to be used
to communicate with the API. Feel free to use these modules inside the client.
Some of them are named `*Request` and some of them `*Response`. 
As you probably noticed inside the `Database` interface the `*Request` 
models will be used if you **save** something in the Database.
The `*Request` one will be used if you **request** some data from the Database.

### Internal
This module has a `internal` package which should **never touched by a client**!
All of the contained classes are designed to work **internally** for this module.
They are likely to change frequently.
 
#### Internal Models
Because the Realtime Database has some requirements how to "model" 
the DTOs the `internal` package has a `internal/model` package
which will be used to design the models like we want to save it 
in the Realtime Database.

#### Internal UseCases
The `internal/usecase` package contains some "usecases" which are
only "abstractions" from the `DefaultDatabase` implementation.

The `DefaultDatabse` simply delegates to these UseCases and handle 
the communication between these UseCases and the client. 