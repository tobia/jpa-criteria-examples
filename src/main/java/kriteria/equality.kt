@file:Suppress("unused", "UNUSED_ANONYMOUS_PARAMETER", "RemoveExplicitTypeArguments")

package kriteria

import org.springframework.data.jpa.domain.Specification
import kotlin.reflect.KProperty1

/**
 * Create a Specification for testing the arguments for equality.
 */
infix fun <R, V> KProperty1<R, V>.eq(that: KProperty1<R, V>) =
    Specification<R> { root, query, builder ->
        builder.equal(root[this], root[that])
    }

/**
 * Create a Specification for testing the arguments for equality.
 */
infix fun <R, V> KProperty1<R, V>.eq(that: V) =
    Specification<R> { root, query, builder ->
        builder.equal(root[this], that)
    }

// ------------------------------------------------------------------------------------------------

/**
 * Create a Specification for testing the arguments for inequality.
 */
infix fun <R, V> KProperty1<R, V>.ne(that: KProperty1<R, V>) =
    Specification<R> { root, query, builder ->
        builder.notEqual(root[this], root[that])
    }

/**
 * Create a Specification for testing the arguments for inequality.
 */
infix fun <R, V> KProperty1<R, V>.ne(that: V) =
    Specification<R> { root, query, builder ->
        builder.notEqual(root[this], that)
    }
