package hu.bme.aut.packager.data

import androidx.room.Embedded
import androidx.room.Relation

class UserWithPackage {
    @Embedded val user: User = TODO()

    @Relation(
            parentColumn = "id",
            entityColumn = "from"
    )
    val packages: MutableList<Package> = TODO()
}