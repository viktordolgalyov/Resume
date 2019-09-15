package com.dolgalyov.resume.common.exception

class ItemNotFoundException(val id: String) :
    IllegalArgumentException("Item with id $id can not be found")