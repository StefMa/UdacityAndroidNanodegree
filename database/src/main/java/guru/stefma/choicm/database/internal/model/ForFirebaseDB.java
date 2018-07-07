package guru.stefma.choicm.database.internal.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes that the field, constructor, whatever
 * is only available or more public than it should be
 * because of the Firebase Realtime Database...
 */
@Retention(RetentionPolicy.SOURCE)
@interface ForFirebaseDB {

}
