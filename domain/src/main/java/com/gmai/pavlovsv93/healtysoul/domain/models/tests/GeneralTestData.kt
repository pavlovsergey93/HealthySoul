package com.gmai.pavlovsv93.healtysoul.domain.models.tests

abstract class GeneralTestData(open var type: Int)

const val PARENT_TYPE = 0
const val CHILD_TYPE = 1

data class ParentData(
    val title: String,
    override var type: Int = PARENT_TYPE,
    var subList: List<ChildData>?,
    var image: String? = null,
    var isExpanded: Boolean = false
) : GeneralTestData(type)

data class ChildData(
    val questionId: String = "fdsjkf",
    val title: String,
    override var type: Int = CHILD_TYPE
) : GeneralTestData(type)
