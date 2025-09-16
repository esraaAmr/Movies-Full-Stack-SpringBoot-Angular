package com.example.movie.util.time;

import java.sql.Timestamp;
import java.time.Instant;

public class CurrentTimeStamp {
    public static Timestamp timestamp() {
        return Timestamp.from(Instant.now());
    }
}
