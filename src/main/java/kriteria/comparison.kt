@file:Suppress("unused", "UNUSED_ANONYMOUS_PARAMETER", "RemoveExplicitTypeArguments")

package kriteria

import org.springframework.data.jpa.domain.Specification
import kotlin.reflect.KProperty1

/**
 * Create a Specification for testing whether the first argument is greater than the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.gt(that: KProperty1<R, V?>) =
    Specification<R> { root, query, builder ->
        builder.greaterThan(root[this], root[that])
    }

/**
 * Create a Specification for testing whether the first argument is greater than the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.gt(that: V) =
    Specification<R> { root, query, builder ->
        builder.greaterThan(root[this], that)
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification for testing whether the first argument is greater than or equal to the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.ge(that: KProperty1<R, V?>) =
    Specification<R> { root, query, builder ->
        builder.greaterThanOrEqualTo(root[this], root[that])
    }

/**
 * Create a Specification for testing whether the first argument is greater than or equal to the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.ge(that: V) =
    Specification<R> { root, query, builder ->
        builder.greaterThanOrEqualTo(root[this], that)
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification for testing whether the first argument is less than the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.lt(that: KProperty1<R, V?>) =
    Specification<R> { root, query, builder ->
        builder.lessThan(root[this], root[that])
    }

/**
 * Create a Specification for testing whether the first argument is less than the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.lt(that: V) =
    Specification<R> { root, query, builder ->
        builder.lessThan(root[this], that)
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification for testing whether the first argument is less than or equal to the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.le(that: KProperty1<R, V?>) =
    Specification<R> { root, query, builder ->
        builder.lessThanOrEqualTo(root[this], root[that])
    }

/**
 * Create a Specification for testing whether the first argument is less than or equal to the second.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.le(that: V) =
    Specification<R> { root, query, builder ->
        builder.lessThanOrEqualTo(root[this], that)
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification for testing whether the first argument is between the bounds of a range.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.between(that: Pair<KProperty1<R, V?>, KProperty1<R, V?>>) =
    Specification<R> { root, query, builder ->
        builder.between(root[this], root[that.first], root[that.second])
    }

/**
 * Create a Specification for testing whether the first argument is between the bounds of a range.
 */
@JvmName("between2")
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.between(that: Pair<V, V>) =
    Specification<R> { root, query, builder ->
        builder.between(root[this], that.first, that.second)
    }

/**
 * Create a Specification for testing whether the first argument is between the bounds of a range.
 */
infix fun <R, V: Comparable<V>> KProperty1<R, V?>.between(that: ClosedRange<V>) =
    Specification<R> { root, query, builder ->
        builder.between(root[this], that.start, that.endInclusive)
    }
