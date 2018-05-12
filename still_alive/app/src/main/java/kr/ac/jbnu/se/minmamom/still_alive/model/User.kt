package kr.ac.jbnu.se.minmamom.still_alive.model

data class User (
        var name: String = " ",
        var email: String = " ",
        var uuid: String = " ",
        var sex: String = " ",
        var age: Int = 0,
        var rivive: Int = 1,
        var survive: Int = 0,
        var deathNum: Int = 0)
