package kriteria.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
class User: Serializable {
    @Id
    @Column(name = "user_id")
    var userId: String? = null

    var name: String? = null

    var password: String? = null

    @Column(name = "user_number")
    var userNumber: Int? = null

    @Column(name = "age")
    var age: Int? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.PERSIST])
    val eMailAddresses: List<EMailAddress>? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
