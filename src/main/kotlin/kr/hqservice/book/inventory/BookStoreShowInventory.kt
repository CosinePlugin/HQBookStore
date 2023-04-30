package kr.hqservice.book.inventory

import kr.hqservice.book.HQBookStore
import kr.hqservice.book.HQBookStore.Companion.prefix
import kr.hqservice.book.extension.setLore
import kr.hqservice.book.extension.setNewLore
import kr.hqservice.book.inventory.InventoryUtils.playButtonSound
import kr.hqservice.book.inventory.InventoryUtils.playOpenBookSound
import kr.hqservice.book.inventory.InventoryUtils.setItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class BookStoreShowInventory(
    plugin: HQBookStore,
    private var page: Int = 0,
    private val isRemoveMode: Boolean = false
) : BookStoreInventoryHolder("책방 ${if (isRemoveMode) ": 제거모드" else ""}", 6, true) {

    private val bookStoreRepository = plugin.bookStoreRepository
    private val books = bookStoreRepository.getBooks()
    private val chunkedBooks get() = books.chunked(45)
    private val nowPageBooks get() = chunkedBooks[page]

    override fun prevInit(inventory: Inventory) {
        inventory.setItem(45, InventoryUtils.beforePageButton)
        inventory.setItem(46..52, InventoryUtils.background)
        inventory.setItem(53, InventoryUtils.nextPageButton)
    }

    override fun init(inventory: Inventory) {
        inventory.setItem(0..44, InventoryUtils.air)
        if (books.isEmpty()) return
        nowPageBooks.forEachIndexed { index, item ->
            inventory.setItem(index, item.clone().apply {
                if (isRemoveMode) {
                    setNewLore("", "§c[ 클릭 시 해당 책을 삭제합니다. ]")
                } else {
                    setNewLore("", "§a[ 클릭 시 해당 책을 펼칩니다. ]")
                }
            })
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        if (event.clickedInventory == null) return

        val slot = event.rawSlot
        if (slot > 53) return

        val player = event.whoClicked as Player

        event.pageController(player)
        event.bookController(player)
    }

    private fun InventoryClickEvent.pageController(player: Player) {
        when (rawSlot) {
            45 -> showBeforePage(player)

            53 -> showNextPage(player)
        }
    }

    private fun showBeforePage(player: Player) {
        player.playButtonSound()
        if (page == 0) {
            player.sendMessage("$prefix 이전 페이지가 존재하지 않습니다.")
            return
        }
        page--
        init(inventory)
    }

    private fun showNextPage(player: Player) {
        player.playButtonSound()
        if (page + 1 >= chunkedBooks.size) {
            player.sendMessage("$prefix 다음 페이지가 존재하지 않습니다.")
            return
        }
        page++
        init(inventory)
    }

    private fun InventoryClickEvent.bookController(player: Player) {
        val slot = rawSlot
        if (books.isEmpty() || nowPageBooks.isEmpty() || slot >= nowPageBooks.size) return

        player.playOpenBookSound(2f, 1.3f)

        val clickedBook = nowPageBooks[slot]
        if (isRemoveMode) {
            bookStoreRepository.removeBook(clickedBook)
            player.sendMessage("$prefix 해당 책을 삭제하였습니다.")

            if (page != 0 && page >= chunkedBooks.size) {
                page--
            }
            init(inventory)
            return
        }
        player.openBook(clickedBook)
    }
}