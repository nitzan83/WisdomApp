package com.example.WisdomApp.utils


const val MINUTE: Long = 60 * 1_000L
const val HOUR: Long = 60 * MINUTE
const val DAY: Long = 24 * HOUR

val intervalOptionToSeconds: HashMap<String, Long> = hashMapOf(
    "Minutes" to MINUTE,
    "Hours" to HOUR,
    "Days" to DAY
)


fun intervalInSeconds(intervalOption: String, interval: Long): Long? {
    return intervalOptionToSeconds[intervalOption]?.times(interval)
}

/*
Input: interval type, interval in seconds
Output: interval in wanted type
 */
fun intervalInTimeOption(intervalOption: String, interval: Long): Long? {
    return interval / intervalOptionToSeconds[intervalOption]!!
}
