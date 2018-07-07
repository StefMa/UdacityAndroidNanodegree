# Choicm Auth
A module which provides a abstraction layer over Firebase Authentication.

## Configuration
This module provides **only** a wrapper around Firebase Authentication.
The module expect that Firebase was already successfully setup inside a Application.

### Get an instance
To get an instance just call `AuthenticationProvider#getInstance()`.
This will return a default implementation to handle the communication between
Firebase Authentication and the "client".

  