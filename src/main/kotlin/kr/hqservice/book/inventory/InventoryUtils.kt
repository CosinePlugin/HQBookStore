package kr.hqservice.book.inventory

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

internal object InventoryUtils {

    val air = ItemStack(Material.AIR)

    val background = ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§f").build()

    val beforePageButton = ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c이전 페이지로 이동").build()

    val nextPageButton = ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§a다음 페이지로 이동").build()

    fun Inventory.setItem(range: IntRange, item: ItemStack) {
        range.forEach { setItem(it, item) }
    }

    fun Inventory.setItem(item: ItemStack, vararg slot: Int) {
        slot.forEach { setItem(it, item) }
    }

    fun Player.playButtonSound(volume: Float = 1f, pitch: Float = 1f) {
        playSound(this.location, Sound.UI_BUTTON_CLICK, volume, pitch)
    }

    fun Player.playOpenBookSound(volume: Float = 1f, pitch: Float = 1f) {
        playSound(this.location, Sound.ITEM_ARMOR_EQUIP_LEATHER, volume, pitch)
    }
}