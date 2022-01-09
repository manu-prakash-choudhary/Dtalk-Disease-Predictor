package com.example.dtalk.Model_Class

class ChatlogUsersList
{

    var Chats: String? = null
    var Date: String? = null
    var ReceiverId: String? = null
    var SenderId: String? = null



    constructor() : this("","","","")


    constructor(Chats: String, Date: String,ReceiverId: String,SenderId: String) {

        this.Chats = Chats
        this.Date = Date
        this.ReceiverId = ReceiverId
        this.SenderId = SenderId


    }
}