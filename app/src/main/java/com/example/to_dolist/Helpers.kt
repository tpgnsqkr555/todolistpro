package com.example.to_dolist

class Helpers{
    val maxLength = 25 //Max length of user input

    /**
     * Description: Receives a string and returns whether or not it is within the max length
     *
     * @param userInput is a String that is given from the user
     * @return whether or not the userInput is within the max length
     */
    fun lessThanMax(userInput: String): Boolean {
        return (userInput.length <= maxLength)
    }
}