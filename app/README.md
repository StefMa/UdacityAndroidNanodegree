# Choicm App
The Application module for Choicm.

## Firebase
This Application module setup the core library for the Firebase project.
Because of this it has implement the `firebase-core` dependency and apply the 
`com.google.gms.google-services` plugin.

The `auth` and `realtime-database` modules provide more features for the App.

### google-services.json
The `google-services.json` which is more or less required to setup Firebase probably 
needs to be placed inside this `app/` module.

They `json`-File is also added to the `.gitignore` because it shouldn't be commit 
to git or any other VCS.