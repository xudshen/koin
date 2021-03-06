package org.koin.core

import org.junit.Assert.*
import org.junit.Test
import org.koin.Simple
import org.koin.core.error.ClosedScopeException
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class ScopeTest {

    @Test
    fun `get definition from current scopes type`() {
        val koin = koinApplication {
            printLogger()
            modules(
                    module {
                        scope(named<ClosedScopeAPI.ScopeType>()) {
                            scoped { Simple.ComponentA() }
                        }
                    }
            )
        }.koin

        val scope = koin.createScope("scope1", named<ClosedScopeAPI.ScopeType>())
        assertNotNull(scope.getOrNull<Simple.ComponentA>())
        assertEquals(scope.getOrNull<Simple.ComponentA>(), scope.getOrNull<Simple.ComponentA>())
        scope.close()
        assertTrue(scope.closed)
        assertNull(scope.getOrNull<Simple.ComponentA>())
        try {
            scope.get<Simple.ComponentA>()
            fail()
        } catch (e: ClosedScopeException) {
            e.printStackTrace()
        }
    }
}