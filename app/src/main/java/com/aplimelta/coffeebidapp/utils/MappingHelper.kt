package com.aplimelta.coffeebidapp.utils

import com.aplimelta.coffeebidapp.data.source.model.ProductModel
import com.aplimelta.coffeebidapp.data.source.model.ProductUserModel
import com.aplimelta.coffeebidapp.data.source.model.SearchProductModel
import com.aplimelta.coffeebidapp.data.source.remote.response.ProductResponseItem
import com.aplimelta.coffeebidapp.data.source.remote.response.SearchProductResponseItem

object MappingHelper {
    fun productResponseToModel(listProduct: List<ProductResponseItem>): List<ProductModel> {
        val result = mutableListOf<ProductModel>()

        listProduct.map { product ->
            result.add(
                ProductModel(
                    name = product.name,
                    createdAt = product.createdAt,
                    description = product.description,
                    endDate = product.endDate,
                    finalPrice = product.finalPrice,
                    productPict = product.productPict,
                    id = product.id,
                    openPrice = product.openPrice,
                    startDate = product.startDate,
                    status = product.status,
                    type = product.type,
                    updatedAt = product.updatedAt,
                    userId = product.userId,
                    uuid = product.uuid,
                    winner = product.winner,
                    user = ProductUserModel(
                        createdAt = product.user.createdAt,
                        password = product.user.password,
                        address = product.user.address,
                        role = product.user.role,
                        contact = product.user.contact,
                        id = product.user.id,
                        uuid = product.user.uuid,
                        email = product.user.email,
                        username = product.user.username,
                        updatedAt = product.user.updatedAt
                    )
                )
            )
        }

        return result
    }

    fun searchProductResponseToModel(listProduct: List<SearchProductResponseItem>): List<SearchProductModel> {
        val result = mutableListOf<SearchProductModel>()

        listProduct.map { product ->
            result.add(
                SearchProductModel(
                    name = product.name,
                    createdAt = product.createdAt,
                    description = product.description,
                    endDate = product.endDate,
                    finalPrice = product.finalPrice,
                    productPict = product.productPict,
                    id = product.id,
                    openPrice = product.openPrice,
                    startDate = product.startDate,
                    status = product.status,
                    type = product.type,
                    updatedAt = product.updatedAt,
                    userId = product.userId,
                    uuid = product.uuid,
                    winner = product.winner
                )
            )
        }

        return result
    }

    fun searchToProductModel(searchProduct: List<SearchProductModel>): List<ProductModel> {
        val result = mutableListOf<ProductModel>()

        searchProduct.map { product ->
            result.add(
                ProductModel(
                    name = product.name,
                    createdAt = product.createdAt,
                    description = product.description,
                    endDate = product.endDate,
                    finalPrice = product.finalPrice,
                    productPict = product.productPict,
                    id = product.id,
                    openPrice = product.openPrice,
                    startDate = product.startDate,
                    status = product.status,
                    type = product.type,
                    updatedAt = product.updatedAt,
                    userId = product.userId,
                    uuid = product.uuid,
                    winner = product.winner,
                    user = null
                )
            )
        }

        return result
    }
}