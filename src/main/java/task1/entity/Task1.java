package task1.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task1")
@ToString
public class Task1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date", nullable = true)
    private String date;
    @Column(name = "latinChars", nullable = true)
    private String latinChars;
    @Column(name = "russianChars", nullable = true)
    private String russianChars;
    @Column(name = "intNumber", nullable = true)
    private int intNumber;
    @Column(name = "floatNumber", nullable = true)
    private double floatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task1 task1 = (Task1) o;
        return Objects.equals(id, task1.id) && Objects.equals(date, task1.date) && Objects.equals(latinChars, task1.latinChars) && Objects.equals(russianChars, task1.russianChars) && Objects.equals(intNumber, task1.intNumber) && Objects.equals(floatNumber, task1.floatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, latinChars, russianChars, intNumber, floatNumber);
    }
}
