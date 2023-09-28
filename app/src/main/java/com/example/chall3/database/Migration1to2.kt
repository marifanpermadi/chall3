package com.example.chall3.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1to2: Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE cart_items ADD COLUMN orderAmount INTEGER NOT NULL DEFAULT 1")
    }
}