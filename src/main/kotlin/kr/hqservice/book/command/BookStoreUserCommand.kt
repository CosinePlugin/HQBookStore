package kr.hqservice.book.command

import kr.hqservice.book.HQBookStore
import kr.hqservice.book.HQBookStore.Companion.prefix
import kr.hqservice.book.extension.later
import kr.hqservice.book.inventory.BookStoreShowInventory
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class BookStoreUserCommand(
    private val plugin: HQBookStore
) : CommandExecutor, TabCompleter {

    private companion object {
        val commandTabList = listOf("등록")
    }

    private val bookStoreRepository = plugin.bookStoreRepository

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): List<String> {
        if (args.size <= 1) {
            return commandTabList
        }
        return emptyList()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        val player: Player = sender

        if (args.isEmpty()) {
            later { BookStoreShowInventory(plugin, 0).openInventory(player) }
            return true
        }
        if (args[0] != "등록") {
            player.sendMessage("$prefix /$label 등록 - 쓰여진 책을 들고 입력해주세요.")
            return true
        }
        val item = player.inventory.itemInMainHand.clone().apply { amount = 1 }
        if (item.type == Material.AIR) {
            player.sendMessage("$prefix 손에 아이템을 들어주세요.")
            return true
        }
        if (item.type != Material.WRITTEN_BOOK) {
            player.sendMessage("$prefix 쓰여진 책만 등록 가능합니다.")
            return true
        }
        if (bookStoreRepository.contains(item)) {
            player.sendMessage("$prefix 같은 책이 이미 등록되어 있습니다.")
            return true
        }
        player.inventory.itemInMainHand.apply { amount-- }
        bookStoreRepository.addBook(item)
        player.sendMessage("$prefix 책이 등록되었습니다.")
        return true
    }
}