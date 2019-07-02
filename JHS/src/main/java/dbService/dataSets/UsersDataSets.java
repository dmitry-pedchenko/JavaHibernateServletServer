package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class UsersDataSets implements Serializable{

        private static final long serialVersionUID = -8706689714326132798L;

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "user_names", unique = false, updatable = true)
        private String user_names;

        @Column(name = "pass", unique = false, updatable = true)
        private String password;

        public UsersDataSets() {
        }

        public UsersDataSets(String user_names, String password) {
            this.id = -1;
            this.password = password;
            this.user_names = user_names;
        }

        public UsersDataSets(long id, String user_names, String password) {
            this.id = id;
            this.user_names = user_names;
            this.password = password;
        }

        public UsersDataSets(String user_names) {
            this.user_names = user_names;
            this.id = -1;
            this.password = user_names;
        }

        public String getUser_names() {
            return user_names;
        }

        public long getId() {
            return id;
        }

        public String getPassword() {
            return password;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setUser_names(String user_names) {
            this.user_names = user_names;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        @Override
        public String toString() {
            return "DataSet: " + "\n" +
                    "id: " + this.getId() + ";\n" +
                    "User name: " + this.getUser_names() + ";\n" +
                    "Password: " + this.getPassword() + ";\n";
        }

}