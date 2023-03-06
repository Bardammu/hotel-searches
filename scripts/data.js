use hotelsearches_db;
db.createCollection("hotel_availability_searches");
db.hotel_availability_searches.insert({
        "_id": "c1b7a472-3c33-4385-81ee-ac20dc5c871c",
        "hotelSearch": {
            "checkIn": ISODate("2023-03-03"),
            "ages": [30, 29, 1, 3],
            "hotelId": "1234aBc",
            "checkOut": ISODate("2023-04-03")
        }
    });
db.hotel_availability_searches.insert({
        "_id": "e546c132-650c-4621-8999-2178d83202cd",
        "hotelSearch": {
            "checkIn": ISODate("2023-03-03"),
            "ages": [30, 29, 1, 3],
            "hotelId": "1234aBc",
            "checkOut": ISODate("2023-04-03")
        }
    }
)
db.hotel_availability_searches.insert({
        "_id": "fb50a798-4f6b-4df3-9483-785dc0c1dcb5",
        "hotelSearch": {
            "checkIn": ISODate("2023-03-03"),
            "ages": [30, 29, 1, 30],
            "hotelId": "1234aBc",
            "checkOut": ISODate("2023-04-03")
        }
    }
)
db.hotel_availability_searches.insert({
        "_id": "b18e83bb-5a52-4ddd-af15-d7058c00f8a2",
        "hotelSearch": {
            "checkIn": ISODate("2023-03-03"),
            "ages": [30, 29, 1, 30],
            "hotelId": "1234aBc",
            "checkOut": ISODate("2023-04-03")
        }
    }
)
