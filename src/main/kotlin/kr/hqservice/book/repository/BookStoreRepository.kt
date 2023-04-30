package kr.hqservice.book.repository

import kr.hqservice.chest.util.CustomConfig
import org.bukkit.inventory.ItemStack

class BookStoreRepository(private val file: CustomConfig) {

    private val config = file.getConfig()

    private val books = mutableListOf<ItemStack>()

    @Suppress("unchecked_cast")
    fun load() {
        if (!config.contains("books")) return
        books.addAll(config.getList("books") as MutableList<ItemStack>)
    }

    fun save() {
        config.set("books", books)
        file.saveConfig()
    }

    fun reload() {
        file.reloadConfig()
        books.clear()
        load()
    }

    fun contains(item: ItemStack): Boolean {
        return books.contains(item)
    }

    fun addBook(item: ItemStack) {
        books.add(item)
    }

    fun removeBook(item: ItemStack) {
        books.remove(item)
    }

    fun getBooks(): List<ItemStack> {
        return books
    }
}