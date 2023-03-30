import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Установление соединения с базой данных
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Autos", "root", "352178000Kdi!");

            // Выборка всех клиентов
            String query = "SELECT * FROM Clients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                System.out.println(id + "\t" + name + "\t" + email + "\t" + phone);
            }

            // Добавление нового клиента, если его email не занят
            String insertQuery = "INSERT INTO Clients (name, email, phone) SELECT ?, ?, ? " +
                    "WHERE NOT EXISTS (SELECT * FROM Clients WHERE email = ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, "Anna Green");
            pstmt.setString(2, "anna.green@example.com");
            pstmt.setString(3, "555-123-4567");
            pstmt.setString(4, "anna.green@example.com");
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Клиент добавлен успешно");
            } else {
                System.out.println("Клиент с таким email уже существует");
            }

            // Обновление имени клиента с id=1
            String updateQuery = "UPDATE Clients SET name=? WHERE id=?";
            PreparedStatement pstmt2 = conn.prepareStatement(updateQuery);
            pstmt2.setString(1, "Jack White");
            pstmt2.setInt(2, 1);
            pstmt2.executeUpdate();

            // Удаление клиента с id=2
            String deleteQuery = "DELETE FROM Clients WHERE id=?";
            PreparedStatement pstmt3 = conn.prepareStatement(deleteQuery);
            pstmt3.setInt(1, 2);
            pstmt3.executeUpdate();

            // Закрытие всех соединений
            pstmt.close();
            pstmt2.close();
            pstmt3.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
