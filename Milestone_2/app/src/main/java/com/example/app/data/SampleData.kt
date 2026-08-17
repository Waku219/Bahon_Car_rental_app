package com.example.app.data

/**
 * Hardcoded data so every screen renders with realistic content.
 * DELETE THIS FILE once repositories are wired up — nothing else should
 * reference it by then except previews.
 */
object SampleData {

    val currentPassenger = User(
        id = "u1", name = "রাহাত", phone = "01712345678",
        role = UserRole.PASSENGER, rating = 4.9, reviewCount = 5
    )

    val currentOwner = User(
        id = "u2", name = "জাহিদ", phone = "01812345678",
        role = UserRole.OWNER, rating = 4.8, reviewCount = 31
    )

    val corolla = Vehicle(
        id = "v1", model = "Toyota Corolla", brand = "Toyota", year = 2022,
        carType = CarType.SEDAN, pricePerDay = 3500, location = "উত্তরা, ঢাকা",
        transmission = "অটোমেটিক (Automatic)", seats = 5,
        fuelType = "অকটেন/সিএনজি (Octane/CNG)",
        description = "ভালো কন্ডিশনের গাড়ি, এসি ও মিউজিক সিস্টেম রয়েছে।",
        rating = 4.8, ownerName = "আরিফ হোসেন", ownerRating = 4.9, ownerReviewCount = 30,
        plateNumber = "ঢাকা মেট্রো-১৪", status = VehicleStatus.ACTIVE,
        todayEarnings = 3500, tripsThisMonth = 12
    )

    val civic = Vehicle(
        id = "v2", model = "Honda Civic", brand = "Honda", year = 2021,
        carType = CarType.SEDAN, pricePerDay = 4000, location = "ধানমন্ডি, ঢাকা",
        transmission = "অটোমেটিক (Automatic)", seats = 5,
        fuelType = "অকটেন (Octane)",
        description = "নতুন টায়ার, সদ্য সার্ভিসিং করা।",
        rating = 4.9, ownerName = "সাকিব রহমান", ownerRating = 4.8, ownerReviewCount = 22
    )

    val xtrail = Vehicle(
        id = "v3", model = "Nissan X-Trail", brand = "Nissan", year = 2020,
        carType = CarType.SUV, pricePerDay = 5500, location = "গুলশান, ঢাকা",
        transmission = "অটোমেটিক (Automatic)", seats = 7,
        fuelType = "ডিজেল (Diesel)",
        description = "পরিবার ও লম্বা ট্রিপের জন্য উপযুক্ত।",
        rating = 4.7, ownerName = "তানভীর আহমেদ", ownerRating = 4.6, ownerReviewCount = 18
    )

    val pajero = Vehicle(
        id = "v4", model = "Mitsubishi Pajero", brand = "Mitsubishi", year = 2019,
        carType = CarType.SUV, pricePerDay = 6000, location = "বনানী, ঢাকা",
        transmission = "ম্যানুয়াল (Manual)", seats = 7,
        fuelType = "ডিজেল (Diesel)",
        description = "অফরোড ট্রিপের জন্য চমৎকার।",
        rating = 4.6, ownerName = "জাহিদ", ownerRating = 4.8, ownerReviewCount = 31,
        plateNumber = "ঢাকা মেট্রো-১৭", status = VehicleStatus.RENTED,
        todayEarnings = 6000, tripsThisMonth = 8
    )

    /** Search results screen */
    val searchResults = listOf(corolla, civic, xtrail)

    /** Owner dashboard — "আমার গাড়িসমূহ" */
    val myCars = listOf(corolla, pajero)

    fun vehicleById(id: String?): Vehicle =
        listOf(corolla, civic, xtrail, pajero).find { it.id == id } ?: corolla

    /** Passenger home — "সাম্প্রতিক বুকিং" */
    val recentBookings = listOf(
        Booking(
            id = "b1", vehicleModel = "Toyota Corolla",
            passengerName = "রাহাত", passengerRating = 4.9, passengerReviewCount = 5,
            dateRange = "১৫ জানুয়ারি ২০২৬", days = 2, pickupLocation = "ধানমন্ডি, ঢাকা",
            rentalFee = 3500, serviceCharge = 0, status = BookingStatus.ONGOING
        ),
        Booking(
            id = "b2", vehicleModel = "Mitsubishi Pajero",
            passengerName = "রাহাত", passengerRating = 4.9, passengerReviewCount = 5,
            dateRange = "১০ জানুয়ারি ২০২৬", days = 1, pickupLocation = "বনানী, ঢাকা",
            rentalFee = 6000, serviceCharge = 0, status = BookingStatus.COMPLETED
        )
    )

    /** Owner — "বুকিং অনুরোধ" */
    val bookingRequests = listOf(
        Booking(
            id = "r1", vehicleModel = "Toyota Corolla",
            passengerName = "রাফাত রহমান", passengerRating = 4.9, passengerReviewCount = 5,
            dateRange = "১৫ জানু - ১৭ জানু, ২০২৬", days = 2,
            pickupLocation = "উত্তরা সেক্টর ৪, ঢাকা",
            rentalFee = 7000, serviceCharge = 350, status = BookingStatus.PENDING
        ),
        Booking(
            id = "r2", vehicleModel = "Mitsubishi Pajero",
            passengerName = "মারুফ হোসেন", passengerRating = 4.7, passengerReviewCount = 12,
            dateRange = "২০ জানু - ২৩ জানু, ২০২৬", days = 3,
            pickupLocation = "বনানী, ঢাকা",
            rentalFee = 18000, serviceCharge = 900, status = BookingStatus.PENDING
        )
    )

    fun bookingById(id: String?): Booking =
        (bookingRequests + recentBookings).find { it.id == id } ?: bookingRequests[0]
}
