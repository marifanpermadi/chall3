package com.example.chall3.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2to3: Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE cart_items ADD COLUMN basePrice INTEGER NOT NULL DEFAULT 1")
    }
}