package com.example.app.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Firebase's Java API returns a Task<T> with callbacks. This turns it into a
 * suspend function so repositories can be written as ordinary sequential code:
 *
 *     val result = auth.signInWithEmailAndPassword(email, pass).await()
 *
 * instead of nesting addOnSuccessListener inside addOnSuccessListener.
 * Returns null for Task<Void> (set/update/delete), which have no result value.
 */
suspend fun <T> Task<T>.await(): T? = suspendCancellableCoroutine { cont ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            cont.resume(task.result)
        } else {
            cont.resumeWithException(
                task.exception ?: RuntimeException("অজানা সমস্যা হয়েছে")
            )
        }
    }
}
