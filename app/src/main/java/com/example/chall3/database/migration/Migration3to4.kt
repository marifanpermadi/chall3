package com.example.chall3.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3to4: Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `cart_items_temp` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`foodImage` TEXT, " +
                    "`foodName` TEXT NOT NULL, " +
                    "`foodPrice` INTEGER NOT NULL, " +
                    "`orderNote` TEXT, " +
                    "`orderAmount` INTEGER NOT NULL, " +
                    "`basePrice` INTEGER NOT NULL)"
        )

        database.execSQL(
            "INSERT INTO `cart_items_temp` (`id`, `foodImage`, `foodName`, `foodPrice`, `orderNote`, `orderAmount`, `basePrice`) " +
                    "SELECT `id`, CAST(`foodImage` AS TEXT), `foodName`, `foodPrice`, `orderNote`, `orderAmount`, `basePrice` FROM `cart_items`"
        )

        database.execSQL("DROP TABLE `cart_items`")

        database.execSQL("ALTER TABLE `cart_items_temp` RENAME TO `cart_items`")
    }
}