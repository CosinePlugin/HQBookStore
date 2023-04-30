package kr.hqservice.book.runnable

import kr.hqservice.book.repository.BookStoreRepository

class BookStoreSaveRunnable(
    private val bookStoreRepository: BookStoreRepository
) : Runnable {

    override fun run() {
        bookStoreRepository.save()
    }
}