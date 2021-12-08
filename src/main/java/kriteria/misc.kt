@file:Suppress("unused", "UNUSED_ANONYMOUS_PARAMETER", "RemoveExplicitTypeArguments")

package kriteria

import javax.persistence.criteria.Path
import kotlin.reflect.KProperty1

operator fun <R, V> Path<R>.get(prop: KProperty1<R, V?>): Path<V> = get(prop.name)
