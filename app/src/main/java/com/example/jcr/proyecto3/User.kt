package com.example.jcr.proyecto3

class User {

    var name: String? = null
    var lastName: String? = null
    var uuid: String? = null

    constructor() {}

    constructor(name: String?, lastName: String?, uuid: String?) {
        this.name = name
        this.lastName = lastName
        this.uuid = uuid
    }

}