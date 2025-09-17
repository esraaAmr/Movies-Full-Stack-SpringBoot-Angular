package com.example.movie.util.time;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * The type Current time stamp.
 */
public class CurrentTimeStamp {
    /**
     * Timestamp timestamp.
     *
     * @return the timestamp
     */
    public static Timestamp timestamp() {
        return Timestamp.from(Instant.now());
    }
}
