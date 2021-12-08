@file:Suppress("unused", "UNUSED_ANONYMOUS_PARAMETER", "RemoveExplicitTypeArguments")

package kriteria

import org.springframework.data.jpa.domain.Specification
import kotlin.reflect.KProperty1

/**
 * Create a Specification for testing whether the argument is true.
 */
val <R> KProperty1<R, Boolean?>.isTrue get() =
    Specification<R> { root, query, builder ->
        builder.isTrue(root[this])
    }

/**
 * Create a Specification for testing whether the argument is false.
 */
val <R> KProperty1<R, Boolean?>.isFalse get() =
    Specification<R> { root, query, builder ->
        builder.isFalse(root[this])
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification from the conjunction of the given arguments.
 */
infix fun <R> KProperty1<R, Boolean?>.and(that: KProperty1<R, Boolean?>) =
    Specification<R> { root, query, builder ->
        builder.and(root[this], root[that])
    }

/**
 * Create a Specification from the conjunction of the given arguments.
 */
infix fun <R> KProperty1<R, Boolean?>.and(that: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.and(builder.isTrue(root[this]), that.toPredicate(root, query, builder))
    }

/**
 * Create a Specification from the conjunction of the given arguments.
 */
infix fun <R> Specification<R>.and(that: KProperty1<R, Boolean?>) =
    Specification<R> { root, query, builder ->
        builder.and(this.toPredicate(root, query, builder), builder.isTrue(root[that]))
    }

/**
 * Create a Specification from the conjunction of the given arguments.
 */
infix fun <R> Specification<R>.and(that: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.and(this.toPredicate(root, query, builder), that.toPredicate(root, query, builder))
    }

/**
 * Create a Specification from the conjunction of the given arguments.
 */
fun <R> and(vararg specs: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.and(*specs.map { it.toPredicate(root, query, builder) }.toTypedArray())
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification from the disjunction of the given arguments.
 */
infix fun <R> KProperty1<R, Boolean?>.or(that: KProperty1<R, Boolean?>) =
    Specification<R> { root, query, builder ->
        builder.or(root[this], root[that])
    }

/**
 * Create a Specification from the disjunction of the given arguments.
 */
infix fun <R> KProperty1<R, Boolean?>.or(that: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.or(builder.isTrue(root[this]), that.toPredicate(root, query, builder))
    }

/**
 * Create a Specification from the disjunction of the given arguments.
 */
infix fun <R> Specification<R>.or(that: KProperty1<R, Boolean?>) =
    Specification<R> { root, query, builder ->
        builder.or(this.toPredicate(root, query, builder), builder.isTrue(root[that]))
    }

/**
 * Create a Specification from the disjunction of the given arguments.
 */
infix fun <R> Specification<R>.or(that: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.or(this.toPredicate(root, query, builder), that.toPredicate(root, query, builder))
    }

/**
 * Create a Specification from the disjunction of the given arguments.
 */
fun <R> or(vararg specs: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.or(*specs.map { it.toPredicate(root, query, builder) }.toTypedArray())
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification from the negation of the given argument.
 */
fun <R> not(prop: KProperty1<R, Boolean?>) =
    Specification<R> { root, query, builder ->
        builder.not(root[prop])
    }

/**
 * Create a Specification from the negation of the given argument.
 */
fun <R> not(spec: Specification<R>) =
    Specification<R> { root, query, builder ->
        builder.not(spec.toPredicate(root, query, builder))
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a constantly true Specification.
 */
fun <R> all() =
    Specification<R> { root, query, builder ->
        builder.conjunction()
    }

/**
 * Create a constantly false Specification.
 */
fun <R> none() =
    Specification<R> { root, query, builder ->
        builder.disjunction()
    }
