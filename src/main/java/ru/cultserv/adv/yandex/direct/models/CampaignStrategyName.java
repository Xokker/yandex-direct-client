package ru.cultserv.adv.yandex.direct.models;

public enum CampaignStrategyName {

	/**
	 * Выключить показ объявлений на поиске.
	 * Это необходимо для использования автоматической стратегии в Рекламной сети Яндекса.
	 * Показ на поиске невозможно выключить, если для Рекламной сети применяется стратегия Default.
	 */
	ShowsDisabled,
	
	/**
	 * Стратегия «Наивысшая доступная позиция»
	 */
	HighestPosition,
	
	/**
	 * Стратегия «Показ в блоке по минимальной цене»
	 */
	LowestCost,
	
	/**
	 * Стратегия «Показ в блоке по минимальной цене», но объявления показываются только в спецразмещении
	 */
	LowestCostPremium,
	
	/**
	 * Стратегия «Показ под результатами поиска» (в нижнем блоке по наименьшей цене)
	 */
	LowestCostGuarantee,
	
	/**
	 * Стратегия «Показ под результатами поиска» (в нижнем блоке на наивысшей позиции, доступной при указанной ставке)
	 */
	RightBlockHighest,
	
	/**
	 * Стратегия «Недельный бюджет: максимум кликов» (обязательный параметр WeeklySumLimit, дополнительный MaxPrice)
	 */
	WeeklyBudget,
	
	/**
	 * Стратегия «Недельный бюджет: максимальная конверсия»
	 * (обязательные параметры WeeklySumLimit и GoalID, дополнительный MaxPrice)
	 */
	CPAOptimizer,
	
	/**
	 * Стратегия «Средняя цена клика» (обязательный параметр AveragePrice, дополнительный WeeklySumLimit)
	 */
	AverageClickPrice,
	
	/**
	 * Стратегия «Недельный пакет кликов»
	 * (обязательный параметр ClicksPerWeek, дополнительные MaxPrice или AveragePrice)
	 */
	WeeklyPacketOfClicks,
	
	/**
	 * Стратегия «Средняя цена конверсии»
	 * (обязательные параметры AverageCPA и GoalID, дополнительные WeeklySumLimit и MaxPrice)
	 */
	AverageCPAOptimization

}
