package kr.hqservice.book.extension

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun Player.sendMessages(vararg message: String?) {
    message.filterNotNull().forEach { sendMessage(it) }
}

fun CommandSender.sendMessages(vararg message: String?) {
    message.filterNotNull().forEach { sendMessage(it) }
}