# Choicm

## Architecture
The App has different modules which different responsibilities.
The "entrypoint" or the better the Application is located inside the `app/` module.

Some other modules are for instance the `logging/` or the `auth/` module.
Both of them provide "helpers" or abstraction layers for different features inside the App.

Each module (even the Application) provides its own `README` which explains 
what they do.

### Module Architecture
Each module has it's own architecture. There is no need that all of them will
have the same architecture. 

So checkout each module `README` to get a deeper understanding how the module works. 