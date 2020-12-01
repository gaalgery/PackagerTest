package hu.bme.aut.packager.DatatTransformer

import hu.bme.aut.packager.data.Carrier
import hu.bme.aut.packager.data.State

class RecyclerPackage constructor(
        val id: Long?,
        val weight: Long,
        val reward: Long,
        val carrier: Carrier,
        val state: State,
        val from: String,
        val height: Long,
        val length: Long,
        val width: Long,
        val to: String,
        val courier: String?
        ) {
}