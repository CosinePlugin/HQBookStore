package kr.hqservice.book.command

import kr.hqservice.book.HQBookStore
import kr.hqservice.book.extension.later
import kr.hqservice.book.inventory.BookStoreShowInventory
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BookStoreAdminCommand(
    private val plugin: HQBookStore
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true
        later { BookStoreShowInventory(plugin, 0, true).openInventory(sender) }
        return true
    }
}