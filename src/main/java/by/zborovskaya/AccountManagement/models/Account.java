package by.zborovskaya.AccountManagement.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "acc_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @Min(value = 0)
    @Column(name = "acc_value")
    private int value;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Person person;

    public Account() {
    }

    public Account(int value, String status, Person person) {
        this.number = number;
        this.value = value;
        this.status = status;
        this.person = person;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", value=" + value +
                ", status='" + status + '\'' +
                ", person=" + person +
                '}';
    }
}
