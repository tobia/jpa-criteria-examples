package kriteria

import kriteria.entity.EMailAddress
import kriteria.entity.User
import org.eclipse.persistence.jpa.JpaQuery
import org.junit.FixMethodOrder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.runners.MethodSorters
import org.springframework.data.jpa.domain.Specification
import java.util.logging.Logger
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import javax.persistence.Tuple
import javax.persistence.criteria.*

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.JVM)
class KriteriaExamples {

    companion object {
        private const val LOG_SEPARATOR = "========="

        private val logger = Logger.getLogger(KriteriaExamples::class.java.simpleName)

        private lateinit var factory: EntityManagerFactory
    }

    @BeforeAll
    fun before(testInfo: TestInfo?) {
        logger.info("start initialize")
        factory = Persistence.createEntityManagerFactory("criteria-examples")
        val em = factory.createEntityManager()
        em.close()
        logger.info("end initialize")
    }

    @AfterAll
    fun after(testInfo: TestInfo?) {
        factory.close()
    }

    @BeforeEach
    fun beforeEach(testInfo: TestInfo) {
        logger.info(LOG_SEPARATOR + " " + testInfo.displayName + " " + LOG_SEPARATOR)
    }

    @AfterEach
    fun afterEach(testInfo: TestInfo?) {
        println("")
    }

    @DisplayName("Select All")
    @Test
    fun selectAll() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Multi Select")
    @Test
    fun multiSelect() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.multiselect(root[User::name])
        val typedQuery = em.createQuery(query)
        (typedQuery as JpaQuery<*>).databaseQuery.dontMaintainCache()
        typedQuery.getResultList()
        em.close()
    }

    @DisplayName("Select With Condition")
    @Test
    fun selectWithCondition() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        val spec: Specification<User> = (User::userId eq "0000")
        query.where(spec.toPredicate(root, query, builder))
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Update")
    @Test
    fun update() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val update = builder.createCriteriaUpdate(User::class.java)
        val root = update.from(User::class.java)
        update.set(root[User::name], "new-name")
        update.where(builder.equal(root[User::userId], "0000"))
        em.transaction.begin()
        em.createQuery(update).executeUpdate()
        em.transaction.commit()
        em.close()
    }

    @DisplayName("Delete")
    @Test
    fun delete() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val delete = builder.createCriteriaDelete(User::class.java)
        val root = delete.from(User::class.java)
        delete.where(builder.equal(root[User::userId], "0000"))
        em.transaction.begin()
        em.createQuery(delete).executeUpdate()
        em.transaction.commit()
        em.close()
    }

    @DisplayName("Count")
    @Test
    fun count() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val countQuery = builder.createQuery(Long::class.java)
        val root = countQuery.from(User::class.java)
        countQuery.select(builder.count(root))
        em.createQuery(countQuery).singleResult
        em.close()
    }

    @DisplayName("Max")
    @Test
    fun max() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val maxQuery = builder.createQuery(Int::class.java)
        val root = maxQuery.from(User::class.java)
        maxQuery.select(builder.max(root[User::age]))
        em.createQuery(maxQuery).singleResult
        em.close()
    }

    @DisplayName("Min")
    @Test
    fun min() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val minQuery = builder.createQuery(Int::class.java)
        val root = minQuery.from(User::class.java)
        minQuery.select(builder.min(root[User::age]))
        em.createQuery(minQuery).singleResult
        em.close()
    }

    @DisplayName("Avg")
    @Test
    fun avg() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val avgQuery = builder.createQuery(Double::class.java)
        val root = avgQuery.from(User::class.java)
        avgQuery.select(builder.avg(root[User::age]))
        em.createQuery(avgQuery).singleResult
        em.close()
    }

    @DisplayName("Left Join")
    @Test
    fun leftJoin() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        val join: Join<User, EMailAddress> = root.join(User::eMailAddresses.name, JoinType.LEFT)
        query.where(builder.like(join[EMailAddress::eMailAddress], "a%"))
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Inner Join")
    @Test
    fun innerJoin() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        val join: Join<User, EMailAddress> = root.join(User::eMailAddresses.name, JoinType.INNER)
        query.where(builder.like(join[EMailAddress::eMailAddress], "a%"))
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Fetch Left Join")
    @Test
    fun fetchLeftJoin() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        root.fetch<User, List<EMailAddress>?>(User::eMailAddresses.name, JoinType.LEFT)
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Fetch Inner Join")
    @Test
    fun fetchInnerJoin() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        root.fetch<User, List<EMailAddress>?>(User::eMailAddresses.name, JoinType.INNER)
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Group By")
    @Test
    fun groupBy() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(Tuple::class.java)
        val root = query.from(User::class.java)
        val sum = builder.sum(root[User::userNumber])
        query.select(builder.tuple(root[User::name], sum))
            .groupBy(root[User::name])
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Group By & Having")
    @Test
    fun groupByHaving() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(Tuple::class.java)
        val root = query.from(User::class.java)
        val sum = builder.sum(root[User::userNumber])
        query.select(builder.tuple(root[User::name], sum))
            .groupBy(root[User::name])
            .having(builder.greaterThan(sum, 100))
        em.createQuery(query).resultList
        em.close()
    }

    @DisplayName("Sub Query")
    @Test
    fun subQuery() {
        val em = factory.createEntityManager()
        val builder = em.criteriaBuilder
        val query = builder.createQuery(User::class.java)
        val root = query.from(User::class.java)
        query.select(root)
        val subquery = query.subquery(String::class.java)
        val subRoot = subquery.from(EMailAddress::class.java)
        subquery.select(subRoot[EMailAddress::userId])
        subquery.where(builder.like(subRoot[EMailAddress::eMailAddress], "%@gmail.com"))
        query.where(root[User::userId].`in`(subquery))
        em.createQuery(query).resultList
        em.close()
    }
}
