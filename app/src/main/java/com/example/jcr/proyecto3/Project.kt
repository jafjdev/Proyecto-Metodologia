package com.example.jcr.proyecto3

class Project {
    var name: String? = null
    var description: String? = null
    var status: String? = null
    var uidCreador:String? = null

    constructor() {}

    constructor(name: String?, description: String?, uidCreador: String?) {
        this.name = name
        this.description = description
        this.status = "Activo" /*Al crear un proyecto por defecto su estado sera activo*/
        this.uidCreador = uidCreador
    }


}