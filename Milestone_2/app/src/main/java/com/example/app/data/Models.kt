package com.example.app.data

/**
 * Plain Kotlin data classes — no database, no Firebase.
 * When you add Firestore later these barely change: you add an `id` field
 * and default values for every property (Firestore needs a no-arg constructor).
 */

enum class UserRole { PASSENGER, OWNER }

enum class CarType(val label: String) {
    SEDAN("Sedan"),
    SUV("SUV"),
    MICRO("Micro"),
    HATCHBACK("হ্যাচব্যাক")
}

enum class BookingStatus(val label: String) {
    PENDING("অপেক্ষমাণ (Pending)"),
    ONGOING("চলমান (Ongoing)"),
    COMPLETED("সম্পন্ন (Completed)"),
    DECLINED("প্রত্যাখ্যাত (Declined)")
}

enum class VehicleStatus(val label: String) {
    ACTIVE("সক্রিয় (Active)"),
    RENTED("ভাড়া দেওয়া হয়েছে (Rented)"),
    INACTIVE("নিষ্ক্রিয় (Inactive)")
}

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val role: UserRole,
    val rating: Double = 0.0,
    val reviewCount: Int = 0
)

data class Vehicle(
    val id: String,
    val model: String,
    val brand: String,
    val year: Int,
    val carType: CarType,
    val pricePerDay: Long,
    val location: String,
    val transmission: String,
    val seats: Int,
    val fuelType: String,
    val description: String,
    val rating: Double,
    val ownerName: String,
    val ownerRating: Double,
    val ownerReviewCount: Int,
    val plateNumber: String = "",
    val status: VehicleStatus = VehicleStatus.ACTIVE,
    val todayEarnings: Long = 0,
    val tripsThisMonth: Int = 0
)

data class Booking(
    val id: String,
    val vehicleModel: String,
    val passengerName: String,
    val passengerRating: Double,
    val passengerReviewCount: Int,
    val dateRange: String,
    val days: Int,
    val pickupLocation: String,
    val rentalFee: Long,
    val serviceCharge: Long,
    val status: BookingStatus
) {
    val total: Long get() = rentalFee + serviceCharge
    /** What the owner receives — platform takes the service charge instead of adding it. */
    val ownerPayout: Long get() = rentalFee - serviceCharge
}
