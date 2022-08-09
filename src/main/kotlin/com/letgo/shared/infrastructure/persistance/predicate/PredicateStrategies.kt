package com.letgo.shared.infrastructure.persistance.predicate

import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

interface PredicateStrategies<T> {
    val map: Map<String, Map<Operator, (Filter, Root<T>, CriteriaBuilder) -> Predicate>>
}
