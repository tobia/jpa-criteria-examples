package kriteria.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "email_address")
@NamedQuery(name = "EMailAddress.findAll", query = "SELECT u FROM EMailAddress u")
class EMailAddress: Serializable {
    @Id
    @Column(name = "user_id")
    var userId: String? = null

    @Column(name = "email_address")
    var eMailAddress: String? = null

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "user_id",
        insertable = false,
        updatable = false
    )
    private val user: User? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
