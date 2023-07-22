package com.vaibhavrawat.crudlist

interface ListClickInterface {
    fun onUpdateClick(position : Int)
    fun onDelete(position : Int)
}