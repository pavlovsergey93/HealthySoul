package com.gmail.pavlovsv93.healthysoul.utils

const val PARENT_TYPE = 0
const val CHILD_TYPE = 1

val category = listOf<ParentData>(
	ParentData(
		title = "Семейные проблемы, проблемы с партнером",
		subList = listOf(
			ChildData(title = "Проблемы с родителями", questionId = ""),
			ChildData(title = "Развод, разрыв отношений", questionId = ""),
			ChildData(title = "Насилие (физическое, сексуальное, эмоциональное)", questionId = ""),
			ChildData(title = "Зависимость от партнера", questionId = ""),
			ChildData(title = "Зависимость партнера (алкогольная, наркотическая, игровая и т.д.)", questionId = ""),
			ChildData(title = "Конфликты (с родителями, партнером, братьями/сестрами)", questionId = ""),
			ChildData(title = "Сложность построения и сохранения отношений", questionId = ""),
			ChildData(title = "Обида на партнера, родственников", questionId = ""),
			ChildData(title = "Сложности с деньгами в семье", questionId = ""),
			ChildData(title = "Отделение от родителей, взросление", questionId = ""),
			ChildData(title = "Измена, предательство", questionId = ""),
			ChildData(title = "Сложности в сексуальной жизни", questionId = ""),
			ChildData(title = "Кризис семейный", questionId = ""),
			ChildData(title = "Синдром \"опустевшего гнезда\"", questionId = ""),
			ChildData(title = "Инфантильность партнера", questionId = ""),
			ChildData(title = "Желание одиночества", questionId = "")
		)
	),
	ParentData(
		title = "Страхи, комплексы",
		subList = listOf(
			ChildData(title = "Низкая самооценка, неуверенность в себе", questionId = ""),
			ChildData(title = "Неизлечимой болезни", questionId = ""),
			ChildData(title = "Страх узнать себя", questionId = ""),
			ChildData(title = "Страх сойти с ума", questionId = ""),
			ChildData(title = "Не реализовать свой потенциал", questionId = ""),
			ChildData(title = "Отделиться от родителей", questionId = ""),
			ChildData(title = "Страх сверхъестественного, темноты", questionId = ""),
			ChildData(title = "Страх начать новое", questionId = ""),
			ChildData(title = "Страх потери денег, дохода, увольнения", questionId = ""),
			ChildData(title = "Страх отношений, зависимости от партнера", questionId = ""),
			ChildData(title = "Страх осуждения", questionId = ""),
			ChildData(title = "Страх расставания, развода", questionId = ""),
			ChildData(title = "Страх одиночества", questionId = ""),
			ChildData(title = "Социофобия, страх общения", questionId = ""),
			ChildData(title = "Страх секса", questionId = ""),
			ChildData(title = "Страх ошибки", questionId = ""),
			ChildData(title = "Страх неизвестности", questionId = ""),
			ChildData(title = "Позиция жертвы (нравится страдать, когда жалеют)", questionId = ""),
			ChildData(title = "Страх насилия", questionId = ""),
			ChildData(title = "Страх проявляться", questionId = ""),
			ChildData(title = "Страх жить", questionId = ""),
//			ChildData(title = "Смерти (собственной, родителей(супруга, близких), детей)", questionId = ""),
//			ChildData(title = "Страх закрытых пространств", questionId = ""),
//			ChildData(title = "Страх открытых пространств", questionId = ""),
			ChildData(title = "Страх иметь детей", questionId = "")
		)
	),
	ParentData(
		title = "Отношения с окружающими",
		subList = listOf(
			ChildData(title = "Одиночество", questionId = ""),
			ChildData(title = "Трудности общения, социофобия", questionId = ""),
			ChildData(title = "Конфликты", questionId = ""),
			ChildData(title = "Неумение говорить \"нет\"", questionId = ""),
			ChildData(title = "Любовь без взаимности", questionId = ""),
			ChildData(title = "Перекладывание ответственности", questionId = ""),
			ChildData(title = "Зависимость от чужого мнения", questionId = ""),
			ChildData(title = "Желание быть хорошим для других", questionId = ""),
			ChildData(title = "Желание что-то доказать другим", questionId = ""),
			ChildData(title = "Чувство долга", questionId = "")
		)
	),
	ParentData(
		title = "Вопросы карьеры, работы, развития",
		subList = listOf(
			ChildData(title = "Реализация внутреннего потенциала", questionId = ""),
			ChildData(title = "Поиск новой работы, смена профиля", questionId = ""),
			ChildData(title = "Карьерный рост", questionId = ""),
			ChildData(title = "Выполнение сложных дел на работе, неуверенность в собственных силах?", questionId = ""),
			ChildData(title = "Сложности с деньгами", questionId = ""),
			ChildData(title = "Бизнес-отношения", questionId = ""),
			ChildData(title = "Конфликты, раздражительность (с начальством, с коллегами", questionId = ""),
			ChildData(title = "Отсутствие целей, мотивации", questionId = "")
		)
	),
	ParentData(
		title = "Эмоциональные проблемы и нервные расстройства",
		subList = listOf(
			ChildData(title = "Депрессия, подавленность, плохое настроение", questionId = ""),
			ChildData(title = "Насилие, травма (гнев, навязчивые воспоминания, сверконтроль, тревожность)", questionId = ""),
			ChildData(title = "Кризис, потеря смысла жизни, поиск себя", questionId = ""),
			ChildData(title = "Неумение контролировать эмоции", questionId = ""),
			ChildData(title = "Раскрытие чувств и эмоций", questionId = ""),
			ChildData(title = "Стресс", questionId = ""),
			ChildData(title = "Тревожность", questionId = ""),
			ChildData(title = "Панические атаки", questionId = ""),
			ChildData(title = "Проблемы сна (Бессонница, Сонливость)", questionId = ""),
			ChildData(title = "Горе, потеря", questionId = ""),
			ChildData(title = "Чувство вины", questionId = ""),
			ChildData(title = "Гнев", questionId = ""),
			ChildData(title = "Сверхконтроль", questionId = ""),
			ChildData(title = "Внутренний конфликт", questionId = ""),
			ChildData(title = "Зависимость (алкогольная, наркотическая, игровая и т.д.)", questionId = ""),
			ChildData(title = "Чувство стыда", questionId = ""),
			ChildData(title = "Навязчивые мысли, воспоминания", questionId = ""),
			ChildData(title = "Постоянная усталость, упадок сил", questionId = "")
		)
	),
	ParentData(
		title = "Пробемы с детьми",
		subList = listOf(
//			ChildData(title = "Конфликт (с детьми, между детьми)", questionId = ""),
//			ChildData(title = "Неуправляемый ребенок", questionId = ""),
//			ChildData(title = "Трудности общения, сложно найти общий язык", questionId = ""),
//			ChildData(title = "Агрессивный ребенок", questionId = ""),
//			ChildData(title = "Невнимательный, рассеянный ребенок", questionId = ""),
//			ChildData(title = "Плохие отметки в школе", questionId = ""),
//			ChildData(title = "Сложности адаптации к школе/саду", questionId = ""),
//			ChildData(title = "Истерики, капризы, непослушание", questionId = ""),
//			ChildData(title = "Подавленность, апатия ребенка, частые смены настроения", questionId = ""),
//			ChildData(title = "Болезни ребенка (часто болеющий ребенок, психосоматика)", questionId = ""),
//			ChildData(title = "Ребенок пережил травму (развод родителей, смерть близкого человека, насилие", questionId = ""),
//			ChildData(title = "Тревожность, страхи за ребенка", questionId = "")
		)
	),
)

