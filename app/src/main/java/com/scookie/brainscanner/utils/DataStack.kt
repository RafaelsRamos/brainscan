package com.scookie.brainscanner.utils

import java.util.*

class DataStack(private val maxValues: Int) {

    val stack: Stack<Int> = Stack()

    fun pushElement(element: Int) {
        stack.push(element)
        if (stack.size > maxValues) {
            popOldest()
        }
    }

    private fun popOldest() {
        stack.removeFirst()
    }

    fun toDoubleArray(): DoubleArray = stack.toList().map { it.toDouble() }.toDoubleArray()

}