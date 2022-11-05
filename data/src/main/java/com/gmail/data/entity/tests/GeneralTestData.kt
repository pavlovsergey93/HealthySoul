package com.gmail.pavlovsv93.healthysoul.utils

abstract class GeneralTestData(open var type: Int)

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
