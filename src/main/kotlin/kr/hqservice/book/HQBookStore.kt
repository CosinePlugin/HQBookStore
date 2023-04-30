package kr.hqservice.book

import kr.hqservice.book.bstats.Metrics
import kr.hqservice.book.command.BookStoreAdminCommand
import kr.hqservice.book.command.BookStoreUserCommand
import kr.hqservice.book.listener.BookStoreInventoryListener
import kr.hqservice.book.repository.BookStoreRepository
import kr.hqservice.book.runnable.BookStoreSaveRunnable
import kr.hqservice.chest.util.CustomConfig
import org.bukkit.plugin.java.JavaPlugin

class HQBookStore : JavaPlugin() {

    companion object {
        const val prefix = "§6[ 책방 ]§f"
        lateinit var plugin: HQBookStore
            private set
    }

    lateinit var bookStoreRepository: BookStoreRepository
        private set

    override fun onLoad() {
        plugin = this
    }

    override fun onEnable() {
        Metrics(this, 18264)

        val bookStoreFile = CustomConfig(plugin, "books.yml")
        bookStoreRepository = BookStoreRepository(bookStoreFile)
        bookStoreRepository.load()

        server.scheduler.runTaskTimerAsynchronously(this, BookStoreSaveRunnable(bookStoreRepository), 6000, 6000)

        server.pluginManager.registerEvents(BookStoreInventoryListener(), this)

        getCommand("책방관리")?.setExecutor(BookStoreAdminCommand(this))
        getCommand("책방")?.setExecutor(BookStoreUserCommand(this))
    }

    override fun onDisable() {
        bookStoreRepository.save()
    }
}